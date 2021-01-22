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
	
	@Column(name = "description", nullable = false, columnDefinition = "text")
	private String description;
	
	@Column(name = "thubmnail_img", nullable = false)
	private byte[] thubmnailImg;
	
	@Column(name = "file_type")
	private String fileType;
	
	@Column(name = "quota", length = 3, nullable = false)
	private Integer quota;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;
	
	@ManyToOne
	@JoinColumn(name = "id_tutor", nullable = false)
	private Users idTutor;
}