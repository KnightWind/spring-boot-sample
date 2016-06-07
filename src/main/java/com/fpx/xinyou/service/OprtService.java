package com.fpx.xinyou.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import com.alibaba.druid.pool.DruidDataSource;
import com.fpx.xinyou.util.BaseConfig;

@Service
public class OprtService {

	DataSource ds;
	
	public OprtService() {
		
		DruidDataSource dataSource = new DruidDataSource();
//		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//		dataSource.setUrl("jdbc:oracle:thin:@172.16.30.26:1521:fz");
//		dataSource.setUsername("oprt");
//		dataSource.setPassword("oprt");
//		dataSource.setMaxActive(20);
		
		dataSource.setDriverClassName(BaseConfig.getInstance().getString("oprt.driver.class.name"));
		dataSource.setUrl(BaseConfig.getInstance().getString("oprt.url"));
		dataSource.setUsername(BaseConfig.getInstance().getString("oprt.username"));
		dataSource.setPassword(BaseConfig.getInstance().getString("oprt.password"));
		dataSource.setMaxActive(BaseConfig.getInstance().getInt("oprt.maxActive"));
		try {
			dataSource.setFilters("stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataSource.setInitialSize(1);
		dataSource.setMaxWait(60000);
		dataSource.setMinIdle(1);
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		dataSource.setMinEvictableIdleTimeMillis(300000);
		dataSource.setValidationQuery("select 'x' FROM DUAL");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxOpenPreparedStatements(20);

		this.ds = dataSource;
	}
	
	
	// B13639426
	public int getBgCodeNum(String bgCode) {
		 
		String sql = "select count(1) from oprt.tms_bag where bg_code = ?";
		try {

			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bgCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	
	/**
	 * 根据bgCode查询bgid
	 * @param bgCode
	 * @return
	 */
	Long getBgIdByCode(String bgCode){
		
		String sql = "select bg_id from oprt.tms_bag where bg_code = ?";
		try {

			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bgCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				return rs.getLong(1);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}
	
	
	
	/**
	 * 查询bgId下面的bsid
	 * @param bgId
	 * @return
	 */
	public List<Long> getBsIds(long bgId){
		
		List<Long> bsIds = new ArrayList<Long>();
		
		String sql = " select BS_ID from  oprt.tms_bagitem  where bg_id = ? union all"
		 +" select BS_ID from  oprt.tms_bagitem_sort  where bg_id = ? ";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bgId);
			pstmt.setLong(2, bgId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bsIds.add(rs.getLong(1));
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bsIds;
	}
	
	/**
	 * 根据bagCode查询袋子id
	 * @param bgCode
	 * @return
	 */
	Long getSgBagIdByCode(String bgCode){
		
		String sql = "select ID from oprt.t_sg_bag where bg_code = ?";
		try {

			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bgCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				return rs.getLong(1);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}
	
	
	/**
	 * 查询sg bs id集合
	 * @param bgId
	 * @return
	 */
	List<Long> getSgBsIds(long bgId){
		
		List<Long> bsIds = new ArrayList<Long>();
		
		String sql = "select BS_ID from  oprt.t_sg_bagitem  where bg_id = ? union all"+
		 " select BS_ID from  oprt.tms_bagitem_sort  where bg_id = ?";
		
		try {
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bgId);
			pstmt.setLong(2, bgId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bsIds.add(rs.getLong(1));
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bsIds;
	}
}
