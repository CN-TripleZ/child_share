package com.ling.framework.database;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.ling.framework.util.Page;
import com.ling.framework.util.ReflectionUtil;

public class BaseJdbcDaoSupport<T> implements IDAOSupport<T> {
	private IDAOSupport<T> jdbcDaoSupport;
	private IDBRouter dbRouter;

	public BaseJdbcDaoSupport(IDAOSupport<T> jdbcDaoSupport) {
		this.jdbcDaoSupport = jdbcDaoSupport;
	}

	public void setDbRouter(IDBRouter dbRouter) {
		this.dbRouter = dbRouter;
	}

	public void insert(String table, Object pojo) {
		Map<String, Object> pojoMap = ReflectionUtil.pojo2Map(pojo);
		table = dbRouter.getTableName(table);
		this.jdbcDaoSupport.insert(table, pojoMap);
	}

	public void execute(String sql, Object... args) {
		sql = wrapExeSql(sql);
		this.jdbcDaoSupport.execute(sql, args);
	}

	public void insert(String table, Map<String, Object> fields) {
		table = this.dbRouter.getTableName(table);
		this.jdbcDaoSupport.insert(table, fields);
	}

	public int query4Int(String sql, Object... args) {
		sql = wrapSelSql(sql);
		return this.jdbcDaoSupport.query4Int(sql, args);
	}

	public List<T> query4List(String sql, RowMapper mapper, Object... args) {
		sql = wrapSelSql(sql);
		return this.jdbcDaoSupport.query4List(sql, mapper, args);
	}

	public List<T> query4List(String sql, Class<T> clazz, Object... args) {
		sql = wrapSelSql(sql);
		return this.jdbcDaoSupport.query4List(sql, clazz, args);
	}

//	public List<Map> query4ListPage(String sql, int start, int limit, Object... args) {
//		sql = wrapSelSql(sql);
//		return this.jdbcDaoSupport.query4ListPage(sql, start, limit, args);
//	}

	public List<T> query4List(String sql, int start, int limit, RowMapper mapper) {
		sql = wrapSelSql(sql);
		return this.jdbcDaoSupport.query4List(sql, start, limit, mapper);
	}

	public long query4Long(String sql, Object... args) {
		sql = wrapSelSql(sql);
		return this.jdbcDaoSupport.query4Long(sql, args);
	}

	public String query4String(String sql) {
		sql = wrapSelSql(sql);
		return this.jdbcDaoSupport.query4String(sql);
	}

	public Map<String, Object> query4Map(String sql, Object... args) {
		sql = wrapSelSql(sql);
		return this.jdbcDaoSupport.query4Map(sql, args);
	}

	public T query4Object(String sql, Class<?> clazz, Object... args) {
		sql = wrapSelSql(sql);
		return this.jdbcDaoSupport.query4Object(sql, clazz, args);
	}

	public T query4Object(String sql, ParameterizedRowMapper<?> mapper, Object... args) {
		sql = wrapSelSql(sql);
		return this.jdbcDaoSupport.query4Object(sql, mapper, args);
	}

	public Page<T> query(String sql, int start, int limit, Object... args) {
		sql = wrapSelSql(sql);
		return this.jdbcDaoSupport.query(sql, start, limit, args);
	}

	public Page<T> query(String sql, int start, int limit, RowMapper rowMapper, Object... args) {
		sql = wrapSelSql(sql);
		return this.jdbcDaoSupport.query(sql, start, limit, rowMapper, args);
	}

	public Page<T> query(String sql, int start, int limit, Class<T> clazz, Object... args) {
		sql = wrapSelSql(sql);
		return this.jdbcDaoSupport.query(sql, start, limit, clazz, args);
	}

	public void update(String table, Map<String, Object> fields, Map<String, Object> where) {
		table = this.dbRouter.getTableName(table);
		this.jdbcDaoSupport.update(table, fields, where);
	}

	public void update(String table, Map<String, Object> fields, String where) {
		table = this.dbRouter.getTableName(table);
		this.jdbcDaoSupport.update(table, fields, where);
	}

	public void update(String table, T pojo, Map<String, Object> where) {
		table = this.dbRouter.getTableName(table);
		this.jdbcDaoSupport.update(table, pojo, where);
	}

	public void update(String table, T po, String where) {
		table = this.dbRouter.getTableName(table);
		this.jdbcDaoSupport.update(table, po, where);
	}

	public String wrapExeSql(String sql) {
		String pattern;
		if (sql.indexOf("update") >= 0) {
			pattern = "(update\\s+)(\\w+)(.+)";
		} else if (sql.indexOf("delete") >= 0) {
			pattern = "(delete\\s+from\\s+)(\\w+)(.+)";
		} else if (sql.indexOf("insert") >= 0) {
			pattern = "(insert\\s+into\\s+)(\\w+)(.+)";
		} else {
			return sql;
		}

		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(sql);
		if (m.find()) {
			String tname = m.group(2);
			sql = m.replaceAll("$1 " + this.dbRouter.getTableName(tname)
					+ " $3");
		}
		return sql;
	}

	/**
	 * 替换join句里的表名
	 * 
	 * @param sql
	 * @return
	 */
	public String rpJoinTbName(String sql) {
		String pattern = "(join\\s+)(\\w+)(\\s+)";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		if (m.find()) {
			String tname = m.group(2);
			m.appendReplacement(sb, "join " + this.dbRouter.getTableName(tname)
					+ " ");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 替换from句里的表名
	 * 
	 * @param sql
	 * @return
	 */
	public String rpFromTbName(String sql) {

		String pattern = "(from\\s+)(\\w+)(\\s*)";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();

		if (m.find()) {
			String tname = m.group(2);
			m.appendReplacement(sb, "from " + this.dbRouter.getTableName(tname)
					+ " ");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 替换select语句里的表名
	 * 
	 * @param sql
	 * @return
	 */
	public String rpSelTbName(String sql) {
		sql = rpJoinTbName(sql);
		sql = rpFromTbName(sql);
		return sql;
	}

	/**
	 * 将select语句包装为相应的saas sql
	 * 
	 * @param sql
	 * @return
	 */
	public String wrapSelSql(String sql) {
		sql = rpSelTbName(sql);
		return sql;
	}

	public static void main(String args[]) {
		// //on tb1.a1=tb2.a2 where w=1 group by g order by o
		//
		// String sql = "select * from tb1 left join tb2 ";
		// // select * from tb
		// String pattern = "(.*)from(\\s+)(\\w+)(\\s+)()";
		// // select *from tb
		// //sql=addWhereUid(sql);
		// System.out.println(sql);

		// String sql ="delete from bac  where b=2";
		// sql=wrapExeSql(sql);
		// System.out.println(sql);

		String pattern = "(from\\s+)(\\w+)(\\s+)";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher("select * from settings");
		StringBuffer sb = new StringBuffer();
		if (m.find()) {
			String tname = m.group(2);
			m.appendReplacement(sb, "from " + tname + "a " + " ");
		}
		m.appendTail(sb);
		System.out.println(sb);
	}
}
