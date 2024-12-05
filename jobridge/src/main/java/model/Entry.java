package model;

import java.sql.Date;

public class Entry {
    private int entryId;
    private int userId;
    private Date date;
    private int mood;
    private String comment;

    // Constructor
    public Entry(int entryId, int userId, Date date, int mood, String comment) {
        this.entryId = entryId;
        this.userId = userId;
        this.date = date;
        this.mood = mood;
        this.comment = comment;
    }

    // Getters and Setters
    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
