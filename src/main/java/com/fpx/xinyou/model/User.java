package com.fpx.xinyou.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="XY_USER")
public class User {
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select SEQ_XYUSER.nextval from dual")
	private Integer id;
	
	
	private String username;
	
	
	private String password;
	
	
	private String nickName;
	
	
	private String contactInfo;

	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getContactInfo() {
		return contactInfo;
	}


	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	
	
}
