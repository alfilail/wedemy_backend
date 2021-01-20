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
@Table(name = "t_r_forums")
@EqualsAndHashCode(callSuper=false)
@Data
public class Forums extends BaseTransaction {
	
	private static final long serialVersionUID = 2066438852664914222L;
	
	@Column(name = "hdr_forum_datetime")
	private LocalDateTime forumDateTime;
	
	@Column(name = "content_text")
	private String contentText;
	
	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false)
	private Users idUser;
	
	@ManyToOne
	@JoinColumn(name = "id_dtl_module_registration", nullable = false)
	private DetailModuleRegistrations idDetailModuleRegistration;
	
	
}
