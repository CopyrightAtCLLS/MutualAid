package com.clls.service;

import com.clls.domain.Information;
import com.clls.domain.PageBean;

public interface InformationService {
    public PageBean<Information> findByPage(String currentPage);//,String rows);

    //根据推文id查找发布者的id
    public int findUserIdById(int id);

    //删除一条记录
    public void deleteOneInformation(int id);

    //修改一条记录的当前状态
    public void modifyState(int id,int state);

    //返回当前记录的状态
    public int findState(int id);

    //保存一条推文
    public void saveInformation(Information information);

    public int findServantId(int infoId);

    public PageBean<Information> findByPage(String currentPage,int userId,int identifier);//,String rows);

    //完成订单
    public void completeOrderByInfoId(int infoId);

    //取消接单
    public void cancelAnOrderByInfoId(int infoId);

}
