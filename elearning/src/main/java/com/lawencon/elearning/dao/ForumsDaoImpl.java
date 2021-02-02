package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Forums;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

@Repository
public class ForumsDaoImpl extends ElearningBaseDaoImpl<Forums> implements ForumsDao{

	@Override
	public void insertForum(Forums forum, Callback before) throws Exception {
		save(forum, before, null);
	}

	@Override
	public void updateContentForum(Forums forum, Callback before) throws Exception {
		save(forum, before, null);
//		String sql = "UPDATE t_r_forums SET content_text = ?1 WHERE id = ?2 ";
//		createNativeQuery(sql).setParameter(1, forum.getContentText()).setParameter(2, forum.getId()).getSingleResult();
	}

	@Override
	public void deleteForumById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public List<Forums> getAllForums() throws Exception {
		return getAll();
	}

	@Override
	public Forums getForumById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public Forums getForumByIdDetailModuleRegistration(String id) throws Exception {
		String sql = sqlBuilder("SELECT f.created_at, f.created_by, f.updated_at, f.updated_by, f.version, f.trx_date, f.trx_number, "
				 ,"f.content_text, f.forum_datetime, p.fullname from t_r_forums f "
				 ,"inner join t_r_detail_module_registrations dmr on dmr.id = f.id_dtl_module_registration "
				 ,"inner join t_m_users u on u.id = f.id_user "
				 ,"inner join t_m_profiles p on p.id = u.id_profile "
				 ,"where dmr.id = ?1 ").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();
		return HibernateUtils.bMapperList(listObj, Forums.class, "createdAt", "createdBy", "updatedAt", "updatedBy", "version", 
				"trxDate", "trxNumber", "contentText", "forumDateTime", "fullName").get(0);
	}
	
	@Override
	public void softDeleteForumById(String id, String idUser) throws Exception {
		updateNativeSQL("UPDATE t_r_forums SET is_active = false", id, idUser);
	}
	
	@Override
	public List<?> validateDeleteForum(String id) throws Exception {
		String sql = sqlBuilder("select from t_r_forums trf full join t_r_dtl_forums trdf ",
				" on trf.id = trdf.id_forum where trf.id = ?1 ").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, id).setMaxResults(1).getResultList();
		List<String> result = new ArrayList<String>();
		listObj.forEach(val -> {
			Object obj = (Object) val;
			result.add(obj != null ? obj.toString() : null);
		});
		return result;
	}

}
