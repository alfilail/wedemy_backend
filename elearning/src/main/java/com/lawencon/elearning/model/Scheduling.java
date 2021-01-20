package com.lawencon.elearning.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.exolab.castor.types.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lawencon.model.BaseTransaction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "t_r_scheduling")
@EqualsAndHashCode(callSuper = false)
@Data
public class Scheduling extends BaseTransaction {
	
	private static final long serialVersionUID = -6561365319769189182L;

	@Column(name = "schedule_date", nullable = false)
	private Date scheduleDate;

	@Column(name = "start_datetime", nullable = false)
	private DateTime startDateTime;

	@Column(name = "end_datetime", nullable = false)
	private DateTime endDateTime;

	@ManyToOne
	@JoinColumn(name = "id_module_rgs", nullable = false)
	private ModuleRegistrations idModuleRegistration;
}