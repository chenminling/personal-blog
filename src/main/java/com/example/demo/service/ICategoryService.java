package com.example.demo.service;

import com.example.demo.model.domain.Category;
import com.github.pagehelper.PageInfo;

public interface ICategoryService {
    public PageInfo<Category> selectCategoryWithPage(Integer page, Integer count);

    public void updateCategoryWithId(Category category);
    public Category selectCategoryWithId(Integer id);


    public void publishCategory(Category category);
    //删除分类
    public void deleteCategoryWithId(int id);
    //分类的创建
    public void createCategory(Category category);
}
