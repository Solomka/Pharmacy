package com.upp.apteka.utils;

import java.awt.Component;
import java.awt.Container;
import java.util.List;

import javassist.NotFoundException;

public interface FrameContext {
	List<Component> getAllComponents(final Container c);
	
	Component findComponentByName(Container container, String name) throws NotFoundException;
	
	Component findComponentByName(String name) throws NotFoundException;
}
