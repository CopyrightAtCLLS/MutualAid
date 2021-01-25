package com.clls.dao;

import com.clls.domain.Information;
import com.clls.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户dao接口
 */
@Repository
public interface UserDao {

    //查询所有用户
    public List<User> findAll();

    //保存用户信息
    public void saveUser(User user);

    //根据用户名查询特定用户的信息
    public User findOneByUsername(String username);

    //根据用户ID查询用户信息
    public User findOneByUserId(int id);

    //查询该用户发布过的推文
    public List<Information> findInfosByUserId(User user);

    //接单
    public void receiveOrder(@Param("userId") int userId, @Param("infoId") int infoId);

    //查询接单表根据订单id得到接单人id
    public int findServantIdByInfoId(int infoId);
}
