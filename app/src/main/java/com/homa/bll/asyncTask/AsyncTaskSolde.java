package com.homa.bll.asyncTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.homa.R;
import com.homa.bo.Solde;
import com.homa.dao.AppDataBase;
import com.homa.dao.Connexion;
import com.homa.ihm.activity.ModifierSoldeActivity;
import com.homa.ihm.adapter.ListSoldeAdapter;

import java.util.List;

/**
 * Classe qui gére la liste des soldes.
 */
@SuppressLint("StaticFieldLeak")
public class AsyncTaskSolde extends AsyncTask<Void, Solde, List<Solde>>  {

    /**
     * Context de l'activité {@code Context}
     */
    private final Context ctx;

    /**
     * ListView des soldes {@code ListView}
     */
    private final ListView listView;

    /**
     * Date des comptes en cours {@code Date}
     */

    private String dateAccount;

    /**
     * Constructeur
     */
    public AsyncTaskSolde(Context ctx, ListView listView) {
        this.ctx = ctx;
        this.listView = listView;
    }

    /**
     * Constructeur
     */
    public AsyncTaskSolde(Context ctx, ListView listView, String dateAccount) {
        this.ctx = ctx;
        this.listView = listView;
        this.dateAccount = dateAccount;
    }

    /**
     * Traitement de la tache de fond qui récupère la liste des soldes en bdd.
     *
     * @param voids .
     * @return Liste de soldes en fonction de la date {@code List<Solde>}
     */
    @Override
    protected List<Solde> doInBackground(Void... voids) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        return bdd.soldeDao().getAllWhereDate(dateAccount);
    }

    /**
     * Affichage de la liste soldes dans l'activité.
     * Gestion du click sur un élément de la liste.
     *
     * @param soldes Liste des soldes.
     */
    @Override
    protected void onPostExecute(List<Solde> soldes) {
        super.onPostExecute(soldes);
        ListSoldeAdapter listSoldeAdapter = new ListSoldeAdapter(ctx, R.layout.ligne_list_solde, soldes);
        listView.setAdapter(listSoldeAdapter);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = soldes.size() * 250;
        listView.setLayoutParams(params);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ctx, ModifierSoldeActivity.class);
                intent.putExtra("libelleSolde", soldes.get(i).getLibelle());
                intent.putExtra("montantSolde", String.valueOf(soldes.get(i).getMontant()));
                intent.putExtra("idSolde", String.valueOf(soldes.get(i).getId()));
                intent.putExtra("dateAccount", dateAccount);
                ctx.startActivity(intent);
            }
        });
    }
}
