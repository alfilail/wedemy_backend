package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Assignments;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

@Repository
public class AssignmentsDaoImpl extends ElearningBaseDaoImpl<Assignments> implements AssignmentsDao {

	@Override
	public void insertAssignment(Assignments assignments, Callback before) throws Exception {
		save(assignments, before, null);
	}

	@Override
	public List<Assignments> getAllAssignments() throws Exception {
		String query = sqlBuilder("SELECT id, code FROM t_r_assignments").toString();
		List<Assignments> listAssignments = new ArrayList<>();
		List<?> listObj = createNativeQuery(query).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Assignments assignments = new Assignments();
			assignments.setId((String) objArr[0]);
			assignments.setCode((String) objArr[1]);

			listAssignments.add(assignments);
		});
		return listAssignments;
	}

	@Override
	public Assignments getAssignmentsById(String id) throws Exception {
		String query = sqlBuilder("SELECT id, code FROM t_r_assignments WHERE id = ?1 ").toString();
		List<?> listObj = createNativeQuery(query).setParameter(1, id).getResultList();
		return HibernateUtils.bMapperList(listObj, Assignments.class, "id", "code").get(0);
	}

	@Override
	public Assignments getAssignmentsByCode(String code) throws Exception {
		String query = sqlBuilder("SELECT id, code FROM t_r_assignments WHERE code = ?1 ").toString();
		List<?> listObj = createNativeQuery(query).setParameter(1, code).getResultList();
		return HibernateUtils.bMapperList(listObj, Assignments.class, "id", "code")
				.get(0);
	}

}
