package com.jn.mapper;


import com.jn.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    @Select("select * from user where id=#{id}")
    User getUserById(Integer id);

    @Select("select * from user")
    List<User> getUser();

    @Select("select * from user where username=#{username}")
    User getUserByUsername(String username);

    @Update("update user set username=#{username} where id=#{id}")
    int updateUser(User user);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into user(username) values(#{username})")
    int insertUser(User user);

    @Delete("delete from user where id=#{id}")
    int deleteUser(Integer id);
}
