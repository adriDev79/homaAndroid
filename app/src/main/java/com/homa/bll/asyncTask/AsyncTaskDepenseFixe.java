package com.homa.bll.asyncTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.homa.R;
import com.homa.bo.DepenseFixe;
import com.homa.dao.SqlService;
import com.homa.ihm.activity.ModifierDepenseFixeActivity;
import com.homa.ihm.adapter.ListDepenseFixeAdapter;
import com.homa.utils.HomaUtils;

import java.util.List;

/**
 * Classe qui gére la liste des dépenses fixes
 */
@SuppressLint("StaticFieldLeak")
public class AsyncTaskDepenseFixe extends AsyncTask<Void, DepenseFixe, List<DepenseFixe>> {

    /**
     * Context de l'activité {@code Context}
     */
    private final Context ctx;

    /**
     * ListView des dépenses fixes {@code ListView}
     */
    private final ListView listView;

    /**
     * Date des comptes en cours {@code Date}
     */
    private String dateAccount;

    /**
     * élément d'affichage du message pour récupérer les dépenses du mois précédent {@code LinearLayout}
     */
    private LinearLayout llRecupDepenseFixe;

    /**
     * Constructeur
     */
    public AsyncTaskDepenseFixe(Context ctx, ListView view, String dateAccount) {
        this.ctx = ctx;
        this.listView = view;
        this.dateAccount = dateAccount;
    }

    /**
     * Constructeur
     */
    public AsyncTaskDepenseFixe(Context ctx, ListView listView, String dateAccount, LinearLayout llRecupDepenseFixe) {
        this.ctx = ctx;
        this.listView = listView;
        this.dateAccount = dateAccount;
        this.llRecupDepenseFixe = llRecupDepenseFixe;
    }

    /**
     * Traitement de la tache de fond qui récupère la liste des dépenses fixes en bdd
     *
     * @param voids .
     * @return Liste de dépenses fixes en fonction de la date {@code List<DepenseFixe>}
     */
    @Override
    protected List<DepenseFixe> doInBackground(Void... voids) {
        SqlService sqlService = new SqlService();
        return sqlService.getAllDFWhereDate(ctx, dateAccount);
    }

    /**
     * Affichage de la liste des dépense fixes dans l'activité.
     * Gestion du click sur un élément de la liste.
     *
     * @param depenseFixes Liste des dépense fixes
     */
    @Override
    protected void onPostExecute(List<DepenseFixe> depenseFixes) {
        super.onPostExecute(depenseFixes);

        // Si la liste est vide on affiche le message pour récuperer les dépense du mois précédent
        if(depenseFixes.size() == 0) {
            if (llRecupDepenseFixe != null) {
                HomaUtils.visibiltyLinearLayout(llRecupDepenseFixe, View.VISIBLE, 100);
            }
        } else {
            if (llRecupDepenseFixe != null) {
                HomaUtils.visibiltyLinearLayout(llRecupDepenseFixe, View.INVISIBLE, 0);
            }
        }

        ListDepenseFixeAdapter listDepenseFixeAdapter = new ListDepenseFixeAdapter(ctx, R.layout.ligne_list_depense_fixe, depenseFixes);
        listView.setAdapter(listDepenseFixeAdapter);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = depenseFixes.size() * 310;
        listView.setLayoutParams(params);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ctx, ModifierDepenseFixeActivity.class);

                String typeDepense = null;
                for (String item : HomaUtils.MAP_TYPE_DEPENSE.keySet()) {
                    if (HomaUtils.MAP_TYPE_DEPENSE.get(item) != null) {
                        if (HomaUtils.MAP_TYPE_DEPENSE.get(item) == depenseFixes.get(i).getIdTypeDepense()) {
                            typeDepense = item;
                        }
                    }
                }
                intent.putExtra("typeDepenseFixe", typeDepense);
                intent.putExtra("libelleDepenseFixe", depenseFixes.get(i).getLibelle());
                intent.putExtra("montantDepenseFixe", String.valueOf(depenseFixes.get(i).getMontant()));
                intent.putExtra("datePrelevement", String.valueOf(depenseFixes.get(i).getDateDePrelevement()));
                intent.putExtra("idDepenseFixe", String.valueOf(depenseFixes.get(i).getId()));
                intent.putExtra("isPayerDepenseFixe", String.valueOf(depenseFixes.get(i).isPayer()));
                intent.putExtra("dateAccount", dateAccount);
                ctx.startActivity(intent);
            }
        });
    }
}
