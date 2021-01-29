package com.homa.bll.asyncTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.homa.bo.DepenseFixe;
import com.homa.bo.Revenu;
import com.homa.dao.AppDataBase;
import com.homa.dao.Connexion;

import java.util.List;

/**
 * Classe qui gére la récupèration des dépense fixe du mois précédent
 */
@SuppressLint("StaticFieldLeak")
public class AsyncTaskRecupDepenseFixe extends AsyncTask<Void, DepenseFixe, List<DepenseFixe>> {

    /**
     * Context de l'activité {@code Context}
     */
    private final Context ctx;


    /**
     * Date des comptes en cours {@code Date}
     */
    private final String dateAccount;

    /**
     * Date des comptes du mois précédent {@code Date}
     */
    private final String datePreviousMonth;


    /**
     * Constructeur
     */
    public AsyncTaskRecupDepenseFixe(Context ctx, String dateAccount, String datePreviousMonth) {
        this.ctx = ctx;
        this.dateAccount = dateAccount;
        this.datePreviousMonth = datePreviousMonth;
    }

    /**
     * Traitement de la tache de fond qui récupère la liste des dépenses fixe en bdd et qui insert les nouvelles dépenses
     *
     * @param voids .
     * @return null {@code null}
     */
    @Override
    protected List<DepenseFixe> doInBackground(Void... voids) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        List<DepenseFixe> depenseFixes = bdd.depenseFixeDao().getAllWhereDate(datePreviousMonth);
        if (depenseFixes.size() > 0) {
            for (DepenseFixe df : depenseFixes) {
                String dateReceptionPreviousMonth = df.getDateDePrelevement();
                String dateReceptionCurrentMonth = df.getDateDePrelevement();
                if (!dateReceptionPreviousMonth.equals("Pas de date")) {
                    String[] date = dateReceptionPreviousMonth.split("/");
                    int month = Integer.parseInt(date [1]);
                    int year = Integer.parseInt(date[2]);

                    if (month == 12) {
                        month = 1;
                        year = year + 1;
                    } else {
                        month = month + 1;
                    }
                    String currentMonth = month < 10 ? "0" + month : String.valueOf(month);
                    dateReceptionCurrentMonth = date[0] + "/" + currentMonth + "/" + year;
                }

                DepenseFixe depenseFixe = new DepenseFixe();
                depenseFixe.setIdTypeDepense(df.getIdTypeDepense());
                depenseFixe.setDateDeCreation(dateAccount);
                depenseFixe.setLibelle(df.getLibelle());
                depenseFixe.setMontant(df.getMontant());
                depenseFixe.setDateDePrelevement(dateReceptionCurrentMonth);
                bdd.depenseFixeDao().insert(depenseFixe);
            }
        }
        return null;
    }
}
