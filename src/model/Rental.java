package model;

import java.util.Date;

public class Rental {

    private int gameID;
    private int memberID;
    private Date dueDate;
    private Date rentDate;
    private Date returnDate;

    public Rental(int gameID, int memberID, Date dueDate, Date rentDate, Date returnDate) {
        this.gameID = gameID;
        this.memberID = memberID;
        this.dueDate = dueDate;
        this.rentDate = rentDate;
        this.returnDate = null;
    }

    public int getGameID() {
        return gameID;
    }

    public int getMemberID() {
        return memberID;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Date getRentDate() {
        return rentDate;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "gameID=" + gameID +
                ", memberID=" + memberID +
                ", dueDate=" + dueDate +
                ", rentDate=" + rentDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
