package com.example.demo.service;

import com.example.demo.model.domain.Tags;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;


public interface ITagsService {
    public PageInfo<Tags> selectTagsWithPage(Integer page, Integer count);


    //删除分类
    public void deleteTagsWithId(int id);
    //分类的创建
    public void createTags(Tags tags);
}
