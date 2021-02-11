package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Users;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

@Repository
public class UsersDaoImpl extends ElearningBaseDaoImpl<Users> implements UsersDao {

	@Override
	public void insert(Users user, Callback before) throws Exception {
		save(user, before, null);
	}

	@Override
	public List<Users> getAllUser() throws Exception {
		List<Users> listUsers = createQuery("FROM Users WHERE isActive = ?1", Users.class).setParameter(1, true)
				.getResultList();
		return resultCheckList(listUsers);
	}

	@Override
	public Users getUserById(String id) throws Exception {
		List<Users> users = createQuery("FROM Users WHERE id = ?1 AND isActive = ?2 ", Users.class).setParameter(1, id).setParameter(2, true)
				.getResultList();
		return resultCheck(users);
	}

	@Override
	public Users getByUsername(String username) throws Exception {
		List<Users> user = createQuery("FROM Users WHERE username = ?1 AND isActive = ?2 ", Users.class).setParameter(1, username)
				.setParameter(2, true).getResultList();
		return resultCheck(user);
	}

	@Override
	public void update(Users user, Callback before) throws Exception {
		save(user, before, null, true, true);
	}

	@Override
	public void deleteById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public Users getByIdProfile(Profiles profile) throws Exception {
		List<Users> user = createQuery("FROM Users WHERE idProfile.id = ?1 AND isActive = ?2 ", Users.class).setParameter(1, profile.getId())
				.setParameter(2, true).getResultList();
		return resultCheck(user);
	}

	@Override
	public List<Users> getByRoleCode(String code) throws Exception {
		String sql = sqlBuilder("SELECT u.id, u.username, r.code, p.fullname, p.id_number, p.birth_place, p.birth_date,",
				" p.email, p.phone, p.address, p.bio FROM t_m_users u INNER JOIN t_m_profiles p ON p.id = u.id_profile",
				" INNER JOIN t_m_roles r ON r.id = u.id_role WHERE r.code = ?1 AND u.is_active = ?2").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, code).setParameter(2, true).getResultList();
		return HibernateUtils.bMapperList(listObj, Users.class, "id", "username", "idRole.code", "idProfile.fullName",
				"idProfile.idNumber", "idProfile.birthPlace", "idProfile.birthDate", "idProfile.email",
				"idProfile.phone", "idProfile.address", "idProfile.bio");
	}

	@Override
	public void softDeleteById(String id, String idUser) throws Exception {
		updateNativeSQL("UPDATE t_m_users SET is_active = false", id, idUser);
	}

	@Override
	public List<?> validateDelete(String id) throws Exception {
		String sql = sqlBuilder("SELECT tmc.id as id_class, trp.id as id_presence, ",
				" tras.id as id_assignment_submission, trf.id as id_forum, ",
				" trdf.id as id_detail_forum, trce.id as id_class_enrollment ",
				" FROM t_m_users tmu ",
				" FULL JOIN t_m_classes tmc on tmu.id = tmc.id_tutor ",
				" FULL JOIN t_r_presences trp on tmu.id = trp.id_user ",
				" FULL JOIN t_r_assignment_submissions tras on tmu.id = tras.id_participant ",
				" FULL JOIN t_r_forums trf on tmu.id = trf.id_user ",
				" FULL JOIN t_r_dtl_forums trdf on tmu.id = trdf.id_user ",
				" FULL JOIN t_r_class_enrollments trce on tmu.id = trce.id_user where tmu.id = ?1 ").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, id).setMaxResults(1).getResultList();
		List<String> result = new ArrayList<String>();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			result.add(objArr[0] != null ? objArr[0].toString() : null);
			result.add(objArr[1] != null ? objArr[1].toString() : null);
			result.add(objArr[2] != null ? objArr[2].toString() : null);
			result.add(objArr[3] != null ? objArr[3].toString() : null);
			result.add(objArr[4] != null ? objArr[4].toString() : null);
			result.add(objArr[5] != null ? objArr[5].toString() : null);
		});
		return result;
	}

	@Override
	public Users getByIdNumber(String idNumber) throws Exception {
		String sql = sqlBuilder(
				" SELECT u.id, u.username, r.code, p.fullname, p.id_number, ",
				" p.birth_place, p.birth_date, p.email, p.phone, p.address ",
				" FROM t_m_users u ",
				" INNER JOIN t_m_profiles p ON p.id = u.id_profile",
				" INNER JOIN t_m_roles r ON r.id = u.id_role ",
				" WHERE p.id_number = ?1 AND u.is_active = ?2 ").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idNumber).setParameter(2, true).getResultList();
		List<Users> listUsers = HibernateUtils.bMapperList(listObj, Users.class, "id", "username", "idRole.code",
				"idProfile.fullName", "idProfile.idNumber", "idProfile.birthPlace", "idProfile.birthDate",
				"idProfile.email", "idProfile.phone", "idProfile.address");
		return resultCheck(listUsers);
	}

	@Override
	public Users getByIdDetailClass(String idDtlClass) throws Exception {
		List<Users> listResult = new ArrayList<>();
//		String sql = sqlBuilder("SELECT p.fullname, p.address, p.birth_date, p.birth_place, p.id_number, p.email, "
//				"p.phone FROM t_m_detail ").toString();
		return resultCheck(listResult);
	}
	
	@Override
	public Users getByIdClass(String idClass) throws Exception {
		String sql = sqlBuilder(
				" SELECT u.id, u.username, r.code, p.fullname, p.id_number, ",
				" p.birth_place, p.birth_date, p.email, p.phone, p.address ",
				" FROM t_m_users u ",
				" INNER JOIN t_m_profiles p ON p.id = u.id_profile ",
				" INNER JOIN t_m_roles r ON r.id = u.id_role ",
				" INNER JOIN t_m_classes c ON u.id = c.id_tutor ",
				" WHERE c.id = ?1 and u.is_active = ?2 ").toString();
		List<?> listResult = createNativeQuery(sql).setParameter(1, idClass)
				.setParameter(2, true).getResultList();
		List<Users> listUsers = HibernateUtils.bMapperList(listResult, Users.class, "id", "username", "idRole.code",
				"idProfile.fullName", "idProfile.idNumber", "idProfile.birthPlace", "idProfile.birthDate",
				"idProfile.email", "idProfile.phone", "idProfile.address");
		return resultCheck(listUsers);
	}

}
