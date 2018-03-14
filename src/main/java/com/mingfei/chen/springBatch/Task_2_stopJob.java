package com.mingfei.chen.springBatch;

import com.mingfei.chen.springBatch.CalcEngineJobController;

import java.util.Date;

public class Task_2_stopJob  implements Runnable
	{
	    private String name;
	    private CalcEngineJobController calcEngineJobController;
	 
	    public Task_2_stopJob(CalcEngineJobController calcEngineJobController) {
	        this.calcEngineJobController = calcEngineJobController;
	    }
	     
	    public CalcEngineJobController getCalcEngineJobController() {
	        return this.calcEngineJobController;
	    }
	    
	    public void setCalcEngineJobController(CalcEngineJobController calcEngineJobController) {
	        this.calcEngineJobController=calcEngineJobController;
	    }
	 
	    @Override
	    public void run()
	    {
	        try {
	        	calcEngineJobController.stopJob();
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}