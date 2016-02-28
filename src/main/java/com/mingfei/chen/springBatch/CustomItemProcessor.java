package com.mingfei.chen.springBatch;


import org.springframework.batch.item.ItemProcessor;

import com.mingfei.chen.model.Account;

 
public class CustomItemProcessor implements ItemProcessor<Account, Account> {
 
	@Override
	public Account process(Account item) throws Exception {
 
		Thread.sleep(200);
		return item;
	}
 
}