package com.homa.bll.asyncTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.homa.R;
import com.homa.bo.Revenu;
import com.homa.dao.AppDataBase;
import com.homa.dao.Connexion;
import com.homa.dao.SqlService;
import com.homa.ihm.activity.ModifierRevenuActivity;
import com.homa.ihm.adapter.ListRevenuAdapter;
import com.homa.utils.HomaToastUtils;
import com.homa.utils.HomaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui gére la liste des revenus
 */
@SuppressLint("StaticFieldLeak")
public class AsyncTaskRecupRevenu extends AsyncTask<Void, Revenu, List<Revenu>> {

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
    public AsyncTaskRecupRevenu(Context ctx, String dateAccount, String datePreviousMonth) {
        this.ctx = ctx;
        this.dateAccount = dateAccount;
        this.datePreviousMonth = datePreviousMonth;
    }

    /**
     * Traitement de la tache de fond qui récupère la liste des revenus en bdd et qui insert les nouveaux revenus
     *
     * @param voids .
     * @return null {@code null}
     */
    @Override
    protected List<Revenu> doInBackground(Void... voids) {
        SqlService sqlService = new SqlService();

        List<Revenu> revenus = sqlService.getAllRevenuWhereDate(ctx, datePreviousMonth);

        if (revenus.size() > 0) {
            for (Revenu r : revenus) {
                String dateReceptionPreviousMonth = r.getDateDeReception();
                String dateReceptionCurrentMonth = r.getDateDeReception();

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

                Revenu revenu = new Revenu();
                revenu.setDateDeCreation(dateAccount);
                revenu.setLibelle(r.getLibelle());
                revenu.setMontant(r.getMontant());
                revenu.setDateDeReception(dateReceptionCurrentMonth);

                sqlService.insertRevenu(ctx, revenu);
            }
        }
        return null;
    }
}
