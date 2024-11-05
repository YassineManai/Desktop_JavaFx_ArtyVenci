package entities;

import java.sql.Timestamp;
import java.util.Date;

public class Message {

    private int idMsg , idSender , idDis ;
    private String content , reaction ;
    private Date date ;
    private Timestamp time ;
    private Date now = new Date();

    public Message() {
    }

    public Message(int idSender, int idDis, String content, String reaction) {
        this.idSender = idSender;
        this.idDis = idDis;
        this.content = content;
        this.reaction = reaction;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idMsg=" + idMsg +
                ", idSender=" + idSender +
                ", idDis=" + idDis +
                ", content='" + content + '\'' +
                ", reaction='" + reaction + '\'' +
                ", time=" + time +
                ", now=" + now +
                '}';
    }

    public Message(int idMsg, int idSender, int idDis, String content, String reaction, Timestamp time) {
        this.idMsg = idMsg;
        this.idSender = idSender;
        this.idDis = idDis;
        this.content = content;
        this.reaction = reaction;
        this.time = time;
    }

    public Message(int idMsg, int idSender, int idDis, String content, String reaction, Date date, Timestamp time, Date now) {
        this.idMsg = idMsg;
        this.idSender = idSender;
        this.idDis = idDis;
        this.content = content;
        this.reaction = reaction;
        this.date = date;
        this.time = time;
        this.now = now;
    }

    public Message(int idSender, String content, String reaction) {
        this.idSender = idSender;
        this.content = content;
        this.reaction = reaction;

        this.date= now ;
        this.time = new Timestamp(date.getTime()) ;
    }

    public Message(int idMsg, String content) {
        this.idMsg = idMsg;
        this.content = content;
    }

    public Message(int idMsg) {
        this.idMsg = idMsg;
    }

    public int getIdMsg() {
        return idMsg;
    }

    public void setIdMsg(int idMsg) {
        this.idMsg = idMsg;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public int getIdDis() {
        return idDis;
    }

    public void setIdDis(int idDis) {
        this.idDis = idDis;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }
}
