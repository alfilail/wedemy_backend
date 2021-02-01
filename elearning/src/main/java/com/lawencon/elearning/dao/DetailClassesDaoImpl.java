package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Classes;
import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.util.Callback;

@Repository
public class DetailClassesDaoImpl extends ElearningBaseDaoImpl<DetailClasses> implements DetailClassesDao {
	@Override
	public void insertDetailClass(DetailClasses detailClass, Callback before) throws Exception {
		save(detailClass, before, null);
	}

	@Override
	public List<DetailClasses> getAllDetailClass() throws Exception {
		return getAll();
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
		String sql = sqlBuilder("SELECT dc.id, c.class_name, c.description, c.thumbnail_img ",
				"FROM t_m_detail_classes dc INNER JOIN t_m_classes c ON dc.id_class = c.id WHERE c.id_tutor = ?1")
						.toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idTutor).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			DetailClasses detailClass = new DetailClasses();
			detailClass.setId((String) objArr[0]);
			Classes clazz = new Classes();
			clazz.setClassName((String) objArr[1]);
			clazz.setDescription((String) objArr[2]);
			clazz.setThumbnailImg((byte[]) objArr[3]);
			detailClass.setIdClass(clazz);
			listResult.add(detailClass);
		});
		return listResult;
	}
}