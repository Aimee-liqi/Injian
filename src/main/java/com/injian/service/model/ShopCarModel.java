package com.injian.service.model;

import java.math.BigDecimal;

public class ShopCarModel {

    private Integer shopcarId;

    private String itemName;

    private String imgUrl;

    private Integer userId;

    private  Integer itemId;

    private Integer promoId;

    private BigDecimal itemPrice;
    //购买数量
    private Integer amount;

    public Integer getShopcarId() {
        return shopcarId;
    }

    public void setShopcarId(Integer shopcarId) {
        this.shopcarId = shopcarId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


}
