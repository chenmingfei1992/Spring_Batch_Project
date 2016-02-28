package com.mingfei.chen.springBatch;



public class Task_1_startJob  implements Runnable
	{
	    
	    private CalcEngineJobController calcEngineJobController;
	 
	    public Task_1_startJob(CalcEngineJobController calcEngineJobController) {
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
	        	calcEngineJobController.startJob();
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}