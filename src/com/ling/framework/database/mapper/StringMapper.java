package com.ling.framework.database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class StringMapper implements ParameterizedRowMapper<String> {

	public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		String str = rs.getString(1);
		return str;
	}
}
