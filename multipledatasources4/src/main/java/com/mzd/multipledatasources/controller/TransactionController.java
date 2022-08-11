package com.mzd.multipledatasources.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mzd.multipledatasources.bean.TeachersBean;
import com.mzd.multipledatasources.bean.TestBean;
import com.mzd.multipledatasources.datasource.DataSourceType;
import com.mzd.multipledatasources.entity.User;
import com.mzd.multipledatasources.mapper.PersonMapper;
import com.mzd.multipledatasources.mapper.TransactionMapping2;
import com.mzd.multipledatasources.mapper.UserMapper;
import com.mzd.multipledatasources.service.TransactionService1;
import com.mzd.multipledatasources.service.TransactionService2;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
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
	private SqlSessionFactory sqlSessionFactory;
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * https://blog.csdn.net/qq_25223941/article/details/110160360
	 * sqlSession执行原生sql
	 * @throws SQLException
	 */
	@GetMapping("test")
	public void aa() throws SQLException {
		DataSourceType.resolveKey("a");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.getConnection().prepareStatement("INSERT INTO `user`(`id`, `name`, `sex`) VALUES (14, '小明', '难222')").execute();
		//int insert = sqlSession.insert("INSERT INTO `vts-mos`.`user`(`id`, `name`, `sex`) VALUES (6, '小明', '难222');\n");
		DataSourceType.resolveKey("b");
		SqlSession sqlSession1 = sqlSessionFactory.openSession();
		sqlSession1.getConnection().prepareStatement("INSERT INTO `t_person`(`name`, `email`, `password`, `phone`, `id`) VALUES ('张三哥2aa', '657@qq.comaa', '123456', '159238aa', NULL)").execute();
		//int insert1 = sqlSession1.insert("INSERT INTO `mos-news`.`t_person`(`name`, `email`, `password`, `phone`, `id`) VALUES ('张三哥2aa', '657@qq.comaa', '123456', '159238aa', NULL);\n");
		System.out.println("aa");
	}
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
		ts2.test02_saveTeachersBean(tb);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		SqlSession sqlSession1 = sqlSessionFactory.openSession();
		List<TeachersBean> list = sqlSession.getMapper(TransactionMapping2.class).select();
		System.out.println(objectMapper.writeValueAsString(list));
		//sqlSession.close();
		List<TeachersBean> list1 = sqlSession1.getMapper(TransactionMapping2.class).select();
		System.out.println(objectMapper.writeValueAsString(list1));
		return objectMapper.writeValueAsString(list);
	}

	@GetMapping("twoDataSource")
	public void twoDataSource() throws JsonProcessingException {
		List<TestBean> select = ts1.select();
		System.out.println("数据库1"+objectMapper.writeValueAsString(select));
		List<TeachersBean> select1 = ts2.select();
		System.out.println("数据库2"+objectMapper.writeValueAsString(select1));
	}

	@GetMapping("twoDataSource1")
	public void twoDataSource1() throws JsonProcessingException {

		List<TeachersBean> select1 = ts2.select();
		System.out.println("数据库2"+objectMapper.writeValueAsString(select1));
	}

	@Autowired
	PersonMapper personMapper;
	@Autowired
	UserMapper userMapper;

	@GetMapping("test2")
	public void test2() {
		userMapper.save(new User());
		personMapper.selectAll().forEach(x-> System.out.println(x.toString()));
	}
	
}
