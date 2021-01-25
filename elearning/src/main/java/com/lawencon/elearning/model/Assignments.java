package com.lawencon.elearning.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lawencon.model.BaseTransaction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "t_r_assignments")
@EqualsAndHashCode(callSuper = false)
@Data
public class Assignments extends BaseTransaction {
	
	private static final long serialVersionUID = -5535282774180832869L;

	@Column(name = "code", length = 6, unique = true, nullable = false)
	private String code;
	
	@Column(name = "file", nullable = false)
	private byte[] file;
	
	@Column(name = "file_type")
	private String fileType;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "start_datetime", nullable = false)
	private LocalDateTime startDateTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "end_datetime", nullable = false)
	private LocalDateTime endDateTime;
	
	@ManyToOne
	@JoinColumn(name = "id_dtl_module_rgs")
	private DetailModuleRegistrations idDetailModuleRegistration;
	
	@ManyToOne
	@JoinColumn(name = "id_assignment_type")
	private AssignmentTypes idAssignmentType;
}