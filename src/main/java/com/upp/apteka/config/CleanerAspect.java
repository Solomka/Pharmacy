package com.upp.apteka.config;

import javax.swing.JFrame;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CleanerAspect {
	
	@Autowired
	private JFrame dispatcherFrame;
	
	@Before("execution(* com.upp.apteka.controller.*.*(..))") 
	public void cleanBeforeShowing(){
		dispatcherFrame.getContentPane().removeAll();
	}

	@After("execution(* com.upp.apteka.controller.*.*(..))") 
	public void repaint(){
		dispatcherFrame.revalidate();
		dispatcherFrame.repaint();
	}
}
