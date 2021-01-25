package com.clls.dao;

import com.clls.domain.Information;
import com.clls.domain.PageBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InformationDao {

    //查询所有推文
    public List<Information> findAll();

    //保存一条推文
    public void saveInformation(Information information);

    //查询一条推文
    public Information findOne(int num);

    //查询总的记录条数
    public int findTotalCount();

    //查询一定范围的记录
    public List<Information> findByPage(@Param("start") int start, @Param("rows") int rows);

    //删除一条记录
    public void deleteOneInformation(int id);

    //根据记录ID查询用户id
    public int findUserIdById(int id);

    //修改一条记录的当前状态
    public void modifyState(@Param("id") int id,@Param("state") int state);

    //返回当前记录的状态
    public int findState(int id);

    //根据用户发布的悬赏ID查询接单人ID
    public int findServantId(int infoId);

    //查找某用户的发布
    public List<Information> findInfoOfUserByPage(@Param("start") int start, @Param("rows") int rows,@Param("userId") int userId);

    //查询某一用户发布的总信息数量
    public int findTotalCountOfUser(int userId);

    //根据接单人id返回所有他接的单
    public List<Information> findAllTakenOrderByPage(@Param("start") int start, @Param("rows") int rows,@Param("userId") int userId);

    //根据接单人id返回所有他接的单的数量
    public int findTotalCountOfTakenOrder(int userId);
    //根据订单号删除接单表中的一行，联立下面的cancelAnOrder使用
    public void deleteTakenOrderByInfoId(int infoId);
    //根据订单号取消接单
    public void cancelAnOrderByInfoId(int infoId);

    //完成订单
    public void completeOrderByInfoId(int infoId);
}
