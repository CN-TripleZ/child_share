package com.ling.framework;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * 单元测试基类<br/>
 * 应用此类必须保证以classpath下有spring包，
 * 且含有声明了simpleJdbcTemplate和jdbcTemplate的application的xml配置文件
 */
public class SpringTestSupport {

	private static ApplicationContext context;

	protected SimpleJdbcTemplate simpleJdbcTemplate;

	protected JdbcTemplate jdbcTemplate;

	@Before
	public void setup() {
		context = new ClassPathXmlApplicationContext(
				new String[] { "classpath*:spring/*.xml" });
		simpleJdbcTemplate = (SimpleJdbcTemplate) this.getBean("simpleJdbcTemplate");
		jdbcTemplate = (JdbcTemplate) this.getBean("jdbcTemplate");
	}

	protected <T> T getBean(String name) {
		return (T) context.getBean(name);
	}
}
