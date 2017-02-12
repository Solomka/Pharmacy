package com.upp.apteka.utils;

import java.awt.Component;
import java.awt.Container;
import java.util.List;

import javassist.NotFoundException;

public interface FrameContext {
	
	//A component is an object having a graphical representation that can be displayed on the screen and that can interact with the user.
	//Examples of components are the buttons, checkboxes, and scrollbars of a typical graphical user interface.
	List<Component> getAllComponents(final Container c);
	
	Component findComponentByName(Container container, String name) throws NotFoundException;
	
	Component findComponentByName(String name) throws NotFoundException;
}
