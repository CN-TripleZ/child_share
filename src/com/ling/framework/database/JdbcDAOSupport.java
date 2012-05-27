package com.ling.framework.database;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ling.framework.util.Page;
import com.ling.framework.util.ReflectionUtil;
import com.ling.framework.util.StringUtil;

/**
 * jdbc数据库操作支撑
 */
@Transactional
public class JdbcDAOSupport<T> implements IDAOSupport<T> {
	protected final Logger logger = Logger.getLogger(getClass().getName());

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcTemplate simpleJdbcTemplate;

	public void execute(String sql, Object... args) {
		try {
			simpleJdbcTemplate.update(sql, args);
		} catch (Exception e) {
			throw new RuntimeException(sql, e);
		}
	}

	public void insert(String table, Map<String, Object> fields) {
		String sql = "";
		try {
			Assert.hasText(table,   "表名不能为空");
			Assert.notEmpty(fields, "字段不能为空");
			
			table = quoteCol(table);

			Object[] cols   = fields.keySet().toArray();
			Object[] values = new Object[cols.length];
			for (int i = 0; i < cols.length; i++) {
				if (fields.get(cols[i]) == null) {
					values[i] = null;
				} else {
					values[i] = fields.get(cols[i]).toString();
				}
				cols[i] = quoteCol(cols[i].toString());
			}
			sql = "INSERT INTO " + table + " ("
					+ StringUtil.implode(", ", cols) + ") VALUES ("
					+ StringUtil.implodeValue(", ", values) + ")";
			jdbcTemplate.update(sql, values);
		} catch (Exception e) {
			throw new RuntimeException(sql, e);
		}
	}

	public void insert(String table, Object pojo) {
		insert(table, ReflectionUtil.pojo2Map(pojo));
	}

