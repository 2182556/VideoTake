package com.videotake.Domain;

public class GuestUser implements User{
    private String session_Id;

    public GuestUser(String session_Id){
        this.session_Id = session_Id;
    }

    public String getDisplayName(){ return "Guest user"; }

    @Override
    public String getSession_Id() {
        return session_Id;
    }
}
