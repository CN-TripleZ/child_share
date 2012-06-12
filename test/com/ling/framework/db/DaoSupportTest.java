package com.ling.framework.db;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.ling.framework.SpringTestSupport;
import com.ling.framework.database.IDAOSupport;
import com.ling.framework.util.Page;

public class DaoSupportTest extends SpringTestSupport {
	private IDAOSupport<User> daoSupport;

	/**
	 * 建立表结构
	 * 
	 */
	@Before
	public void beforeTest() {
		daoSupport = (IDAOSupport) this.getBean("daoSupport");
		this.jdbcTemplate.execute("drop table if exists enation_user;");
		this.jdbcTemplate.execute("create table enation_user(user_id int not null auto_increment,username  varchar(50),password  varchar(50),primary key (user_id));");
	}


	/**
	 * 更新测试的断言
	 * 
	 */
	private void assertUpdate() {
		Map user_map = this.jdbcTemplate.queryForMap("select * from enation_user");
		assertEquals("王峰1", user_map.get("username"));
		assertEquals("test1", user_map.get("password"));
	}

	/**
	 * 测试读取一个实体
	 */
	// @Test
	public void testGet() {
		User user = daoSupport.query4Object("select * from enation_user where user_id=?", User.class, 1);
		assertEquals("王峰", user.getUsername());
	}

	@SuppressWarnings("rawtypes")
//	@Test
	public void testIntertPojo() {
		User user = new User();
		user.setUsername("dd");
		user.setPassword("test");
		daoSupport.insert("enation_user", user);
		
		Map user_map = jdbcTemplate.queryForMap("select * from enation_user");
		assertEquals("dd", user_map.get("username"));
		assertEquals("test", user_map.get("password"));
	}

	/**
	 * 测试可将一个map形式的数据插入到数据库
	 * 
	 */
	// @Test
	public void testIntertMap() {
		Map data = new HashMap();
		data.put("username", "王峰");
		data.put("password", "test");
		this.daoSupport.insert("enation_user", data);
	}

	/**
	 * 测试更新数据库 数据通过Map传递 ，条件通过字串传递
	 */
	// @Test
	public void testUpdate1() {
		HashMap data = new HashMap();
		data.put("username", "王峰1");
		data.put("password", "test1");
		daoSupport.update("enation_user", data, "user_id=1");
		assertUpdate();
	}

	/**
	 * 测试更新数据库 数据通过Map传递 ，条件通过Map递
	 */
	// @Test
	public void testUpdate2() {
		HashMap data = new HashMap();
		data.put("username", "王峰1");
		data.put("password", "test1");

		HashMap where = new HashMap();
		where.put("user_id", "1");
		daoSupport.update("enation_user", data, where);
		assertUpdate();
	}

	/**
	 * 测试更新数据库 数据通过po实体传递 ，条件通字串传递
	 */
	// @Test
	public void testUpdate3() {
		User user = new User();
		user.setUsername("王峰1");
		user.setPassword("test1");

		daoSupport.update("enation_user", user, "user_id=1");
		assertUpdate();
	}

	/**
	 * 测试更新数据库 数据通过po实体传递 ，条件通Map传递
	 */
	// @Test
	public void testUpdate4() {
		User user = new User();
		user.setUsername("王峰1");
		user.setPassword("test1");

		HashMap where = new HashMap();
		where.put("user_id", "1");

		daoSupport.update("enation_user", user, where);
		assertUpdate();
	}

	@Test
	public void testList() {
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setUsername("cc" + i);
			user.setPassword("test");
			daoSupport.insert("enation_user", user);
		}

		String sql = "select * from enation_user";
		List<User> userList = daoSupport.query4List(sql, User.class);
		assertEquals(userList.size(), 10);
		for (User user : userList) {
			System.out.println("user[" + user.getUsername() + "]");
		}
	}
}
