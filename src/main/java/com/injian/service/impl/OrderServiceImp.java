package com.injian.service.impl;


import com.injian.dao.OrderAddressStatusDOMapper;
import com.injian.dao.OrderDOMapper;
import com.injian.dao.SequenceDOMapper;
import com.injian.dataobject.OrderAddressStatusDO;
import com.injian.dataobject.OrderDO;
import com.injian.dataobject.SequenceDO;
import com.injian.error.BusinessException;
import com.injian.error.EmBusinessError;
import com.injian.service.AddressService;
import com.injian.service.ItemService;
import com.injian.service.OrderService;
import com.injian.service.ValidateService;
import com.injian.service.model.*;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ValidateService validateService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Autowired
    private OrderAddressStatusDOMapper addressStatusDOMapper;

    //private List<OrderModel> orderModelConfrimList = new ArrayList<>();

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId , Integer amount) throws BusinessException {
        //校验下单状态，下单产品是否存在，用户是否合法，购买数量是否正确
        //验证
       validateService.userAndItemValidate(userId,itemId,promoId,amount);
        //落单减库存
        boolean result = itemService.decreaseStock(itemId,amount);
        if(!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        //订单入库
        ItemModel itemModel = itemService.getItemById(itemId);
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if (promoId != null){//若有活动，则改成促销价格
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setPromoId(promoId);
        //订单总价
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));
        //获取当前时间
        orderModel.setOrderTime(new DateTime());
        //生成及交易流水号
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        //销量增加
        itemService.increaseSales(itemId,amount);
        //返回前端
        return orderModel;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<OrderModel> createOrderShopCar(Integer userId,List<ShopCarModel> shopCarModelList) throws BusinessException {

        List<OrderModel> orderModellist = new ArrayList<>();
        for(ShopCarModel shopCarModel : shopCarModelList){
            OrderModel orderModel = this.createOrder(userId,shopCarModel.getItemId(),shopCarModel.getPromoId(),shopCarModel.getAmount());
            orderModellist.add(orderModel);
        }
        //返回前端用于展示刚刚下的订单内容
        return orderModellist;
    }


    @Override
    public List<OrderModel> showOrderForConfirm(Integer userId,String orderIdList) throws BusinessException {
        validateService.userValidate(userId);
        if(orderIdList == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        List<OrderModel> orderModelConfrimList = new ArrayList<>();
        //处理参数按照空格分割字符串得到每个id
        String [] spString = orderIdList.split("\\s+");
        for(String orderId : spString){
            OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);
            ItemModel itemModel = itemService.getItemById(orderDO.getItemId());
            OrderModel orderModel = this.convertFromOrderDO(orderDO,itemModel,null);
            orderModelConfrimList.add(orderModel);
        }
        return orderModelConfrimList;
    }

    @Override
    @Transactional
    public void addOrderAddress(Integer userId, Integer addressId,String orderIdList) throws BusinessException {
        validateService.userValidate(userId);
        if (addressId == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"请重新选择地址");
        }
        String [] spString = orderIdList.split("\\s+");
        for(String orderId : spString){
            OrderAddressStatusDO orderAddressStatusDO = new OrderAddressStatusDO();
            orderAddressStatusDO.setOrderId(orderId);
            orderAddressStatusDO.setOrderAddressId(addressId);
            orderAddressStatusDO.setOrderStatus(0);//未支付订单
            addressStatusDOMapper.insertSelective(orderAddressStatusDO);
        }

    }

    @Override
    public List<OrderModel> getOrderByUserId(Integer userId) throws BusinessException {
        validateService.userValidate(userId);
       List<OrderDO> orderDOList= orderDOMapper.selectByUserId(userId);
       if (orderDOList != null){
           List<OrderModel> orderModelList = orderDOList.stream().map(orderDO -> {
               //得到商品信息
               ItemModel itemModel = itemService.getItemById(orderDO.getItemId());
               OrderModel orderModel = new OrderModel();
               //获得订单地址信息
               OrderAddressStatusDO orderAddressStatusDO = addressStatusDOMapper.selectByOrderId(orderDO.getId());
               if(orderAddressStatusDO != null ){
                   AddressModel addressModel = addressService.getAddressInfoById(orderAddressStatusDO.getOrderAddressId());
                   orderModel = this.convertFromOrderDO(orderDO,itemModel,orderAddressStatusDO);
                   orderModel.setAddressModel(addressModel);
               }else{
                   orderModel = this.convertFromOrderDO(orderDO,itemModel,null);
               }
               return orderModel;
           }).collect(Collectors.toList());
           return orderModelList;
       }else{
           return null;
        }
    }

    @Override
    @Transactional
    public void delOrder(String orderId) throws BusinessException {
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);
        if (orderDO == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        addressStatusDOMapper.deleteByOrderId(orderId);
        orderDOMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public List<OrderModel> searchOrder(String keyword, Integer orderStatus, String orderId,Integer userId) throws BusinessException {
        List<OrderModel> userOrderList = this.getOrderByUserId(userId);
        List<OrderModel> searchOrderList = new ArrayList<>();
        for(OrderModel orderModel : userOrderList){
            if(!orderId.equals("")){
                if(orderModel.getId().equals(orderId)){
                    searchOrderList.add(orderModel);
                    break;
                }
            }else if(keyword.equals("") && orderStatus !=null){
                if(orderModel.getOrderStatus() != null && orderModel.getOrderStatus().intValue() == orderStatus.intValue()){
                    searchOrderList.add(orderModel);
                }
            }else if(!keyword.equals("") && orderStatus == null){
                if( orderModel.getItemName().contains(keyword)){
                    searchOrderList.add(orderModel);
                }
            }else if(!keyword.equals("") && orderStatus != null){
                if(orderModel.getOrderStatus() != null
                    && orderModel.getOrderStatus().intValue() == orderStatus.intValue()
                    && orderModel.getItemName().contains(keyword)){
                        searchOrderList.add(orderModel);
                }
            }else{
                searchOrderList.add(orderModel);
            }
        }
        return searchOrderList;
    }

    @Override
    @Transactional
    public void updateOrderStatus(String orderId, Integer orderStatus) throws BusinessException {
        //校验订单是否存在
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);
        //校验订单是否选址
        OrderAddressStatusDO orderAddressStatusDO = addressStatusDOMapper.selectByOrderId(orderId);
        if(orderDO == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"该订单不存在");
        }else if(orderAddressStatusDO == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"该订单地址信息不正确");
        }
        orderAddressStatusDO.setOrderStatus(orderStatus);
        addressStatusDOMapper.updateStatusByOrderId(orderId,orderStatus);
    }


    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        orderDO.setOrderTime(orderModel.getOrderTime().toDate());
        return orderDO;
    }

    private OrderModel convertFromOrderDO(OrderDO orderDO, ItemModel itemModel,OrderAddressStatusDO orderAddressStatusDO){
        if(orderDO == null){
            return null;
        }
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDO,orderModel);
        orderModel.setItemPrice(new BigDecimal(orderDO.getItemPrice()));
        orderModel.setOrderPrice(new BigDecimal(orderDO.getOrderPrice()));
        orderModel.setOrderTime(new DateTime(orderDO.getOrderTime()));
        if(itemModel != null){
            orderModel.setImgUrl(itemModel.getImgUrl());
            orderModel.setItemName(itemModel.getTitle());
        }
        if(orderAddressStatusDO != null){
            orderModel.setAddressId(orderAddressStatusDO.getOrderAddressId());
            orderModel.setOrderStatus(orderAddressStatusDO.getOrderStatus());
        }
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)//重新开启事务
    private String generateOrderNo(){
        //16位
        StringBuilder stringBuilder = new StringBuilder();
        //前8位为时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);
        //中间六位为自增序列
        //获取当前seqence
        //有可能存在以下问题：1.没有设置最大值，解决办法：在表里设置初始值和最大值，当前值超过最大值的时候再初始化
        //调取该方法的方法内事务回滚，会生成相同的订单号失去全局唯一性
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        //得到数据之后更新表，现在加步长
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        //凑够六位.用0填充
        for (int i = 0;i< 6-sequenceStr.length();i++ ){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);
        //后两位为分库分表位
        stringBuilder.append("00");
        return stringBuilder.toString();
    }
}
