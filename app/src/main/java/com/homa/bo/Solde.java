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
}
