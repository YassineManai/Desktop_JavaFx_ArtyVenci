package entities;
import java.sql.Timestamp;
import java.util.Date;


public class ForumEntity {
    private int id_forum;
    private String title,description;
    private int replies_num;
    private Date date;

    //Constructors

    public ForumEntity(){

    }

    public ForumEntity(int id_forum, String title, String description, int replies_num, Date date) {
        this.id_forum = id_forum;
        this.title = title;
        this.description = description;
        this.replies_num = replies_num;
        this.date = date;
    }

    public ForumEntity(int id_forum, String title, String description, int replies_num) {
        this.id_forum = id_forum;
        this.title = title;
        this.description = description;
        this.replies_num = replies_num;
        Date now = new Date();
        this.date = now;
    }

    public ForumEntity(String title, String description, int replies_num) {
        this.title = title;
        this.description = description;
        this.replies_num = replies_num;
        Date now = new Date();
        this.date = now;
    }

    public ForumEntity(String title, String description) {
        this.title = title;
        this.description = description;
        this.replies_num = 0;
        Date now = new Date();
        this.date = now;
    }

    //Getters and Setters


    public int getId_forum() {
        return id_forum;
    }

    public void setId_forum(int id_forum) {
        this.id_forum = id_forum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReplies_num() {
        return replies_num;
    }

    public void setReplies_num(int replies_num) {
        this.replies_num = replies_num;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //ToString

    @Override
    public String toString() {
        return "ForumEntity{" +
                "id_forum=" + id_forum +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", replies_num=" + replies_num +
                ", date=" + date +
                '}';
    }
}
