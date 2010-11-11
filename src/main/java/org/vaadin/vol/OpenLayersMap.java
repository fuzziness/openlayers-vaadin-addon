package org.vaadin.vol;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;

/**
 * Server side component for the VOpenLayersMap widget.
 */
@SuppressWarnings("serial")
@com.vaadin.ui.ClientWidget(org.vaadin.vol.client.ui.VOpenLayersMap.class)
public class OpenLayersMap extends AbstractComponentContainer {

	private List<Component> layers = new LinkedList<Component>();

	public OpenLayersMap() {
		setWidth("500px");
		setHeight("350px");
	}
	
	@Override
	public void addComponent(Component c) {
		super.addComponent(c);
		layers.add(c);
	}

	public Iterator<Component> getComponentIterator() {
		return layers.iterator();
	}
	
	public void replaceComponent(Component oldComponent, Component newComponent) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);
		for (Component component : layers) {
			component.paint(target);
		}
	}

	/**
	 * Receive and handle events and other variable changes from the client.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void changeVariables(Object source, Map<String, Object> variables) {
		super.changeVariables(source, variables);

	}


}
