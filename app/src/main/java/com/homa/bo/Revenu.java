package com.homa.bo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Revenu {

    /**
     * id du revenu {@code int}
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * libelle du revenu {@code String}
     */
    @ColumnInfo(name = "libelle")
    private String libelle;

    /**
     * montant du revenu {@code Float}
     */
    @ColumnInfo(name = "montant")
    private Float montant;

    /**
     * date de réception du revenu {@code Date}
     */
    @ColumnInfo(name = "date_de_reception")
    private String dateDeReception;

    /**
     * date de création du revenu {@code Date}
     */
    @ColumnInfo(name = "date_de_creation")
    private String dateDeCreation;

    /**
     * date de modification du revenu {@code Date}
     */
    @ColumnInfo(name = "date_de_modification")
    private String dateDeModification;

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

    public String getDateDeReception() {
        return dateDeReception;
    }

    public void setDateDeReception(String dateDeReception) {
        this.dateDeReception = dateDeReception;
    }


    @Override
    public String toString() {
        return "Revenu{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", montant=" + montant +
                ", dateDeCreation=" + dateDeCreation +
                ", dateDeModification=" + dateDeModification +
                '}';
    }
}
