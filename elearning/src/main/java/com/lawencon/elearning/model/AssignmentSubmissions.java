package com.lawencon.elearning.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private Blob file;
	
	@Column(name = "score", length = 3, nullable = false)
	private Float score;
	
}
