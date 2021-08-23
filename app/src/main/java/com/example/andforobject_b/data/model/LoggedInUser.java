package com.example.andforobject_b.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String UserId;
    private String displayName;

    public LoggedInUser(String UserId, String displayName) {
        this.UserId = UserId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return UserId;
    }

    public String getDisplayName() {
        return displayName;
    }
}