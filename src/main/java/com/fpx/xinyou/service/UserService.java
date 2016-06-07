package com.fpx.xinyou.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fpx.xinyou.components.MD5;
import com.fpx.xinyou.mapper.UserLoginRecordMapper;
import com.fpx.xinyou.mapper.UserMapper;
import com.fpx.xinyou.model.User;
import com.fpx.xinyou.model.UserLoginRecord;

@Service
public class UserService {
	
	@Autowired
    private UserMapper userMapper;
	
	@Autowired
	private UserLoginRecordMapper recoedMapper;
	
	@Resource(name="MD5")
	private MD5 MD5;
	
	public User getUserBy(User parms){
		return userMapper.selectOne(parms);
	}
	
	
	/**
	 * 根据用户名查询用户对象
	 * @param username
	 * @return
	 */
	public User getUserName(String username){
		User u = new User();
		u.setUsername(username);
		return userMapper.selectOne(u);
	}
	
	
	/**
	 * 保存登录记录
	 * @param user
	 * @return
	 */
	public String genSessionInfo(User user){
		String sid = MD5.encrypt(user.getId()+"##"+System.currentTimeMillis());
		try{
			recoedMapper.updateActiveFlag(user.getId());
			
			UserLoginRecord record = new UserLoginRecord();
			record.setAuthKey(sid);
			record.setUserId(user.getId());
			record.setLoginTime(new Date());
			record.setLastActiveTime(new Date());
			recoedMapper.insertRecord(record);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return sid;
	}
	
	
	/**
	 * 根据sid获取登录用户
	 * @param sid
	 * @return
	 */
	public UserLoginRecord getLoginRord(String sid){
		if(StringUtils.isEmpty(sid)) return null;
		UserLoginRecord record = new UserLoginRecord();
		record.setAuthKey(sid);
		return recoedMapper.selectOne(record);
	}
	
	
	/**
	 * 
	 * @param sid
	 */
	public void updateActivTime(UserLoginRecord ulr){
		ulr.setLastActiveTime(new Date());
		recoedMapper.updateByPrimaryKey(ulr);
	}
	
	
	/**
	 * 查询用户
	 * @param sid
	 * @return
	 */
	public User getUserBySid(String sid){
		if(StringUtils.isEmpty(sid)) return null;
		UserLoginRecord ulr = getLoginRord(sid);
		if(ulr == null) return null;
		return userMapper.selectByPrimaryKey(ulr.getUserId());
	}
	
	
	/**
	 * 禁用某个用户的token记录
	 * @param userId
	 */
	public void disableUserToken(Integer userId){
		if(null == userId || userId < 1) return;
		recoedMapper.updateActiveFlag(userId);
	}
}
