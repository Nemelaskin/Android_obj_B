package Models;

public class Sensors {
    public int sensorId ;
    public String nameSensor;
    public Users user;
    public int userId ;
    public String coordinates;

    public Sensors(String nameSensor, int userId, String coordinates) {
        this.nameSensor = nameSensor;
        this.userId = userId;
        this.coordinates = coordinates;
    }

    public Sensors(int sensorId, String nameSensor, int userId, String coordinates) {
        this.sensorId = sensorId;
        this.nameSensor = nameSensor;
        this.userId = userId;
        this.coordinates = coordinates;
    }
}
