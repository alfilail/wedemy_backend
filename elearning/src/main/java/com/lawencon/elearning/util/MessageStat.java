package com.lawencon.elearning.util;

public enum MessageStat {
	
	SUCCESS_CREATED("Data berhasil disimpan"),
	SUCCESS_CREATE_ENROLL("Daftar kelas berhasil"),
	SUCCESS_UPDATE("Data berhasil diubah"),
	SUCCESS_DELETE("Data berhasil dihapus"),
	SUCCESS_RETRIEVE("Data berhasil diambil"),
	FAILED("Gagal");
	
	public String msg;
	
	MessageStat(String msg) {
		this.msg = msg;
	}

}
