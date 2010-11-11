/**
 * 
 */
package org.vaadin.vol;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;

public class WebMapServiceLayer extends AbstractComponent {
	private String uri = "http://labs.metacarta.com/wms/vmap0";
	private String name = "metacarta";
	private String layer = "basic";

	public WebMapServiceLayer() {
		
	}

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		// TODO Auto-generated method stub
		super.paintContent(target);
		target.addAttribute("uri", uri);
		target.addAttribute("name", name);
		target.addAttribute("layer", layer);
		
	}

	public void setUri(String uri) {
		this.uri = uri;
	}


	public String getUri() {
		return uri;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setLayer(String layer) {
		this.layer = layer;
	}


	public String getLayer() {
		return layer;
	}
}