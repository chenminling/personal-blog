package com.example.demo.service;

import com.example.demo.model.domain.Comment;
import com.github.pagehelper.PageInfo;

/**
 * @Classname ICommentService
 * @Description 文章评论业务处理接口
 * @Date 2019-3-14 10:13
 * @Created by CrazyStone
 */
public interface ICommentService {
    // 获取文章下的评论
    public PageInfo<Comment> getComments(Integer aid, int page, int count);

    // 用户发表评论
    public void pushComment(Comment comment);
    public PageInfo<Comment> getAllComments(Integer aid,int page,int count);

    public void updateComment(Integer id);
    public void updatenoComment(Integer id);
    public void deleteCommentId(Integer id);



}

