package com.lawencon.elearning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.exolab.castor.types.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lawencon.model.BaseMaster;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "t_m_classes")
@EqualsAndHashCode(callSuper = false)
@Data
public class Classes extends BaseMaster {
	
	private static final long serialVersionUID = 7106895578165586513L;

	@Column(name = "code", length = 6, unique = true, nullable = false)
	private String code;
	
	@Column(name = "class_name", length = 30, nullable = false)
	private String className;
	
	@Column(name = "description", length = 100, nullable = false)
	private String description;
	
	@Column(name = "thubmnail_img", length = 100, nullable = false)
	private String thubmnailImg;
	
	@Column(name = "quota", length = 3, nullable = false)
	private Integer quota;
	
	@Column(name = "start_datetime", nullable = false)
	private DateTime startDateTime;
	
	@Column(name = "end_datetime", nullable = false)
	private DateTime endDateTime;
	
	@ManyToOne
	@JoinColumn(name = "id_tutor", nullable = false)
	private Users idTutor;
}