package com.ling.framework.database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class DoubleMapper implements ParameterizedRowMapper<Double> {
	
	public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
		Double dobule = new Double(rs.getDouble(1));
		return dobule;
	}
}
