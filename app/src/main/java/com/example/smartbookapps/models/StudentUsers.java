package com.example.smartbookapps.models;

public class StudentUsers {

    String Email, category, fName, matricNum, phoneNum, id;
    int count= 0;



    public StudentUsers(String email, String category, String fName, String matricNum,  String phoneNum,  String id) {

        this.Email = email;
        this.category = category;
        this.fName = fName;
        this.matricNum = matricNum;
        this.phoneNum = phoneNum;
        this.id = id;
    }
    public StudentUsers(){}

//    public String getPassword() {
//        return password;
//    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getMatricNum() {
        return matricNum;
    }

    public void setMatricNum(String matricNum) {
        this.matricNum = matricNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
