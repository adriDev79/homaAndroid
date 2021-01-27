package com.homa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.homa.bll.asyncTask.AsyncTaskBilan;
import com.homa.bll.asyncTask.AsyncTaskDepenseAnnexe;
import com.homa.bll.asyncTask.AsyncTaskDepenseFixe;
import com.homa.bll.asyncTask.AsyncTaskRevenu;
import com.homa.bll.asyncTask.AsyncTaskSolde;

import com.homa.ihm.activity.AjouterDepenseAnnexeActivity;
import com.homa.ihm.activity.AjouterDepenseFixeActivity;
import com.homa.ihm.activity.AjouterRevenuActivity;
import com.homa.ihm.activity.AjouterSoldeActivity;

import com.homa.utils.HomaDisplayUtils;
import com.homa.utils.HomaUtils;

import java.util.Calendar;
import java.util.Date;

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
        String dateAccount = null;

        Intent intent = getIntent();
        if (intent.getStringExtra("dateAccount") == null) {
            dateAccount = HomaUtils.dateFormat("MMMM yyyy", date);
        } else {
            dateAccount = intent.getStringExtra("dateAccount");
        }

        TextView tvDateCompte = findViewById(R.id.tv_date_compte);
        tvDateCompte.setText(dateAccount);

        AsyncTaskRevenu asyncTaskRevenu = new AsyncTaskRevenu(ctx, findViewById(R.id.lv_revenu), dateAccount);
        asyncTaskRevenu.execute();

        AsyncTaskDepenseFixe asyncTaskDepenseFixe = new AsyncTaskDepenseFixe(ctx, findViewById(R.id.lv_depense_fixe), dateAccount);
        asyncTaskDepenseFixe.execute();

        AsyncTaskDepenseAnnexe asyncTaskDepenseAnnexe = new AsyncTaskDepenseAnnexe(ctx, findViewById(R.id.lv_depense_annexe), dateAccount);
        asyncTaskDepenseAnnexe.execute();

        AsyncTaskSolde asyncTaskSolde = new AsyncTaskSolde(ctx, findViewById(R.id.lv_solde), dateAccount);
        asyncTaskSolde.execute();

        AsyncTaskBilan asyncTaskBilan = new AsyncTaskBilan(ctx, findViewById(R.id.lv_bilan), dateAccount);
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

        TextView tvDateCompte = findViewById(R.id.tv_date_compte);
        String dateCompte = tvDateCompte.getText().toString();

        switch (idView) {
            case HomaDisplayUtils.ID_VIEW_REVENU :
                AsyncTaskRevenu asyncTaskRevenu = new AsyncTaskRevenu(ctx, findViewById(R.id.lv_revenu), dateCompte);
                HomaUtils.visibilityListViewRevenu(findViewById(R.id.lv_revenu), findViewById(R.id.btn_visibility_revenu), asyncTaskRevenu);
                break;
            case HomaDisplayUtils.ID_VIEW_DEPENSE_FIXE :
                AsyncTaskDepenseFixe asyncTaskDepenseFixe = new AsyncTaskDepenseFixe(ctx, findViewById(R.id.lv_depense_fixe), dateCompte);
                HomaUtils.visibilityListViewDepenseFixe(findViewById(R.id.lv_depense_fixe), findViewById(R.id.btn_visibility_depenseFixe), asyncTaskDepenseFixe);
                break;
            case HomaDisplayUtils.ID_VIEW_DEPENSE_ANNEXE :
                AsyncTaskDepenseAnnexe asyncTaskDepenseAnnexe = new AsyncTaskDepenseAnnexe(ctx, findViewById(R.id.lv_depense_annexe), dateCompte);
                HomaUtils.visibilityListViewDepenseAnnexe(findViewById(R.id.lv_depense_annexe), findViewById(R.id.btn_visibility_depenseAnnexe), asyncTaskDepenseAnnexe);
                break;
            case HomaDisplayUtils.ID_VIEW_SOLDE :
                AsyncTaskSolde asyncTaskSolde = new AsyncTaskSolde(ctx, findViewById(R.id.lv_solde), dateCompte);
                HomaUtils.visibilityListViewSolde(findViewById(R.id.lv_solde), findViewById(R.id.btn_visibility_solde), asyncTaskSolde);
                break;
            default: Exception exception;
        }
    }

    public void clickAjouterRevenu(View view) {
        Intent intention = new Intent(this, AjouterRevenuActivity.class);
        intentDate(intention);
        startActivity(intention);
    }

    public void clickAjouterDepenseFixe(View view) {
        Intent intention = new Intent(this, AjouterDepenseFixeActivity.class);
        intentDate(intention);
        startActivity(intention);
    }

    public void clickAjouterDepenseAnnexe(View view) {
        Intent intention = new Intent(this, AjouterDepenseAnnexeActivity.class);
        intentDate(intention);
        startActivity(intention);
    }

    public void clickAjouterSolde(View view) {
        Intent intention = new Intent(this, AjouterSoldeActivity.class);
        intentDate(intention);
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

                TextView tvDateCompte = findViewById(R.id.tv_date_compte);
                tvDateCompte.setText(HomaUtils.dateFormat("MMMM yyyy", myCalendar.getTime()));

                String dateAccount = HomaUtils.dateFormat("MMMM yyyy", myCalendar.getTime());

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("dateChoisis", dateAccount);

                AsyncTaskRevenu asyncTaskRevenu = new AsyncTaskRevenu(MainActivity.this, findViewById(R.id.lv_revenu), dateAccount);
                asyncTaskRevenu.execute();

                AsyncTaskDepenseFixe asyncTaskDepenseFixe = new AsyncTaskDepenseFixe(MainActivity.this, findViewById(R.id.lv_depense_fixe), dateAccount);
                asyncTaskDepenseFixe.execute();

                AsyncTaskDepenseAnnexe asyncTaskDepenseAnnexe = new AsyncTaskDepenseAnnexe(MainActivity.this, findViewById(R.id.lv_depense_annexe), dateAccount);
                asyncTaskDepenseAnnexe.execute();

                AsyncTaskSolde asyncTaskSolde = new AsyncTaskSolde(MainActivity.this, findViewById(R.id.lv_solde), dateAccount);
                asyncTaskSolde.execute();

                AsyncTaskBilan asyncTaskBilan = new AsyncTaskBilan(MainActivity.this, findViewById(R.id.lv_bilan), dateAccount);
                asyncTaskBilan.execute();
            }
        };
        new DatePickerDialog(
                this,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void intentDate(Intent intent) {
        TextView textView = findViewById(R.id.tv_date_compte);
        String date = textView.getText().toString();
        intent.putExtra("dateAccount", date);
    }
}