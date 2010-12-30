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
	private String icon_url;
	private String html_content = "";
	private String projection = "EPSG:4326";
	private int icon_w;
	private int icon_h;

	public Marker(double lon, double lat) {
		this.lon = lon;
		this.lat = lat;
	}

	public void setIcon(String url, int width, int height) {
		this.icon_url = url;
		this.icon_w = width;
		this.icon_h = height;
	}

	public void setPopUpContent(String content) {
		this.html_content = content;
	}

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);
		target.addAttribute("lon", lon);
		target.addAttribute("lat", lat);
		target.addAttribute("content", html_content);
		target.addAttribute("pr", projection);
		if (icon_url != null) {
			target.addAttribute("icon_url", icon_url);
			target.addAttribute("icon_w", icon_w);
			target.addAttribute("icon_h", icon_h);

		}
	}

}