/**
 * 
 */
package org.vaadin.vol;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;

public class Marker extends AbstractComponent {
	private double lon;
	private double lat;

	public Marker(double lon, double lat) {
		this.lon = lon;
		this.lat = lat;
	}
	
	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);
		target.addAttribute("lon", lon);
		target.addAttribute("lat", lat);
	}
	
}