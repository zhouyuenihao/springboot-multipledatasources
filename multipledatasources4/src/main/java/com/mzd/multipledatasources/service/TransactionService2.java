package com.mzd.multipledatasources.service;

import com.mzd.multipledatasources.bean.TeachersBean;
import com.mzd.multipledatasources.bean.TestBean;
import com.mzd.multipledatasources.mapper.TransactionMapping2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService2 {
	@Autowired
	private TransactionMapping2 tm2;

	@Transactional
	public void test02_saveTeachersBean(TeachersBean t) {
		try {
			tm2.save(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<TeachersBean> select() {
		return tm2.select();
	}

}
