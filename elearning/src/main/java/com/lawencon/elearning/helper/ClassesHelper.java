package com.lawencon.elearning.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lawencon.elearning.model.Classes;
import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.elearning.model.Modules;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class ClassesHelper{
	private Classes clazz;
	private DetailClasses detailClass;
	private Modules[] module;
}
