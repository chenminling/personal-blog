package com.example.demo.web.admin;

import com.example.demo.model.ResponseData.ArticleResponseData;
import com.example.demo.model.ResponseData.CommentResponseData;
import com.example.demo.model.ResponseData.StaticticsBo;
import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Category;
import com.example.demo.model.domain.Comment;
import com.example.demo.model.domain.Tags;
import com.example.demo.service.*;
import com.example.demo.service.impl.CategoryServiceImpl;
import com.github.pagehelper.PageInfo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;
/**
 * @Classname AdminController
 * @Description 后台管理模块
 * @Date 2019-3-14 10:39
 * @Created by CrazyStone
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ISiteService siteServiceImpl;
    @Autowired
    private IArticleService articleServiceImpl;
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private ITagsService iTagsService;
    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    // 管理中心起始页
    @GetMapping(value = {"", "/index"})
    public String index(HttpServletRequest request) {
        // 获取最新的5篇博客、评论以及统计数据
        List<Article> articles = siteServiceImpl.recentArticles(5);
        List<Comment> comments = siteServiceImpl.recentComments(5);
        StaticticsBo staticticsBo = siteServiceImpl.getStatistics();
        // 向Request域中存储数据
        request.setAttribute("comments", comments);
        request.setAttribute("articles", articles);
        request.setAttribute("statistics", staticticsBo);
        return "back/index";
    }

    // 向文章发表页面跳转
    @GetMapping(value = "/article/toEditPage")
    public String newArticle( ) {
        return "back/article_edit";
    }
    // 发表文章
    @PostMapping(value = "/article/publish")
    @ResponseBody
    public ArticleResponseData publishArticle(Article article) {
        if (StringUtils.isBlank(article.getCategories())) {
            article.setCategories("默认分类");
        }
        try {
            articleServiceImpl.publish(article);
            logger.info("文章发布成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章发布失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    //分类标签页面跳转
    @GetMapping(value = "/category")
    public String category(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "count", defaultValue = "10") int count,
                           HttpServletRequest request){
        PageInfo<Category> pageInfo = iCategoryService.selectCategoryWithPage(page, count);
        request.setAttribute("category", pageInfo);
        return "back/category";

    }
    //分类标签跳转页面2
    @GetMapping(value = "/category1")
    public String category1(@RequestParam(value = "page", defaultValue = "1") int page,
                            @RequestParam(value = "count", defaultValue = "10") int count,
                            HttpServletRequest request){
        PageInfo<Tags> pageInfo = iTagsService.selectTagsWithPage(page,count);
//        PageInfo<Article> pageInfo = articleServiceImpl.selectArticleWithPage(page, count);
        request.setAttribute("category1", pageInfo);
        return "back/category1";
    }
    // 跳转到后台文章列表页面
    @GetMapping(value = "/article")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "count", defaultValue = "10") int count,
                        HttpServletRequest request) {
        PageInfo<Article> pageInfo = articleServiceImpl.selectArticleWithPage(page, count);
        request.setAttribute("articles", pageInfo);
        return "back/article_list";
    }

    @GetMapping(value = "/comment")
    public String indexByArticleId(
            @RequestParam(
                    value = "page", defaultValue = "1") int page,
            @RequestParam(value = "count", defaultValue = "10") int count,
//                        @PathVariable("article") Integer aid,
//                        @RequestParam(value = "article",defaultValue = "1") Integer aid,
            @PathParam("article_id") Integer aid,
            HttpServletRequest request,
            Model model
    ) {
        PageInfo<Comment> pageInfo = iCommentService.getAllComments(aid, page, count);
//        List<Comment> list = iCommentService.findAllComments();
        request.setAttribute("comments", pageInfo);
//        model.addAttribute("comment", pageInfo);
        return "back/comment_list";


    }

    // 向文章修改页面跳转
    @GetMapping(value = "/article/{id}")
    public String editArticle(@PathVariable("id") String id, HttpServletRequest request) {
        Article article = articleServiceImpl.selectArticleWithId(Integer.parseInt(id));
        request.setAttribute("contents", article);
        request.setAttribute("categories", article.getCategories());
        return "back/article_edit";
    }

    // 文章修改处理
    @PostMapping(value = "/article/modify")
    @ResponseBody
    public ArticleResponseData modifyArticle(Article article) {
        try {
            articleServiceImpl.updateArticleWithId(article);
            logger.info("文章更新成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章更新失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    // 文章删除
    @PostMapping(value = "/article/delete")
    @ResponseBody
    public ArticleResponseData delete(@RequestParam int id) {
        try {
            articleServiceImpl.deleteArticleWithId(id);
            logger.info("文章删除成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章删除失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }
    @PostMapping(value = "/comment/delete")
    @ResponseBody
    public CommentResponseData deleteComment(@RequestParam int id) {
        try {
            iCommentService.deleteCommentId(id);
            logger.info("评论删除成功！！");
            return CommentResponseData.ok();

        } catch (Exception e) {
            logger.error("评论删除失败。错误信息为：" + e.getMessage());
            return CommentResponseData.fail();
        }

    }




    @PostMapping(value = "comment/update")
    @ResponseBody
    public CommentResponseData updateComment(@RequestParam int id) {
        try {
            iCommentService.updateComment(id);
            logger.info("评论状态更新成功！！");
            return CommentResponseData.ok();
        } catch (Exception e) {
            logger.error("更新失败。错误信息为：" + e.getMessage());
            return CommentResponseData.fail();
        }
    }
    @PostMapping(value = "comment/noupdate")
    @ResponseBody
    public CommentResponseData updatenoComment(@RequestParam int id) {
        try {
            iCommentService.updatenoComment(id);
            logger.info("评论状态更新成功！！");
            return CommentResponseData.ok();
        } catch (Exception e) {
            logger.error("更新失败。错误信息为：" + e.getMessage());
            return CommentResponseData.fail();
        }
    }

//    @PutMapping(value = "/category/publish")
//    @ResponseBody
//    public ArticleResponseData publishCategory(Category category) {
//        if (StringUtils.isBlank(category.getCategories())) {
//            category.setCategories("默认分类");
//        }
//        try {
//
//            categoryServiceImpl.publishCategory(category);
//            logger.info("分类创建成功");
//            return ArticleResponseData.ok();
//        } catch (Exception e) {
//            logger.error("分类创建失败，错误信息: " + e.getMessage());
//            return ArticleResponseData.fail();
//        }
//    }
    @GetMapping(value = "/category/{id}")
    public String editCategory(@PathVariable("id") String id,HttpServletRequest request){
        Category category = categoryServiceImpl.selectCategoryWithId(Integer.parseInt(id));
        request.setAttribute("contents",category);
        request.setAttribute("category",category.getCategory());
        return "back/category";
    }

    @PostMapping(value = "/category/modify")
    @ResponseBody
    public ArticleResponseData modifyCategory(Category category){
        try {
            categoryServiceImpl.updateCategoryWithId(category);
            logger.info("分类更新成功");
            return ArticleResponseData.ok();

        }catch (Exception e){
            logger.error("分类更新失败，错误信息：" +e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    //删除分类
    @PostMapping(value = "/category/delete")
    @ResponseBody
    public CommentResponseData deleteCategory(@RequestParam int id) {
        try {
//            iCommentService.deleteCommentId(id);
            iCategoryService.deleteCategoryWithId(id);
            logger.info("分类删除成功！！");
            return CommentResponseData.ok();

        } catch (Exception e) {
            logger.error("分类删除失败。错误信息为：" + e.getMessage());
            return CommentResponseData.fail();
        }

    }

    @PostMapping(value = "/category/create")
    public String createCategory(@ModelAttribute Category category) {

        try {
            iCategoryService.createCategory(category);
//            iTagsService.createTag(tags);
            logger.info("分类添加成功");

        } catch (Exception e) {
            logger.error("分类添加失败，错误信息: " + e.getMessage());

        }

        return "redirect:/admin/category";
    }

    @PostMapping(value = "/category1/delete")
    @ResponseBody
    public CommentResponseData deleteTags(@RequestParam int id) {
        try {
//            iCommentService.deleteCommentId(id);
            iTagsService.deleteTagsWithId(id);
            logger.info("标签删除成功！！");
            return CommentResponseData.ok();

        } catch (Exception e) {
            logger.error("标签删除失败。错误信息为：" + e.getMessage());
            return CommentResponseData.fail();
        }

    }

    @PostMapping(value = "/category1/create")
    public String createTags(@ModelAttribute Tags tags) {

        try {
            iTagsService.createTags(tags);
//            iTagsService.createTag(tags);
            logger.info("标签添加成功");

        } catch (Exception e) {
            logger.error("标签添加失败，错误信息: " + e.getMessage());

        }

        return "redirect:/admin/category1";
    }

    @GetMapping(value = "/setting")
    public String Setting(HttpServletRequest request){
//        PageInfo<Tags> pageInfo = iTagsService.selectTagsWithPage(page,count);
//        PageInfo<Article> pageInfo = articleServiceImpl.selectArticleWithPage(page, count);
//        request.setAttribute("category1", pageInfo);
        return "back/setting";
    }




}

