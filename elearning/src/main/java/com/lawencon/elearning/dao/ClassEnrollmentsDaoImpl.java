package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.helper.CertificateHelper;
import com.lawencon.elearning.model.ClassEnrollments;
import com.lawencon.elearning.model.Classes;
import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.elearning.model.Files;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Users;
import com.lawencon.util.Callback;

@Repository
public class ClassEnrollmentsDaoImpl extends ElearningBaseDaoImpl<ClassEnrollments> implements ClassEnrollmentsDao {
	@Override
	public void insertClassEnrollment(ClassEnrollments classEnrollment, Callback before) throws Exception {
		save(classEnrollment, before, null, true, true);
	}

	@Override
	public List<ClassEnrollments> getAllClassEnrollments() throws Exception {
		return getAll();
	}

	@Override
	public ClassEnrollments getclassEnrollmentByCode(String code) throws Exception {
		return null;
	}

	@Override
	public ClassEnrollments getClassEnrollmentById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<ClassEnrollments> getAllClassEnrollmentsByIdUser(String id) throws Exception {
		List<ClassEnrollments> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT dc.id, c.class_name, c.description, f.file, c.id_tutor, p.fullname ",
				"FROM t_r_class_enrollments ce INNER JOIN t_m_detail_classes dc ON ce.id_detail_class = dc.id ",
				"INNER JOIN t_m_classes c ON dc.id_class = c.id INNER JOIN t_m_files f ON c.id_file = f.id ",
				"INNER JOIN t_m_users u ON c.id_tutor = u.id ",
				"INNER JOIN t_m_profiles p ON u.id_profile = p.id WHERE ce.id_user =?1").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();
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
			ClassEnrollments classEnrollment = new ClassEnrollments();
			classEnrollment.setIdDetailClass(detailClass);
			listResult.add(classEnrollment);
		});
		return listResult;
	}

	@Override
	public ClassEnrollments getClassEnrollmentByIdDtlClassAndIdUser(String idDtlClass, String idUser) {
		List<ClassEnrollments> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT id FROM t_r_class_enrollments WHERE id_detail_class = ?1 AND id_user =?2")
				.toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idDtlClass).setParameter(2, idUser).getResultList();
		listObj.forEach(val -> {
			Object obj = (Object) val;
			ClassEnrollments classEnrollment = new ClassEnrollments();
			classEnrollment.setId((String) obj);
			listResult.add(classEnrollment);
		});
		return listResult.size() > 0 ? listResult.get(0) : null;
	}

	@Override
	public void deleteclassEnrollmentById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public void updateClassEnrollment(ClassEnrollments classEnrollment, Callback before) throws Exception {
		save(classEnrollment, before, null, true, true);
	}

	@Override
	public List<?> getCertificate(String idUser, String idClass) throws Exception {
		String query = sqlBuilder(" select tmp.fullname, tmc.class_name from t_r_class_enrollments trce ",
				" inner join t_m_users tmu on tmu.id = trce.id_user ",
				" inner join t_m_profiles tmp on tmp.id = tmu.id_profile ",
				" inner join t_m_detail_classes tmdc on tmdc.id = trce.id_detail_class ",
				" inner join t_m_classes tmc on tmc.id = tmdc.id_class ", " where trce.id_user = ?1 and tmc.id = ?2")
						.toString();
		List<CertificateHelper> listCertificate = new ArrayList<>();
		List<?> listObj = createNativeQuery(query).setParameter(1, idUser).setParameter(2, idClass).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Profiles profile = new Profiles();
			profile.setFullName((String) objArr[0]);
			CertificateHelper certificateHelper = new CertificateHelper();
			certificateHelper.setFullname(profile);
			Classes clazz = new Classes();
			clazz.setClassName((String) objArr[1]);
			certificateHelper.setClassName(clazz);
			listCertificate.add(certificateHelper);
		});
		return listCertificate;
	}

	@Override
	public Integer getTotalParticipantsByIdDtlClass(String id) throws Exception {
		List<Integer> total = new ArrayList<>();
		String sql = sqlBuilder("SELECT COUNT(*) FROM t_r_class_enrollments WHERE id_detail_class =?1 ",
				"GROUP BY id_detail_class").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();
		listObj.forEach(val -> {
			Object obj = (Object) val;
			total.add(Integer.valueOf(obj.toString()));
		});
		return total.size() > 0 ? total.get(0) : null;
	}
}
