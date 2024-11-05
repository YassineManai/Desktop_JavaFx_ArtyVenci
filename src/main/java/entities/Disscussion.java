package entities;

public class Disscussion {

    int idDis , idSender , idReceiver ;
    String signal ;

    public Disscussion() {
    }

    public Disscussion(int idDis) {
        this.idDis = idDis;
    }

    public Disscussion(int idSender, int idReceiver) {
        this.idSender = idSender;
        this.idReceiver = idReceiver;
    }

    public Disscussion(int idDis, int idSender, int idReceiver) {
        this.idDis = idDis;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
    }

    public int getIdDis() {
        return idDis;
    }

    public void setIdDis(int idDis) {
        this.idDis = idDis;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public int getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(int idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    @Override
    public String toString() {
        return "Disscussion{" +
                "idDis=" + idDis +
                ", idSender=" + idSender +
                ", idReceiver=" + idReceiver +
                '}';
    }
}
