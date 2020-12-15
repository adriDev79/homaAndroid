package com.homa.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.homa.bo.Revenu;
import com.homa.bo.Solde;

import java.util.List;

@Dao
public interface SoldeDao {

    /**
     *
     * @return {@code LinkedList<Revenu>}
     */
    @Query("SELECT * FROM solde")
    List<Solde> getAll();

    /**
     * Ajouter un solde
     *
     * @param solde .
     * @return {@code Revenu}
     */
    @Insert
    void insert(Solde solde);

    /**
     * Mis à jour du solde
     *
     * @param id .
     * @param libelle .
     * @param montant .
     */
    @Query("UPDATE solde SET libelle = :libelle, montant = :montant WHERE id = :id")
    void update(int id, String libelle, float montant);

    /**
     * Supression d'un solde
     *
     * @param id .
     * @return {@code Revenu}
     */
    @Query("DELETE FROM solde WHERE id = :id")
    void delete(int id);
}
