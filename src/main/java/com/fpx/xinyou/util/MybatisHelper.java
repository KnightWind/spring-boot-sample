package com.fpx.xinyou.util;



import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class MybatisHelper {

	
	private static SqlSessionFactory sqlSessionFactory;
	
	
    public  SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public  void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		MybatisHelper.sqlSessionFactory = sqlSessionFactory;
	}
	

	public void init() {
        try {
        	System.out.println("will init MybatisHelper! ========");
            //创建SqlSessionFactory
            Reader reader = null;
            //创建数据库
            SqlSession session = null;
            try {
                session = sqlSessionFactory.openSession();
                Connection conn = session.getConnection();
                reader = Resources.getResourceAsReader("createDB.sql");
                ScriptRunner runner = new ScriptRunner(conn);
                runner.setLogWriter(null);
                runner.runScript(reader);
                reader.close();
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Session
     * @return
     */
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
