package entities;

import java.util.Date;
import java.sql.Timestamp;

public class PostEntity  {
    private int id_post,id_forum,id_user;
    private String text;
    private String title;
    private int like_num;
    private Timestamp time;
    private Date date;

    //Constructors
    public PostEntity()
    {

    }

    public PostEntity(int id_post, int id_forum, int id_user, String text, String title, int like_num, Timestamp time, Date date) {
        this.id_post = id_post;
        this.id_forum = id_forum;
        this.id_user = id_user;
        this.text = text;
        this.title = title;
        this.like_num = like_num;
        this.time = time;
        this.date = date;
    }

    public PostEntity(int id_post, int id_forum, int id_user, String title, String text, int like_num) {
        this.id_post = id_post;
        this.title = title;
        this.id_forum = id_forum;
        this.id_user = id_user;
        this.text = text;
        this.like_num = like_num;

        Date now = new Date();
        this.date = now;

        this.time = new Timestamp(date.getTime());
    }
    public PostEntity(int id_post, int id_forum, int id_user, String text, int like_num) {
        this.id_post = id_post;
        this.id_forum = id_forum;
        this.id_user = id_user;
        this.text = text;
        this.like_num = like_num;

        Date now = new Date();
        this.date = now;

        this.time = new Timestamp(date.getTime());
    }

    public PostEntity(int id_forum, int id_user,String title, String text) {
        this.id_forum = id_forum;
        this.id_user = id_user;
        this.title = title;
        this.text = text;
        this.like_num = 0;
        //date
        Date now = new Date();
        this.date = now;
        //time
        this.time = new Timestamp(date.getTime());
    }
    // GETTERS AND SETTERS

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public int getId_forum() {
        return id_forum;
    }

    public void setId_forum(int id_forum) {
        this.id_forum = id_forum;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
        return "PostEntity{" +
                "id_post=" + id_post +
                ", id_forum=" + id_forum +
                ", id_user=" + id_user +
                ", text='" + text + '\'' +
                ", like_num=" + like_num +
                ", time=" + time +
                ", date=" + date +
                '}';
    }
}