	public int query4Int(String sql, Object... args) {
		try {
			return simpleJdbcTemplate.queryForInt(sql, args);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public String query4String(String sql) {
		try {
			return (String) jdbcTemplate.queryForObject(sql, String.class);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings({ "rawtypes" })
	public List query4List(String sql, Object... args) {
		return jdbcTemplate.queryForList(sql, args);
	}

	@SuppressWarnings("unchecked")
	public List<T> query4List(String sql, RowMapper mapper, Object... args) {
		try {
			return jdbcTemplate.query(sql, args, mapper);
		} catch (Exception ex) {
			throw new RuntimeException(sql, ex);
		}
	}

	public List<T> query4List(String sql, Class<T> clazz, Object... args) {
		return simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
	}

	@SuppressWarnings({"unchecked" })
	public List<T> query4ListPage(String sql, int start, int limit, Object... args) {
		try {
			Assert.hasText(sql, "SQL语句不能为空");
			return query4List(buildPageSQL(sql, start, limit), args);
		} catch (Exception ex) {
			throw new RuntimeException(sql, ex);
		}
	}

	public List<T> query4List(String sql, int start, int limit, RowMapper mapper) {
		try {
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(start >= 1, "start 必须大于等于1");
			return this.query4List(buildPageSQL(sql, start, limit), mapper);
		} catch (Exception ex) {
			throw new RuntimeException(sql, ex);
		}
	}

	public long query4Long(String sql, Object... args) {
		return simpleJdbcTemplate.queryForLong(sql, args);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map query4Map(String sql, Object... args) {
		return this.jdbcTemplate.queryForMap(sql, args);
	}

	@SuppressWarnings("unchecked")
	public T query4Object(String sql, Class<?> clazz, Object... args) {
		try {
			return (T) simpleJdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
		} catch (Exception ex) {
			throw new RuntimeException("error", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T query4Object(String sql, ParameterizedRowMapper<?> mapper, Object... args) {
		return (T) this.simpleJdbcTemplate.queryForObject(sql, mapper, args);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> query(String sql, int start, int limit, Object... args) {
		try {
			Assert.hasText(sql, "SQL语句不能为空");
			String countSQL = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));
			List result = query4List(buildPageSQL(sql, start, limit), args);
			int count   = query4Int(countSQL, args);
			return new Page<T>(0, count, limit, result);
		} catch (Exception ex) {
			throw new RuntimeException(sql, ex);
		}
	}

	public Page<T> query(String sql, int start, int limit, RowMapper rowMapper, Object... args) {
		try {
			Assert.hasText(sql, "SQL语句不能为空");
			
			String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));
			List<T> result = query4List(buildPageSQL(sql, start, limit), rowMapper, args);
			int count    = query4Int(countSql, args);
			return new Page<T>(0, count, limit, result);
		} catch (Exception ex) {
			throw new RuntimeException(sql, ex);
		}
	}

	public Page<T> query(String sql, int start, int limit, Class<T> clazz, Object... args) {

		try {
			Assert.hasText(sql, "SQL语句不能为空");
			String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));
			List<T> result = query4List(buildPageSQL(sql, start, limit), clazz, args);
			int count      = query4Int(countSql, args);
			return new Page<T>(0, count, limit, result);
		} catch (Exception ex) {
			throw new RuntimeException(sql, ex);
		}
	}

	public void update(String table, Map<String, Object> fields, Map<String, Object> where) {
		String whereSql = "";
		if (where != null) {
			Object[] wherecols = where.keySet().toArray();
			for (int i = 0; i < wherecols.length; i++) {
				wherecols[i] = quoteCol(wherecols[i].toString()) + "="
						+ quoteValue(where.get(wherecols[i]).toString());
			}
			whereSql += StringUtil.implode(" AND ", wherecols);
		}
		update(table, fields, whereSql);
	}

	public void update(String table, T pojo, Map<String, Object> where) {
		String whereSql = "";
		// where值
		if (where != null) {
			Object[] wherecols = where.keySet().toArray();
			for (int i = 0; i < wherecols.length; i++) {
				wherecols[i] = quoteCol(wherecols[i].toString()) + "="
						+ quoteValue(where.get(wherecols[i]).toString());
			}
			whereSql += StringUtil.implode(" AND ", wherecols);
		}
		update(table, ReflectionUtil.pojo2Map(pojo), whereSql);

	}

	public void update(String table, T pojo, String where) {
		this.update(table, ReflectionUtil.pojo2Map(pojo), where);
	}

	public void update(String table, Map<String, Object> fields, String where) {
		String sql = "";
		try {
			Assert.hasText(table, "表名不能为空");
			Assert.notEmpty(fields, "字段不能为空");
			Assert.hasText(where, "where条件不能为空");
			
			table = quoteCol(table);
			// 字段值
			Object[] cols = fields.keySet().toArray();

			Object[] values = new Object[cols.length];
			for (int i = 0; i < cols.length; i++) {
				if (fields.get(cols[i]) == null) {
					values[i] = null;
				} else {
					values[i] = fields.get(cols[i]).toString();
				}
				cols[i] = quoteCol(cols[i].toString()) + "=?";
			}
			sql = "UPDATE " + table + " SET " + StringUtil.implode(", ", cols) + " WHERE " + where;
			simpleJdbcTemplate.update(sql, values);
		} catch (Exception e) {
			throw new RuntimeException(sql, e);
		}
	}

	public String buildPageSQL(String _sql, int start, int limit) {
		String sql_str = "";
		int end = start + limit;
		String db_type = "mysql";
		if (db_type.equals("mysql")) {
			sql_str = _sql + " LIMIT " + start + "," + end;
		} else {
			StringBuffer sql = new StringBuffer("SELECT * FROM (SELECT t1.*,rownum sn1 FROM (");
			sql.append(_sql);
			sql.append(") t1) t2 WHERE t2.sn1 BETWEEN ");
			sql.append(start);
			sql.append(" AND ");
			sql.append(end);
			sql_str = sql.toString();
		}
		return sql_str.toString();
	}

	/**
	 * 格式化列名 只适用于Mysql
	 * 
	 * @param col
	 * @return
	 */
	private String quoteCol(String col) {
		if (col == null || col.equals("")) {
			return "";
		}
		return col;
	}

	/**
	 * 格式化值 只适用于Mysql
	 * 
	 * @param value
	 * @return
	 */
	private String quoteValue(String value) {
		if (value == null || value.equals("")) {
			return "''";
		}
		return "'" + value.replaceAll("'", "''") + "'";
	}

	/**
	 * 去除sql的select 子句，未考虑union的情况,用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}
}
