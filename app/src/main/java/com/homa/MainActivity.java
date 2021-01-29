package com.homa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.homa.bll.asyncTask.AsyncTaskBilan;
import com.homa.bll.asyncTask.AsyncTaskDepenseAnnexe;
import com.homa.bll.asyncTask.AsyncTaskDepenseFixe;
import com.homa.bll.asyncTask.AsyncTaskRecupDepenseFixe;
import com.homa.bll.asyncTask.AsyncTaskRecupRevenu;
import com.homa.bll.asyncTask.AsyncTaskRevenu;
import com.homa.bll.asyncTask.AsyncTaskSolde;

import com.homa.bll.utils.AsynktaskUtils;
import com.homa.ihm.activity.AjouterDepenseAnnexeActivity;
import com.homa.ihm.activity.AjouterDepenseFixeActivity;
import com.homa.ihm.activity.AjouterRevenuActivity;
import com.homa.ihm.activity.AjouterSoldeActivity;

import com.homa.utils.HomaUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private LinkedList<ListView> listViews;
    private LinkedList<LinearLayout> linearLayouts;
    private Context ctx;
    private TextView tvDateCompte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViews = new LinkedList<>();
        listViews.add(findViewById(R.id.lv_revenu));
        listViews.add(findViewById(R.id.lv_depense_fixe));
        listViews.add(findViewById(R.id.lv_depense_annexe));
        listViews.add(findViewById(R.id.lv_solde));
        listViews.add(findViewById(R.id.lv_bilan));

        linearLayouts = new LinkedList<>();
        linearLayouts.add(findViewById(R.id.ll_recuperer_revenu));
        linearLayouts.add(findViewById(R.id.ll_recuperer_depense_fixe));

        ctx = MainActivity.this;
        tvDateCompte = findViewById(R.id.tv_date_compte);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Date date = new Date();

        Intent intent = getIntent();
        String intentDate = intent.getStringExtra("dateAccount");
        String dateAccount = intentDate == null ?
                HomaUtils.dateFormat("MMMM yyyy", date) : intentDate;

        TextView tvDateCompte = findViewById(R.id.tv_date_compte);
        tvDateCompte.setText(dateAccount);

        AsynktaskUtils.asyncTasksExecute(ctx, dateAccount, listViews, linearLayouts);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Fonction qui permet de masquer les listes.
     *
     * @param view {@code}
     */
    public void visibility(View view) {
        String dateCompte = tvDateCompte.getText().toString();

        if (view == findViewById(R.id.btn_visibility_revenu)) {
            AsyncTaskRevenu asyncTaskRevenu = new AsyncTaskRevenu(ctx, listViews.get(0), dateCompte);
            HomaUtils.visibilityListViewRevenu(listViews.get(0), findViewById(R.id.btn_visibility_revenu), asyncTaskRevenu);
        }
        if (view == findViewById(R.id.btn_visibility_depenseFixe)) {
            AsyncTaskDepenseFixe asyncTaskDepenseFixe = new AsyncTaskDepenseFixe(ctx, listViews.get(1), dateCompte);
            HomaUtils.visibilityListViewDepenseFixe(listViews.get(1), findViewById(R.id.btn_visibility_depenseFixe), asyncTaskDepenseFixe);
        }
        if (view == findViewById(R.id.btn_visibility_depenseAnnexe)) {
            AsyncTaskDepenseAnnexe asyncTaskDepenseAnnexe = new AsyncTaskDepenseAnnexe(ctx, listViews.get(2), dateCompte);
            HomaUtils.visibilityListViewDepenseAnnexe(listViews.get(2), findViewById(R.id.btn_visibility_depenseAnnexe), asyncTaskDepenseAnnexe);
        }
        if (view == findViewById(R.id.btn_visibility_solde)) {
            AsyncTaskSolde asyncTaskSolde = new AsyncTaskSolde(ctx, listViews.get(3), dateCompte);
            HomaUtils.visibilityListViewSolde(listViews.get(3), findViewById(R.id.btn_visibility_solde), asyncTaskSolde);
        }
    }

    /**
     * Fonction qui permet d'acceder à l'activité d'ajout des listes.
     *
     * @param view {@code View}
     */
    public void addActivity(View view) {
        Intent intention;
        if (view == findViewById(R.id.ibtn_ajouter_revenu)) {
            intention = new Intent(ctx, AjouterRevenuActivity.class);
            intentPutDate(intention);
            startActivity(intention);
        }
        if (view == findViewById(R.id.ibtn_ajouter_DF)) {
            intention = new Intent(ctx, AjouterDepenseFixeActivity.class);
            intentPutDate(intention);
            startActivity(intention);
        }
        if (view == findViewById(R.id.ibtn_ajouter_DA)) {
            intention = new Intent(ctx, AjouterDepenseAnnexeActivity.class);
            intentPutDate(intention);
            startActivity(intention);
        }
        if (view == findViewById(R.id.ibtn_ajouter_solde)) {
            intention = new Intent(ctx, AjouterSoldeActivity.class);
            intentPutDate(intention);
            startActivity(intention);
        }
    }

    /**
     * Fonction qui affiche le calendrier, permet de choisr la date des comptes voulus.
     * Rechargement des listes en fonction de la date souhaité.
     *
     * @param view {@code View}
     */
    public void clickCalendarDateCompte(View view) {
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TextView tvDateCompte = findViewById(R.id.tv_date_compte);
            tvDateCompte.setText(HomaUtils.dateFormat("MMMM yyyy", myCalendar.getTime()));

            String dateAccount = HomaUtils.dateFormat("MMMM yyyy", myCalendar.getTime());

            Intent intent = new Intent(ctx, MainActivity.class);
            intent.putExtra("dateChoisis", dateAccount);

            AsynktaskUtils.asyncTasksExecute(ctx, dateAccount, listViews, linearLayouts);
        };
        new DatePickerDialog(
                ctx,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Fonction qui permet d'ajouter une une date dans un intent.
     *
     * @param intent {@code Intent}
     */
    public void intentPutDate(Intent intent) {
        String dateCompte = tvDateCompte.getText().toString();
        intent.putExtra("dateAccount", dateCompte);
    }

    /**
     * Fonction qui permet de récupèrer les revenus ou les dépenses du mois précédent.
     *
     * @param view {@code View}
     */
    public  void recoverRevenuOrDF(View view) {
        //Recupération de la date des comptes en cours
        String dateCompte = tvDateCompte.getText().toString();
        String[] date = dateCompte.split(" ");
        String month = date[0];
        int year = Integer.parseInt(date[1]);
        int positionMonth = 0;

        if (month.equals("janvier")) {
            year = year -1;
            positionMonth = 11;
        } else {
            for (int i = 0; i < HomaUtils.MAP_MONTHS.size(); i++) {
                if (month.equals(HomaUtils.MAP_MONTHS.get(i))) {
                    positionMonth = i - 1;
                }
            }
        }
        String datePreviousMonth = HomaUtils.MAP_MONTHS.get(positionMonth) + " " + year;

        if (view == findViewById(R.id.ibtn_valider)) {
            AsyncTaskRecupRevenu asyncTaskRecupRevenu = new AsyncTaskRecupRevenu(ctx, dateCompte, datePreviousMonth);
            asyncTaskRecupRevenu.execute();

            AsyncTaskRevenu asyncTaskRevenu = new AsyncTaskRevenu(ctx, listViews.get(0), dateCompte, linearLayouts.get(0));
            asyncTaskRevenu.execute();

            AsyncTaskBilan asyncTaskBilan = new AsyncTaskBilan(ctx, listViews.get(4), dateCompte);
            asyncTaskBilan.execute();

            Toast.makeText(ctx, "Revenu(s) récupèré(s) !", Toast.LENGTH_SHORT).show();
        }
        if (view == findViewById(R.id.ibtn_valider_DF)) {
            AsyncTaskRecupDepenseFixe asyncTaskRecupDepenseFixe = new AsyncTaskRecupDepenseFixe(ctx, dateCompte, datePreviousMonth);
            asyncTaskRecupDepenseFixe.execute();

            AsyncTaskDepenseFixe asyncTaskDepenseFixe = new AsyncTaskDepenseFixe(ctx, listViews.get(1), dateCompte, findViewById(R.id.ll_recuperer_depense_fixe));
            asyncTaskDepenseFixe.execute();

            AsyncTaskBilan asyncTaskBilan = new AsyncTaskBilan(ctx, listViews.get(4), dateCompte);
            asyncTaskBilan.execute();

            Toast.makeText(ctx, "Dépense(s) fixe(s) récupèré(s) !", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Fonction qui supprime le message pour récupèrer les revenus et les dépenses du mois précédent.
     *
     * @param view {@code View}
     */
    public void refuseRecover(View view) {
        if (view == findViewById(R.id.ibtn_refuser)) {
            linearLayouts.get(0).setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = linearLayouts.get(0).getLayoutParams();
            params.height = 0;
            linearLayouts.get(0).setLayoutParams(params);
        }
        if (view == findViewById(R.id.ibtn_refuser_DF)) {
            linearLayouts.get(1).setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = linearLayouts.get(1).getLayoutParams();
            params.height = 0;
            linearLayouts.get(1).setLayoutParams(params);
        }
    }
}