/**
 *
 */
package org.vaadin.vol;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;

public class WebMapServiceLayer extends AbstractComponent {
	private String uri = "http://labs.metacarta.com/wms/vmap0";
	private String name = "wms";
	private String layer = "basic";
	private String display_name = "Basic WMS";
	private Boolean isBaseLayer = true;
	private Double opacity = 1.0;
	private String feature_id = "";

	public WebMapServiceLayer() {

	}

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		// TODO Auto-generated method stub
		super.paintContent(target);
		target.addAttribute("uri", uri);
		target.addAttribute("name", name);
		target.addAttribute("layer", layer);
		target.addAttribute("display", display_name);
		target.addAttribute("isBaseLayer", isBaseLayer);
		target.addAttribute("opacity", opacity);
		target.addAttribute("featureid", feature_id);
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setIsBaseLayer(boolean isBaseLayer) {
		this.isBaseLayer = isBaseLayer;
	}

	public boolean getIsBaseLayer() {
		return isBaseLayer;
	}

	public void setOpacity(Double opacity) {
		this.opacity = opacity;
	}

	public Double getOpacity() {
		return opacity;
	}

	public String getDisplay() {
		return display_name;
	}

	public void setDisplay(String displayName) {
		this.display_name = displayName;
	}

	public String getUri() {
		return uri;
	}

	public void setServiceType(String name) {
		this.name = name;
	}

	public String getServiceType() {
		return name;
	}

	public String getFeatureID() {
		return feature_id;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public void resetFeatures() {
		this.feature_id = "";
	}

	public void setFeatureID(String featureid) {
		if (feature_id.equals("")) {
			this.feature_id = featureid;
		} else {
			StringBuilder buf = new StringBuilder(feature_id);
			buf.append(",");
			buf.append(featureid);
			this.feature_id = null;
			this.feature_id = buf.toString();
			// this.feature_id = fid;
		}
	}

	public String getLayer() {
		return layer;
	}
}