package com.example.demo.service.impl;

import com.example.demo.dao.CommentMapper;
import com.example.demo.dao.StatisticMapper;
import com.example.demo.dao.TagsMapper;
import com.example.demo.model.domain.Tags;
import com.example.demo.service.ITagsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagsServiceImpl implements ITagsService {
    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private CommentMapper commentMapper;



    @Override
    public PageInfo<Tags> selectTagsWithPage(Integer page, Integer count){
        PageHelper.startPage(page,count);
//        List<Tags> tagsList = tagsMapper.selectTagsWithPage();

        List<Tags> tagsList = tagsMapper.selectTagsWithPage();
        PageInfo<Tags> tagsInfo = new PageInfo<>(tagsList);
        return tagsInfo;

    }

    @Override
    public void deleteTagsWithId(int id) {
        // 删除文章的同时，删除对应的缓存
        tagsMapper.deleteTagsWithId(id);
//
//        redisTemplate.delete("article_" + id);
//        // 同时删除对应文章的统计数据
//        statisticMapper.deleteStatisticWithId(id);
//        // 同时删除对应文章的评论数据
//        commentMapper.deleteCommentWithId(id);
    }
    @Override
    public void createTags(Tags tags) {
//        tagMapper.createTag(tag);
        tagsMapper.createTags(tags);
//        tagsMapper.createTags()
//        categoryMapper.createCategory(tags);

    }
}
