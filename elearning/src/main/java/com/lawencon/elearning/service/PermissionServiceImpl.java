package com.lawencon.elearning.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.elearning.helper.Permission;

/**
 * @author Nur Alfilail
 */

public class PermissionServiceImpl extends ElearningBaseServiceImpl implements PermissionService {

	@Autowired
	private PresencesService presenceService;

	@Autowired
	private ApprovementsRenewalService arService;

	@Override
	public Permission getPermission(String idDtlModuleRgs, String idUser) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
