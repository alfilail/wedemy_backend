package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.AssignmentSubmissions;
import com.lawencon.elearning.model.Files;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.util.EmptyField;
import com.lawencon.util.Callback;

@Repository
public class AssignmentSubmissionsDaoImpl extends ElearningBaseDaoImpl<AssignmentSubmissions>
		implements AssignmentSubmissionsDao {

	@Override
	public void insertAssignmentSubmission(AssignmentSubmissions assignmentSubmission, Callback before)
			throws Exception {
		save(assignmentSubmission, before, null);
	}

	@Override
	public List<AssignmentSubmissions> getAllAssignmentSubmissions() throws Exception {
		return getAll();
	}

	@Override
	public List<AssignmentSubmissions> getAllByIdDtlModuleRgs(String idDtlModuleRgs) throws Exception {
		List<AssignmentSubmissions> listResult = new ArrayList<>();
		return listResult;
	}
	
	@Override
	public AssignmentSubmissions getByIdDtlModuleRgsAndIdParticipant(String idDtlModuleRgs, String idParticipant)
			throws Exception {
		List<AssignmentSubmissions> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT asm.id, asm.version, f.file FROM t_r_assignment_submissions asm ",
				"INNER JOIN t_m_files f ON asm.id_file = f.id WHERE asm.id_dtl_module_rgs = ?1 AND ",
				"asm.id_participant = ?2").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idDtlModuleRgs).setParameter(2, idParticipant)
				.getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			AssignmentSubmissions submission = new AssignmentSubmissions();
			submission.setId(objArr[0] != null ? (String) objArr[0] : EmptyField.EMPTY.msg);
			submission.setVersion(objArr[1] != null ? Long.valueOf(objArr[1].toString()) : null);
			Files file = new Files();
			file.setFile(objArr[2] != null ? (byte[]) objArr[2] : null);
			submission.setIdFile(file);
			listResult.add(submission);
		});
		return resultCheck(listResult);
	}

	@Override
	public AssignmentSubmissions getAssignmentSubmissionByCode(String code) throws Exception {
		return null;
	}

	@Override
	public AssignmentSubmissions getAssignmentSubmissionById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public Profiles getTutorProfile(AssignmentSubmissions assignmentSubmission) throws Exception {
		Profiles tutor = new Profiles();
		String sql = sqlBuilder("SELECT p.fullname, p.email FROM t_r_assignment_submissions asm ",
				"INNER JOIN t_r_detail_module_registrations dmr ON asm.id_dtl_module_rgs = dmr.id ",
				"INNER JOIN t_r_module_registrations mr ON dmr.id_module_rgs = mr.id ",
				"INNER JOIN t_m_detail_classes dc ON mr.id_detail_class = dc.id ",
				"INNER JOIN t_m_classes c ON dc.id_class = c.id INNER JOIN t_m_users u ON c.id_tutor = u.id ",
				"INNER JOIN t_m_profiles p ON u.id_profile = p.id WHERE asm.id_dtl_module_rgs = ?1").toString();
		List<?> listObj = createNativeQuery(sql)
				.setParameter(1, assignmentSubmission.getIdDetailModuleRegistration().getId()).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			tutor.setFullName((String) objArr[0]);
			tutor.setEmail((String) objArr[1]);
		});
		return tutor;
	}

	@Override
	public Profiles getParticipantProfile(AssignmentSubmissions assignmentSubmission) throws Exception {
		Profiles participant = new Profiles();
		String sql = sqlBuilder(" SELECT p.fullname, p.email FROM t_r_assignment_submissions asm ",
				" INNER JOIN t_m_users u ON asm.id_participant = u.id ",
				" INNER JOIN t_m_profiles p ON u.id_profile = p.id ", " WHERE asm.id_dtl_module_rgs = ?1 ").toString();
		List<?> listObj = createNativeQuery(sql)
				.setParameter(1, assignmentSubmission.getIdDetailModuleRegistration().getId()).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			participant.setFullName((String) objArr[0]);
			participant.setEmail((String) objArr[1]);
		});
		return participant;
	}

	@Override
	public void updateAssignmentSubmission(AssignmentSubmissions assignmentSubmission, Callback before)
			throws Exception {
		save(assignmentSubmission, before, null, true, true);
	}

	@Override
	public void deleteAssignmentSubmissionById(String id) throws Exception {
		deleteById(id);
	}

}