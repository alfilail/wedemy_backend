package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.DetailClassesDao;
import com.lawencon.elearning.model.Classes;
import com.lawencon.elearning.model.DetailClasses;

@Service
public class DetailClassesServiceImpl extends ElearningBaseServiceImpl implements DetailClassesService {
	@Autowired
	private DetailClassesDao detailClassesDao;

	@Autowired
	private ClassesService classService;
	
	@Autowired
	private ClassEnrollmentService classEnrollmentService;

	@Override
	public void insertDetailClass(DetailClasses detailClass) throws Exception {
		Classes clazz = classService.getClassById(detailClass.getIdClass().getId());
		detailClass.setCode(generateCodeDetailClass(clazz.getCode()));
		detailClassesDao.insertDetailClass(detailClass, () -> validateInsert(detailClass));
	}

	@Override
	public List<DetailClasses> getAllDetailClass() throws Exception {
		return detailClassesDao.getAllDetailClass();
	}

	@Override
	public DetailClasses getDetailClassById(String id) throws Exception {
		DetailClasses dtlClass = detailClassesDao.getDetailClassById(id);
		dtlClass.setTotalParticipant(classEnrollmentService.getTotalParticipantsByIdDtlClass(id));
		return dtlClass;
	}
	
	@Override
	public DetailClasses getDetailClassByCode(String code) throws Exception {
		return detailClassesDao.getDetailClassByCode(code);
	}
	
	private void validateInsert(DetailClasses detailClass) throws Exception {
		if(detailClass.getStartDate() == null) {
			throw new Exception("Tanggal mulai detail kelas tidak boleh kosong!");
		}
		else if(detailClass.getEndDate() == null) {
			throw new Exception("Tanggal akhir detail kelas tidak boleh kosong!");
		}
		else if(detailClass.getStartTime() == null) {
			throw new Exception("Waktu mulai detail kelas tidak boleh kosong!");
		}
		else if(detailClass.getEndTime() == null) {
			throw new Exception("Waktu akhir detail kelas tidak boleh kosong!");
		}
	}
	
	private String generateCodeDetailClass(String classCode) {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yy-MM-dd");
		String formattedDate = localDate.format(myFormat);
		String date = bBuilder(formattedDate).toString();
		date = date.replaceAll("-", "");
		String detailClassCode = bBuilder(classCode, date).toString();
		return detailClassCode;
	}
}