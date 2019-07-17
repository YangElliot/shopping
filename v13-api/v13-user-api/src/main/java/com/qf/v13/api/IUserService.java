package com.qf.v13.api;

import com.qf.v13.common.base.IBaseService;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TUser;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/25 17:28
 */
public interface IUserService extends IBaseService<TUser> {


    //保存在redis中的凭证key
    public ResultBean checkLogin(TUser user);

    //保存在客户端的凭证信息
    public ResultBean checkIsLogin(String uuid);

    //注销
    public ResultBean loginOut(String uuid);
}
