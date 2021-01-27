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
     * Récupere toute les dépenses annexes.
     *
     * @return {@code LinkedList<DepenseAnnexe>}
     */
    @Query("SELECT * FROM depenseannexe")
    List<DepenseAnnexe> getAll();

    /**
     * Récupere toute les dépenses annexes en fonction de la date choisis.
     *
     * @return {@code LinkedList<DepenseAnnexe>}
     */
    @Query("SELECT * FROM depenseannexe WHERE date_de_creation = :date")
    List<DepenseAnnexe> getAllWhereDate(String date);

    /**
     * Récupere toute les dépenses annexes si elles sont payées.
     *
     * @return {@code LinkedList<DepenseAnnexe>}
     */
    @Query("SELECT * FROM depenseannexe WHERE payer = :isPayed")
    List<DepenseAnnexe> getAllIsPayed(boolean isPayed);

    /**
     * Récupere toute les dépenses annexes en fonction de la date choisis et si elles sont payées.
     *
     * @return {@code LinkedList<DepenseAnnexe>}
     */
    @Query("SELECT * FROM depenseannexe WHERE date_de_creation = :date AND payer = :isPayed")
    List<DepenseAnnexe> getAllWhereDateAndIsPayed(boolean isPayed, String date);

    /**
     * Ajouter une dépense annexe.
     *
     * @param depenseAnnexe .
     * @return {@code depenseAnnexe}
     */
    @Insert
    void insert(DepenseAnnexe depenseAnnexe);

    /**
     * Mettre à jour une dépense Annexe.
     *
     * @return {@code DepenseAnnexe}
     */
    @Query("UPDATE depenseannexe " +
            "SET libelle = :libelle," +
            " montant = :montant," +
            " date_de_modification = :dateModif," +
            " date_de_prelevement = :datePrel," +
            " id_type_depense = :idTypeDepense," +
            " payer = :payer," +
            " date_fin_prelevement = :finPrelevement" +
            " WHERE id = :id")
    void update(int id, String libelle, float montant, String dateModif, int idTypeDepense, String datePrel, boolean payer, String finPrelevement);

    /**
     * Supprimer une dépense annexe.
     *
     * @return {@code DepenseAnnexe}
     */
    @Query("DELETE FROM depenseannexe WHERE id = :id")
    void delete(int id);
}
