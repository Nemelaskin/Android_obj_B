package Models;

public class Rooms {

    public int roomId;
    public String nameRoom;
    public Companies company;
    public int companyId ;
    public String coordinatesRoom;

    public Rooms(String nameRoom, int companyId ) {
        this.nameRoom = nameRoom;
        this.companyId = companyId;
    }

    public Rooms(int roomId, String nameRoom, int companyId, String coordinatesRoom) {
        this.roomId = roomId;
        this.nameRoom = nameRoom;
        this.companyId = companyId;
        this.coordinatesRoom = coordinatesRoom;
    }
}
