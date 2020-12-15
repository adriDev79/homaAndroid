package com.homa.dao;

import android.content.Context;

import androidx.room.Room;

public class Connexion {

    public static AppDataBase getConnexion(Context ctx) {
        return Room.databaseBuilder(ctx, AppDataBase.class, "homaBdd").build();
    }
}
