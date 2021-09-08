package com.mzd.multipledatasources.mapper;

import org.springframework.stereotype.Repository;

import com.mzd.multipledatasources.bean.TeachersBean;

import java.util.List;

@Repository
public interface TransactionMapping2 {

	void save(TeachersBean t);

	List<TeachersBean> select();

}
