package com.example.demo.dao;

import com.example.demo.model.domain.Tags;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagsMapper {

    //查询所有的标签
    @Select("SELECT * FROM t_tags ORDER BY id DESC")
    public List<Tags> selectTagsWithPage();
    @Delete("DELETE FROM t_tags WHERE id=#{id}")
    public void deleteTagsWithId(int id);

    @Insert("INSERT INTO t_tags (tags) VALUES (#{tags})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer createTags(Tags tags);
}
