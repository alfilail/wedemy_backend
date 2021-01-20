package com.lawencon.elearning.model;

import java.sql.Blob;
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
	private Blob file;
	
	@Column(name = "score", length = 3, nullable = false)
	private Double score;
	
	@Column(name = "submit_datetime", nullable = false)
	private LocalDateTime submitDateTime;
	
	@ManyToOne
	@JoinColumn(name = "id_participant", nullable = false)
	private Users idParticipant;
	
	@ManyToOne
	@JoinColumn(name = "id_grade", nullable = false)
	private Grades idGrade;
	
	@ManyToOne
	@JoinColumn(name = "id_submission_status", nullable = false)
	private SubmissionStatus idSubmissionStatus;
	
	@ManyToOne
	@JoinColumn(name = "id_assignments", nullable = false)
	private Assignments idAssignments;
	
}
