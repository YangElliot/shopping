package com.qf.v13.mapper;

import com.qf.v13.common.base.IBaseDao;
import com.qf.v13.entity.TProduct;

import java.util.List;

public interface TProductMapper extends IBaseDao<TProduct> {

    Long batchUpdateFlag(List<Long> ids);

}