package com.example.guestdigital;

public class GuestEverything {
    String v_visitorID;
    String v_firstName;
    String v_secondName;
    String v_lastName;
    String v_address;
    String v_phoneNum;
    String v_email;
    String v_comment;
    String v_dateOfVisit;
    String v_timeOfVisit;

    public GuestEverything() {

    }

    public GuestEverything(String v_visitorID, String v_firstName, String v_secondName, String v_lastName, String v_address,
                           String v_phoneNum, String v_email, String v_comment, String v_dateOfVisit, String v_timeOfVisit) {
        this.v_visitorID = v_visitorID;
        this.v_firstName = v_firstName;
        this.v_secondName = v_secondName;
        this.v_lastName = v_lastName;
        this.v_address = v_address;
        this.v_phoneNum = v_phoneNum;
        this.v_email = v_email;
        this.v_comment = v_comment;
        this.v_dateOfVisit = v_dateOfVisit;
        this.v_timeOfVisit = v_timeOfVisit;

    }

    //Getters
    public String getV_visitorID() {
        return v_visitorID;
    }

    public String getV_firstName() {
        return v_firstName;
    }

    public String getV_secondName() {
        return v_secondName;
    }

    public String getV_lastName() {
        return v_lastName;
    }

    public String getV_address() {
        return v_address;
    }

    public String getV_phoneNum() {
        return v_phoneNum;
    }

    public String getV_email() {
        return v_email;
    }

    public String getV_comment() {
        return v_comment;
    }

    public String getV_dateOfVisit() {
        return v_dateOfVisit;
    }

    public String getV_timeOfVisit() {
        return v_timeOfVisit;
    }


    //Setters
    public void setV_visitorID(String v_visitorID) {
        this.v_visitorID = v_visitorID;
    }

    public void setV_firstName(String v_firstName) {
        this.v_firstName = v_firstName;
    }

    public void setV_secondName(String v_secondName) {
        this.v_secondName = v_secondName;
    }

    public void setV_lastName(String v_lastName) {
        this.v_lastName = v_lastName;
    }

    public void setV_address(String v_address) {
        this.v_address = v_address;
    }

    public void setV_phoneNum(String v_phoneNum) {
        this.v_phoneNum = v_phoneNum;
    }

    public void setV_email(String v_email) {
        this.v_email = v_email;
    }

    public void setV_comment(String v_comment) {
        this.v_comment = v_comment;
    }

    public void setV_dateOfVisit(String v_dateOfVisit) {
        this.v_dateOfVisit = v_dateOfVisit;
    }

    public void setV_timeOfVisit(String v_timeOfVisit) {
        this.v_timeOfVisit = v_timeOfVisit;
    }

}
