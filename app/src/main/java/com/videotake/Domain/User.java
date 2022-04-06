package com.videotake.Domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "user_table", primaryKeys = {"getSession_Id"})
public interface User {
    @ColumnInfo(name = "display_name")
    public String getDisplayName();

    @ColumnInfo(name = "session_ID")
    public String getSession_Id();
}
