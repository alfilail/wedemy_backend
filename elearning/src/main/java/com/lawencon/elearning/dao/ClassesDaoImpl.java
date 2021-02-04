package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Classes;
import com.lawencon.util.Callback;

@Repository
public class ClassesDaoImpl extends ElearningBaseDaoImpl<Classes> implements ClassesDao{

	@Override
	public void insertClass(Classes clazz, Callback before) throws Exception {
		save(clazz, before, null);
	}

	@Override
	public List<Classes> getAllClasses() throws Exception {
		List<Classes> listClass = createQuery("FROM Classes WHERE isActive = ?1 ", Classes.class).setParameter(1, true)
				.getResultList();
		return listClass;
	}

	@Override
	public Classes getClassById(String id) throws Exception {
		List<Classes> listClass = createQuery("FROM Classes WHERE id = ?1 AND isActive = ?2 ", Classes.class).setParameter(1, id)
				.setParameter(2, true).getResultList();
		return resultCheck(listClass);
	}

	@Override
	public void updateClass(Classes clazz, Callback before) throws Exception {
		save(clazz, before, null, true, true);
	}

	@Override
	public void deleteClassById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public Classes getClassByCode(String code) throws Exception {
		List<Classes> clsList = createQuery("FROM Classes WHERE code = ?1 AND isActive = ?2", Classes.class)
				.setParameter(1, code).setParameter(2, true).getResultList();
		return clsList.size() > 0 ? clsList.get(0) : null;
	}

	@Override
	public void softDeleteClassById(String id, String idUser) throws Exception {
		updateNativeSQL("UPDATE t_m_classes SET is_active = FALSE", id, idUser);
	}

	@Override
	public List<?> validateDeleteClass(String id) throws Exception {
		String sql = sqlBuilder("SELECT dc.id FROM t_m_classes c ",
				" FULL JOIN t_m_detail_classes dc ON dc.id_class = c.id ",
				" WHERE c.id = ?1").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, id).setMaxResults(1).getResultList();
		List<String> result = new ArrayList<>();
		listObj.forEach(val -> {
			Object obj = (Object) val;
			result.add(obj != null ? obj.toString() : null);
		});
		return result;
	}

	@Override
	public List<Classes> getAllInactiveClass() throws Exception {
		List<Classes> listClass = createQuery("FROM Classes WHERE isActive = ?1 ", Classes.class).setParameter(1, false)
				.getResultList();
		return listClass;
	}
	
	@Override
	public void updateClassIsActive(String id, String idUser) throws Exception {
		updateNativeSQL("UPDATE t_m_classes SET is_active = true", id, idUser);
	}
	
	@Override
	public Classes getInActiveClassById(String id) throws Exception {
		List<Classes> listClass = createQuery("FROM Classes WHERE isActive = ?1 ", Classes.class).setParameter(1, false)
				.getResultList();
		return listClass.size() > 0 ? listClass.get(0) : null;
	}

}