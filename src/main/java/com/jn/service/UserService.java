package com.jn.service;

import com.jn.entity.User;
import com.jn.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@CacheConfig(cacheNames = "User")
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<User> findAll() {
        return userMapper.getUser();
    }

    @Cacheable(key = "'userid:'+#id", unless = "#result==null")
    public User findU(Integer id) {
        System.out.println("查询数据库");
        return userMapper.getUserById(id);
    }

    @CachePut(key = "'userid:'+#user.id")
    public User insertU(User user) {
        userMapper.insertUser(user);
        return user;
    }

    @CachePut(key = "'userid:'+#user.id")
    public User updateU(User user) {
        userMapper.updateUser(user);
        return user;
    }

    @CacheEvict(key = "'userid:'+#id")
    public int deleteU(Integer id) {
        return userMapper.deleteUser(id);
    }

}
