package com.example.demo.dao;

import com.example.demo.model.domain.User;
import com.example.demo.model.domain.user_authority;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    List<User> findAll();
//    User findByName(String username);
//    String findPswByName(String userName);
//    void save(User user);

    @Select("SELECT * FROM t_user WHERE username = #{username}")
    User findByName(String username);

    String findPswByName(String userName);

    void save(User user);

    @Insert("INSERT into t_user_authority(id,user_id,authority_id) VALUES (#{id},#{userid},#{authorityid})")
    public int insert(user_authority user_authority);

}