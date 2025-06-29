//package com.example.demo.model.ResponseData;
//
///**
// * 全站服务统计类
// */
//public class StaticticsBo {
//    private Integer articles;
//    private Integer comments;
//
//    public Integer getArticles() {
//        return articles;
//    }
//
//    public void setArticles(Integer articles) {
//        this.articles = articles;
//    }
//
//    public Integer getComments() {
//        return comments;
//    }
//
//    public void setComments(Integer comments) {
//        this.comments = comments;
//    }
//
//    @Override
//    public String toString() {
//        return "StaticticsBo{" +
//                "articles=" + articles +
//                ", comments=" + comments +
//                '}';
//    }
//}
package com.example.demo.model.ResponseData;

public class StaticticsBo {
    private Integer articles;
    private Integer comments;

    // 添加无参构造函数
    public StaticticsBo() {
    }

    // 添加带两个参数的构造函数
    public StaticticsBo(Integer articles, Integer comments) {
        this.articles = articles;
        this.comments = comments;
    }

    // 保留原有的 getter 和 setter
    public Integer getArticles() {
        return articles;
    }

    public void setArticles(Integer articles) {
        this.articles = articles;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}