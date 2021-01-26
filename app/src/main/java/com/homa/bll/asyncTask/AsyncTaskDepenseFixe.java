package com.homa.bll.asyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.homa.MainActivity;
import com.homa.R;
import com.homa.bo.DepenseFixe;
import com.homa.dao.AppDataBase;
import com.homa.dao.Connexion;
import com.homa.ihm.activity.ModifierDepenseFixeActivity;
import com.homa.ihm.adapter.ListDepenseFixeAdapter;
import com.homa.utils.HomaUtils;

import java.util.List;

public class AsyncTaskDepenseFixe extends AsyncTask<Void, DepenseFixe, List<DepenseFixe>> {

    private Context ctx;
    private ListView listView;

    public AsyncTaskDepenseFixe() {}

    public AsyncTaskDepenseFixe(Context ctx, ListView view) {
        this.ctx = ctx;
        this.listView = view;
    }

    @Override
    protected List<DepenseFixe> doInBackground(Void... voids) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        List<DepenseFixe> depenseFixes = bdd.depenseFixeDao().getAll();
        return depenseFixes;
    }

    @Override
    protected void onPostExecute(List<DepenseFixe> depenseFixes) {
        super.onPostExecute(depenseFixes);
        ListDepenseFixeAdapter listDepenseFixeAdapter = new ListDepenseFixeAdapter(ctx, R.layout.ligne_list_depense_fixe, depenseFixes);
        listView.setAdapter(listDepenseFixeAdapter);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int sizeHeight = depenseFixes.size() * 310;
        params.height = sizeHeight;
        listView.setLayoutParams(params);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ctx, ModifierDepenseFixeActivity.class);

                String typeDepense = null;
                for (String item : HomaUtils.MAP_TYPE_DEPENSE.keySet()) {
                    if (HomaUtils.MAP_TYPE_DEPENSE.get(item) == depenseFixes.get(i).getIdTypeDepense()) {
                        typeDepense = item;
                    }
                }
                intent.putExtra("typeDepenseFixe", typeDepense);
                intent.putExtra("libelleDepenseFixe", depenseFixes.get(i).getLibelle());
                intent.putExtra("montantDepenseFixe", String.valueOf(depenseFixes.get(i).getMontant()));
                intent.putExtra("datePrelevement", String.valueOf(depenseFixes.get(i).getDateDePrelevement()));
                intent.putExtra("idDepenseFixe", String.valueOf(depenseFixes.get(i).getId()));
                intent.putExtra("isPayerDepenseFixe", String.valueOf(depenseFixes.get(i).isPayer()));
                ctx.startActivity(intent);
            }
        });
    }
}
