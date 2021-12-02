package com.mzd.multipledatasources.mapper;

import com.mzd.multipledatasources.bean.TeachersBean;
import org.springframework.stereotype.Repository;

import com.mzd.multipledatasources.bean.TestBean;

import java.util.List;

@Repository
public interface TransactionMapping1 {

	void save(TestBean t);

	List<TestBean> select();

}
