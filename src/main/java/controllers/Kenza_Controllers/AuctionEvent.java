package controllers.Kenza_Controllers;

import javafx.event.Event;
import javafx.event.EventType;

public class AuctionEvent extends Event {
    public static final EventType<AuctionEvent> AUCTION_DELETED =
            new EventType<>(Event.ANY, "AUCTION_DELETED");

    public AuctionEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}