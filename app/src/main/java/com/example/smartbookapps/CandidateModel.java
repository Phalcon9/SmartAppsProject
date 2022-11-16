package com.example.smartbookapps;

public class CandidateModel {
    private String Name, candidateID, department, post, about, id;
    int count = 0;

    public CandidateModel() {
    }

    public CandidateModel(String name, String candidateID, String department, String post, String about, String id) {
        this.Name = name;
        this.candidateID = candidateID;
        this.department = department;
        this.post = post;
        this.about = about;
        this.id =id;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(String candidateID) {
        this.candidateID = candidateID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
