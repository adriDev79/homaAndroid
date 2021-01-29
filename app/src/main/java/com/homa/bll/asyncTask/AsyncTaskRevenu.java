package com.homa.bll.asyncTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.homa.R;
import com.homa.bo.Revenu;
import com.homa.dao.AppDataBase;
import com.homa.dao.Connexion;
import com.homa.dao.SqlService;
import com.homa.ihm.activity.ModifierRevenuActivity;
import com.homa.ihm.adapter.ListRevenuAdapter;
import com.homa.utils.HomaUtils;

import java.util.List;

/**
 * Classe qui gére la liste des revenus
 */
@SuppressLint("StaticFieldLeak")
public class AsyncTaskRevenu extends AsyncTask<Void, Revenu, List<Revenu>> {

    /**
     * Context de l'activité {@code Context}
     */
    private final Context ctx;

    /**
     * ListView des revenus {@code ListView}
     */
    private final ListView listView;

    /**
     * Date des comptes en cours {@code Date}
     */
    private final String dateAccount;

    /**
     * élément d'affichage du message pour récupérer les revenus du mois précédent {@code LinearLayout}
     */
    private LinearLayout llRecupRevenu;

    /**
     * Constructeur
     */
    public AsyncTaskRevenu(Context ctx, ListView listView, String dateAccount) {
        this.ctx = ctx;
        this.listView = listView;
        this.dateAccount = dateAccount;
    }

    /**
     * Constructeur
     */
    public AsyncTaskRevenu(Context ctx, ListView listView, String dateAccount, LinearLayout llRecupRevenu) {
        this.ctx = ctx;
        this.listView = listView;
        this.dateAccount = dateAccount;
        this.llRecupRevenu = llRecupRevenu;
    }

    /**
     * Traitement de la tache de fond qui récupère la liste des revenus en bdd
     *
     * @param voids .
     * @return Liste de revenus trier en fonction de la date {@code List<Revenu>}
     */
    @Override
    protected List<Revenu> doInBackground(Void... voids) {
        SqlService sqlService = new SqlService();
        return sqlService.getAllRevenuWhereDate(ctx, dateAccount);
    }

    /**
     * Affichage de la liste des revenus dans l'activité.
     * Gestion du click sur un élément de la liste.
     *
     * @param revenus Liste de revenus trier
     */
    @Override
    protected void onPostExecute(List<Revenu> revenus) {
        super.onPostExecute(revenus);

        if(revenus.size() == 0) {
            if (llRecupRevenu != null) {
                HomaUtils.visibiltyLinearLayout(llRecupRevenu, View.VISIBLE, 100);
            }
        } else {
            if (llRecupRevenu != null) {
                HomaUtils.visibiltyLinearLayout(llRecupRevenu, View.INVISIBLE, 0);
            }
        }

        ListRevenuAdapter listRevenuAdapter = new ListRevenuAdapter(ctx, R.layout.ligne_list_revenu, revenus);
        listView.setAdapter(listRevenuAdapter);

        // Modification de la taille de la liste en fonction du nombre de revenus
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = revenus.size() * 250;
        listView.setLayoutParams(params);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ctx, ModifierRevenuActivity.class);

                //TODO: transférer directement l'objet
                intent.putExtra("libelle", revenus.get(i).getLibelle());
                intent.putExtra("dateReception", revenus.get(i).getDateDeReception());
                intent.putExtra("montant", String.valueOf(revenus.get(i).getMontant()));
                intent.putExtra("id", String.valueOf(revenus.get(i).getId()));
                intent.putExtra("dateAccount", dateAccount);

                ctx.startActivity(intent);
            }
        });
    }
}
