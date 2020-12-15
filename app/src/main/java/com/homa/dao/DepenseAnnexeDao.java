package com.homa.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.homa.bo.DepenseAnnexe;

import java.util.LinkedList;
import java.util.List;

@Dao
public interface DepenseAnnexeDao {

    /**
     * Récupere toute les dépenses annexes
     *
     * @return {@code LinkedList<DepenseAnnexe>}
     */
    @Query("SELECT * FROM depenseannexe")
    List<DepenseAnnexe> getAll();

    /**
     * Ajouter une dépense annexe
     *
     * @param depenseAnnexe .
     * @return {@code depenseAnnexe}
     */
    @Insert
    void insert(DepenseAnnexe depenseAnnexe);

    /**
     * Mettre à jour une dépense Annexe
     *
     * @return {@code DepenseAnnexe}
     */
    @Query("UPDATE depenseannexe SET libelle = :libelle, montant = :montant, date_de_modification = :dateModif, date_de_prelevement = :datePrel, id_type_depense = :idTypeDepense WHERE id = :id")
    void update(int id, String libelle, float montant, String dateModif, int idTypeDepense, String datePrel);

    /**
     * Supprimer une dépense annexe
     *
     * @return {@code DepenseAnnexe}
     */
    @Query("DELETE FROM depenseannexe WHERE id = :id")
    void delete(int id);
}
