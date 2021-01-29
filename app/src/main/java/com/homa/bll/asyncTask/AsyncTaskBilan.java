package com.homa.bll.asyncTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.ListView;

import com.homa.R;
import com.homa.bo.Total;
import com.homa.dao.SqlService;
import com.homa.ihm.adapter.ListBilanAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Tâche qui gére la liste des soldes.
 */
@SuppressLint("StaticFieldLeak")
public class AsyncTaskBilan extends AsyncTask<Void, Total, List<Total>>  {

    /**
     * Context de l'activité {@code Context}
     */
    private final Context ctx;

    /**
     * ListView du bilan {@code ListView}
     */
    private final ListView listView;

    /**
     * Date des comptes en cours {@code Date}
     */

    private final String dateAccount;

    /**
     * Constructeur
     */
    public AsyncTaskBilan(Context ctx, ListView listView, String dateAccount) {
        this.ctx = ctx;
        this.listView = listView;
        this.dateAccount = dateAccount;
    }

    /**
     * Traitement de la tache de fond qui récupère la toutes les listes en bdd.
     * Calcul du bilan.
     *
     * @param voids .
     * @return Liste de soldes en fonction de la date {@code List<Solde>}
     */
    @Override
    protected List<Total> doInBackground(Void... voids) {
        boolean isPayed = true;

        List<Total> totaux = new ArrayList<>();
        final float[] montantTotalRevenu = {0f};
        final float[] montantTotalDepenseFixe = {0f};
        final float[] montantTotalDepenseAnnexe = {0f};
        final float[] montantTotalsolde = {0f};

        SqlService sqlService = new SqlService();

        sqlService.getAllRevenuWhereDate(ctx, dateAccount).forEach(i -> montantTotalRevenu[0] += i.getMontant());
        totaux.add(new Total("Total des revenus", montantTotalRevenu[0]));

        sqlService.getAllDFWhereDateAndIsPayed(ctx, dateAccount, isPayed).forEach(i -> montantTotalDepenseFixe[0] += i.getMontant());
        totaux.add(new Total("Total des dépenses fixes", montantTotalDepenseFixe[0]));

        sqlService.getAllDAWhereDateAndIsPayed(ctx, dateAccount, isPayed).forEach(i -> montantTotalDepenseAnnexe[0] += i.getMontant());
        totaux.add(new Total("Total des dépenses annexes", montantTotalDepenseAnnexe[0]));

        totaux.add(new Total("Total des dépenses", montantTotalDepenseFixe[0] + montantTotalDepenseAnnexe[0]));

        sqlService.getAllSoldeWhereDate(ctx, dateAccount).forEach(i -> montantTotalsolde[0] += i.getMontant());
        totaux.add(new Total("Total solde compte(s)", montantTotalsolde[0]));

        totaux.add(new Total("Solde final", montantTotalRevenu[0] + montantTotalsolde[0] - (montantTotalDepenseFixe[0] + montantTotalDepenseAnnexe[0])));

        return totaux;
    }

    /**
     * Affichage du bilan dans l'activité.
     *
     * @param totaux Liste des soldes.
     */
    @Override
    protected void onPostExecute(List<Total> totaux) {
        super.onPostExecute(totaux);
        ListBilanAdapter listBilanAdapter = new ListBilanAdapter(ctx, R.layout.ligne_list_bilan, totaux);
        listView.setAdapter(listBilanAdapter);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totaux.size() * 140;
        listView.setLayoutParams(params);
    }
}
