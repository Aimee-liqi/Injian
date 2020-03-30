package com.injian.service.impl;


import com.injian.dataobject.UserDO;
import com.injian.error.BusinessException;
import com.injian.error.EmBusinessError;
import com.injian.service.ItemService;
import com.injian.service.UserService;
import com.injian.service.ValidateService;
import com.injian.service.model.ItemModel;
import com.injian.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateServiceImpl implements ValidateService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Override
    public void userAndItemValidate(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        ItemModel itemModel = itemService.getItemById(itemId);

        if (itemModel == null){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品不存在");
        }

        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户不存在");
        }
        if (amount <= 0 || amount >itemModel.getStock()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"数量信息不正确");
        }
        //校验活动信息
        if(promoId != null && promoId !=0 ){
            //1.校验对应活动是否存在这个适用商品
            if (promoId.intValue() !=itemModel.getPromoModel().getId() ){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }
            //2.校验活动是否正在进行中
            else if(itemModel.getPromoModel().getStatus() != 2){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确,活动还没开始");
            }
        }
    }


    @Override
    public void userValidate(Integer userId) throws BusinessException {
        if(userId == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户不存在");
        }
    }

    @Override
    public void itemValidate(Integer itemId) throws BusinessException {
        ItemModel itemModel = itemService.getItemById(itemId);

        if (itemModel == null){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品不存在");
        }
    }


//    @Override
//    public void itemValidate(Integer itemId) throws BusinessException {
//        ItemModel itemModel = itemService.getItemById(itemId);
//        if(itemModel == null){
//            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品不存在");
//        }
//    }
}
