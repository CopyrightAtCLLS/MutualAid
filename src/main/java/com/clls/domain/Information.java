package com.clls.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Information {
    private int id;
    private int userId;
    private String content;
    private User user;
    private User servant;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;
    private double bounty;
    private int state;//帖子有三种状态，待领取1，进行中2，已结束0

    public User getServant() {
        return servant;
    }

    public void setServant(User servant) {
        this.servant = servant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Information{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", date=" + date +
                ", bounty=" + bounty +
                ", state=" + state +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getBounty() {
        return bounty;
    }

    public void setBounty(double bounty) {
        this.bounty = bounty;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
