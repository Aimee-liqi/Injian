package com.injian.service.impl;

import com.injian.dao.ItemDOMapper;
import com.injian.dao.ItemStockDOMapper;
import com.injian.dataobject.ItemDO;
import com.injian.dataobject.ItemStockDO;
import com.injian.error.BusinessException;
import com.injian.error.EmBusinessError;
import com.injian.service.ItemService;
import com.injian.service.PromoService;
import com.injian.service.model.ItemModel;
import com.injian.service.model.PromoModel;
import com.injian.validator.ValidationResult;
import com.injian.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Autowired
    private PromoService promoService;

    private ItemDO convertItemDOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel)
    {
        if (itemModel == null){
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrorMsg());
        }
        //转化itemModel变为dataobject
        ItemDO itemDO = this.convertItemDOFromItemModel(itemModel);
        //写入数据库
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());
        ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
        //返回创建的对象
        return this.getItemById(itemModel.getId());
    }

    @Override//查询所有的商品信息，按照销量排序
    public List<ItemModel> listItem(Integer categoryId) {
        List<ItemDO> itemDOList = itemDOMapper.listItem(categoryId);
        return this.convertModelListFromDO(itemDOList);
    }

    @Override
    public List<ItemModel> searchItem(String keyword) {
        List<ItemDO> itemDOList = itemDOMapper.selectByKeyword(keyword);
        return this.convertModelListFromDO(itemDOList);
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO == null){
            return null;
        }
        //操作获得库存数量
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
        //将dataobject转化为model
        ItemModel itemModel = convertModelFromDataObject(itemDO,itemStockDO);
        //看商品是否有活动并获得商品活动信息
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        //活动存在并且状态是未开始或者进行中的，设置秒杀活动在商品信息上
        if (promoModel != null && promoModel.getStatus().intValue()!=3){
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) {
        //影响的条目数，若失败则返回结果为0
        int affectedRow = itemStockDOMapper.decreaseStock(itemId,amount);
        if(affectedRow > 0){
            //更新库存失败
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemDOMapper. increaseSales(itemId,amount);
    }

    @Override
    public List<ItemModel> promoItem() {
      List<ItemModel> promoItemList = new ArrayList<>();
      List<ItemModel> itemList = this.listItem(null);
      for(ItemModel itemModel : itemList){
          if(itemModel.getPromoModel() != null){
              promoItemList.add(itemModel);
          }
      }
      return promoItemList;
    }

    private ItemModel convertModelFromDataObject(ItemDO itemDO,ItemStockDO itemStockDO)
    {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }

    private List<ItemModel> convertModelListFromDO(List<ItemDO> itemDOList){
        List<ItemModel> itemModelList = itemDOList.stream().map(itemDO -> {
//            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
//            ItemModel itemModel = this.convertModelFromDataObject(itemDO,itemStockDO);
//            PromoModel promoModel = promoService.getPromoByItemId(itemDO.getId());
//            if (promoModel != null && promoModel.getStatus().intValue()!=3){
//                itemModel.setPromoModel(promoModel);
//            }
            ItemModel itemModel = this.getItemById(itemDO.getId());
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }
}
