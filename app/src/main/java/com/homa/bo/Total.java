package com.homa.bo;

public class Total {

    /**
     * libelle du total {@code String}
     */
    private String libelle;

    /**
     * montant du total {@code String}
     */
    private float montant;

    public Total(String libelle, float montant) {
        this.libelle = libelle;
        this.montant = montant;
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
