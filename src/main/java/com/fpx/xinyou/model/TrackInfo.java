package com.fpx.xinyou.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fpx.xinyou.constant.SysConstant;

/**
 * 保存轨迹信息
 * @author wangchaobo
 *
 */
@Entity
@Table(name="tak_track")
public class TrackInfo {
	
//	TRK_ID,
//	   TBS_ID,
//	   TK_CODE, 
//	   MST_CODE,
//	   TRK_OCCURDATE,
//	   TRK_CREATEDATE,
//	   TRK_CREATEPERSON, 
//	   FILE_ID, 
//	   TRK_AREADESCRIPTION
	@Id
	@Column(name = "TRK_ID")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trkId;
	
	@Column(name = "TBS_ID")
	private Long tbsId;
	
	@Column(name = "TK_CODE")
	private String tkCode = "";
	
	@Column(name = "MST_CODE")
	private String mstCode = "";
	
	@Column(name = "TRK_OCCURDATE")
	private Date trkOccurdate;
	
	@Column(name = "TRK_CREATEDATE")
	private Date trkCreatedate;
	
	@Column(name = "TRK_CREATEPERSON")
	private String trkCreateperson = "";
	
	@Column(name = "FILE_ID")
	private String fileId = "";
	
	@Column(name = "TRK_AREADESCRIPTION")
	private String tkLocation = SysConstant.XY_TK_DEST;

	public Long getTrkId() {
		return trkId;
	}


	public void setTrkId(Long trkId) {
		this.trkId = trkId;
	}


	public Long getTbsId() {
		return tbsId;
	}


	public void setTbsId(Long tbsId) {
		this.tbsId = tbsId;
	}


	public String getTkCode() {
		return tkCode;
	}


	public void setTkCode(String tkCode) {
		this.tkCode = tkCode;
	}


	public String getMstCode() {
		return mstCode;
	}


	public void setMstCode(String mstCode) {
		this.mstCode = mstCode;
	}


	public Date getTrkOccurdate() {
		return trkOccurdate;
	}


	public void setTrkOccurdate(Date trkOccurdate) {
		this.trkOccurdate = trkOccurdate;
	}


	public Date getTrkCreatedate() {
		return trkCreatedate;
	}


	public void setTrkCreatedate(Date trkCreatedate) {
		this.trkCreatedate = trkCreatedate;
	}


	public String getTrkCreateperson() {
		return trkCreateperson;
	}


	public void setTrkCreateperson(String trkCreateperson) {
		this.trkCreateperson = trkCreateperson;
	}


	public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	public String getTkLocation() {
		return tkLocation;
	}

	public void setTkLocation(String tkLocation) {
		this.tkLocation = tkLocation;
	}
	
}
