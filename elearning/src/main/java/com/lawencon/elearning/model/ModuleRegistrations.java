package com.lawencon.elearning.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lawencon.model.BaseTransaction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "t_r_module_registrations")
@EqualsAndHashCode(callSuper = false)
@Data
public class ModuleRegistrations extends BaseTransaction{
	
	private static final long serialVersionUID = 168424229267473712L;

	@ManyToOne
	@JoinColumn(name = "id_class", nullable = false)
	private Classes idClass;
	
	@ManyToOne
	@JoinColumn(name = "id_module", nullable = false)
	private Modules idModule;
	
}