package com.injian.service;


import com.injian.error.BusinessException;
import com.injian.service.model.AddressModel;

import java.util.List;


public interface AddressService {
    void addAddress(AddressModel AddressModel) throws BusinessException;
    AddressModel getAddressInfoById(Integer id);
    List<AddressModel> listAddress(Integer userId) throws BusinessException;
    void delAddress(Integer userId, Integer addressId) throws BusinessException;
    void updateAddress(Integer userId, AddressModel addressModel) throws BusinessException;
}
