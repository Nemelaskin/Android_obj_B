package Models;

import java.math.BigDecimal;

public class Positions {
    public int positionId;
    public String namePosition ;
    public double salary ;

    public Positions(String namePosition, double salary) {
        this.namePosition = namePosition;
        this.salary = salary;
    }

    public Positions(int positionId, String namePosition, double salary) {
        this.positionId = positionId;
        this.namePosition = namePosition;
        this.salary = salary;
    }
}
