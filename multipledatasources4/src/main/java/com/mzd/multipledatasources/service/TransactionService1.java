package com.mzd.multipledatasources.service;

import com.mzd.multipledatasources.bean.TestBean;
import com.mzd.multipledatasources.mapper.TransactionMapping1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService1 {

	@Autowired
	private TransactionMapping1 tm1;

	@Transactional
	public void test01_saveTestBean(TestBean t) {
		tm1.save(t);
	}

	public List<TestBean> select() {
		return tm1.select();
	}

}
