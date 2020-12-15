package com.homa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.homa.bo.DepenseAnnexe;
import com.homa.bo.DepenseFixe;
import com.homa.bo.Revenu;
import com.homa.bo.Solde;
import com.homa.bo.Total;
import com.homa.dao.AppDataBase;
import com.homa.dao.Connexion;
import com.homa.ihm.activity.AjouterDepenseAnnexeActivity;
import com.homa.ihm.activity.AjouterDepenseFixeActivity;
import com.homa.ihm.activity.AjouterRevenuActivity;
import com.homa.ihm.activity.AjouterSoldeActivity;
import com.homa.ihm.activity.ModifierDepenseAnnexeActivity;
import com.homa.ihm.activity.ModifierDepenseFixeActivity;
import com.homa.ihm.activity.ModifierRevenuActivity;
import com.homa.ihm.activity.ModifierSoldeActivity;
import com.homa.ihm.adapter.ListBilanAdapter;
import com.homa.ihm.adapter.ListDepenseAnnexeAdapter;
import com.homa.ihm.adapter.ListDepenseFixeAdapter;
import com.homa.ihm.adapter.ListRevenuAdapter;
import com.homa.ihm.adapter.ListSoldeAdapter;
import com.homa.utils.HomaUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AsyncTaskListRevenu asyncTaskListRevenu = new AsyncTaskListRevenu();
        asyncTaskListRevenu.execute();

        AsyncTaskListDepenseFixe asyncTaskListDepenseFixe = new AsyncTaskListDepenseFixe();
        asyncTaskListDepenseFixe.execute();

        AsyncTaskListDepenseAnnexe asyncTaskListDepenseAnnexe = new AsyncTaskListDepenseAnnexe();
        asyncTaskListDepenseAnnexe.execute();

        AsyncTaskListSolde asyncTaskListSolde = new AsyncTaskListSolde();
        asyncTaskListSolde.execute();

        AsyncTaskListBilan asyncTaskListBilan = new AsyncTaskListBilan();
        asyncTaskListBilan.execute();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void clickAjouterRevenu(View view) {
        Intent intention = new Intent(this, AjouterRevenuActivity.class);
        startActivity(intention);
    }

    public void clickAjouterDepenseFixe(View view) {
        Intent intention = new Intent(this, AjouterDepenseFixeActivity.class);
        startActivity(intention);
    }

    public void clickAjouterDepenseAnnexe(View view) {
        Intent intention = new Intent(this, AjouterDepenseAnnexeActivity.class);
        startActivity(intention);
    }

    public void clickAjouterSolde(View view) {
        Intent intention = new Intent(this, AjouterSoldeActivity.class);
        startActivity(intention);
    }

    public class AsyncTaskListRevenu extends AsyncTask<Void,Revenu, List<Revenu>> {

        @Override
        protected List<Revenu> doInBackground(Void... voids) {
            AppDataBase bdd = Connexion.getConnexion(MainActivity.this);
            List<Revenu> revenus = bdd.revenuDao().getAll();
            return revenus;
        }

        @Override
        protected void onPostExecute(List<Revenu> revenus) {
            super.onPostExecute(revenus);
            ListRevenuAdapter listRevenuAdapter = new ListRevenuAdapter(MainActivity.this, R.layout.ligne_list_revenu, revenus);
            ListView listRevenu = findViewById(R.id.lv_revenu);
            listRevenu.setAdapter(listRevenuAdapter);
            ViewGroup.LayoutParams params = listRevenu.getLayoutParams();
            int sizeHeight = revenus.size() * 170;
            params.height = sizeHeight;
            listRevenu.setLayoutParams(params);

            listRevenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, ModifierRevenuActivity.class);
                    intent.putExtra("libelle", revenus.get(i).getLibelle());
                    intent.putExtra("dateReception", revenus.get(i).getDateDeReception());
                    intent.putExtra("montant", String.valueOf(revenus.get(i).getMontant()));
                    intent.putExtra("id", String.valueOf(revenus.get(i).getId()));
                    startActivity(intent);
                }
            });
        }
    }

    public class AsyncTaskListDepenseFixe extends AsyncTask<Void, DepenseFixe, List<DepenseFixe>> {

        @Override
        protected List<DepenseFixe> doInBackground(Void... voids) {
            AppDataBase bdd = Connexion.getConnexion(MainActivity.this);
            List<DepenseFixe> depenseFixes = bdd.depenseFixeDao().getAll();
            return depenseFixes;
        }

        @Override
        protected void onPostExecute(List<DepenseFixe> depenseFixes) {
            super.onPostExecute(depenseFixes);
            ListDepenseFixeAdapter listDepenseFixeAdapter = new ListDepenseFixeAdapter(MainActivity.this, R.layout.ligne_list_depense_fixe, depenseFixes);
            ListView lvDepenseFixe = findViewById(R.id.lv_depense_fixe);
            lvDepenseFixe.setAdapter(listDepenseFixeAdapter);
            ViewGroup.LayoutParams params = lvDepenseFixe.getLayoutParams();
            int sizeHeight = depenseFixes.size() * 230;
            params.height = sizeHeight;
            lvDepenseFixe.setLayoutParams(params);

            lvDepenseFixe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, ModifierDepenseFixeActivity.class);

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
                    startActivity(intent);
                }
            });
        }
    }
    public class AsyncTaskListDepenseAnnexe extends AsyncTask<Void, DepenseAnnexe, List<DepenseAnnexe>> {

        @Override
        protected List<DepenseAnnexe> doInBackground(Void... voids) {
            AppDataBase bdd = Connexion.getConnexion(MainActivity.this);
            return bdd.depenseAnnexeDao().getAll();
        }

        @Override
        protected void onPostExecute(List<DepenseAnnexe> depenseAnnexes) {
            super.onPostExecute(depenseAnnexes);
            ListDepenseAnnexeAdapter listDepenseAnnexeAdapter = new ListDepenseAnnexeAdapter(MainActivity.this, R.layout.ligne_list_depense_annexe, depenseAnnexes);
            ListView lvDepenseAnnexe = findViewById(R.id.lv_depense_annexe);
            lvDepenseAnnexe.setAdapter(listDepenseAnnexeAdapter);
            ViewGroup.LayoutParams params = lvDepenseAnnexe.getLayoutParams();
            params.height = depenseAnnexes.size() * 230;
            lvDepenseAnnexe.setLayoutParams(params);

            lvDepenseAnnexe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, ModifierDepenseAnnexeActivity.class);

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
                    startActivity(intent);
                }
            });
        }
    }

    public class AsyncTaskListSolde extends AsyncTask<Void, Solde, List<Solde>> {

        @Override
        protected List<Solde> doInBackground(Void... voids) {
            AppDataBase bdd = Connexion.getConnexion(MainActivity.this);
            List<Solde> soldes = bdd.soldeDao().getAll();
            return soldes;
        }

        @Override
        protected void onPostExecute(List<Solde> soldes) {
            super.onPostExecute(soldes);
            ListSoldeAdapter listSoldeAdapter = new ListSoldeAdapter(MainActivity.this, R.layout.ligne_list_solde, soldes);
            ListView lvSolde = findViewById(R.id.lv_solde);
            lvSolde.setAdapter(listSoldeAdapter);
            ViewGroup.LayoutParams params = lvSolde.getLayoutParams();
            int sizeHeight = soldes.size() * 120;
            params.height = sizeHeight;
            lvSolde.setLayoutParams(params);

            lvSolde.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, ModifierSoldeActivity.class);
                    intent.putExtra("libelleSolde", soldes.get(i).getLibelle());
                    intent.putExtra("montantSolde", String.valueOf(soldes.get(i).getMontant()));
                    intent.putExtra("idSolde", String.valueOf(soldes.get(i).getId()));
                    startActivity(intent);
                }
            });
        }
    }

    public class AsyncTaskListBilan extends AsyncTask<Void, Total, List<Total>> {

        @Override
        protected List<Total> doInBackground(Void... voids) {
            List<Total> totaux = new ArrayList<>();
            final float[] montantTotalRevenu = {0f};
            final float[] montantTotalDepenseFixe = {0f};
            final float[] montantTotalDepenseAnnexe = {0f};
            final float[] montantTotalsolde = {0f};

            AppDataBase bdd = Connexion.getConnexion(MainActivity.this);

            bdd.revenuDao().getAll().forEach(i -> montantTotalRevenu[0] += i.getMontant());
            totaux.add(new Total("Total des revenus", montantTotalRevenu[0]));

            bdd.depenseFixeDao().getAll().forEach(i -> montantTotalDepenseFixe[0] += i.getMontant());
            totaux.add(new Total("Total des dépenses fixes", montantTotalDepenseFixe[0]));

            bdd.depenseAnnexeDao().getAll().forEach(i -> montantTotalDepenseAnnexe[0] += i.getMontant());
            totaux.add(new Total("Total des dépenses annexes", montantTotalDepenseAnnexe[0]));

            totaux.add(new Total("total des dépenses", montantTotalDepenseFixe[0] + montantTotalDepenseAnnexe[0]));

            bdd.soldeDao().getAll().forEach(i -> montantTotalsolde[0] += i.getMontant());
            totaux.add(new Total("Total solde compte(s)", montantTotalsolde[0]));

            totaux.add(new Total("solde final", montantTotalRevenu[0] + montantTotalsolde[0] - (montantTotalDepenseFixe[0] + montantTotalDepenseAnnexe[0])));
            Log.i(HomaUtils.TAG, "resultat : " + montantTotalRevenu[0] + montantTotalDepenseFixe[0]+ montantTotalDepenseAnnexe[0]+ montantTotalsolde[0]);

            return totaux;
        }

        @Override
        protected void onPostExecute(List<Total> totaux) {
            super.onPostExecute(totaux);
            ListBilanAdapter listBilanAdapter = new ListBilanAdapter(MainActivity.this, R.layout.ligne_list_bilan, totaux);
            ListView lvBilan = findViewById(R.id.lv_bilan);
            lvBilan.setAdapter(listBilanAdapter);
            ViewGroup.LayoutParams params = lvBilan.getLayoutParams();
            int sizeHeight = totaux.size() * 120;
            params.height = sizeHeight;
            lvBilan.setLayoutParams(params);
        }
    }
}