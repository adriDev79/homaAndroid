package com.homa.bll.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ListView;

import com.homa.MainActivity;
import com.homa.R;
import com.homa.bo.Revenu;
import com.homa.bo.Total;
import com.homa.dao.AppDataBase;
import com.homa.dao.Connexion;
import com.homa.ihm.adapter.ListBilanAdapter;
import com.homa.utils.HomaUtils;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskBilan extends AsyncTask<Void, Total, List<Total>>  {

    private Context ctx;
    private ListView listView;

    public AsyncTaskBilan() {
    }

    public AsyncTaskBilan(Context ctx, ListView listView) {
        this.ctx = ctx;
        this.listView = listView;
    }

    @Override
    protected List<Total> doInBackground(Void... voids) {
        boolean isPayed = true;

        List<Total> totaux = new ArrayList<>();
        final float[] montantTotalRevenu = {0f};
        final float[] montantTotalDepenseFixe = {0f};
        final float[] montantTotalDepenseAnnexe = {0f};
        final float[] montantTotalsolde = {0f};

        AppDataBase bdd = Connexion.getConnexion(ctx);

        bdd.revenuDao().getAll().forEach(i -> montantTotalRevenu[0] += i.getMontant());
        totaux.add(new Total("Total des revenus", montantTotalRevenu[0]));

        bdd.depenseFixeDao().getAllIsPayed(isPayed).forEach(i -> montantTotalDepenseFixe[0] += i.getMontant());
        totaux.add(new Total("Total des dépenses fixes", montantTotalDepenseFixe[0]));

        bdd.depenseAnnexeDao().getAllIsPayed(isPayed).forEach(i -> montantTotalDepenseAnnexe[0] += i.getMontant());
        totaux.add(new Total("Total des dépenses annexes", montantTotalDepenseAnnexe[0]));

        totaux.add(new Total("total des dépenses", montantTotalDepenseFixe[0] + montantTotalDepenseAnnexe[0]));

        bdd.soldeDao().getAll().forEach(i -> montantTotalsolde[0] += i.getMontant());
        totaux.add(new Total("Total solde compte(s)", montantTotalsolde[0]));

        totaux.add(new Total("solde final", montantTotalRevenu[0] + montantTotalsolde[0] - (montantTotalDepenseFixe[0] + montantTotalDepenseAnnexe[0])));

        return totaux;
    }

    @Override
    protected void onPostExecute(List<Total> totaux) {
        super.onPostExecute(totaux);
        ListBilanAdapter listBilanAdapter = new ListBilanAdapter(ctx, R.layout.ligne_list_bilan, totaux);
        listView.setAdapter(listBilanAdapter);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int sizeHeight = totaux.size() * 140;
        params.height = sizeHeight;
        listView.setLayoutParams(params);
    }
}
