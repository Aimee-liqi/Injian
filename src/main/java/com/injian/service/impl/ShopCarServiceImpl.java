package com.injian.service.impl;


import com.injian.error.BusinessException;
import com.injian.error.EmBusinessError;
import com.injian.service.ItemService;
import com.injian.service.ShopCarService;
import com.injian.service.ValidateService;
import com.injian.service.model.ItemModel;
import com.injian.service.model.ShopCarModel;
import com.injian.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ShopCarServiceImpl implements ShopCarService {

    @Autowired
    private ValidateService validateService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RedisUtils redisUtils;


    @Override
    public ShopCarModel addShopCar(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        //Redis存储购物车信息 使用Hash数据结构来存储，一个是外部键，一个是内部键。
        // 外部键可以标记一个唯一的购物车，内部键就可以标记购物车上的一个商品，一个外部键可以对应多个内部键，
        // 及一个购物车可以包含多个商品信息
        validateService.userAndItemValidate(userId,itemId,promoId,amount);
        //1.拼接购物车KEY
        String shopCarKey = this.generateShopCarKey(userId);
        //2.查看该用户是否存在购物车
        //Boolean hasCar = redisUtils.hHasKey(shopCarKey,String.valueOf(itemId));
        ShopCarModel shopCarModel = (ShopCarModel)redisUtils.hget(shopCarKey,String.valueOf(itemId));
        if(shopCarModel == null){
            //如果为空
            ItemModel itemModel = itemService.getItemById(itemId);
            //新建一个商品类
            shopCarModel = new ShopCarModel();
            shopCarModel.setItemName(itemModel.getTitle());
            shopCarModel.setImgUrl(itemModel.getImgUrl());
            shopCarModel.setItemId(itemId);
            shopCarModel.setUserId(userId);
            shopCarModel.setAmount(amount);
            //判断promoId是否为空，若为空则设置为0代表无活动
            if(promoId != null){
                shopCarModel.setPromoId(promoId);
            }
            //判断是否有活动，如果有活动  且 活动正在进行中 则把价格设置成活动价
            if(itemModel.getPromoModel() != null && itemModel.getPromoModel().getStatus() == 2){
                shopCarModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
            }else {
                shopCarModel.setItemPrice(itemModel.getPrice());
            }
            redisUtils.hset(shopCarKey,String.valueOf(itemId),shopCarModel);
        }else{
            //数量增加
            shopCarModel.setAmount(shopCarModel.getAmount() + amount);
            redisUtils.hset(shopCarKey,String.valueOf(itemId),shopCarModel);
        }

        return shopCarModel;
    }


    @Override
    public List<ShopCarModel> getShopCar(Integer userId) throws BusinessException {
       //验证用户是否存在
        validateService.userValidate(userId);
        String shopCarKey = this.generateShopCarKey(userId);
        //用户是否存在购物车
        boolean isHasKey = redisUtils.hasKey(shopCarKey);
        List<ShopCarModel> shopCarList = new ArrayList<>();
        if( !isHasKey ){
            throw new BusinessException(EmBusinessError.SHOPCAR_NOT_EXIST);
        }else{
            //获取用户购物车存在的所有商品id
            Set<Object> itemList = redisUtils.hgetAllItems(generateShopCarKey(userId));
            if(itemList == null){
                throw new BusinessException(EmBusinessError.SHOPCAR_IS_EMPTY);
            }else{
                for(Object itemId : itemList){
                    ItemModel itemModel = itemService.getItemById(Integer.parseInt((String) itemId));
                    ShopCarModel shopCarModel = (ShopCarModel)redisUtils.hget(shopCarKey, (String) itemId);
                    //校验活动更新价格
                    if(itemModel.getPromoModel() != null && itemModel.getPromoModel().getStatus() != 2){
                        shopCarModel.setItemPrice(itemModel.getPrice());
                        redisUtils.hset(shopCarKey,(String) itemId,shopCarModel);
                    }
                    shopCarList.add(shopCarModel);
                }
            }
        }
        return shopCarList;
    }

    @Override
    public void delItem(Integer userId, Integer itemId) throws BusinessException {
        validateService.userValidate(userId);
        validateService.itemValidate(itemId);
        String shopCarKey = generateShopCarKey(userId);
        redisUtils.hdel(shopCarKey,String.valueOf(itemId));

    }


    @Transactional
    @Override
    public void updateAmount(Integer userId,Integer itemId,Integer amount) throws BusinessException {
        //shopCarDOMapper.increaseAmount(id,amount);
        validateService.userValidate(userId);
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品不存在");
        }
        if(amount < 0){
            throw new  BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品数量不能小于零");
        }
        if(itemModel.getStock() < amount){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH,"已超过库存数量");
        }
        String shopCarKey = generateShopCarKey(userId);
        ShopCarModel shopCarModel = (ShopCarModel)redisUtils.hget(shopCarKey,String.valueOf(itemId));
        shopCarModel.setAmount(amount);
        redisUtils.hset(shopCarKey,String.valueOf(itemId),shopCarModel);
    }



    private String generateShopCarKey(Integer userId){
        String shopCarKey = "shopCar_" + userId;
        return shopCarKey;
    }



}
