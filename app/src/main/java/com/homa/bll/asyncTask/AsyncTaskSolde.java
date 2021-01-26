package com.homa.bll.asyncTask;

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

import java.util.Date;
import java.util.List;

public class AsyncTaskSolde extends AsyncTask<Void, Solde, List<Solde>>  {

    private Context ctx;
    private ListView listView;
    private Date date;

    public AsyncTaskSolde() {
    }

    public AsyncTaskSolde(Context ctx, ListView listView) {
        this.ctx = ctx;
        this.listView = listView;
    }

    public AsyncTaskSolde(Context ctx, ListView listView, Date date) {
        this.ctx = ctx;
        this.listView = listView;
        this.date = date;
    }

    @Override
    protected List<Solde> doInBackground(Void... voids) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        List<Solde> soldes = bdd.soldeDao().getAll();
        return soldes;
    }

    @Override
    protected void onPostExecute(List<Solde> soldes) {
        super.onPostExecute(soldes);
        ListSoldeAdapter listSoldeAdapter = new ListSoldeAdapter(ctx, R.layout.ligne_list_solde, soldes);
        listView.setAdapter(listSoldeAdapter);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int sizeHeight = soldes.size() * 250;
        params.height = sizeHeight;
        listView.setLayoutParams(params);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ctx, ModifierSoldeActivity.class);
                intent.putExtra("libelleSolde", soldes.get(i).getLibelle());
                intent.putExtra("montantSolde", String.valueOf(soldes.get(i).getMontant()));
                intent.putExtra("idSolde", String.valueOf(soldes.get(i).getId()));
                ctx.startActivity(intent);
            }
        });
    }
}
