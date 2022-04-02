package com.videotake.Domain;

public class GuestUser implements User{
    private String guestSession_Id;

    public GuestUser(String guestSession_Id){
        this.guestSession_Id = guestSession_Id;
    }

    public String getDisplayName(){ return "Guest user"; }
}
