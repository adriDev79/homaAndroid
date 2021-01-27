package com.homa.bo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Solde {

    /**
     * id du solde{@code int}
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * libelle du solde {@code String}
     */
    @ColumnInfo(name = "libelle")
    private String libelle;

    /**
     * montant du solde {@code Float}
     */
    @ColumnInfo(name = "montant")
    private float montant;

    /**
     * date de création {@code Float}
     */
    @ColumnInfo(name = "date_de_creation")
    private String dateCreation;

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

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
}
