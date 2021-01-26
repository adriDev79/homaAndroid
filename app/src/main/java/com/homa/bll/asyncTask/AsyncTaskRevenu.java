package com.homa.bll.asyncTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.homa.R;
import com.homa.bo.Revenu;
import com.homa.dao.AppDataBase;
import com.homa.dao.Connexion;
import com.homa.ihm.activity.ModifierRevenuActivity;
import com.homa.ihm.adapter.ListRevenuAdapter;
import com.homa.utils.HomaUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe qui va gérer la liste des revenus
 */
@SuppressLint("StaticFieldLeak")
public class AsyncTaskRevenu extends AsyncTask<Void, Revenu, List<Revenu>> {

    /**
     * Context de l'activité {@code Context}
     */
    private Context ctx;

    /**
     * ListView des revenus {@code ListView}
     */
    private ListView listView;

    /**
     * Date des comptes en cours {@code Date}
     */
    private Date dateAccount;

    /**
     * Constructeur
     */
    public AsyncTaskRevenu() {}

    /**
     * Constructeur
     */
    public AsyncTaskRevenu(Context ctx, ListView listView) {
        this.ctx = ctx;
        this.listView = listView;
    }

    /**
     * Constructeur
     */
    public AsyncTaskRevenu(Context ctx, ListView listView, Date dateAccount) {
        this.ctx = ctx;
        this.listView = listView;
        this.dateAccount = dateAccount;
        Log.i(HomaUtils.TAG, "date choisis constructeur " + dateAccount);
    }

    /**
     * Traitement de la tache de fond qui récupère la liste des revenus en bdd
     *
     * @param voids .
     * @return Liste de revenus trier en fonction de la date {@code List<Revenu>}
     */
    @Override
    protected List<Revenu> doInBackground(Void... voids) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        List<Revenu> revenus = bdd.revenuDao().getAll();
        List<Revenu> revenusTrier = new ArrayList<>();

        for (Revenu r : revenus) {
            Date dateRevenu = new Date(r.getDateDeCreation());
            int monthRevenu = dateRevenu.getMonth();
            int yearRevenu = dateRevenu.getYear();
            if (monthRevenu == dateAccount.getMonth() && yearRevenu == dateAccount.getYear()) {
                revenusTrier.add(r);
            }
        }
        return revenusTrier;
    }

    /**
     * Affichage de la liste des revenus dans l'activité.
     *
     * @param revenusTrier Liste de revenus trier
     */
    @Override
    protected void onPostExecute(List<Revenu> revenusTrier) {
        super.onPostExecute(revenusTrier);
//
        ListRevenuAdapter listRevenuAdapter = new ListRevenuAdapter(ctx, R.layout.ligne_list_revenu, revenusTrier);
        listView.setAdapter(listRevenuAdapter);

        // Modification de la taille de la liste en fonction du nombre de revenus
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = revenusTrier.size() * 250;
        listView.setLayoutParams(params);

        /**
         * Click sur un élément de la liste pour accéder à la page de modification.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ctx, ModifierRevenuActivity.class);

                //TODO: transférer directement l'objet
                intent.putExtra("libelle", revenusTrier.get(i).getLibelle());
                intent.putExtra("dateReception", revenusTrier.get(i).getDateDeReception());
                intent.putExtra("montant", String.valueOf(revenusTrier.get(i).getMontant()));
                intent.putExtra("id", String.valueOf(revenusTrier.get(i).getId()));

                ctx.startActivity(intent);
            }
        });
    }
}
