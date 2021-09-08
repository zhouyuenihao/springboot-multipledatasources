package com.mzd.multipledatasources.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mzd.multipledatasources.bean.TeachersBean;
import com.mzd.multipledatasources.bean.TestBean;
import com.mzd.multipledatasources.mapper.TransactionMapping2;
import com.mzd.multipledatasources.service.TransactionService1;
import com.mzd.multipledatasources.service.TransactionService2;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * 多数据源事务测试
 * 
 * @author acer
 *
 */
@RestController
public class TransactionController {
	@Autowired
	private TransactionService1 ts1;
	@Autowired
	private TransactionService2 ts2;
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private ObjectMapper objectMapper;

	@RequestMapping("/savetest")
	public String savetest() {
		TestBean tb = new TestBean();
		tb.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		tb.setScore(71);
		tb.setClassid("1");
		tb.setUserid("22");
		ts1.test01_saveTestBean(tb);
		return "success";
	}

	@RequestMapping("/saveteacher")
	public String saveteacher() throws JsonProcessingException {
		TeachersBean tb = new TeachersBean();
		tb.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		tb.setTeachername("2");
		tb.setClassid("11");
		//ts2.test02_saveTeachersBean(tb);
		List<TeachersBean> list = sqlSession.getMapper(TransactionMapping2.class).select();
		return objectMapper.writeValueAsString(list);
	}

	
}
