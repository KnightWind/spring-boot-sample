package com.fpx.xinyou.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fpx.xinyou.constant.ScansDataStatus;

/**
 * 扫描
 * @author Administrator
 *
 */
@Entity
@Table(name="XY_SCANSDATA")
public class ScansData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6148945257974631353L;
	
	ScansData(){
		
	}

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select SEQ_SCANSDATA.nextval from dual")
	private Long id;
	
	
	private String awbCode;
	
	
	private String bgCode;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date scanTime;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date sendTime;
	
	@Transient
	private Integer page = 1;

	@Transient
	private Integer rows = 10;
	
	//状态 0 待处理  1 验证通过  2 轨迹回写成功 3验证不通过
	private Integer state = 0;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAwbCode() {
		return awbCode;
	}


	public void setAwbCode(String awbCode) {
		this.awbCode = awbCode;
	}

	public String getBgCode() {
		return bgCode;
	}


	public void setBgCode(String bgCode) {
		this.bgCode = bgCode;
	}


	public Date getScanTime() {
		return scanTime;
	}


	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}


	public Date getSendTime() {
		return sendTime;
	}


	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	
	
	public ScansData(String awbCode, String bgNum, Date scanTime, Date sendTime) {
		super();
		this.awbCode = awbCode;
		this.bgCode = bgNum;
		this.scanTime = scanTime;
		this.sendTime = sendTime;
	}
	

	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}
	
	public void setStatus(ScansDataStatus statu){
		this.state = statu.getCode();
	}


	@Override
	public String toString() {
		return "ScansData [id=" + id + ", awbCode=" + awbCode + ", bgCode=" + bgCode + ", scanTime=" + scanTime
				+ ", sendTime=" + sendTime + ", page=" + page + ", rows=" + rows + "]";
	}
	
	
}
