package com.example.smartbookapps;

public class TeacherUsers {

    String Email, category, Name, employeeNum, password, phoneNum, id;
    int count = 0;

    public TeacherUsers(){ }

    public TeacherUsers(String email, String category, String Name, String employeeNum,  String phoneNum, String id) {
        Email = email;
        this.category = category;
        this.Name = Name;
        this.employeeNum = employeeNum;
        this.password = password;
        this.phoneNum = phoneNum;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
