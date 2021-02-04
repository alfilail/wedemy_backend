package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Classes;
import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.elearning.model.Files;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Users;
import com.lawencon.util.Callback;

@Repository
public class DetailClassesDaoImpl extends ElearningBaseDaoImpl<DetailClasses> implements DetailClassesDao {
	@Override
	public void insertDetailClass(DetailClasses detailClass, Callback before) throws Exception {
		save(detailClass, before, null);
	}

	@Override
	public List<DetailClasses> getAllDetailClass() throws Exception {
		List<DetailClasses> listDtlClasses = createQuery("FROM DetailClasses WHERE isActive = ?1 ", DetailClasses.class).setParameter(1, true)
				.getResultList();
		return listDtlClasses;
	}

	@Override
	public DetailClasses getDetailClassById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public DetailClasses getDetailClassByCode(String code) throws Exception {
		List<DetailClasses> detailClassList = createQuery("FROM DetailClasses WHERE code = ?1 ", DetailClasses.class)
				.setParameter(1, code).getResultList();
		return detailClassList.size() > 0 ? detailClassList.get(0) : null;
	}

	@Override
	public List<DetailClasses> getTutorClasses(String idTutor) throws Exception {
		List<DetailClasses> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT dc.id, c.class_name, c.description, f.file, c.id_tutor, p.fullname ",
				"FROM t_m_detail_classes dc INNER JOIN t_m_classes c ON dc.id_class = c.id ",
				"INNER JOIN t_m_files f ON c.id_file = f.id INNER JOIN t_m_users u ON c.id_tutor = u.id ",
				"INNER JOIN t_m_profiles p ON u.id_profile = p.id WHERE c.id_tutor = ?1").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idTutor).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			DetailClasses detailClass = new DetailClasses();
			detailClass.setId((String) objArr[0]);
			Classes clazz = new Classes();
			clazz.setClassName((String) objArr[1]);
			clazz.setDescription((String) objArr[2]);
			Files file = new Files();
			file.setFile((byte[]) objArr[3]);
			clazz.setIdFile(file);
			Users user = new Users();
			user.setId((String) objArr[4]);
			Profiles profile = new Profiles();
			profile.setFullName((String) objArr[5]);
			user.setIdProfile(profile);
			clazz.setIdTutor(user);
			detailClass.setIdClass(clazz);
			listResult.add(detailClass);
		});
		return listResult;
	}

	@Override
	public void updateViews(String id) throws Exception {
		String sql = sqlBuilder("UPDATE t_m_detail_classes SET views = (views + 1) WHERE id = ?1").toString();
		createNativeQuery(sql).setParameter(1, id).executeUpdate();
	}

	@Override
	public List<DetailClasses> getPopularClasses() throws Exception {
		List<DetailClasses> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT c.class_name, c.description, f.file, dc.id ",
				"FROM t_m_detail_classes dc INNER JOIN t_m_classes c ON dc.id_class = c.id ",
				"INNER JOIN t_m_files f ON c.id_file = f.id ORDER BY dc.views DESC LIMIT 3").toString();
		List<?> listObj = createNativeQuery(sql).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Classes clazz = new Classes();
			clazz.setClassName((String) objArr[0]);
			clazz.setDescription((String) objArr[1]);
			Files thumbnailImg = new Files();
			thumbnailImg.setFile((byte[]) objArr[2]);
			clazz.setIdFile(thumbnailImg);
			DetailClasses detailClass = new DetailClasses();
			detailClass.setId((String) objArr[3]);
			detailClass.setIdClass(clazz);
			listResult.add(detailClass);
		});
		return listResult;
	}
	
	@Override
	public void deleteClassById(String id, String idUser) throws Exception {
		updateNativeSQL("UPDATE t_m_detail_classes SET is_active = FALSE", id, idUser);
	}
	
	@Override
	public List<DetailClasses> getAllDetailClassByIdClass(String idClass) throws Exception {
		List<DetailClasses> detailClassList = createQuery("FROM DetailClasses WHERE idClass.id = ?1 ", DetailClasses.class)
				.setParameter(1, idClass).getResultList();
		return detailClassList.size() > 0 ? detailClassList : null;
	}

	@Override
	public void updateDetailClass(DetailClasses dtlClass, Callback before) throws Exception {
		save(dtlClass, before, null);
	}

	@Override
	public List<DetailClasses> getAllInactiveDetailClass() throws Exception {
		List<DetailClasses> listDtlClasses = createQuery("FROM DetailClasses WHERE isActive = ?1 ", DetailClasses.class).setParameter(1, false)
				.getResultList();
		return listDtlClasses;
	}
}