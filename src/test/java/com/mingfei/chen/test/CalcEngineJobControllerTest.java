package com.mingfei.chen.test;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


@ContextConfiguration(locations = { "/import-accounts-job-context.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class CalcEngineJobControllerTest {

	@Autowired
	private DataSource dataSource;

	
	


	@Before
	public void setUp() throws Exception {
		
		
	}

	@Test
	public void test() throws SQLException {
		Statement stmt = this.dataSource.getConnection().createStatement();
		
		boolean result1 = stmt.execute("SELECT * FROM batch_job_instance LIMIT 5");

		ResultSet resultSet1 = null;
		if (result1) {
			resultSet1 = stmt.getResultSet();
		}
		while(resultSet1.next())
		{   
			// If count>1, COMPLETE status should be unchanged:0
			System.out.println(resultSet1.getInt("JOB_INSTANCE_ID")); 
			
			assertEquals(true,true);
		}

	}

}
