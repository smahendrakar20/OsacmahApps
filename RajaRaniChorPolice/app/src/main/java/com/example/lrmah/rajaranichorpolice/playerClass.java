package com.example.lrmah.rajaranichorpolice;

/**
 * Created by lrmah on 06-10-2017.
 */

public class playerClass {
    private String name;
    private String USERname;
    private String character;
    private String stateOfPlayer;
    private String message;
    private String photoUrl;
    private String score;

    public playerClass() {
    }

    public playerClass(String name,String score) {
        this.score=score;
        this.USERname = name;


    }
    public playerClass(String text, String name, String character,String message,String photoUrl) {
        this.name = text;
        this.USERname = name;
        this.character = character;
        this.message=message;
        this.photoUrl=photoUrl;

    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getUserName() {
        return USERname;
    }

    public void setUserName(String USERname) {
        this.USERname = USERname;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
    public String getStateOfPlayer() {
        return stateOfPlayer;
    }

    public void setStateOfPlayer(String state) {
        this.stateOfPlayer = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }
}
