package com.homa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.homa.bll.asyncTask.AsyncTaskBilan;
import com.homa.bll.asyncTask.AsyncTaskDepenseAnnexe;
import com.homa.bll.asyncTask.AsyncTaskDepenseFixe;
import com.homa.bll.asyncTask.AsyncTaskRevenu;
import com.homa.bll.asyncTask.AsyncTaskSolde;
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
import com.homa.utils.HomaDisplayUtils;
import com.homa.utils.HomaUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Context ctx = MainActivity.this;
        Date date = new Date();
        TextView tvDateCompte = findViewById(R.id.tv_date_compte);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.FRANCE);
        tvDateCompte.setText(sdf.format(date));


        AsyncTaskRevenu asyncTaskRevenu = new AsyncTaskRevenu(ctx, findViewById(R.id.lv_revenu), date);
        asyncTaskRevenu.execute();

        AsyncTaskDepenseFixe asyncTaskDepenseFixe = new AsyncTaskDepenseFixe(ctx, findViewById(R.id.lv_depense_fixe));
        asyncTaskDepenseFixe.execute();

        AsyncTaskDepenseAnnexe asyncTaskDepenseAnnexe = new AsyncTaskDepenseAnnexe(ctx, findViewById(R.id.lv_depense_annexe));
        asyncTaskDepenseAnnexe.execute();

        AsyncTaskSolde asyncTaskSolde = new AsyncTaskSolde(ctx, findViewById(R.id.lv_solde));
        asyncTaskSolde.execute();

        AsyncTaskBilan asyncTaskBilan = new AsyncTaskBilan(ctx, findViewById(R.id.lv_bilan));
        asyncTaskBilan.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void visibility(View view) {
        Context ctx = MainActivity.this;
        int idView = view.getId();

        switch (idView) {
            case HomaDisplayUtils.ID_VIEW_REVENU :
                AsyncTaskRevenu asyncTaskRevenu = new AsyncTaskRevenu(ctx, findViewById(R.id.lv_revenu));
                HomaUtils.visibilityListViewRevenu(findViewById(R.id.lv_revenu), findViewById(R.id.btn_visibility_revenu), asyncTaskRevenu);
                break;
            case HomaDisplayUtils.ID_VIEW_DEPENSE_FIXE :
                AsyncTaskDepenseFixe asyncTaskDepenseFixe = new AsyncTaskDepenseFixe(ctx, findViewById(R.id.lv_depense_fixe));
                HomaUtils.visibilityListViewDepenseFixe(findViewById(R.id.lv_depense_fixe), findViewById(R.id.btn_visibility_depenseFixe), asyncTaskDepenseFixe);
                break;
            case HomaDisplayUtils.ID_VIEW_DEPENSE_ANNEXE :
                AsyncTaskDepenseAnnexe asyncTaskDepenseAnnexe = new AsyncTaskDepenseAnnexe(ctx, findViewById(R.id.lv_depense_annexe));
                HomaUtils.visibilityListViewDepenseAnnexe(findViewById(R.id.lv_depense_annexe), findViewById(R.id.btn_visibility_depenseAnnexe), asyncTaskDepenseAnnexe);
                break;
            case HomaDisplayUtils.ID_VIEW_SOLDE :
                AsyncTaskSolde asyncTaskSolde = new AsyncTaskSolde(ctx, findViewById(R.id.lv_solde));
                HomaUtils.visibilityListViewSolde(findViewById(R.id.lv_solde), findViewById(R.id.btn_visibility_solde), asyncTaskSolde);
                break;
            default: Exception exception;
        }
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

    public void clickCalendarDateCompte(View view) {
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.FRANCE);
                TextView tvDateCompte = findViewById(R.id.tv_date_compte);
                tvDateCompte.setText(sdf.format(myCalendar.getTime()));

                Date dateAccount = myCalendar.getTime();

                AsyncTaskRevenu asyncTaskRevenu = new AsyncTaskRevenu(MainActivity.this, findViewById(R.id.lv_revenu), dateAccount);
                asyncTaskRevenu.execute();
            }
        };
        new DatePickerDialog(
                this,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}