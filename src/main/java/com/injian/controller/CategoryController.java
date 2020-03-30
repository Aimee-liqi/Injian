package com.injian.controller;

import com.injian.response.CommonReturnType;
import com.injian.service.CategoryService;
import com.injian.service.model.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("category")
@RequestMapping("/category")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/getCategoryList",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType categoryList(){
        CategoryModel categoryModel = categoryService.getCategoryList();
        return CommonReturnType.create(categoryModel);
    }
}
