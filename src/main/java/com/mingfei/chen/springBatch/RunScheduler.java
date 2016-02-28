package com.mingfei.chen.springBatch;


import java.rmi.RMISecurityManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Assert;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration({"/import-accounts-job-context.xml"})
public class RunScheduler {

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

	public void run() {

		try {
			Connection conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			
		
			String dateParam = new Date().toString();
			JobParameters param = new JobParametersBuilder().addString("date", dateParam).toJobParameters();
			
			System.out.println(dateParam);
			
			JobExecution execution = jobLauncher.run(job, param);
			
			
			boolean result0 = stmt.execute("SELECT * FROM BATCH_JOB_EXECUTION");

			ResultSet resultSet0 = null;
			if (result0) {
				resultSet0 = stmt.getResultSet();
			}
			while(resultSet0.next())
			{   
				System.out.println(resultSet0.getInt("JOB_EXECUTION_ID"));
			}
			conn.close();
			
			
			//execution.setId((long)5);
			Long x = execution.getId();
		
			CalcEngineJobController c= new CalcEngineJobController();
			RunScheduler r= new RunScheduler();
			c.jobIdentifier=(long) 1.0;
			c.setJobExplorer(this.jobExplorer);
			c.setJobOperator(this.jobOperator);
			
			if((long)c.jobIdentifier==x)
			{
//				System.out.println(c.isJobComplete());
//				System.out.println(c.getJobRunTime());
//				System.out.println(jobOperator.getSummary(x));
				c.startJob();
			}
		
			
			String jobName = execution.getJobInstance().getJobName();
			JobParameters jobPara = execution.getJobInstance().getJobParameters();
			Long y = execution.getJobInstance().getId();
			System.out.println("job exe id: "+ x);
			System.out.println("job instance id: "+ y);
			System.out.println(jobName);
			System.out.println(jobPara);
			System.out.println("Exit Status : " + execution.getStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Autowired
	public JobExplorer getJobExplorer()
	{
		return this.jobExplorer;
	}
}