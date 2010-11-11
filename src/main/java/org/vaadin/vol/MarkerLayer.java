/**
 * 
 */
package org.vaadin.vol;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;

public class MarkerLayer extends AbstractComponentContainer {
	
	private List<Component> markers = new LinkedList<Component>();

	@Override
	public void addComponent(Component m) {
		super.addComponent(m);
		markers.add(m);
	}

	public Iterator<Component> getComponentIterator() {
		return markers.iterator();
	}

	public void replaceComponent(Component oldComponent,
			Component newComponent) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);
		for (Component m : markers) {
			m.paint(target);
		}
	}
	
}