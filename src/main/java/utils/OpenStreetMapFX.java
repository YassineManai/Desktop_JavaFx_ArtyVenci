package utils;

import java.awt.BorderLayout;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.StackPane;
import javax.swing.JPanel;


import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

public class OpenStreetMapFX extends StackPane {

    SwingNode swingNode = new SwingNode();

    JXMapViewer mapViewer = new JXMapViewer();
    TileFactoryInfo info = new OSMTileFactoryInfo();
    DefaultTileFactory tileFactory = new DefaultTileFactory(info);

    public OpenStreetMapFX(String place) {

        mapViewer.setTileFactory(tileFactory);

        GeoPosition initialPosition = new GeoPosition(36.89511521060213, 10.189153846743208);
        mapViewer.setCenterPosition(initialPosition);
        mapViewer.setZoom(17);

        mapViewer.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int wheelRotation = e.getWheelRotation();
                int currentZoom = mapViewer.getZoom();
                if (wheelRotation > 0) {
                    mapViewer.setZoom(currentZoom + 1);
                } else {
                    mapViewer.setZoom(currentZoom - 1);
                }
                mapViewer.repaint();
            }
        });

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(mapViewer, BorderLayout.CENTER);
        swingNode.setContent(jPanel);



        List<Waypoint> waypoints = new ArrayList<>();
        switch (place){
            case "Esprit":
                waypoints.add(new DefaultWaypoint(new GeoPosition(36.89771,10.18962)));
                break;
            case "Esprim" :
                waypoints.add(new DefaultWaypoint(new GeoPosition(35.76574412236688,10.809485968774094)));
                break;
            case "Esprit charguia":
                waypoints.add(new DefaultWaypoint(new GeoPosition(36.85344287067442,10.207073076079238)));
                break;

        }



        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(new HashSet<>(waypoints));

        List<org.jxmapviewer.painter.Painter<JXMapViewer>> painters = new ArrayList<>();
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(compoundPainter);

        getChildren().add(swingNode);
    }



    public void moveUp() {
        GeoPosition currentPosition = mapViewer.getCenterPosition();
        mapViewer.setCenterPosition(new GeoPosition(currentPosition.getLatitude() + 1, currentPosition.getLongitude()));

    }

    public void moveLeft() {
        GeoPosition currentPosition = mapViewer.getCenterPosition();
        mapViewer.setCenterPosition(new GeoPosition(currentPosition.getLatitude(), currentPosition.getLongitude() - 1));

    }

    public void moveDown() {
        GeoPosition currentPosition = mapViewer.getCenterPosition();
        mapViewer.setCenterPosition(new GeoPosition(currentPosition.getLatitude() - 1, currentPosition.getLongitude()));

    }

    public void moveRight() {
        GeoPosition currentPosition = mapViewer.getCenterPosition();
        mapViewer.setCenterPosition(new GeoPosition(currentPosition.getLatitude(), currentPosition.getLongitude() + 1));

    }

}

