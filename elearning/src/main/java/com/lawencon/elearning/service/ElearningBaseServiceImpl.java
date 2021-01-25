package com.lawencon.elearning.service;

import com.lawencon.base.BaseServiceImpl;

public class ElearningBaseServiceImpl extends BaseServiceImpl {
	protected StringBuilder bBuilder(String... datas) {
		StringBuilder b = new StringBuilder();
		for (String d : datas) {
			b.append(d);
		}
		return b;
	}
}
