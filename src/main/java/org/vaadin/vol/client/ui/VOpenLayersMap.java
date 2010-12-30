package org.vaadin.vol.client.ui;

import java.util.Iterator;

import org.gwtopenmaps.openlayers.client.Icon;
import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Marker;
import org.gwtopenmaps.openlayers.client.Size;
import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;
import org.gwtopenmaps.openlayers.client.control.ZoomIn;
import org.gwtopenmaps.openlayers.client.control.ZoomOut;
import org.gwtopenmaps.openlayers.client.event.EventHandler;
import org.gwtopenmaps.openlayers.client.event.EventObject;
import org.gwtopenmaps.openlayers.client.layer.GMapType;
import org.gwtopenmaps.openlayers.client.layer.Google;
import org.gwtopenmaps.openlayers.client.layer.GoogleOptions;
import org.gwtopenmaps.openlayers.client.layer.Layer;
import org.gwtopenmaps.openlayers.client.layer.Markers;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.WMS;
import org.gwtopenmaps.openlayers.client.layer.WMSOptions;
import org.gwtopenmaps.openlayers.client.layer.WMSParams;
import org.gwtopenmaps.openlayers.client.popup.FramedCloud;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VOpenLayersMap extends MapWidget implements Paintable {
	static MapOptions getDefaultMapOptions() {
		MapOptions o = new MapOptions();
		return o;
	}

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-openlayersmap";

	/** The client side widget identifier */
	protected String paintableId;

	/** Reference to the server connection object. */
	protected ApplicationConnection client;

	private MarkerLayer markerLayer;

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VOpenLayersMap() {
		super("500px", "500px", VOpenLayersMap.getDefaultMapOptions());

		getMap().addControl(new LayerSwitcher());
		getMap().addControl(new ZoomIn());
		getMap().addControl(new ZoomOut());
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

		getMap().setOptions(getDefaultMapOptions());

		// TODO should update existing layers instead of clearing and rebuilding
		Layer[] layers = getMap().getLayers();
		for (Layer layer : layers) {
			getMap().removeLayer(layer);
		}

		Iterator<Object> childIterator = uidl.getChildIterator();
		while (childIterator.hasNext()) {
			UIDL layer = (UIDL) childIterator.next();
			String name = layer.getStringAttribute("name");
			assert name != null;

			if (name.equals("google")) {
				// TODO make this work with Gmaps V3
				GoogleOptions params = new GoogleOptions();
				params.setType(GMapType.G_SATELLITE_MAP);
				params.setSphericalMercator(false);
				params.setIsBaseLayer(layer.getBooleanAttribute("isBaseLayer"));
				Google google = new Google("Google Satelitte", params);

				getMap().addLayers(new Layer[] { google });
			} else if (name.equals("osm")) {

				OSM osm = OSM.Mapnik(null);
				osm.setIsBaseLayer(layer.getBooleanAttribute("isBaseLayer"));
				getMap().addLayer(osm);

			} else if (name.equals("markers")) {
				markerLayer = new MarkerLayer("Markers");
				markerLayer.updateFromUIDL(layer);
				getMap().addLayer(markerLayer);
			} else if (name.equals("wms")) {
				// Defining a WMSLayer and adding it to a Map
				WMSParams wmsParams = new WMSParams();
				wmsParams.setFormat("image/jpeg");
				wmsParams.setLayers(layer.getStringAttribute("layer"));

				if (layer.hasAttribute("featureid")) {
					if (!layer.getStringAttribute("featureid").equals("")) {
						// wmsParams.setFilters(layer.getStringAttribute("featureid"));
					}
				}
				WMSOptions wmsOptions = new WMSOptions();
				wmsOptions.setIsBaseLayer(layer
						.getBooleanAttribute("isBaseLayer"));
				if (layer.hasAttribute("opacity")) {
					wmsOptions.setLayerOpacity(layer
							.getDoubleAttribute("opacity"));
				}
				if (layer.hasAttribute("projection")) {
					wmsOptions.setProjection(layer
							.getStringAttribute("projection"));
				}

				WMS wms = new WMS(layer.getStringAttribute("display"),
						layer.getStringAttribute("uri"), wmsParams, wmsOptions);

				// TODO save by name/id or similar, targeted updates, best
				// would be to make layers vaadin widgets somehow ->
				// framework
				// would point updates to right place
				getMap().addLayer(wms);

			}
		}

		updateZoomAndCenter(uidl);

	}

	private void updateZoomAndCenter(UIDL uidl) {
		// TODO set zoom only if marked dirty on server, also separately from
		// center point
		int zoom = uidl.getIntAttribute("zoom");
		if (uidl.hasAttribute("clat")) {
			double lat = uidl.getDoubleAttribute("clat");
			double lon = uidl.getDoubleAttribute("clon");
			LonLat lonLat = new LonLat(lon, lat);
			// expect center point to be in WSG84
			lonLat.transform("EPSG:4326", getMap().getProjection());

			getMap().setCenter(lonLat, zoom);
		}
	}

	class MarkerLayer extends Markers {
		public MarkerLayer(String name) {
			super(name);
		}

		public void updateFromUIDL(UIDL layer) {
			int childCount = layer.getChildCount();
			for (int i = 0; i < childCount; i++) {
				UIDL childUIDL = layer.getChildUIDL(i);
				double lon = childUIDL.getDoubleAttribute("lon");
				double lat = childUIDL.getDoubleAttribute("lat");
				final String projection = childUIDL.getStringAttribute("pr");
				LonLat point = new LonLat(lon, lat);
				point.transform(projection, getMap().getProjection());

				final String content = childUIDL.getStringAttribute("content");
				final Marker marker;
				if (childUIDL.hasAttribute("icon_url")) {
					Icon icon;
					String url = childUIDL.getStringAttribute("icon_url");
					int width = childUIDL.getIntAttribute("icon_w");
					int height = childUIDL.getIntAttribute("icon_h");
					icon = new Icon(url, new Size(width, height));
					// TODO offset support
					marker = new Marker(point, icon);
				} else {
					marker = new Marker(point);
				}
				marker.getEvents().register("click", marker,
						new EventHandler() {

							@Override
							public void onHandle(EventObject eventObject) {
								FramedCloud popup = new FramedCloud("", marker
										.getLonLat(), new Size(200, 150),
										content + projection, marker.getIcon(),
										true);

								getMap().addPopup(popup);
							}

						});
				addMarker(marker);
			}

		}

	}
}