package com.homa.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.homa.bo.Revenu;

import java.util.List;

@Dao
public interface RevenuDao {

    /**
     *
     * @return {@code LinkedList<Revenu>}
     */
    @Query("SELECT * FROM revenu")
    List<Revenu> getAll();

    /**
     *
     * @return {@code LinkedList<Revenu>}
     */
    @Query("SELECT * FROM revenu WHERE date_de_creation = :date")
    List<Revenu> getAllWhereDate(String date);

    /**
     * Ajouter un revenu
     *
     * @param revenu .
     * @return {@code Revenu}
     */
    @Insert
    void insert(Revenu revenu);

    /**
     * Mis à jour du revenu
     *
     * @param id .
     * @param libelle .
     * @param montant .
     * @param dateModif .
     * @param dateReception .
     */
    @Query("UPDATE revenu SET libelle = :libelle, montant = :montant, date_de_modification = :dateModif, date_de_reception = :dateReception WHERE id = :id")
    void update(int id, String libelle, float montant, String dateModif, String dateReception);

    /**
     * Supression d'un revenu
     *
     * @param id .
     * @return {@code Revenu}
     */
    @Query("DELETE FROM revenu WHERE id = :id")
    void delete(int id);
}
