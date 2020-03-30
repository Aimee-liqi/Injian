package com.injian.controller;


import com.injian.controller.viewobject.ItemVO;
import com.injian.error.BusinessException;
import com.injian.response.CommonReturnType;
import com.injian.service.ItemService;
import com.injian.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller("item")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    //创建商品
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title") String title,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl") String imgUrl,
                                       @RequestParam(name = "category") Integer category) throws BusinessException {
        //封装service请求创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        itemModel.setCategoryId(category);

        ItemModel itemModelForReturn = itemService.createItem(itemModel);

        ItemVO itemVO = convertVOFromModel(itemModelForReturn);
        return CommonReturnType.create(itemVO);
    }

    //商品详情页浏览
    @RequestMapping(value = "/get",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id") Integer id)
    {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertVOFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }

    //商品列表浏览页面
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem(@RequestParam(name = "categoryId") Integer categoryId){
        List<ItemModel> itemModelList = itemService.listItem(categoryId);
        //将List内的model转化成itemVO并放进一个list里面
        List<ItemVO> itemVOList = this.convertVOListFromModel(itemModelList);
        return CommonReturnType.create(itemVOList);
    }
    //搜索商品
    @RequestMapping(value = "/search",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType searchItem(@RequestParam(name = "keyWord")String keyWord){
        List<ItemModel> itemModelList = itemService.searchItem(keyWord);
        List<ItemVO> itemVOList = this.convertVOListFromModel(itemModelList);
        return CommonReturnType.create(itemVOList);
    }
    //活动商品
    @RequestMapping(value = "/promo",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType promoItem(){
        List<ItemModel> itemModelList = itemService.promoItem();
        List<ItemVO> itemVOList = this.convertVOListFromModel(itemModelList);
        return CommonReturnType.create(itemVOList);
    }




    private ItemVO convertVOFromModel(ItemModel itemModel){
        if(itemModel == null)
        {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        if(itemModel.getPromoModel()!=null){
            //有正在进行或者即将进行的秒杀活动
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else {
            itemVO.setPromoStatus(0);
        }

        return itemVO;
    }

    private List<ItemVO> convertVOListFromModel(List<ItemModel> itemModelList){
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return itemVOList;
    }
}
