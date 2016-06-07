package com.fpx.xinyou.mapper;

import com.fpx.xinyou.model.UserLoginRecord;
import com.fpx.xinyou.util.MyMapper;


/**
 * userMapper 
 * @author wangchaobo
 *
 */
public interface UserLoginRecordMapper extends MyMapper<UserLoginRecord> {
	
	public int insertRecord(UserLoginRecord userLoginRecord);
	
	
	/**
	 * 更新用户token活动标志
	 * @param userId
	 */
	public void updateActiveFlag(int userId);
}
