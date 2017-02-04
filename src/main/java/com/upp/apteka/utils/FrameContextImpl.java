package com.upp.apteka.utils;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class FrameContextImpl implements FrameContext {

	@Autowired
	private JFrame dispatcherFrame;

	public List<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container)
				compList.addAll(getAllComponents((Container) comp));
		}

		return compList;
	}

	@Override
	public Component findComponentByName(String name) throws NotFoundException {
		for (Component component : getAllComponents(dispatcherFrame)) {
			if (component.getName() != null && component.getName().equals(name)) {
				return component;
			}
		}

		throw new NotFoundException("Немає такого поля в контексті фрейма");
	}
}
