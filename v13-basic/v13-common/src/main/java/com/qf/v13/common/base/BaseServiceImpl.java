package com.qf.v13.common.base;

import java.util.List;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/11 19:25
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T> {


    //子类再实现这个抽象方法时候，注入具体的mapper
    public abstract IBaseDao<T> getBaseDao();

    public int deleteByPrimaryKey(Long id) {
        return getBaseDao().deleteByPrimaryKey(id);
    }

    public int insert(T record) {
        return getBaseDao().insert(record);
    }

    public int insertSelective(T record) {
        return getBaseDao().insertSelective(record);
    }

    public T selectByPrimaryKey(Long id) {
        return getBaseDao().selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(T record) {
        return getBaseDao().updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(T record) {
        return getBaseDao().updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(T record) {
        return getBaseDao().updateByPrimaryKey(record);
    }

    public List<T> list(){
        return getBaseDao().list();
    }
}
