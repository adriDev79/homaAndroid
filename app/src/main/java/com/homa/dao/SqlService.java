package com.homa.dao;

import android.content.Context;

import com.homa.bo.DepenseAnnexe;
import com.homa.bo.DepenseFixe;
import com.homa.bo.Revenu;
import com.homa.bo.Solde;

import java.util.List;

public class SqlService {

    /**
     * Liste de revenus en fonction de la date de création.
     *
     * @param ctx context de l'activité {@code Context}
     * @param dateAccount date du compte {@code String}
     * @return Liste de revenus {@code List<Revenu>}
     */
    public List<Revenu> getAllRevenuWhereDate(Context ctx, String dateAccount) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        return bdd.revenuDao().getAllWhereDate(dateAccount);
    }

    /**
     * Liste de dépenses annexes en fonction de la date de création.
     *
     * @param ctx context de l'activité {@code Context}
     * @param dateAccount date du compte {@code String}
     * @return Liste de dépenses annexes {@code List<DepenseAnnexe>}
     */
    public List<DepenseAnnexe> getAllDAWhereDate(Context ctx, String dateAccount) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        return bdd.depenseAnnexeDao().getAllWhereDate(dateAccount);
    }

    /**
     * Liste de dépenses fixes en fonction de la date de création.
     *
     * @param ctx context de l'activité {@code Context}
     * @param dateAccount date du compte {@code String}
     * @return Liste de dépenses fixes {@code List<DepenseFixe>}
     */
    public List<DepenseFixe> getAllDFWhereDate(Context ctx, String dateAccount) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        return bdd.depenseFixeDao().getAllWhereDate(dateAccount);
    }

    /**
     * Liste des soldes en fonction de la date de création.
     *
     * @param ctx context de l'activité {@code Context}
     * @param dateAccount date du compte {@code String}
     * @return Liste des soldes {@code List<DepenseFixe>}
     */
    public List<Solde> getAllSoldeWhereDate(Context ctx, String dateAccount) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        return bdd.soldeDao().getAllWhereDate(dateAccount);
    }

    /**
     * Liste des dépense fixes en fonction de la date de création et si la dépense est payé.
     *
     * @param ctx context de l'activité {@code Context}
     * @param dateAccount date du compte {@code String}
     * @return Liste des dépenses fixes {@code List<DepenseFixe>}
     */
    public List<DepenseFixe> getAllDFWhereDateAndIsPayed(Context ctx, String dateAccount, boolean isPayed) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        return bdd.depenseFixeDao().getAllWhereDateAndIsPayed(isPayed, dateAccount);
    }

    /**
     * Liste des dépenses annexes en fonction de la date de création et si la dépense est payé.
     *
     * @param ctx context de l'activité {@code Context}
     * @param dateAccount date du compte {@code String}
     * @return Liste des dépenses annexes {@code List<DepenseAnnexe>}
     */
    public List<DepenseAnnexe> getAllDAWhereDateAndIsPayed(Context ctx, String dateAccount, boolean isPayed) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        return bdd.depenseAnnexeDao().getAllWhereDateAndIsPayed(isPayed, dateAccount);
    }

    /**
     * Insert une dépense fixe.
     *
     * @param ctx context de l'activité {@code Context}
     * @param depenseFixe {@code DepenseFixe}
     */
    public void insertDF(Context ctx, DepenseFixe depenseFixe) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.depenseFixeDao().insert(depenseFixe);
    }

    /**
     * Insert une dépense annexe.
     *
     * @param ctx context de l'activité {@code Context}
     * @param depenseAnnexe {@code DepenseAnnexe}
     */
    public void insertDA(Context ctx, DepenseAnnexe depenseAnnexe) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.depenseAnnexeDao().insert(depenseAnnexe);
    }

    /**
     * Insert un revenu.
     *
     * @param ctx context de l'activité {@code Context}
     * @param revenu {@code Revenu}
     */
    public void insertRevenu(Context ctx, Revenu revenu) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.revenuDao().insert(revenu);
    }

    /**
     * Insert un solde.
     *
     * @param ctx context de l'activité {@code Context}
     * @param solde {@code Solde}
     */
    public void insertSolde(Context ctx, Solde solde) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.soldeDao().insert(solde);
    }

    /**
     * Maj de la dépense annexe.
     *
     * @param ctx context de l'activité {@code Context}
     * @param id id de la dépense {@code int}
     * @param libelle libelle de la dépense {@code String}
     * @param montant montant de la dépense {àcode float}
     * @param today date de modification {@code String}
     * @param finalIdTypeDepense id du type de dépense {@code int}
     * @param datePrelevement date de prélévement {@code String}
     * @param check prélévement payé {@code boolean}
     * @param dateFinPrelevement date fin du prélévement {@code String}
     */
    public void updateDA(Context ctx,
                         int id,
                         String libelle,
                         float montant,
                         String today,
                         int finalIdTypeDepense,
                         String datePrelevement,
                         boolean check,
                         String dateFinPrelevement ) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.depenseAnnexeDao().update(id, libelle, montant, today, finalIdTypeDepense, datePrelevement, check, dateFinPrelevement);
    }

    /**
     * Maj de la dépense fixe.
     *
     * @param ctx context de l'activité {@code Context}
     * @param id id de la dépense {@code int}
     * @param libelle libelle de la dépense {@code String}
     * @param montant montant de la dépense {àcode float}
     * @param today date de modification {@code String}
     * @param finalIdTypeDepense id du type de dépense {@code int}
     * @param datePrelevement date de prélévement {@code String}
     * @param check prélévement payé {@code boolean}
     */
    public void updateDF(Context ctx,
                         int id,
                         String libelle,
                         float montant,
                         String today,
                         int finalIdTypeDepense,
                         String datePrelevement,
                         boolean check) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.depenseFixeDao().update(id, libelle, montant, today, finalIdTypeDepense, datePrelevement, check);
    }

    /**
     * Maj du revenu.
     *
     * @param ctx context de l'activité {@code Context}
     * @param id id de la dépense {@code int}
     * @param libelle libelle du revenu {@code String}
     * @param montant montant du revenu {àcode float}
     * @param today date de modification {@code String}
     * @param dateReception date de reception {@code String}
     */
    public void updateRevenu(Context ctx,
                         int id,
                         String libelle,
                         float montant,
                         String today,
                         String dateReception) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.revenuDao().update(id, libelle, montant, today, dateReception);
    }

    /**
     * Maj du solde.
     *
     * @param ctx context de l'activité {@code Context}
     * @param id id de la dépense {@code int}
     * @param libelle libelle du solde {@code String}
     * @param montant montant du solde {àcode float}
     */
    public void updateSolde(Context ctx,
                             int id,
                             String libelle,
                             float montant) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.soldeDao().update(id, libelle, montant);
    }

    /**
     * Supression de la dépense annexe.
     *
     * @param ctx context de l'activité {@code Context}
     * @param id id de la dépense {@code int}
     */
    public void deleteDA(Context ctx, int id) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.depenseAnnexeDao().delete(id);
    }

    /**
     * Supression de la dépense fixe.
     *
     * @param ctx context de l'activité {@code Context}
     * @param id id de la dépense {@code int}
     */
    public void deleteDF(Context ctx, int id) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.depenseFixeDao().delete(id);
    }

    /**
     * Supression du revenu.
     *
     * @param ctx context de l'activité {@code Context}
     * @param id id du revenu {@code int}
     */
    public void deleteRevenu(Context ctx, int id) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.revenuDao().delete(id);
    }

    /**
     * Supression du solde.
     *
     * @param ctx context de l'activité {@code Context}
     * @param id id du solde {@code int}
     */
    public void deleteSolde(Context ctx, int id) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.soldeDao().delete(id);
    }
}
