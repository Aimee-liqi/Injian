package com.injian.service.model;

import java.util.List;

public class CategoryModel {
    private Integer id;
    private String name;
    private Integer parentId;
    private List<CategoryModel> subCategorylist;//子分类list

    public CategoryModel(){}

    public CategoryModel(Integer id, String name, Integer parentId){
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public List<CategoryModel> getSubCategorylist() {
        return subCategorylist;
    }

    public void setSubCategorylist(List<CategoryModel> subCategorylist) {
        this.subCategorylist = subCategorylist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
