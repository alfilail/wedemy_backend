package com.lawencon.elearning.dao;

import java.io.Serializable;

import com.lawencon.base.BaseDaoImpl;

/**
 * @author Nur Alfilail
 */

public class ElearningBaseDaoImpl<T extends Serializable> extends BaseDaoImpl<T> {

	protected StringBuilder sqlBuilder(String... syntax) {
		StringBuilder sql = new StringBuilder();
		for (String builder : syntax) {
			sql.append(builder);
		}
		return sql;
	}

}
