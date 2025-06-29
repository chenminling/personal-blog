//package com.example.demo.service.impl;
//
//import com.example.demo.dao.ArticleMapper;
//import com.example.demo.dao.CommentMapper;
//import com.example.demo.dao.StatisticMapper;
//import com.example.demo.model.ResponseData.StaticticsBo;
//import com.example.demo.model.domain.Article;
//import com.example.demo.model.domain.Comment;
//import com.example.demo.model.domain.Statistic;
//import com.example.demo.service.ISiteService;
//import com.github.pagehelper.PageHelper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * @Classname SiteServiceImpl
// * @Description TODO
// * @Date 2019-3-14 10:15
// * @Created by CrazyStone
// */
//@Service
//@Transactional
//public class SiteServiceImpl implements ISiteService {
//    @Autowired
//    private CommentMapper commentMapper;
//    @Autowired
//    private ArticleMapper articleMapper;
//    @Autowired
//    private StatisticMapper statisticMapper;
//
//    @Override
//    public void updateStatistics(Article article) {
//        Statistic statistic = statisticMapper.selectStatisticWithArticleId(article.getId());
//        statistic.setHits(statistic.getHits()+1);
//        statisticMapper.updateArticleHitsWithId(statistic);
//    }
//
//    @Override
//    public List<Comment> recentComments(int limit) {
//        PageHelper.startPage(1, limit>10 || limit<1 ? 10:limit);
//        List<Comment> byPage = commentMapper.selectNewComment();
//        return byPage;
//    }
//
////    @Override
////    public List<Article> recentArticles(int limit) {
////        PageHelper.startPage(1, limit>10 || limit<1 ? 10:limit);
////        List<Article> list = articleMapper.selectArticleWithPage();
////        // 封装文章统计数据
////        for (int i = 0; i < list.size(); i++) {
////            Article article = list.get(i);
////            Statistic statistic = statisticMapper.selectStatisticWithArticleId(article.getId());
////            article.setHits(statistic.getHits());
////            article.setCommentsNum(statistic.getCommentsNum());
////        }
////        return list;
////    }
//
//    @Override
//    public List<Article> recentArticles(int limit) {
//        // 设置分页参数
//        PageHelper.startPage(1, limit > 10 || limit < 1 ? 10 : limit);
//        List<Article> list = articleMapper.selectArticleWithPage();
//
//        // 封装文章统计数据
//        for (int i = 0; i < list.size(); i++) {
//            Article article = list.get(i);
//            if (article == null) {
//                // 处理文章对象为 null 的情况
//                continue; // 或者抛出异常，根据业务需求决定
//            }
//
//            Statistic statistic = statisticMapper.selectStatisticWithArticleId(article.getId());
//            if (statistic == null) {
//                // 处理统计数据对象为 null 的情况
//                // 例如，可以设置默认值或抛出异常
//                article.setHits(0);
//                article.setCommentsNum(0);
//            } else {
//                article.setHits(statistic.getHits());
//                article.setCommentsNum(statistic.getCommentsNum());
//            }
//        }
//        return list;
//    }
//
//    @Override
//    public StaticticsBo getStatistics() {
//        StaticticsBo staticticsBo = new StaticticsBo();
//        Integer articles = articleMapper.countArticle();
//        Integer comments = commentMapper.countComment();
//        staticticsBo.setArticles(articles);
//        staticticsBo.setComments(comments);
//        return staticticsBo;
//    }
//}
//


package com.example.demo.service.impl;

import com.example.demo.dao.ArticleMapper;
import com.example.demo.dao.CommentMapper;
import com.example.demo.dao.StatisticMapper;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ResponseData.StaticticsBo;
import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Comment;
import com.example.demo.model.domain.Statistic;
import com.example.demo.service.ISiteService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SiteServiceImpl implements ISiteService {
    private static final int DEFAULT_RECENT_LIMIT = 10;
    private static final int MAX_RECENT_LIMIT = 20;

    private final CommentMapper commentMapper;
    private final ArticleMapper articleMapper;
    private final StatisticMapper statisticMapper;

    // 使用构造函数注入（推荐）
    @Autowired
    public SiteServiceImpl(CommentMapper commentMapper,
                           ArticleMapper articleMapper,
                           StatisticMapper statisticMapper) {
        this.commentMapper = commentMapper;
        this.articleMapper = articleMapper;
        this.statisticMapper = statisticMapper;
    }

    @Override
    public void updateStatistics(Article article) {
        // 参数校验
        if (article == null || article.getId() == null) {
            throw new IllegalArgumentException("Article or article ID cannot be null");
        }

        // 获取或创建统计记录
        Statistic statistic = Optional.ofNullable(
                statisticMapper.selectStatisticWithArticleId(article.getId())
        ).orElseGet(() -> {
            Statistic newStat = new Statistic();
            newStat.setArticleId(article.getId());
            newStat.setHits(0);
            newStat.setCommentsNum(0);
            statisticMapper.insertStatistic(newStat);
            return newStat;
        });

        // 更新点击量
        statistic.setHits(statistic.getHits() + 1);
        statisticMapper.updateArticleHitsWithId(statistic);
    }

    @Override
    public List<Comment> recentComments(int limit) {
        int safeLimit = validateLimit(limit);
        PageHelper.startPage(1, safeLimit);
        return commentMapper.selectNewComment();
    }

    @Override
    public List<Article> recentArticles(int limit) {
        int safeLimit = validateLimit(limit);
        PageHelper.startPage(1, safeLimit);
        List<Article> articles = articleMapper.selectArticleWithPage();

        // 为每篇文章设置统计数据
        articles.forEach(article -> {
            Statistic statistic = statisticMapper.selectStatisticWithArticleId(article.getId());
            if (statistic != null) {
                article.setHits(statistic.getHits());
                article.setCommentsNum(statistic.getCommentsNum());
            } else {
                // 记录警告或初始化统计
                article.setHits(0);
                article.setCommentsNum(0);
            }
        });

        return articles;
    }

//    @Override
//    public StaticticsBo getStatistics() {
//        Integer articles = articleMapper.countArticle();
//        Integer comments = commentMapper.countComment();
//
//        // 确保不为null
//        articles = articles != null ? articles : 0;
//        comments = comments != null ? comments : 0;
//
//        return new StaticticsBo(articles, comments);
//    }
    @Override
    public StaticticsBo getStatistics() {
        StaticticsBo staticticsBo = new StaticticsBo();

        Integer articles = articleMapper.countArticle();
        Integer comments = commentMapper.countComment();

        // 处理可能的空值
        articles = articles != null ? articles : 0;
        comments = comments != null ? comments : 0;

        // 使用 setter 方法而不是构造函数
        staticticsBo.setArticles(articles);
        staticticsBo.setComments(comments);

        return staticticsBo;
    }

    /**
     * 验证并返回安全的limit值
     */
    private int validateLimit(int limit) {
        if (limit < 1) {
            return DEFAULT_RECENT_LIMIT;
        }
        return Math.min(limit, MAX_RECENT_LIMIT);
    }

    /**
     * 确保文章创建时初始化统计数据
     */
    public void initStatisticsForArticle(Article article) {
        if (article.getId() == null) {
            throw new IllegalArgumentException("Article must be saved first");
        }

        Statistic statistic = new Statistic();
        statistic.setArticleId(article.getId());
        statistic.setHits(0);
        statistic.setCommentsNum(0);
        statisticMapper.insertStatistic(statistic);
    }
}
