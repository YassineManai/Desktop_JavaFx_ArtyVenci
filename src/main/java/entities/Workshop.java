package entities;

public class Workshop {
    private int Id_Workshop;
    private String Title;
    private String Details;
    private String image;
    private int Id_Event;

    public Workshop() {
    }

    public Workshop(String title, String details, String image, int id_Event) {
        Title = title;
        Details = details;
        this.image = image;
        Id_Event = id_Event;
    }

    public Workshop(int id_Workshop, String title, String details, String image, int id_Event) {
        Id_Workshop = id_Workshop;
        Title = title;
        Details = details;
        this.image = image;
        Id_Event = id_Event;
    }

    public int getId_Workshop() {
        return Id_Workshop;
    }

    public void setId_Workshop(int id_Workshop) {
        Id_Workshop = id_Workshop;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId_Event() {
        return Id_Event;
    }

    public void setId_Event(int id_Event) {
        Id_Event = id_Event;
    }

    @Override
    public String toString() {
        return "Workshop{" +
                "Id_Workshop=" + Id_Workshop +
                ", Title='" + Title + '\'' +
                ", Details='" + Details + '\'' +
                ", image='" + image + '\'' +
                ", Id_Event=" + Id_Event +
                '}';
    }
}
