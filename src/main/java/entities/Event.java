package entities;

import java.util.Date;

public class Event {
    private int Id_Event;
    private String E_Name;
    private String Place;
    private Date E_Date;
    private double Ticket_Price;
    private String image;

    public Event() {
    }

    public Event(String e_Name, String place, Date e_Date, double ticket_Price, String image) {
        E_Name = e_Name;
        Place = place;
        E_Date = e_Date;
        Ticket_Price = ticket_Price;
        this.image = image;
    }

    public Event(int id_Event, String e_Name, String place, Date e_Date, double ticket_Price, String image) {
        Id_Event = id_Event;
        E_Name = e_Name;
        Place = place;
        E_Date = e_Date;
        Ticket_Price = ticket_Price;
        this.image = image;
    }

    public int getId_Event() {
        return Id_Event;
    }

    public void setId_Event(int id_Event) {
        Id_Event = id_Event;
    }

    public String getE_Name() {
        return E_Name;
    }

    public void setE_Name(String e_Name) {
        E_Name = e_Name;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public Date getE_Date() {
        return E_Date;
    }

    public void setE_Date(Date e_Date) {
        E_Date = e_Date;
    }

    public double getTicket_Price() {
        return Ticket_Price;
    }

    public void setTicket_Price(double ticket_Price) {
        Ticket_Price = ticket_Price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Event{" +
                "Id_Event=" + Id_Event +
                ", E_Name='" + E_Name + '\'' +
                ", Place='" + Place + '\'' +
                ", E_Date=" + E_Date +
                ", Ticket_Price=" + Ticket_Price +
                ", image='" + image + '\'' +
                '}';
    }
}
