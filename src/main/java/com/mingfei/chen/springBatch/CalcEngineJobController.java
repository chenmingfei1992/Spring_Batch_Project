package com.mingfei.chen.springBatch;

import org.apache.commons.logging.Log;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

/**
 * Controls jobs in the Calculation Engine
 */

@ContextConfiguration({"classpath:import-accounts-job-context.xml"})
public class CalcEngineJobController implements JobController {
	/**
	 * Returns run time of job in seconds
	 */
	public Long jobRunTime;
	/**
	 * The JOB_EXECUTION_ID of the spring batch job
	 */
	public Long jobIdentifier;
	public JobStatus jobStatus;
	//private static final Log logger;

	@Autowired
	private JobOperator jobOperator;

	@Autowired
	private JobExplorer jobExplorer;

	/**
	 * Throw exception if job start fails
	 */
	public void startJob() {
		

		JobExecution jobExecution =jobExplorer.getJobExecution(jobIdentifier);
		try {System.out.println(jobOperator);
		jobOperator.start(jobExecution.getJobInstance().getJobName(), jobExecution.getJobInstance().getJobParameters().toString());
		} catch (NoSuchJobException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Throw exception if job stop fails
	 */
	public void stopJob() {

		try {
			jobOperator.stop(jobIdentifier);
		} catch (NoSuchJobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobExecutionNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Boolean isJobComplete() {
		JobExecution jobExecution =jobExplorer.getJobExecution(jobIdentifier);
		return jobExecution.getExitStatus().equals(ExitStatus.COMPLETED);

	}

	public Long getJobRunTime() {
		JobExecution jobExecution =jobExplorer.getJobExecution(jobIdentifier);
		long diff = jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();

		//seconds
		this.jobRunTime= diff / 1000 % 60;  
		return this.jobRunTime;
	}

	public Long getJobIdentifier() {
		return this.jobIdentifier;
	}

	public void setJobIdentifier(Long i) {
		this.jobIdentifier = i;
	}

	public JobStatus getJobStatus() {
		JobExecution jobExecution =jobExplorer.getJobExecution(jobIdentifier);
		switch(jobExecution.getStatus())
		{
		case STARTED: this.jobStatus = JobStatus.COMPLETED;
		break;

		case STARTING:this.jobStatus = JobStatus.STARTING;
		break;

		case COMPLETED:this.jobStatus = JobStatus.COMPLETED;
		break;

		case FAILED:this.jobStatus = JobStatus.FAILED;
		break;

		default:
			break;

		}
		return this.jobStatus;
	}

	@Autowired
	public JobExplorer getJobExplorer()
	{
		return this.jobExplorer;
	}

	@Autowired
	public void setJobExplorer( JobExplorer j)
	{
		this.jobExplorer=j;
	}

	@Autowired
	public JobOperator getJobOperatorr()
	{
		return this.jobOperator;
	}

	@Autowired
	public void setJobOperator( JobOperator j)
	{
		this.jobOperator=j;
	}

}