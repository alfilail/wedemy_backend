package com.lawencon.elearning.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lawencon.model.BaseMaster;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Nur Alfilail
 */

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "t_m_detail_classes")
@EqualsAndHashCode(callSuper = false)
@Data
public class DetailClasses extends BaseMaster {

	private static final long serialVersionUID = 4459003731813958629L;

	@OneToOne
	@JoinColumn(name = "id_class", nullable = false)
	private Classes idClass;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

}
