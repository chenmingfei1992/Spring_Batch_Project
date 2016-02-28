package com.mingfei.chen.springBatch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.mingfei.chen.model.*;


public class CustomWriter implements ItemWriter<Account> {

	@Override
	public void write(List<? extends Account> items) throws Exception {

		System.out.println("writer..." + items.size());		
		for(Account item : items){
			System.out.println(item);
		}

	}

}