package com.homa.bo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class DepenseAnnexe {

    /**
     * id de la depense annexe {@code int}
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * libelle de la depense annexe {@code String}
     */
    @ColumnInfo(name = "libelle")
    private String libelle;

    /**
     * montant de la depense annexe {@code Float}
     */
    @ColumnInfo(name = "montant")
    private Float montant;

    /**
     * id du type de depense {@code int}
     */
    @ColumnInfo(name = "id_type_depense")
    private int idTypeDepense;

    /**
     * date de la création de la depense {@code Date}
     */
    @ColumnInfo(name = "date_de_creation")
    private String dateDeCreation;

    /**
     * date de modification de la depense {@code Date}
     */
    @ColumnInfo(name = "date_de_modification")
    private String dateDeModification;

    /**
     * date de prélévement {@code Date}
     */
    @ColumnInfo(name = "date_de_prelevement")
    private String dateDePrelevement;

    /**
     * dépense payée {@code Date}
     */
    @ColumnInfo(name = "payer")
    private boolean isPayer;

    /**
     * date fin prélévement{@code Date}
     */
    @ColumnInfo(name = "date_fin_prelevement")
    private String dateFinPrelevement;

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

    public boolean isPayer() {
        return isPayer;
    }

    public void setPayer(boolean payer) {
        isPayer = payer;
    }

    public String getDateFinPrelevement() {
        return dateFinPrelevement;
    }

    public void setDateFinPrelevement(String dateFinPrelevement) {
        this.dateFinPrelevement = dateFinPrelevement;
    }

    @Override
    public String toString() {
        return "DepenseAnnexe{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", montant=" + montant +
                ", idTypeDepense=" + idTypeDepense +
                ", dateDeCreation=" + dateDeCreation +
                ", dateDeModification=" + dateDeModification +
                '}';
    }
}
