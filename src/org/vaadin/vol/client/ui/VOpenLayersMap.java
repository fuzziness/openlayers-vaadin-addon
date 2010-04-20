package org.vaadin.vol.client.ui;

import java.util.Iterator;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Marker;
import org.gwtopenmaps.openlayers.client.layer.Layer;
import org.gwtopenmaps.openlayers.client.layer.Markers;
import org.gwtopenmaps.openlayers.client.layer.WMS;
import org.gwtopenmaps.openlayers.client.layer.WMSParams;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VOpenLayersMap extends MapWidget implements Paintable {
	
	static MapOptions getDefaultMapOptions() {
		MapOptions o  = new MapOptions();
//		o.setNumZoomLevels(16);
		return o;
	}
	
	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-openlayersmap";

	/** The client side widget identifier */
	protected String paintableId;

	/** Reference to the server connection object. */
	protected ApplicationConnection client;

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VOpenLayersMap() {
		super("350px", "500px", getDefaultMapOptions());

		// This method call of the Paintable interface sets the component
		// style name in DOM tree
		setStyleName(CLASSNAME);

	}

	/**
	 * Called whenever an update is received from the server
	 */
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		// This call should be made first.
		// It handles sizes, captions, tooltips, etc. automatically.
		if (client.updateComponent(this, uidl, true)) {
			// If client.updateComponent returns true there has been no changes
			// and we
			// do not need to update anything.
			return;
		}

		// Save reference to server connection object to be able to send
		// user interaction later
		this.client = client;

		// Save the client side identifier (paintable id) for the widget
		paintableId = uidl.getId();
		
		// TODO read zoom levels and coordinates
		
		// TODO should update existing layers instead of clearing and rebuilding
		Layer[] layers = getMap().getLayers();
		for (Layer layer : layers) {
			getMap().removeLayer(layer);
		}
		
		Iterator<Object> childIterator = uidl.getChildIterator();
		while(childIterator.hasNext()) {
			UIDL layer = (UIDL) childIterator.next();
			String name = layer.getStringAttribute("name");
			// TODO implement client side counterparts for map components
			if(name != null && name.equals("metacarta")) {
				//Defining a WMSLayer and adding it to a Map
				WMSParams wmsParams = new WMSParams();
				wmsParams.setFormat("image/png");
				wmsParams.setLayers(layer.getStringAttribute("layer"));
				wmsParams.setStyles("");
				
				WMS l = new WMS("Basic WMS", layer.getStringAttribute("uri"), wmsParams);
				getMap().addLayer(l);
			} else {
				MarkerLayer markerLayer = new MarkerLayer(name);
				markerLayer.updateFromUIDL(layer);
				getMap().addLayer(markerLayer);
			}
		}
	}
	
	
	
	class MarkerLayer extends Markers {

		public MarkerLayer(String name) {
			super(name);
		}

		public void updateFromUIDL(UIDL layer) {
			int childCount = layer.getChildCount();
			for(int i = 0; i < childCount; i++) {
				UIDL childUIDL = layer.getChildUIDL(i);
				double lon = childUIDL.getDoubleAttribute("lon");
				double lat = childUIDL.getDoubleAttribute("lat");
				Marker marker = new Marker(new LonLat(lon, lat));
				addMarker(marker);
				// TODO remove this when map has proper API
				getMap().setCenter(new LonLat(lon, lat), 5);
			}
			
		}
		
	}

}
