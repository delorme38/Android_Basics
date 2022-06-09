package com.delorme.androidbasics.model;

public class User {
    private String mFirstName;
    private int mScore;

    public int getScore() {
        return mScore;
    }

    public User setScore(int score) {
        mScore = score;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public User setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }
}
