package com.injian.controller;



import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.injian.controller.viewobject.AddressVO;
import com.injian.controller.viewobject.OrderVO;
import com.injian.controller.viewobject.ShopCarProductVO;
import com.injian.error.BusinessException;
import com.injian.error.EmBusinessError;
import com.injian.response.CommonReturnType;
import com.injian.service.OrderService;
import com.injian.service.model.OrderModel;
import com.injian.service.model.ShopCarModel;
import com.injian.service.model.UserModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户直接从下单请求
    @RequestMapping(value = "/createorder",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId")Integer itemId,
                                        @RequestParam(name = "promoId",required = false)Integer promoId,
                                        @RequestParam(name = "amount")Integer amount) throws BusinessException {

        //判断用户是否登录
        UserModel userModel = validateUserLogin();
        //下单
        OrderModel orderModel = orderService.createOrder(userModel.getId(),itemId,promoId,amount);
        return CommonReturnType.create(orderModel);
    }

    //用户从购物车下单请求
    @RequestMapping(value = "/createshopCarorder",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType shopCarOrder(@RequestParam(name = "orderItemList")String  orderItemList) throws BusinessException, IOException {
        if(orderItemList == null ||  orderItemList.equals("")) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"请选择商品或无下单商品");
        }
        UserModel userModel = validateUserLogin();
        //jackson对象
        ObjectMapper mapper = new ObjectMapper();
        //使用jackson将json转为List
        JavaType jt = mapper.getTypeFactory().constructParametricType(List.class, ShopCarProductVO.class);
        List<ShopCarProductVO> VOList =  mapper.readValue(orderItemList, jt);
        //将VO转成Model
        List<ShopCarModel> shopCarModelList = VOList.stream().map(shopCarProductVO -> {
            ShopCarModel shopCarModel = this.convertModelFromVO(shopCarProductVO);
            return shopCarModel;
        }).collect(Collectors.toList());

        //下单
        List<OrderModel> orderModelList = orderService.createOrderShopCar(userModel.getId(),shopCarModelList);
        return CommonReturnType.create(orderModelList);

    }

    //订单确认
    @RequestMapping(value = "/confirmOrder",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType confirmOrder(@RequestParam(name = "orderIdList") String orderIdList) throws BusinessException {
        UserModel userModel = validateUserLogin();
        if(orderIdList == null || orderIdList.equals("")){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        List<OrderModel> orderModelList = orderService.showOrderForConfirm(userModel.getId(),orderIdList);
        List<OrderVO> orderVOList = convertVOListFromModel(orderModelList);
        return CommonReturnType.create(orderVOList);

    }

    @RequestMapping(value = "/orderAddress",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType orderAddress(@RequestParam(name = "addressId") Integer addressId,
                                         @RequestParam(name = "orderIdList")String orderList) throws BusinessException {
        UserModel userModel = validateUserLogin();
        if(addressId == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        orderService.addOrderAddress(userModel.getId(),addressId,orderList);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/showOrder",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType showOrder() throws BusinessException {
        UserModel userModel = validateUserLogin();
        List<OrderModel> orderModelList = orderService.getOrderByUserId(userModel.getId());
        List<OrderVO> orderVOList = convertVOListFromModel(orderModelList);
        return CommonReturnType.create(orderVOList);
    }

    @RequestMapping(value = "/delOrder",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType delOrder(@RequestParam(name = "orderId") String orderId) throws BusinessException {
        UserModel userModel = validateUserLogin();
        orderService.delOrder(orderId);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/searchOrder",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType searchOrder(@RequestParam(name = "keyword") String keyword,
                                        @RequestParam(name = "orderStatus") Integer orderStatus,
                                        @RequestParam(name = "orderId") String orderId ) throws BusinessException {
        UserModel userModel = validateUserLogin();
        List<OrderModel> searchOrderList = orderService.searchOrder(keyword,orderStatus,orderId,userModel.getId());
        List<OrderVO> searchOrderVOList = this.convertVOListFromModel(searchOrderList);
        return CommonReturnType.create(searchOrderVOList);
    }

    @RequestMapping(value = "/updateOrder",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType updateOrder(@RequestParam(name = "orderId")String orderId) throws BusinessException {
        UserModel userModel = validateUserLogin();
        orderService.updateOrderStatus(orderId,3);
        return CommonReturnType.create(null);
    }




    private ShopCarModel convertModelFromVO(ShopCarProductVO shopCarProductVO){
        if(shopCarProductVO == null){
            return null;
        }
        ShopCarModel shopCarModel = new ShopCarModel();
        BeanUtils.copyProperties(shopCarProductVO,shopCarModel);
        return shopCarModel;
    }

    private OrderVO convertOrderVOFrmoMoedel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orderModel,orderVO);
        orderVO.setOrderTime(orderModel.getOrderTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        if(orderModel.getAddressModel() != null){
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(orderModel.getAddressModel(),addressVO);
            orderVO.setAddressVO(addressVO);
        }
        return orderVO;
    }

    private List<OrderVO> convertVOListFromModel(List<OrderModel> orderModelList){
        List<OrderVO> orderVOList = orderModelList.stream().map(orderModel -> {
            OrderVO orderVO = this.convertOrderVOFrmoMoedel(orderModel);
            return orderVO;
        }).collect(Collectors.toList());
        return orderVOList;
    }

}
