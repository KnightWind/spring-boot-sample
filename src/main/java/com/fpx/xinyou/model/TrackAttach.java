package com.fpx.xinyou.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 轨迹附属信息
 * @author Administrator
 *
 */
@Entity
@Table(name="TAK_TRACKATTACH")
public class TrackAttach {
	
	@Column(name="TRK_ID")
	private Long trkId;
	
//	 TRK_SERVICEDESCRIPTION,
//	   TRK_SERVICECODE, 
//	   TRK_SIGNATORY,
//	   TRK_TRACKSOURCE
	@Column(name="TRK_SERVICEDESCRIPTION")
	private String trkServiceDesc = "";
	
	
	@Column(name="TRK_SERVICECODE")
	private String serviceCode = "";
	
	@Column(name="TRK_SIGNATORY")
	private String signatory = "";
	
	@Column(name="TRK_TRACKSOURCE")
	private String tkSource = "";

	public Long getTrkId() {
		return trkId;
	}

	public void setTrkId(Long trkId) {
		this.trkId = trkId;
	}

	public String getTrkServiceDesc() {
		return trkServiceDesc;
	}

	public void setTrkServiceDesc(String trkServiceDesc) {
		this.trkServiceDesc = trkServiceDesc;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getSignatory() {
		return signatory;
	}

	public void setSignatory(String signatory) {
		this.signatory = signatory;
	}

	public String getTkSource() {
		return tkSource;
	}

	public void setTkSource(String tkSource) {
		this.tkSource = tkSource;
	}
	
	
}
