package com.example.demo.service.impl;

import com.example.demo.dao.CategoryMapper;
import com.example.demo.dao.CommentMapper;
import com.example.demo.dao.StatisticMapper;
import com.example.demo.model.domain.Category;
import com.example.demo.service.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public PageInfo<Category> selectCategoryWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);
//
        List<Category> categoryList = categoryMapper.selectCategoryWithPage();
        PageInfo<Category> categoryInfo = new PageInfo<>(categoryList);
        return categoryInfo;
//
    }

//     发布文章
    @Override
    public void publishCategory(Category category) {
        categoryMapper.publishCategory(category);
        // 插入文章，同时插入文章统计数据
//        statisticMapper.addStatisticCategory(category);
//        categoryMapper.publishCategory(category);
//        statisticMapper.addStatistic(article);
    }

    //更新分类
    public void updateCategoryWithId(Category category){
        categoryMapper.updateCategoryWithId(category);
        redisTemplate.delete("category_" +category.getId());

    }
    //

    public Category selectCategoryWithId(Integer id){
        Category category =null;
        Object o = redisTemplate.opsForValue().get("category_" + id);
        categoryMapper.selectCategoryWithId(id);
//        if (o!=null){
//            category=(Category)o;
//        }else {
//            category = categoryMapper.selectCategoryWithId(id);
//            if (category!=null){
//                redisTemplate.opsForValue().set("category_" +id,category);
//            }
//        }
        return category;

    }

    @Override
    public void deleteCategoryWithId(int id) {
        // 删除文章的同时，删除对应的缓存
        categoryMapper.deleteCategoryWithId(id);
//        articleMapper.deleteArticleWithId(id);
//        redisTemplate.delete("article_" + id);
        // 同时删除对应文章的统计数据
//        statisticMapper.deleteStatisticWithId(id);
        // 同时删除对应文章的评论数据
//        commentMapper.deleteCommentWithId(id);
    }
    @Override
    public void createCategory(Category category) {
//        tagMapper.createTag(tag);
        categoryMapper.createCategory(category);

    }



}
