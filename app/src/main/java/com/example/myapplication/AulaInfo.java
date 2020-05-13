package com.example.myapplication;

public class AulaInfo {
    private String name, mobileNumber, department;
    private String bloodGroup, roomNumber, permanentLocation, fatherName, motherName;
    private String gurdianPhoneNumber;
    private String birthDay;

    public AulaInfo(String name, String mobileNumber, String department, String bloodGroup, String roomNumber, String permanentLocation, String fatherName, String motherName, String gurdianPhoneNumber, String birthDay) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.department = department;
        this.bloodGroup = bloodGroup;
        this.roomNumber = roomNumber;
        this.permanentLocation = permanentLocation;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.gurdianPhoneNumber = gurdianPhoneNumber;
        this.birthDay = birthDay;
    }

    public AulaInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getPermanentLocation() {
        return permanentLocation;
    }

    public void setPermanentLocation(String permanentLocation) {
        this.permanentLocation = permanentLocation;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getGurdianPhoneNumber() {
        return gurdianPhoneNumber;
    }

    public void setGurdianPhoneNumber(String gurdianPhoneNumber) {
        this.gurdianPhoneNumber = gurdianPhoneNumber;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
}
