package com.lawencon.elearning.model;

import java.time.LocalTime;

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
@Table(name = "t_r_presence")
@EqualsAndHashCode(callSuper = false)
@Data
public class Presence extends BaseTransaction{
	
	private static final long serialVersionUID = 4676572555617809925L;

	@Column(name = "presence_time", nullable = false)
	private LocalTime presenceTime;
	
	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false)
	private Users idUser;
	
	@ManyToOne
	@JoinColumn(name = "id_schedule", nullable = false)
	private Scheduling idSchedule;
}