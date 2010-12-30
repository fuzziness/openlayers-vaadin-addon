package org.vaadin.vol.demo;

import org.vaadin.vol.Marker;
import org.vaadin.vol.MarkerLayer;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.WebMapServiceLayer;

import com.vaadin.Application;
import com.vaadin.ui.Window;

public class VolApplication extends Application {
	@Override
	public void init() {
		Window mainWindow = new Window("Vol example Application");
		setMainWindow(mainWindow);
		OpenLayersMap map = new OpenLayersMap();


		WebMapServiceLayer osm = new WebMapServiceLayer();
		// Open street maps layer
		osm.setServiceType("osm");


		// Defining a WMS layer
		WebMapServiceLayer wms = new WebMapServiceLayer();
		wms.setServiceType("wms");

		// routes_layer.setUri("http://41.204.186.81:8080/geoserver/wms?");
		// Defines the URI of the wms server
		// wms.setUri("http://yourserver.com/geoserver/wms?");
		// Name of the Layer you wish to load
		// wms.setLayer("layer:name");
		// The name of the Layer. Refer to VVOPenLayersMap. Used to check type
		// of Layer we are loading
		// wms.setName("routes");
		// The display name of the Layer in Layer switcher
		// wms.setDisplay("metacarte");
		// If the Layer is a base layer
		wms.setIsBaseLayer(true);
		// The opacity of the Layer
		// wms.setOpacity(0.75);

		// Definig a Marker Layer
		MarkerLayer markerLayer = new MarkerLayer();

		// Defining a new Marker

		Marker marker = new Marker(22.30083, 60.452541);
		// The Popup content for the marker
		marker.setPopUpContent("Vaadin Ltd HQ");
		// URL of marker Icon
		 marker.setIcon("http://dev.vaadin.com/chrome/site/vaadin-trac.png", 60, 20);
		// Add the marker to the marker Layer
		markerLayer.addComponent(marker);
		map.setCenter(22.30, 60.452);
		map.setZoom(15);

		// add layers
//		map.addComponent(wms);
		map.addComponent(osm);
		map.addComponent(markerLayer);
		map.setSizeFull();
		mainWindow.getContent().setSizeFull();
		mainWindow.addComponent(map);

	}

}