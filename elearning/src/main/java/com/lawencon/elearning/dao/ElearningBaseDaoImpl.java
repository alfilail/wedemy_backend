package com.lawencon.elearning.dao;

import java.io.Serializable;
import java.util.List;

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
	
	protected T resultCheck(List<T> model) {
		return model.size() > 0 ? model.get(0) : null;
	}

}
