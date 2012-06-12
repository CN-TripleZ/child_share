package com.ling.framework.database;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class LowerCaseJdbcTemplate extends JdbcTemplate {
	
	protected RowMapper getColumnMapRowMapper() {
//		if("".equals(EopSetting.DBTYPE)){
//			return new LowerCaseColumnMapRowMapper();
//		}else{
			return new ColumnMapRowMapper();
//		}
	}
}
