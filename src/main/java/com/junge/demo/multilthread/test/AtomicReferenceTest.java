package com.junge.demo.multilthread.test;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

	public static void main(String[] args) {
		
		Account a1 = new Account("张三");
		Account a2 = new Account("李四");

		AtomicReference<Account> atomicReference = new AtomicReference<Account>(a1);
		
		a1.setName("修改名称");
		System.out.println(atomicReference.compareAndSet(a1, a2));
		System.out.println(atomicReference.get().getName());

	}

}

class Account {
	private String name;

	public Account(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
