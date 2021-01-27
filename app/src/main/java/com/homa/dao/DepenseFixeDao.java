package com.homa.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.homa.bo.DepenseAnnexe;
import com.homa.bo.DepenseFixe;

import java.util.LinkedList;
import java.util.List;

@Dao
public interface DepenseFixeDao {

    /**
     * Récupère toute les dépenses fixes
     *
     * @return {@code LinkedList<DepenseFixe>}
     */
    @Query("SELECT * FROM depensefixe")
    List<DepenseFixe> getAll();

    /**
     * Récupère toute les dépenses fixes en fonction de la date choisis
     *
     * @return {@code LinkedList<DepenseFixe>}
     */
    @Query("SELECT * FROM depensefixe WHERE date_de_creation = :date")
    List<DepenseFixe> getAllWhereDate(String date);

    /**
     * Récupere toute les dépenses fixe si elles sont payées
     *
     * @return {@code LinkedList<DepenseAnnexe>}
     */
    @Query("SELECT * FROM depensefixe WHERE payer = :isPayed")
    List<DepenseFixe> getAllIsPayed(boolean isPayed);

    /**
     * Récupère toute les dépenses fixes en fonction de la date choisis
     *
     * @return {@code LinkedList<DepenseFixe>}
     */
    @Query("SELECT * FROM depensefixe WHERE date_de_creation = :date AND payer = :isPayed")
    List<DepenseFixe> getAllWhereDateAndIsPayed(boolean isPayed, String date);

    /**
     * Ajouter une dépense fixe
     *
     * @param depenseFixe .
     * @return {@code DepenseFixe}
     */
    @Insert
    void insert(DepenseFixe depenseFixe);

    /**
     * Mettre à jour une dépense fixe
     *
     * @return {@code DepenseFixe}
     */
    @Query("UPDATE depensefixe" +
            " SET libelle = :libelle," +
            " montant = :montant," +
            " date_de_modification = :dateModif," +
            " date_de_prelevement = :datePrel," +
            " id_type_depense = :idTypeDepense," +
            " payer = :payer WHERE id = :id")
    void update(int id, String libelle, float montant, String dateModif, int idTypeDepense, String datePrel, boolean payer);

    /**
     * Supprimer une dépense fixe
     *
     * @return {@code DepenseFixe}
     */
    @Query("DELETE FROM depensefixe WHERE id = :id")
    void delete(int id);
}
