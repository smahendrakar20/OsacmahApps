package com.example.lrmah.rajaranichorpolice;

/**
 * Created by lrmah on 06-10-2017.
 */

public class roomCreate {

    private String id;
    private String roomName;
    private int numberOfPlayers;
    private int numberOfGames;
    private String isGameStarted;
    private int currentPriority;
    private String status="";

    public roomCreate() {
    }

    public roomCreate(String id, String name, int numberOfPlayers,int numberOfGames) {
        this.id = id;
        this.roomName = name;
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfGames = numberOfGames;
        isGameStarted="0";
        currentPriority=1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String name) {
        this.roomName = name;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int number) {
        this.numberOfPlayers = number;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int number) {
        this.numberOfGames = number;
    }

    public String getIsGameStarted() {
        return isGameStarted;
    }

    public void setIsGameStarted(String isGameStarted) {
        this.isGameStarted = isGameStarted;
    }

    public int getCurrentPriority() {
        return currentPriority;
    }

    public void setCurrentPriority(int currentPriority) {
        this.currentPriority = currentPriority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
