package com.clls.service.impl;

import com.clls.dao.InformationDao;
import com.clls.dao.UserDao;
import com.clls.domain.Information;
import com.clls.domain.PageBean;
import com.clls.domain.User;
import com.clls.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("informationService")
public class InformationServiceImpl implements InformationService {

    @Autowired
    InformationDao informationDao;
    @Autowired
    UserDao userDao;

    /**
     * 返回主页的PageBean
     *
     * @param _currentPage 前端当前页码
     * @return
     */
    @Override
    public PageBean<Information> findByPage(String _currentPage) {//, String _rows) {
        PageBean<Information> pb = new PageBean<Information>();
        int currentPage = Integer.parseInt(_currentPage);
        if (currentPage < 1)
            currentPage = 1;
        //全局参数，设置页面显示的帖子数量
        final int ROWS = 5;

        //设置PageBean参数
        pb.setCurrentPage(currentPage);
        pb.setRows(ROWS);

        int totalCount = informationDao.findTotalCount();
        pb.setTotalCount(totalCount);

        int start = (currentPage - 1) * ROWS;
        if (start < 0)
            start = 0;
        //从数据库根据页码选择一定数量，封装到list中
        List<Information> list = informationDao.findByPage(start, ROWS);
        //如果取出的information的状态为2（进行中），封装接单者信息
        if (list != null) {
            for (Information information : list) {
                if (information.getState() == 2) {
                    information.setServant(new User());
                    information.getServant().setId(informationDao.findServantId(information.getId()));
                    int userId = information.getServant().getId();
                    information.getServant().setUsername(userDao.findOneByUserId(userId).getUsername());
                }

            }
        }
        pb.setList(list);

        int totalPage = totalCount % ROWS == 0 ? totalCount / ROWS : totalCount / ROWS + 1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    /**
     * 返回用户主页的PageBean，根据主页用户的ID来获取 供spaceController调用
     *
     * @param _currentPage 前端当前页码
     * @param userId       用户主页主人的用户ID
     * @param identifier   标示前端页面路由的页面号
     * @return
     */
    @Override
    public PageBean<Information> findByPage(String _currentPage, int userId, int identifier) {
        PageBean<Information> pb = new PageBean<Information>();
        //全局参数，设置页面显示的帖子数量
        final int ROWS = 5;
        //历史发布订单界面
        if (identifier == 0) {
            int currentPage = Integer.parseInt(_currentPage);
            if (currentPage < 1)
                currentPage = 1;
            //设置PageBean参数
            pb.setCurrentPage(currentPage);
            pb.setRows(ROWS);

            int totalCount = informationDao.findTotalCountOfUser(userId);
            pb.setTotalCount(totalCount);

            int start = (currentPage - 1) * ROWS;
            if (start < 0)
                start = 0;
            //为用户的订单绑定接单人
            List<Information> list = informationDao.findInfoOfUserByPage(start, ROWS, userId);
            if (list != null) {
                for (Information information : list) {
                    if (information.getState() == 2) {
                        information.setServant(new User());
                        //封装服务人员的用户ID
                        information.getServant().setId(informationDao.findServantId(information.getId()));
                        int id = information.getServant().getId();
                        //封装服务人员的用户名
                        information.getServant().setUsername(userDao.findOneByUserId(id).getUsername());
                    }
                }
            }
            pb.setList(list);
            int totalPage = totalCount % ROWS == 0 ? totalCount / ROWS : totalCount / ROWS + 1;
            pb.setTotalPage(totalPage);
        }

        //历史接单界面
        else if (identifier == 1) {
            int currentPage = Integer.parseInt(_currentPage);
            if(currentPage<1)
                currentPage=1;

            //设置PageBean参数
            pb.setCurrentPage(currentPage);
            pb.setRows(ROWS);

            int totalCount = informationDao.findTotalCountOfTakenOrder(userId);
            pb.setTotalCount(totalCount);

            int start = (currentPage - 1) * ROWS;
            if (start < 0)
                start = 0;
            //找到该用户接过的所有订单
            System.out.println(start + " " + ROWS + " " + userId);
            List<Information> list = informationDao.findAllTakenOrderByPage(start, ROWS, userId);
            //封装发布人信息
            if (list != null) {
                for (Information information : list) {
                    information.setUser(userDao.findOneByUserId(information.getUserId()));
                }
                //为用户的订单绑定接单人
                for (Information information : list) {
                    if (information.getState() == 2) {
                        information.setServant(new User());
                        //封装服务人员的用户ID
                        information.getServant().setId(informationDao.findServantId(information.getId()));
                        int id = information.getServant().getId();
                        //封装服务人员的用户名
                        information.getServant().setUsername(userDao.findOneByUserId(id).getUsername());
                    }
                }
            }
            pb.setList(list);
            int totalPage = totalCount % ROWS == 0 ? totalCount / ROWS : totalCount / ROWS + 1;
            pb.setTotalPage(totalPage);
        }
        return pb;
    }

    @Override
    public int findUserIdById(int id) {
        return informationDao.findUserIdById(id);
    }

    @Override
    public void deleteOneInformation(int id) {
        informationDao.deleteOneInformation(id);
    }

    @Override
    public void modifyState(int id, int state) {
        informationDao.modifyState(id, state);
    }

    @Override
    public int findState(int id) {
        return informationDao.findState(id);
    }

    @Override
    public void saveInformation(Information information) {
        informationDao.saveInformation(information);
    }

    @Override
    public int findServantId(int infoId) {
        return informationDao.findServantId(infoId);
    }


    @Override
    public void completeOrderByInfoId(int infoId) {
        informationDao.completeOrderByInfoId(infoId);
    }

    @Override
    public void cancelAnOrderByInfoId(int infoId) {
        informationDao.cancelAnOrderByInfoId(infoId);
        informationDao.deleteTakenOrderByInfoId(infoId);
    }
}
