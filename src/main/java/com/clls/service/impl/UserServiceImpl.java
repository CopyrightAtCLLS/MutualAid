package com.clls.service.impl;

import com.clls.dao.UserDao;
import com.clls.domain.User;
import com.clls.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        List<User> users=userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
        return userDao.findAll();
    }

    @Override
    public void saveUser(User user){
        userDao.saveUser(user);
//        int i=1/0;
    }

    @Override
    public User findOneByUsername(String username) {
        User user;
        user=userDao.findOneByUsername(username);
        return user;
    }

    @Override
    public void receiveOrder(int userId, int infoId) {
        userDao.receiveOrder(userId,infoId);
    }

    @Override
    public User findOneByUserId(int id) {
        return userDao.findOneByUserId(id);
    }

    @Override
    public int findServantIdByInfoId(int infoId) {
        return userDao.findServantIdByInfoId(infoId);
    }
}
