package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.DetailForums;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

@Repository
public class DetailForumsDaoImpl extends ElearningBaseDaoImpl<DetailForums> implements DetailForumsDao {
	@Override
	public void insertDetailForum(DetailForums detailForum, Callback before) throws Exception {
		save(detailForum, before, null, true, true);
	}

	@Override
	public List<DetailForums> getAllDetailForums() throws Exception {
		return getAll();
	}

	@Override
	public DetailForums getDetailForumByCode(String code) throws Exception {
		DetailForums detailForum = createQuery("FROM DetailForums WHERE code = ?1", DetailForums.class)
				.setParameter(1, code).getSingleResult();
		return detailForum;
	}

	@Override
	public DetailForums getDetailForumById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public void deleteDetailForumById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public void updateDetailForum(DetailForums detailForum, Callback before) throws Exception {
		save(detailForum, before, null, true, true);
	}

	@Override
	public List<DetailForums> getAllDetailForumsByIdForum(String idForum) throws Exception {
		String sql = sqlBuilder(
				"SELECT id, content_text, dtl_forum_datetime, id_user FROM t_r_dtl_forums WHERE id_forum =?1")
						.toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idForum).getResultList();
		return HibernateUtils.bMapperList(listObj, DetailForums.class, "id", "contentText");
	}
}
