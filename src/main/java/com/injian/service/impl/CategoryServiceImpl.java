package com.injian.service.impl;

import com.injian.dao.CategoryDOMapper;
import com.injian.dataobject.CategoryDO;
import com.injian.service.CategoryService;
import com.injian.service.model.CategoryModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDOMapper categoryDOMapper;
    @Override
    public CategoryModel getCategoryList() {
        List<CategoryDO> categoryDOList = categoryDOMapper.getCategoryList();
        //得到分类list
        List<CategoryModel> categoryModelList = categoryDOList.stream().map(categoryDO -> {
            CategoryModel categoryModel = this.convertFromCategoryDO(categoryDO);
            return categoryModel;
        }).collect(Collectors.toList());
        //遍历list得到分类树
        CategoryModel categoryTree = this.createCategoryTree(categoryModelList);
        return categoryTree;
    }

    private CategoryModel createCategoryTree(List<CategoryModel> categoryModelList){
        //根据父节点id分组
        Map<Integer, List<CategoryModel>> map = categoryModelList.stream()
                .filter(o -> Objects.nonNull(o.getParentId()))
                .collect(Collectors.groupingBy(CategoryModel::getParentId));
        //循环处理子节点 构建树状结构
        categoryModelList.forEach(categoryModel -> {
            if (map.containsKey(categoryModel.getId())) {
                categoryModel.setSubCategorylist(map.get(categoryModel.getId()));
            }
        });
        //获取指定节点的对象
        CategoryModel categoryTree = categoryModelList.stream()
                .filter(categoryModel -> categoryModel.getId() == 1)
                .findFirst().orElse(null);
        return categoryTree;
    }

    private CategoryModel convertFromCategoryDO(CategoryDO categoryDO){
        if(categoryDO == null){
            return null;
        }
        CategoryModel categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryDO,categoryModel);
        return categoryModel;
    }
}
