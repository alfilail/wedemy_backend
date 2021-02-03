package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ClassesDao;
import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.model.Classes;
import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.elearning.model.Files;
import com.lawencon.elearning.model.Users;

@Service
public class ClassesServiceImpl extends BaseServiceImpl implements ClassesService {

	@Autowired
	private ClassesDao classesDao;

	@Autowired
	private ModuleRegistrationsService moduleRegistrationsService;

	@Autowired
	private DetailClassesService detailClassesService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private FilesService filesService;

	@Override
	public void insertClass(ClassesHelper helper, MultipartFile file) throws Exception {
		try {
			begin();
			if (null != helper.getClazz().getCode()) {
				Classes clazz = helper.getClazz();
				if (null != helper.getClazz().getIdTutor()) {
					Users tutor = usersService.getUserByIdNumber(clazz.getIdTutor().getIdProfile().getIdNumber());
					if (null != tutor) {
						clazz.setIdTutor(tutor);
						Files thumbnailImg = new Files();
						thumbnailImg.setCreatedBy(helper.getClazz().getCreatedBy());
						thumbnailImg.setFile(file.getBytes());
						thumbnailImg.setType(file.getContentType());
						filesService.insertFile(thumbnailImg);
						clazz.setIdFile(thumbnailImg);
						classesDao.insertClass(clazz, () -> validateInsert(clazz));
						if (helper.getDetailClass() != null) {
							DetailClasses detailClass = helper.getDetailClass();
							detailClass.setIdClass(clazz);
							detailClass.setViews(0);
							helper.setDetailClass(detailClass);
							detailClassesService.insertDetailClass(helper.getDetailClass());
							if (helper.getModule() != null) {
								moduleRegistrationsService.insertModuleRegistration(helper);
							}
						}
					} else {
						validateInsert(helper.getClazz());
					}
				} else {
					validateInsert(helper.getClazz());
				}
			} else {
				validateInsert(helper.getClazz());
			}
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public List<Classes> getAllClasses() throws Exception {
		return classesDao.getAllClasses();
	}

	@Override
	public Classes getClassById(String id) throws Exception {
		return classesDao.getClassById(id);
	}

	@Override
	public Classes getClassByCode(String code) throws Exception {
		return classesDao.getClassByCode(code);
	}

	@Override
	public void updateClass(Classes clazz, MultipartFile file) throws Exception {
		Files thumbnailImg = new Files();
		thumbnailImg.setFile(file.getBytes());
		thumbnailImg.setType(file.getContentType());
		filesService.insertFile(thumbnailImg);
		clazz.setIdFile(thumbnailImg);
		classesDao.updateClass(clazz, () -> validateUpdate(clazz));
	}

	@Override
	public void deleteClassById(String id) throws Exception {
		try {
			begin();
			classesDao.deleteClassById(id);
			commit();
		} catch(Exception e) {
			e.getMessage();
			rollback();
		}
	}

	private void validateInsert(Classes clazz) throws Exception {
		if (clazz.getCode() == null || clazz.getCode().trim().equals("")) {
			throw new Exception("Kode kelas tidak boleh kosong!");
		} else {
			Classes cls = getClassByCode(clazz.getCode());
			if (cls != null) {
				throw new Exception("Kode kelas yang dimasukkan sudah ada!");
			} else {
				if (clazz.getIdTutor() == null) {
					throw new Exception("Tutor tidak boleh kosong!");
				} else {
					Users user = usersService.getUserByIdNumber(clazz.getIdTutor().getIdProfile().getIdNumber());
					if (user == null) {
						throw new Exception("Id Tutor tidak ada!");
					} else {
						String[] type = clazz.getIdFile().getType().split("/");
						String ext = type[1];
						if (ext != null) {
							if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("png")
									|| ext.equalsIgnoreCase("jpeg")) {
							} else {
								throw new Exception("File harus gambar!");
							}
						} else if (clazz.getClassName() == null) {
							throw new Exception("Nama kelas tidak boleh kosong!");
						} else if (clazz.getDescription() == null) {
							throw new Exception("Dekripsi kelas tidak boleh kosong!");
						} else if (clazz.getQuota() == null) {
							throw new Exception("Quota kelas tidak boleh kosong!");
						}
					}
				}
			}
		}
	}

	private void validateUpdate(Classes clazz) throws Exception {
		if (clazz.getId() == null || clazz.getId().trim().equals("")) {
			throw new Exception("Id kelas tidak boleh kosong!");
		} else {
			Classes cls = classesDao.getClassById(clazz.getId());
			if (clazz.getVersion() == null) {
				throw new Exception("Kelas version tidak boleh kosong!");
			} else {
				if (clazz.getVersion() != cls.getVersion()) {
					throw new Exception("Kelas version tidak sama!");
				} else {
					if (clazz.getCode() == null || clazz.getCode().trim().equals("")) {
						throw new Exception("Kode kelas tidak boleh kosong!");
					} else {
						Classes clz = classesDao.getClassByCode(clazz.getCode());
						if (clz != null) {
							throw new Exception("Kode kelas tidak boleh sama");
						} else {
							if (clazz.getIdTutor() == null) {
								throw new Exception("Tutor tidak boleh kosong!");
							} else {
								Users user = usersService.getUserById(clazz.getIdTutor().getId());
								if (user == null) {
									throw new Exception("Id Tutor tidak ada!");
								} else {
									String[] type = clazz.getIdFile().getType().split("/");
									String ext = type[1];
									if (ext != null) {
										if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("png")
												|| ext.equalsIgnoreCase("jpeg")) {
										} else {
											throw new Exception("File harus gambar!");
										}
									} else if (clazz.getClassName() == null) {
										throw new Exception("Nama kelas tidak boleh kosong!");
									} else if (clazz.getDescription() == null) {
										throw new Exception("Dekripsi kelas tidak boleh kosong!");
									} else if (clazz.getQuota() == null) {
										throw new Exception("Quota kelas tidak boleh kosong!");
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
