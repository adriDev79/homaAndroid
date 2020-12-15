package com.homa.utils;

import java.util.LinkedHashMap;

public class HomaUtils {

    public static final String EMPTY = "empty";
    public static final String NON_RENSEIGNE = "Non renseigné";
    public static final int DELAY_LAUNCHER_PAGE = 4300;
    // LOG ACTIVITY
    public static final String TAG = "HOMALOG";

    public static final String DEBUT = "Début";
    public static final String FIN = "fin";
    public static final String SUCCESS = "success";
    public static final String ECHEC = "echec";
    public static final String ACTION = " | action : ";
    public static final String ERREUR = " | erreur : ";
    public static final String RESULTAT = " | résultat : ";

    public static final String CHAMPS_VIDE = "Champs vide";

    //Action
    public static final String ACTION_AJOUTER_REVENU = "Ajouter un revenu";
    public static final String ACTION_AJOUTER_SOLDE = "Ajouter un solde";
    public static final String ACTION_AJOUTER_DEPENSE_FIXE = "Ajouter une dépense fixe";
    public static final String ACTION_AJOUTER_DEPENSE_ANNEXE = "Ajouter une dépense annexe";

    public static final String ACTION_MODIFIER_REVENU = "Modifier un revenu";
    public static final String ACTION_MODIFIER_SOLDE = "Modifier un solde";
    public static final String ACTION_MODIFIER_DEPENSE_FIXE = "Modifier une dépense fixe";
    public static final String ACTION_MODIFIER_DEPENSE_ANNEXE = "Modifier une dépense annexe";

    public static final String ACTION_SUPPRIMER_REVENU = "Supprimer un revenu";
    public static final String ACTION_SUPPRIMER_SOLDE = "Supprimer un solde";
    public static final String ACTION_SUPPRIMER_DEPENSE_FIXE = "Supprimer une dépense fixe";
    public static final String ACTION_SUPPRIMER_DEPENSE_ANNEXE = "Supprimer une dépense annexe";

    //AUTRE
    public static final LinkedHashMap<String, Integer> MAP_TYPE_DEPENSE = mapTypeDepense();

    private static LinkedHashMap<String, Integer> mapTypeDepense() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("Loyer", 1);
        map.put("Nourriture", 2);
        map.put("Essence", 3);
        map.put("Elec/ gaz", 4);
        map.put("Eau", 5);
        map.put("Box internet", 6);
        map.put("Forfait mobile", 7);
        map.put("Impôt", 8);
        map.put("Crédit", 9);
        map.put("Assurance", 10);
        map.put("Mutuelle", 11);
        map.put("Entretien voiture", 12);
        map.put("Loisir", 13);
        map.put("Vacance", 14);
        map.put("Aménagement de la maison", 15);
        map.put("Soin", 16);

        return map;
    }
}
