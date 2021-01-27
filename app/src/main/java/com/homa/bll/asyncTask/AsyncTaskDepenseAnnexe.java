package com.homa.bll.asyncTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.homa.R;
import com.homa.bo.DepenseAnnexe;
import com.homa.dao.AppDataBase;
import com.homa.dao.Connexion;
import com.homa.ihm.activity.ModifierDepenseAnnexeActivity;
import com.homa.ihm.adapter.ListDepenseAnnexeAdapter;
import com.homa.utils.HomaUtils;

import java.util.List;

/**
 * Classe qui gére la liste des dépenses annexes
 */
@SuppressLint("StaticFieldLeak")
public class AsyncTaskDepenseAnnexe extends AsyncTask<Void, DepenseAnnexe, List<DepenseAnnexe>> {

    /**
     * Context de l'activité {@code Context}
     */
    private final Context ctx;

    /**
     * ListView des dépenses annexes {@code ListView}
     */
    private final ListView listView;

    /**
     * Date des comptes en cours {@code Date}
     */

    private String dateAccount;

    /**
     * Constructeur
     */
    public AsyncTaskDepenseAnnexe(Context ctx, ListView listView) {
        this.ctx = ctx;
        this.listView = listView;
    }

    /**
     * Constructeur
     */
    public AsyncTaskDepenseAnnexe(Context ctx, ListView listView, String dateAccount) {
        this.ctx = ctx;
        this.listView = listView;
        this.dateAccount = dateAccount;
    }

    /**
     * Traitement de la tache de fond qui récupère la liste des dépenses annexes en bdd
     *
     * @param voids .
     * @return Liste de dépenses annexes en fonction de la date {@code List<DepenseAnnexe>}
     */
    @Override
    protected List<DepenseAnnexe> doInBackground(Void... voids) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        return bdd.depenseAnnexeDao().getAllWhereDate(dateAccount);
    }

    /**
     * Affichage de la liste des dépense annexes dans l'activité.
     * Gestion du click sur un élément de la liste.
     *
     * @param depenseAnnexes Liste des dépense annexes
     */
    @Override
    protected void onPostExecute(List<DepenseAnnexe> depenseAnnexes) {
        super.onPostExecute(depenseAnnexes);
        ListDepenseAnnexeAdapter listDepenseAnnexeAdapter = new ListDepenseAnnexeAdapter(ctx, R.layout.ligne_list_depense_annexe, depenseAnnexes);
        listView.setAdapter(listDepenseAnnexeAdapter);
        listView.getLayoutParams().height = depenseAnnexes.size() * 310;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ctx, ModifierDepenseAnnexeActivity.class);

                String typeDepense = null;
                for (String item : HomaUtils.MAP_TYPE_DEPENSE.keySet()) {
                    if (HomaUtils.MAP_TYPE_DEPENSE.get(item) == depenseAnnexes.get(i).getIdTypeDepense()) {
                        typeDepense = item;
                    }
                }
                intent.putExtra("typeDepenseAnnexe", typeDepense);
                intent.putExtra("libelleDepenseAnnexe", depenseAnnexes.get(i).getLibelle());
                intent.putExtra("datePrelevementDA", depenseAnnexes.get(i).getDateDePrelevement());
                intent.putExtra("montantDepenseAnnexe", String.valueOf(depenseAnnexes.get(i).getMontant()));
                intent.putExtra("idDepenseAnnexe", String.valueOf(depenseAnnexes.get(i).getId()));
                intent.putExtra("isPayer", String.valueOf(depenseAnnexes.get(i).isPayer()));
                intent.putExtra("finPrelevement", String.valueOf(depenseAnnexes.get(i).getDateFinPrelevement()));
                intent.putExtra("dateAccount", dateAccount);

                ctx.startActivity(intent);
            }
        });
    }
}
