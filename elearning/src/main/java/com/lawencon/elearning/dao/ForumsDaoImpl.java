package com.lawencon.elearning.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Forums;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Users;
import com.lawencon.util.Callback;

@Repository
public class ForumsDaoImpl extends ElearningBaseDaoImpl<Forums> implements ForumsDao{

	@Override
	public void insertForum(Forums forum, Callback before) throws Exception {
		save(forum, before, null, true, true);
	}

	@Override
	public void updateContentForum(Forums forum, Callback before) throws Exception {
		save(forum, before, null, true, true);
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
	public List<Forums> getForumByIdDetailModuleRegistration(String id) throws Exception {
		List<Forums> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT f.id, f.created_at, f.content_text, p.fullname FROM t_r_forums f ",
				"INNER JOIN t_m_users u ON f.id_user = u.id INNER JOIN t_m_profiles p ON u.id_profile = p.id ",
				"WHERE f.id_dtl_module_registration =?1").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Forums forum = new Forums();
			forum.setId((String) objArr[0]);
			forum.setCreatedAt(((Timestamp) objArr[1]).toLocalDateTime());
			forum.setContentText((String) objArr[2]);
			Profiles profile = new Profiles();
			profile.setFullName((String) objArr[3]);
			Users user = new Users();
			user.setIdProfile(profile);
			forum.setIdUser(user);
			listResult.add(forum);
		});
		return listResult;
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
