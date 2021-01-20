package com.lawencon.elearning.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lawencon.model.BaseMaster;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "t_m_learning_materials")
@EqualsAndHashCode(callSuper = false)
@Data
public class LearningMaterials extends BaseMaster {

	private static final long serialVersionUID = 2720057305959510748L;

	@Column(name = "code", length = 10, unique = true, nullable = false)
	private String code;

	@Column(name = "learning_material_name", length = 35, nullable = false)
	private String learningMaterialName;

	@Column(name = "description", nullable = false, columnDefinition = "text")
	private String description;

	@Column(name = "file", nullable = false)
	private Blob file;
}
