package com.lawencon.elearning.service;

import com.lawencon.elearning.helper.Permission;

/**
 @author Nur Alfilail
*/

public interface PermissionService {

	Permission getPermission(String idDtlModuleRgs, String idUser) throws Exception;
}
