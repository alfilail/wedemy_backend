package com.lawencon.elearning.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.lawencon.elearning.model.Assignments;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

@Repository
public class AssignmentsDaoImpl extends ElearningBaseDaoImpl<Assignments> implements AssignmentsDao{

	@Override
	public void insertAssignment(Assignments assignments, Callback before) throws Exception {
		save(assignments, before, null);
	}

	@Override
	public List<Assignments> getAllAssignments() throws Exception {
		String query = sqlBuilder("SELECT id, code, start_datetime, end_datetime ",
				" FROM t_r_assignments").toString();
		List<Assignments> listAssignments = new ArrayList<>();
		List<?> listObj = createNativeQuery(query).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Assignments assignments = new Assignments();
			assignments.setId((String) objArr[0]);
			assignments.setCode((String) objArr[1]);
			Timestamp startDate = (Timestamp) objArr[2];
			Timestamp endDate = (Timestamp) objArr[3];
			assignments.setStartDateTime((LocalDateTime) startDate.toLocalDateTime());
			assignments.setEndDateTime((LocalDateTime) endDate.toLocalDateTime());
			
			listAssignments.add(assignments);
		});
		return listAssignments;
	}

	@Override
	public Assignments getAssignmentsById(String id) throws Exception {
		String query = sqlBuilder("SELECT id, code, start_datetime, end_datetime ",
				" FROM t_r_assignments WHERE id = ?1 ").toString();
		List<?> listObj = createNativeQuery(query).setParameter(1, id).getResultList();
		return HibernateUtils.bMapperList(listObj, Assignments.class, "id", "code", "startDateTime", "endDateTime").get(0);
	}

	@Override
	public Assignments getAssignmentsByCode(String code) throws Exception {
		String query = sqlBuilder("SELECT id, code, start_datetime, end_datetime ",
				" FROM t_r_assignments WHERE code = ?1 ").toString();
		List<?> listObj = createNativeQuery(query).setParameter(1, code).getResultList();
		return HibernateUtils.bMapperList(listObj, Assignments.class, "id", "code", "startDateTime", "endDateTime").get(0);
	}

}
