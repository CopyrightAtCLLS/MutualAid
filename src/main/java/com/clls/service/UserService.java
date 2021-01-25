package com.clls.service;

import com.clls.domain.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    //查询所有用户
    public List<User> findAll();

    //保存用户信息
    public void saveUser(User user);

    //根据用户名查询特定用户的信息
    public User findOneByUsername(String username);

    //接单
    public void receiveOrder(int userId,int infoId);

    //根据用户ID查询用户信息
    public User findOneByUserId(int id);

    //根据订单id查询接单者id
    public int findServantIdByInfoId(int infoId);
}
