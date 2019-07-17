package com.qf.v13centerweb.pojo;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/15 14:43
 */
public class WangeditorResultBean {

    private String errno;

    private String[] data;

    public WangeditorResultBean(String errno, String[] data) {
        this.errno = errno;
        this.data = data;
    }

    public WangeditorResultBean() {
    }

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

}
