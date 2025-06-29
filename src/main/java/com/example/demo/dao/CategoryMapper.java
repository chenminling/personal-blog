package com.example.demo.dao;

import com.example.demo.model.domain.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("SELECT * FROM t_category ORDER BY id DESC")
    public List<Category> selectCategoryWithPage();



    @Insert("INSERT INTO t_category (category)" +
            " VALUES (#{category})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public Integer publishCategory(Category category);



    @Update("UPDATE t_category SET category = #{category} WHERE id = #{id}")
    public Integer updateCategoryWithId(Category category);

    @Select("SELECT * FROM t_category WHERE id=#{id}")
    public Category selectCategoryWithId(Integer id);

    @Delete("DELETE FROM t_category WHERE id=#{id}")
    public void deleteCategoryWithId(int id);

    @Insert("INSERT INTO t_category (category) VALUES (#{category})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer createCategory(Category category);

}
