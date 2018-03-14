package com.mingfei.chen.test;


import com.mingfei.chen.springBatch.CalcEngineJobController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import com.mingfei.chen.springBatch.Task_1_startJob;
import com.mingfei.chen.springBatch.Task_2_stopJob;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/import-accounts-job-context.xml" })
public class App {
	

	@Autowired
	private JobLauncher jobLauncher;


	@Autowired
	private JobOperator jobOperator;
	
	@Autowired
	private JobExplorer jobExplorer;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private Job job;
	
	
	@Before
	public void setUp() throws Exception
	{
		for(int i=0;i<4;i++)
		{
		String dateParam = new Date().toString();
		JobParameters param = new JobParametersBuilder().addString("date", dateParam).toJobParameters();
		
		System.out.println(dateParam);
		
		JobExecution execution = jobLauncher.run(job, param);	
		}
	}

	
	@Test
	public void testStop()
	{
		
	    
		
		CalcEngineJobController c= new CalcEngineJobController();

		c.setJobIdentifier((long)4);
		c.setJobExplorer(this.jobExplorer);
		c.setJobOperator(this.jobOperator);
	
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		
		Task_1_startJob task1 = new Task_1_startJob(c);
		Task_2_stopJob task2 = new Task_2_stopJob(c);
  
        System.out.println("The time is : " + new Date());
         
        executor.schedule(task1, 5 , TimeUnit.SECONDS);
       // executor.schedule(task2, 8 , TimeUnit.SECONDS);
         
        try {
              executor.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
              e.printStackTrace();
        }
         
        executor.shutdown();
	
	}
	
	
	
	@Test
    public void testStart() throws SQLException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException
    {
		
		int oldCount=0;
		int newCount=0;
	Connection conn = this.dataSource.getConnection();
	Statement stmt = conn.createStatement();

	boolean result0 = stmt.execute("SELECT * FROM BATCH_JOB_EXECUTION");

	ResultSet resultSet0 = null;
	if (result0) {
		resultSet0 = stmt.getResultSet();
	}
	while(resultSet0.next())
	{   
		oldCount = resultSet0.getInt("JOB_EXECUTION_ID");
		System.out.println(oldCount);
	}

    
	
	CalcEngineJobController c= new CalcEngineJobController();

	c.setJobIdentifier((long)2);
	c.setJobExplorer(this.jobExplorer);
	c.setJobOperator(this.jobOperator);
	c.startJob();

	
	boolean result1 = stmt.execute("SELECT * FROM BATCH_JOB_EXECUTION");

	ResultSet resultSet1 = null;
	if (result1) {
		resultSet1 = stmt.getResultSet();
	}
	while(resultSet1.next())
	{   
		newCount = resultSet1.getInt("JOB_EXECUTION_ID");
		System.out.println(newCount);
	}
	Assert.assertEquals(oldCount+1,newCount);
    }
}