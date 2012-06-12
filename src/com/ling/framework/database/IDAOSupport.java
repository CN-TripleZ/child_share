package com.ling.framework.database;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.ling.framework.util.Page;

/**
 * 数据库操作支撑接口
 */
public interface IDAOSupport<T> {

	/** 执行sql语句 **/
	public void execute(String sql, Object... args);

	/**
	 * 查询单一结果集<br/> 并将结果转为<code>int</code>型返回
	 * 
	 * @param sql 查询的sql语句，确定结果为一行一列，且为数字型
	 * @param args 对应sql语句中的参数值
	 */
	public int query4Int(String sql, Object... args);

	/**
	 * 查询单一结果集<br/> 并将结果转为<code>long</code>型返回
	 * 
	 * @param sql  查询的sql语句，确保结果为一行一列，且为数字型
	 * @param args 对应sql语句中的参数值
	 */
	public long query4Long(String sql, Object... args);

	/**
	 * 查询单一结果集<br/> 并将结果转为<code>String</code>型返回
	 * 
	 * @param sql  查询的sql语句，确保结果为一行一列，且为字符串型
	 */
	public String query4String(String sql);

	/**
	 * 查询单一结果集<br/> 并将结果转为<code>T</code>对象返回
	 * 
	 * @param sql  查询的sql语句,确保结果列名和对象属性对应
	 * @param clazz   <code>T</code>的Class对象
	 * @param args   对应sql语句中的参数值
	 */
	public T query4Object(String sql, Class<?> clazz, Object... args);

	public T query4Object(String sql, ParameterizedRowMapper<?> mapper, Object... args);

	/**
	 * 查询单一结果集<br/>并将结果转为<code>Map</code>对象返回
	 * 
	 * @param sql 查询的sql语句
	 * @param args   对应sql语句中的参数值
	 * @return 以结果集中的列为key，值为value的<code>Map</code>
	 */
	public Map<String, Object> query4Map(String sql, Object... args);

//	/**
//	 * 查询多行结果集<br/>并将结果转为<code>List<Map></code>
//	 * 
//	 * @param sql 查询的sql语句
//	 * @param args  对应sql语句中的参数值
//	 * @return 列表中元素为<code>Map</code>的<code>List</code>,<br/>
//	 *         Map结构：以结果集中的列为key，值为value,
//	 */
//	public List<Map<String, Object>> query4List(String sql, Object... args);

	/**
	 * 查询多行结果集<br/> 并将结果转为<code>List<T></code>
	 * 
	 * @param sql  查询的sql语句
	 * @param mapper 列和对象属性的Mapper
	 * @param args 对应sql语句中的参数值
	 * @return 列表中元素为<code>T</code>的<code>List</code>
	 */
	public List<T> query4List(String sql, RowMapper mapper, Object... args);

	/**
	 * 查询多行结果集<br/>并将结果转为<code>List<T></code>
	 * 
	 * @param sql  查询的sql语句
	 * @param clazz  <code><T></code>的Class对象
	 * @param args 对应sql语句中的参数值
	 * @return 列表中元素为<code>T</code>的<code>List</code>
	 */
	public List<T> query4List(String sql, Class<T> clazz, Object... args);

//	/**
//	 * 分页查询多行结果集<br/>
//	 * 
//	 * @param sql  查询的sql语句
//	 * @param start 查询的起始
//	 * @param limit 每页数量
//	 * @param args  对应sql语句中的参数值
//	 * @return 列表中元素为<code>Map</code>的<code>List</code>,<br/>
//	 *         Map结构：以结果集中的列为key，值为value,
//	 */
//	public List<Map<String, Object>> query4List(String sql, int start, int limit, Object... args);

	/**
	 * 分页查询多行结果集<br/>
	 * 
	 * @param sql  查询的sql语句
	 * @param start   查询的起始
	 * @param limit  每页数量
	 * @param mapper   列和对象属性的Mapper
	 * @return 列表中元素为<code>T</code>的<code>List</code>
	 */
	public List<T> query4List(String sql, int start, int limit, RowMapper mapper);

	/**
	 * 分页查询
	 * 
	 * @param sql 查询的sql语句
	 * @param start  查询的起始
	 * @param limit 每页数量
	 * @param args 对应sql语句中的参数值
	 * @return 分页结果集对象
	 */
	public Page<T> query(String sql, int start, int limit, Object... args);

	/**
	 * 分页查询
	 * 
	 * @param sql  查询的sql语句
	 * @param start  查询的起始
	 * @param limit  每页数量
	 * @param rowMapper  列和对象属性的Mapper
	 * @param args 对应sql语句中的参数值
	 * @return 分页结果集对象
	 */
	public Page<T> query(String sql, int start, int limit, RowMapper rowMapper, Object... args);

	/**
	 * 分页查询
	 * 
	 * @param sql  查询的sql语句
	 * @param start 查询的起始
	 * @param limit  每页数量
	 * @param clazz <code><T></code>的Class对象
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	public Page<T> query(String sql, int start, int limit, Class<T> clazz, Object... args);

	/**
	 * 更新数据
	 * 
	 * @param table 表名
	 * @param fields  字段-值Map
	 * @param where 更新条件(字段-值Map)
	 */
	public void update(String table, Map<String, Object> fields, Map<String, Object> where);

	/**
	 * 更新数据
	 * 
	 * @param table  表名
	 * @param fields  字段-值Map
	 * @param where  更新条件,如"a='1' AND b='2'"
	 */
	public void update(String table, Map<String, Object> fields, String where);

	/**
	 * 更新数据
	 * 
	 * @param table   表名
	 * @param po 要更新的对象，保证对象的属性名和字段名对应
	 * @param where   更新条件(字段-值Map)
	 */
	public void update(String table, T pojo, Map<String, Object> where);

	/**
	 * 更新数据
	 * 
	 * @param table 表名
	 * @param po   要更新的对象，保证对象的属性名和字段名对应
	 * @param where 更新条件,如"a='1' AND b='2'"
	 */
	public void update(String table, T pojo, String where);

	/**
	 * 新增数据
	 * 
	 * @param table 表名
	 * @param fields   字段-值Map
	 */
	public void insert(String table, Map<String, Object> fields);

	/**
	 * 新增数据
	 * 
	 * @param table  表名
	 * @param po  要新增的对象，保证对象的属性名和字段名对应
	 */
	public void insert(String table, Object pojo);
}
