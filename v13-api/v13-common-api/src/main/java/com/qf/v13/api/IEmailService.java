package com.qf.v13.api;

import com.qf.v13.common.pojo.ResultBean;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/25 20:25
 */
public interface IEmailService {

    public ResultBean send(String to,String subject,String text);

}
