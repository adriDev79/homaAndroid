package com.homa.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.homa.bo.DepenseAnnexe;
import com.homa.bo.DepenseFixe;
import com.homa.bo.Revenu;
import com.homa.bo.Solde;

@Database(entities = {Revenu.class, DepenseFixe.class, DepenseAnnexe.class, Solde.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract RevenuDao revenuDao();
    public abstract DepenseFixeDao depenseFixeDao();
    public abstract DepenseAnnexeDao depenseAnnexeDao();
    public abstract SoldeDao soldeDao();
}
