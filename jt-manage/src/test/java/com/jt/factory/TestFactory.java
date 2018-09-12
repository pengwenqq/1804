package com.jt.factory;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFactory {
@Test
	public void testStaticFactory(){
		ApplicationContext context=new ClassPathXmlApplicationContext("/spring/Factory.xml");
		
		Calendar calendar=(Calendar) context.getBean("calendar1");
		System.out.println("获取时间1:"+calendar.getTime());
		Calendar calendar2=(Calendar) context.getBean("calendar2");
		System.out.println("获取时间2:"+calendar2.getTime());
		Calendar calendar3=(Calendar) context.getBean("calendar3");
		System.out.println("获取时间3:"+calendar3.getTime());
		
	}
}
