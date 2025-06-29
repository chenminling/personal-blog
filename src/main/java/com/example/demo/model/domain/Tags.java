package com.example.demo.model.domain;

public class Tags {
    private Integer id;
    private String tags;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "tags{" +
                "id=" + id +
                ", tags='" + tags + '\'' +
                '}';
    }
}
