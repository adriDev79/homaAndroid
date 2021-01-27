package com.homa.ihm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.homa.MainActivity;
import com.homa.R;
import com.homa.bo.Revenu;
import com.homa.dao.AppDataBase;
import com.homa.dao.Connexion;
import com.homa.utils.HomaToastUtils;
import com.homa.utils.HomaUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AjouterRevenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_revenu);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void validerAjoutRevenu(View view) {
        EditText etLibelle = findViewById(R.id.et_libelle);
        String libelle = etLibelle.getText().toString().equals("") ? HomaUtils.EMPTY : etLibelle.getText().toString();

        EditText etMontant = findViewById(R.id.et_montant);
        float montant = etMontant.getText().toString().equals("") ? 0f : Float.parseFloat(etMontant.getText().toString());

        TextView etDateReception = findViewById(R.id.tv_reception);
        String dateReception = etDateReception.getText().toString().equals("")? HomaUtils.NON_RENSEIGNE : etDateReception.getText().toString();

        if (!HomaUtils.EMPTY.equals(libelle) && montant != 0f) {
            Log.i(HomaUtils.TAG, HomaUtils.DEBUT + HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_REVENU);

            Intent intent = getIntent();
            String dateCreation = intent.getStringExtra("dateAccount").toString();

            Revenu revenu = new Revenu();
            revenu.setLibelle(libelle);
            revenu.setMontant(montant);
            revenu.setDateDeCreation(dateCreation);
            revenu.setDateDeReception(dateReception);

            try {
                new Thread(() -> {
                    AppDataBase bdd = Connexion.getConnexion(AjouterRevenuActivity.this);
                    bdd.revenuDao().insert(revenu);
                }).start();
                Toast.makeText(this, HomaToastUtils.REVENU_AJOUTER, Toast.LENGTH_SHORT).show();
                Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_REVENU + HomaUtils.RESULTAT + HomaUtils.SUCCESS);
                Intent intention = new Intent(this, MainActivity.class);
                intention.putExtra("dateAccount", revenu.getDateDeCreation());
                startActivity(intention);
            } catch (Exception e) {
                Toast.makeText(this, HomaToastUtils.ECHEC_REVENU_AJOUTER, Toast.LENGTH_SHORT).show();
                Log.e(HomaUtils.TAG, HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_REVENU + HomaUtils.ERREUR + e.getMessage() + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            }
        } else {
            Log.e(HomaUtils.TAG, HomaUtils.CHAMPS_VIDE);
            Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_REVENU + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            Toast.makeText(this, HomaToastUtils.REMPLIR_CHAMPS, Toast.LENGTH_SHORT).show();
        }
    }

    public void clickRetourAjouterRevenu(View view) {
        Intent intent = getIntent();

        Intent intention = new Intent(this, MainActivity.class);
        intention.putExtra("dateAccount", intent.getStringExtra("dateAccount").toString());
        startActivity(intention);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void clickCalendar(View view) {
        TextView textView= findViewById(R.id.tv_reception);
        HomaUtils.calendar(view.getContext(),textView);
    }
}