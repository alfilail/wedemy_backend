package com.lawencon.elearning.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.DetailForums;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Users;
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
		List<DetailForums> detailForum = createQuery("FROM DetailForums WHERE code = ?1", DetailForums.class)
				.setParameter(1, code).getResultList();
		return resultCheck(detailForum);
	}

	@Override
	public DetailForums getDetailForumById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public void softDeleteDetailForumById(String id, String idUser) throws Exception {
		updateNativeSQL("UPDATE t_m_dtl_forums SET is_active = FALSE", id, idUser);
	}

	@Override
	public void updateDetailForum(DetailForums detailForum, Callback before) throws Exception {
		save(detailForum, before, null, true, true);
	}

	@Override
	public List<DetailForums> getAllDetailForumsByIdForum(String idForum) throws Exception {
		List<DetailForums> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT df.id, df.created_at, df.content_text, p.fullname FROM t_r_dtl_forums df ",
				"INNER JOIN t_m_users u ON df.id_user = u.id INNER JOIN t_m_profiles p ON u.id_profile = p.id ",
				"WHERE df.id_forum =?1").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idForum).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			DetailForums detailForum = new DetailForums();
			detailForum.setId((String) objArr[0]);
			detailForum.setCreatedAt(((Timestamp) objArr[1]).toLocalDateTime());
			detailForum.setContentText((String) objArr[2]);
			Profiles profile = new Profiles();
			profile.setFullName((String) objArr[3]);
			Users user = new Users();
			user.setIdProfile(profile);
			detailForum.setIdUser(user);
			listResult.add(detailForum);
		});
		return listResult;
	}
}
