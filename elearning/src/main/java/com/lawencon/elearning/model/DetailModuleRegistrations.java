package com.lawencon.elearning.model;

import java.time.LocalDate;

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
@Table(name = "t_r_detail_module_registrations")
@EqualsAndHashCode(callSuper = false)
@Data
public class DetailModuleRegistrations extends BaseTransaction {

	private static final long serialVersionUID = -8107533276767612098L;

	@ManyToOne
	@JoinColumn(name = "id_module_rgs", nullable = false)
	private ModuleRegistrations idModuleRegistration;

	@ManyToOne
	@JoinColumn(name = "id_learning_material", nullable = false)
	private LearningMaterials idLearningMaterial;

	@Column(name = "order_number", nullable = false)
	private Integer orderNumber;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "schedule_date", nullable = false)
	private LocalDate scheduleDate;
}
