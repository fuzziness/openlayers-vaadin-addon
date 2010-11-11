package org.vaadin.vol;


import com.vaadin.Application;
import com.vaadin.ui.*;

public class VolApplication extends Application {
	@Override
	public void init() {
		Window mainWindow = new Window("Vol Application");
		OpenLayersMap map = new OpenLayersMap();
		
		map.addComponent(new WebMapServiceLayer());
		
		Marker marker = new Marker(22.30, 60.452);
		MarkerLayer markerLayer = new MarkerLayer();
		markerLayer.addComponent(marker);
		map.addComponent(markerLayer);
		
		mainWindow.addComponent(map);
		setMainWindow(mainWindow);
	}

}
