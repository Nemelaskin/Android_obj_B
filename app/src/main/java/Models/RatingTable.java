package Models;

public class RatingTable {
    public int Position ;
    public String Name ;
    public String Email ;

    public RatingTable(int position, String name,  String Email) {
        Position = position;
        Name = name;
        Email = Email;
    }

    public int getPosition() {
        return Position;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }
}
