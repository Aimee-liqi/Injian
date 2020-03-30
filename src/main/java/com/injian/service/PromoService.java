package com.injian.service;


import com.injian.service.model.PromoModel;

public interface PromoService {
    //获取商品活动
    PromoModel getPromoByItemId(Integer itemId);

}
