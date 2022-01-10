package com.example.hotelmanagmentsystem.model;

import java.util.Date;

public class ReservedRoom {
    private int id;
    private int rId;
    private int uId;
    private boolean isCheckedIn;
    private boolean isCheckedOut;
    private Date startDate;
    private Date endDate;

    public ReservedRoom(int id, int rId, int uId, boolean isCheckedIn, boolean isCheckedOut, Date startDate, Date endDate) {
        this.id = id;
        this.rId = rId;
        this.uId = uId;
        this.isCheckedIn = isCheckedIn;
        this.isCheckedOut = isCheckedOut;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
    }

    public boolean getCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public Date getstartDate() {
        return startDate;
    }

    public void setstartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getendDate() {
        return endDate;
    }

    public void setendDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ReservedRoom{" +
                "id=" + id +
                ", rId=" + rId +
                ", uId=" + uId +
                ", isCheckedIn=" + isCheckedIn +
                ", isCheckedOut=" + isCheckedOut +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
