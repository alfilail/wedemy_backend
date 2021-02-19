package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.General;
import com.lawencon.elearning.util.HibernateUtils;

@Repository
public class GeneralDaoImpl extends ElearningBaseDaoImpl<General> implements GeneralDao{
	
	@Override
	public General getTemplateEmail(String code) throws Exception {
		String sql = sqlBuilder("SELECT template_html ",
				" FROM t_m_general WHERE code =?1").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, code).getResultList();
		return HibernateUtils.bMapperList(listObj, General.class, "templateHtml").get(0);
	}
}
