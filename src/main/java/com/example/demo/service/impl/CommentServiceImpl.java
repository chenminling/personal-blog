package com.example.demo.service.impl;

import com.example.demo.dao.CommentMapper;
import com.example.demo.dao.StatisticMapper;
import com.example.demo.model.domain.Comment;
import com.example.demo.model.domain.Statistic;
import com.example.demo.service.ICommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * @Classname CommentServiceImpl
 * @Description TODO
 * @Date 2019-3-14 10:15
 * @Created by CrazyStone
 */

@Service
@Transactional
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private StatisticMapper statisticMapper;

    // 根据文章id分页查询评论
    @Override
    public PageInfo<Comment> getComments(Integer aid, int page, int count) {
        PageHelper.startPage(page,count);
        List<Comment> commentList = commentMapper.selectCommentWithPage(aid);
        PageInfo<Comment> commentInfo = new PageInfo<>(commentList);
        return commentInfo;
    }

    @Override
    public PageInfo<Comment> getAllComments(Integer aid, int page, int count) {
        PageHelper.startPage(page,count);
        List<Comment> commentList = commentMapper.selectAllComment(aid);
        PageInfo<Comment> commentInfo = new PageInfo<>(commentList);
        return commentInfo;
    }
    // 用户发表评论
    @Override
    public void pushComment(Comment comment){
        commentMapper.pushComment(comment);
        // 更新文章评论数据量
        Statistic statistic = statisticMapper.selectStatisticWithArticleId(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum()+1);
        statisticMapper.updateArticleCommentsWithId(statistic);
    }
    @Override
    public void updateComment(Integer id){
        commentMapper.updateComment(id);
    }
    @Override
    public void updatenoComment(Integer id){
        commentMapper.updatenoComment(id);
    }

    @Override
    public void deleteCommentId(Integer id){
        commentMapper.deleteCommentId(id);


    }


}

