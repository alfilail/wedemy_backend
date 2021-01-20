package com.lawencon.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	public static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@JsonIgnore
	@Column(name = "created_by")
	private String createdBy;

	@JsonIgnore
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@JsonIgnore
	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "version")
	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		if (version == null) {
			this.version = 0L;
		} else {
			this.version = version;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}