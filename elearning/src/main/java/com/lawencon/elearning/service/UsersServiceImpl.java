package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.UsersDao;
import com.lawencon.elearning.helper.RegisterHelper;
import com.lawencon.elearning.model.Profiles;
//import com.lawencon.elearning.model.Roles;
import com.lawencon.elearning.model.Users;

import net.bytebuddy.utility.RandomString;

@Service
public class UsersServiceImpl extends BaseServiceImpl implements UsersService {

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ProfilesService profilesService;
	
//	@Autowired
//	private RolesService rolesService;
	
	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public void insertUser(RegisterHelper register) throws Exception {
		try {
			begin();
			profilesService.insertProfile(register.getProfile());
			Users user = register.getUser();
//		Roles role = rolesService.getRoleById(user.getIdRole().getId());
//		user.setIdRole(role);
			user.setIdProfile(register.getProfile());
			user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
			usersDao.insertUser(user, () -> validateInsert(user));
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
		usersDao.updateUser(user, () -> validateUpdate(user));
	}

	@Override
	public void deleteUserById(String id) throws Exception {
		usersDao.deleteUserById(id);
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
		sendEmail(pass, profiles);
		System.out.println("Done");
		return user;
	}
	
	private String generatePassword() {
		RandomString random = new RandomString(5);
		String pass = random.nextString();
		return pass;
	}
	
	private void sendEmail(String pass, Profiles profile) throws Exception {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(profile.getEmail());
		msg.setSubject("Password has been reset");
		msg.setText("Dear "+ profile.getFullName() +",\nPassword has been reset. Here is your new password."
				+ "\nPassword : " + pass
				+ "\n\nClick http://localhost:8080/api/login to login. \n"
				+ "\n Save information of your account and "
				+" change your password soon for your security account."
				+" \n \n Best Regards, \n Elearning Alfione");
		javaMailSender.send(msg);
	}

	private void validateInsert(Users user) {

	}

	private void validateUpdate(Users user) {

	}

}
