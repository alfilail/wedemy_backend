package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ClassesDao;
import com.lawencon.elearning.helper.ClassInput;
import com.lawencon.elearning.helper.TotalClassAndUser;
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
	public void insert(ClassInput classInput, MultipartFile file) throws Exception {
		try {
			begin();
			if (classInput.getClazz() != null) {
				Classes clazz = classInput.getClazz();
				Files thumbnailImg = new Files();
				thumbnailImg.setFile(file.getBytes());
				thumbnailImg.setType(file.getContentType());
				thumbnailImg.setName(file.getOriginalFilename());
				filesService.insert(thumbnailImg);
				clazz.setIdFile(thumbnailImg);
				classesDao.insert(clazz, () -> validateInsert(clazz));
				if (classInput.getDetailClass() != null) {
					DetailClasses detailClass = classInput.getDetailClass();
					detailClass.setCreatedBy(clazz.getCreatedBy());
					detailClass.setIdClass(clazz);
					detailClass.setViews(0);
					classInput.setDetailClass(detailClass);
					detailClassesService.insert(classInput.getDetailClass());
					if (classInput.getModule() != null) {
						moduleRegistrationsService.insertModuleRegistration(classInput);
					}
				}
			}
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public void update(Classes clazz, MultipartFile file) throws Exception {
		try {
			begin();
			if (file != null && !file.isEmpty()) {
				Files logo = new Files();
				logo.setFile(file.getBytes());
				logo.setType(file.getContentType());
				logo.setName(file.getOriginalFilename());
				filesService.update(logo);
				clazz.setIdFile(logo);
			} else {
				Files logoPict = filesService.getById(clazz.getIdFile().getId());
				clazz.setIdFile(logoPict);
			}
			if (clazz.getIdTutor() == null) {
				System.out.println(clazz.getIdTutor());
				Users tutor = usersService.getByIdClass(clazz.getId());
				clazz.setIdTutor(tutor);
			}
			classesDao.update(clazz, () -> validateUpdate(clazz));
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}

	}

	@Override
	public void deleteById(String id, String idUser) throws Exception {
		try {
			begin();
			classesDao.softDeleteClassById(id, idUser);
			List<DetailClasses> dtlClass = detailClassesService.getAllByIdClass(id);
			for (DetailClasses dtl : dtlClass) {
				detailClassesService.deleteById(dtl.getId(), idUser);
			}
			commit();
		} catch (Exception e) {
			e.getMessage();
			rollback();
		}
	}

	@Override
	public void reactivate(String id, String idUser) throws Exception {
		classesDao.reactivateClass(id, idUser);
	}

	@Override
	public Classes getById(String id) throws Exception {
		return classesDao.getClassById(id);
	}

	@Override
	public Classes getByCode(String code) throws Exception {
		return classesDao.getClassByCode(code);
	}

	@Override
	public Classes getInActiveById(String id) throws Exception {
		return classesDao.getInActiveById(id);
	}

	@Override
	public TotalClassAndUser getTotalClassAndUser() throws Exception {
		return classesDao.getTotalClassAndUser();
	}

	@Override
	public List<Classes> getAll() throws Exception {
		return classesDao.getAllClass();
	}

	@Override
	public List<Classes> getAllInactive() throws Exception {
		return classesDao.getAllInactive();
	}

	private void validateInsert(Classes clazz) throws Exception {
		if (clazz.getCode() == null || clazz.getCode().trim().equals("")) {
			throw new Exception("Kode kelas tidak boleh kosong!");
		} else {
			Classes cls = getByCode(clazz.getCode());
			if (cls != null) {
				throw new Exception("Kode kelas yang dimasukkan sudah ada!");
			} else {
				if (clazz.getIdTutor() == null) {
					throw new Exception("Tutor tidak boleh kosong!");
				} else {
					Users user = usersService.getByIdNumber(clazz.getIdTutor().getIdProfile().getIdNumber());
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
						if (!cls.getCode().equalsIgnoreCase(clazz.getCode())) {
							Classes clz = classesDao.getClassByCode(clazz.getCode());
							if (clz != null) {
								throw new Exception("Kode kelas tidak boleh sama");
							}
						} else {
							if (clazz.getIdFile() != null) {
								String[] type = clazz.getIdFile().getType().split("/");
								String ext = type[1];
								if (ext != null) {
									if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("png")
											|| ext.equalsIgnoreCase("jpeg")) {
									} else {
										throw new Exception("File harus gambar!");
									}
								}
							}
							if (clazz.getClassName() == null) {
								throw new Exception("Nama kelas tidak boleh kosong!");
							}
							if (clazz.getDescription() == null) {
								throw new Exception("Dekripsi kelas tidak boleh kosong!");
							}
							if (clazz.getQuota() == null) {
								throw new Exception("Quota kelas tidak boleh kosong!");
							}
						}
					}
				}
			}
		}
	}

}
