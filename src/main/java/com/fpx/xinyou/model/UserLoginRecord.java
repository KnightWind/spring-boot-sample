package com.fpx.xinyou.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 记录用户登录记录
 * @author wangchaobo
 *
 */
@Entity
@Table(name="XY_USER_LOGIN_RECORD")
public class UserLoginRecord {
	
//	  ID               NUMBER(11) not null,
//	  AUTH_KEY         VARCHAR2(64) not null,
//	  USER_ID          NUMBER(11) not null,
//	  LOGIN_TIME       DATE not null,
//	  LAST_ACTIVE_TIME DATE not null,
//	  ACTIVE_FLAG      NUMBER(1) not null
	@Id
	@Column(name="id")
	private Long id;
	
	
	private String authKey;
	
	
	private Integer userId;
	
	
	private Date loginTime;
	
	@Column(name="LAST_ACTIVE_TIME")
	private Date lastActiveTime;
	
	
	private Integer activeFlag = 1;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAuthKey() {
		return authKey;
	}


	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}


	 

	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Date getLoginTime() {
		return loginTime;
	}


	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}


	public Date getLastActiveTime() {
		return lastActiveTime;
	}


	public void setLastActiveTime(Date lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}


	public Integer getActiveFlag() {
		return activeFlag;
	}


	public void setActiveFlag(Integer activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	
}
