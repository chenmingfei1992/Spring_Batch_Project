package com.mingfei.chen.springBatch;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Interface for controlling a type of job
 */
public interface JobController {
	/**
	 * Returns run time of job in seconds
	 */
	public Integer jobRunTime=0;
	/**
	 * Return a unique integer ID for the job
	 */
	public Integer jobIdentifier=0;
	/**
	 * Enumerated value specifying the status of the job
	 */
	public JobStatus jobStatus=JobStatus.STARTING;

	/**
	 * Throw exception if job start fails
	 */
	public void startJob();


	/**
	 * Throw exception if job stop fails
	 */
	public void stopJob();

	/**
	 * Returns boolean true if the job has completed
	 */
	public Boolean isJobComplete();

	public Long getJobRunTime();

	public Long getJobIdentifier();

	public JobStatus getJobStatus();
}