package com.ling.framework.database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class IntegerMapper implements ParameterizedRowMapper<Integer> {

	public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Integer v = rs.getInt(1);
		return v;
	}
}
