package com.lawencon.elearning.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.UsersDao;
import com.lawencon.elearning.helper.MailHelper;
import com.lawencon.elearning.model.General;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Roles;
//import com.lawencon.elearning.model.Roles;
import com.lawencon.elearning.model.Users;
import com.lawencon.elearning.util.MailUtil;

import net.bytebuddy.utility.RandomString;

@Service
public class UsersServiceImpl extends BaseServiceImpl implements UsersService {

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ProfilesService profilesService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private GeneralService generalService;
	
	@Autowired
	private MailUtil mailUtil;

	@Override
	public void insertUser(Users user) throws Exception {
		try {
			begin();
			usersDao.insertUser(user, () -> {
				validateInsert(user);
				Roles role = rolesService.getRoleByCode(user.getIdRole().getCode());
				user.setIdRole(role);
				profilesService.insertProfile(user.getIdProfile());
				user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
			});
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public List<Users> getAllUsers() throws Exception {
		return usersDao.getAllUsers();
	}

	@Override
	public Users getUserById(String id) throws Exception {
		return usersDao.getUserById(id);
	}

	@Override
	public Users getUserByUsername(String username) throws Exception {
		return usersDao.getUserByUsername(username);
	}

	@Override
	public void updateUser(Users user) throws Exception {
		usersDao.updateUser(user, () -> {
			validateUpdate(user);
			user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		});
	}

	@Override
	public void deleteUserById(String id, String idUser) throws Exception {
		begin();
		if(validateDelete(id) == true) {
			usersDao.softDeleteUserById(id, idUser);
			Users user = usersDao.getUserById(id);
			profilesService.softDeleteProfileById(user.getIdProfile().getId(), idUser);
		}
		else {
			usersDao.deleteUserById(id);
			Users user = usersDao.getUserById(id);
			profilesService.deleteProfileById(user.getIdProfile().getId());
		}
		commit();
	}

	@Override
	public Users updateUserPassword(Profiles profile) throws Exception {
		Profiles profiles = profilesService.getProfileByEmail(profile.getEmail());
		Users user = usersDao.getUserByIdProfile(profiles);
		String pass = generatePassword();
		System.out.println(pass);
		user.setUserPassword(passwordEncoder.encode(pass));
		usersDao.updateUser(user, () -> validateUpdate(user));
		System.out.println("Sending mail...");
		sendEmailResetPassword(pass, profiles);
		System.out.println("Done");
		return user;
	}

	private String generatePassword() {
		RandomString random = new RandomString(5);
		String pass = random.nextString();
		return pass;
	}

	private void sendEmailResetPassword(String pass, Profiles profile) throws Exception {
		General general = generalService.getTemplateEmail("pwdrst");
		String text = general.getTemplateHtml();
		
		text = text.replace("#1#", profile.getFullName());
		text = text.replace("#2#", pass);
		
		MailHelper mailHelper = new MailHelper();
//		mailHelper.setFrom("elearningalfione@gmail.com");
		mailHelper.setTo(profile.getEmail());
		mailHelper.setSubject("Password has been reset");
		mailHelper.setText(text);
		new MailServiceImpl(mailUtil, mailHelper).start();
	}

	private void validateInsert(Users user) throws Exception {
		if (user.getUsername() == null || user.getUsername().trim().equals("")) {
			throw new Exception("Username tidak boleh kosong");
		} else if (user.getUsername() != null) {
			Users usr = getUserByUsername(user.getUsername());
			if (usr != null) {
				throw new Exception("Username sudah ada");
			} else {
				if (user.getUserPassword() == null || user.getUserPassword().trim().equals("")) {
					throw new Exception("Password tidak boleh kosong");
				} else if (user.getIdProfile().getFullName() == null
						|| user.getIdProfile().getFullName().trim().equals("")) {
					throw new Exception("Nama Lengkap tidak boleh kosong");
				} else if (user.getIdProfile().getEmail() == null || user.getIdProfile().getEmail().trim().equals("")) {
					throw new Exception("Email tidak boleh kosong");
				} else if (user.getIdRole().getCode() != null) {
					Roles role = rolesService.getRoleByCode(user.getIdRole().getCode());
					if (role.getCode().equals("TTR") || role.getCode().equals("ADM")) {
						validateInsertExceptParticipant(user);
					}
					else {
						System.out.println("Sending Email......");
						sendEmailRegister(user.getIdProfile());
						System.out.println("Done");
					}
				}
			}
		}

	}

	private void validateInsertExceptParticipant(Users user) throws Exception {
		if (user.getIdProfile().getIdNumber() == null || user.getIdProfile().getIdNumber().trim().equals("")) {
			throw new Exception("Nomor Kartu Penduduk tidak boleh kosong");
		} else if (user.getIdProfile().getBirthPlace() == null || user.getIdProfile().getBirthPlace().trim().equals("")) {
			throw new Exception("Tempat Lahir tidak boleh kosong");
		} else if (user.getIdProfile().getBirthDate() == null || user.getIdProfile().getBirthDate().toString().trim().equals("")) {
			throw new Exception("Tanggal Lahir tidak boleh kosong");
		} else if (user.getIdProfile().getPhone() == null) {
			throw new Exception("Nomor Handphone tidak boleh kosong");
		} else if (user.getIdProfile().getEmail() == null) {
			throw new Exception("Email tidak boleh kosong");
		} 
	}

	private void validateUpdate(Users user) throws Exception {
		if(user.getId() == null || user.getId().trim().equals("")) {
			throw new Exception("Id user tidak boleh kosong");
		} else if (user.getId() != null) {
			Users usr = getUserById(user.getId());
			if(user.getUsername() == null || user.getUsername().trim().equals("")) {
				throw new Exception("Username tidak boleh kosong");
			} else if(user.getUserPassword() == null || user.getUserPassword().trim().equals("")) {
				throw new Exception("Password tidak boleh kosong");
			} else if(user.getUserPassword() != null) {
				if(passwordEncoder.matches(user.getUserPassword(), usr.getUserPassword())) {
					throw new Exception("Password tidak boleh sama dengan sebelumnya");
				}				
			}
		}
	}

	@Override
	public List<Users> getUsersByRoleCode(String code) throws Exception {
		return usersDao.getUsersByRoleCode(code);
	}
	
	@Override
	public Users getUserByIdNumber(String idNumber) throws Exception {
		return usersDao.getUserByIdNumber(idNumber);
	}
	
	private boolean validateDelete(String idUser) throws Exception {
		List<?> listObj = usersDao.validateDeleteUser(idUser);
		listObj.forEach(System.out::println);
		List<?> list =  listObj.stream().filter(val -> val != null)
				.collect(Collectors.toList());
		System.out.println(list.size());
		return list.size() > 0 ? true : false;
	}
	
	private void sendEmailRegister(Profiles profile) throws Exception {
		General general = generalService.getTemplateEmail("rgs");
		String text = general.getTemplateHtml();
	
		text = text.replace("#1#", profile.getFullName());
		
		MailHelper mailHelper = new MailHelper();
//		mailHelper.setFrom("elearningalfione@gmail.com");
		mailHelper.setTo(profile.getEmail());
		mailHelper.setSubject("Your account has been registered");
		mailHelper.setText(text);
		new MailServiceImpl(mailUtil, mailHelper).start();
	}

}
