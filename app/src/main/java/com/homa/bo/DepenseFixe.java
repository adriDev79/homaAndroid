package com.homa.bo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class DepenseFixe {

    /**
     * id de la dépense fixe {@code int}
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * Libelle de la dépense fixe {@code String}
     */
    @ColumnInfo(name = "libelle")
    private String libelle;

    /**
     * Montant de la dépense fixe {@code Float}
     */
    @ColumnInfo(name = "montant")
    private Float montant;

    /**
     * id du type de dépense {@code int}
     */
    @ColumnInfo(name = "id_type_depense")
    private int idTypeDepense;

    /**
     * Date de création de la dépense fixe {@code Date}
     */
    @ColumnInfo(name = "date_de_creation")
    private String dateDeCreation;

    /**
     * Date de modification de la dépense fixe {@code Date}
     */
    @ColumnInfo(name = "date_de_modification")
    private String dateDeModification;

    /**
     * Date de prélevement {@code Date}
     */
    @ColumnInfo(name = "date_de_prelevement")
    private String dateDePrelevement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public int getIdTypeDepense() {
        return idTypeDepense;
    }

    public void setIdTypeDepense(int idTypeDepense) {
        this.idTypeDepense = idTypeDepense;
    }

    public String getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(String dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public String getDateDeModification() {
        return dateDeModification;
    }

    public void setDateDeModification(String dateDeModification) {
        this.dateDeModification = dateDeModification;
    }

    public String getDateDePrelevement() {
        return dateDePrelevement;
    }

    public void setDateDePrelevement(String dateDePrelevement) {
        this.dateDePrelevement = dateDePrelevement;
    }

    @Override
    public String toString() {
        return "DepenseFixe{" +
                "id='" + id + '\'' +
                ", libelle='" + libelle + '\'' +
                ", montant=" + montant +
                ", idTypeDepense=" + idTypeDepense +
                ", dateDeCreation=" + dateDeCreation +
                ", dateDeModification=" + dateDeModification +
                '}';
    }
}
