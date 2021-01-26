package com.lawencon.elearning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lawencon.model.BaseTransaction;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Nur Alfilail
 */

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "t_r_evaluations")
@EqualsAndHashCode(callSuper = false)
@Data
public class Evaluations extends BaseTransaction {
	private static final long serialVersionUID = -6413847636799608477L;

	@OneToOne
	@JoinColumn(name = "id_assignment_submission")
	private AssignmentSubmissions idAssignmentSubmission;

	@Column(name = "score")
	private Double score;

	@ManyToOne
	@JoinColumn(name = "id_grade")
	private Grades idGrade;

}
