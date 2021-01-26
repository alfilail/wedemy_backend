package com.lawencon.elearning.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
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
@Table(name = "t_r_assignment_submissions")
@EqualsAndHashCode(callSuper = false)
@Data
public class AssignmentSubmissions extends BaseTransaction {

	private static final long serialVersionUID = -4540610639400514155L;

	@Column(name = "file", nullable = false)
	private byte[] file;

	@Column(name = "file_type")
	private String fileType;

	@Column(name = "submit_datetime", nullable = false)
	private LocalDateTime submitDateTime;

	@ManyToOne
	@JoinColumn(name = "id_participant", nullable = false)
	private Users idParticipant;

	@ManyToOne
	@JoinColumn(name = "id_dtl_module_rgs")
	private DetailModuleRegistrations idDetailModuleRegistration;

}
