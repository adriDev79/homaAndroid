package com.homa.ihm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.homa.MainActivity;
import com.homa.R;
import com.homa.bo.DepenseAnnexe;
import com.homa.bo.DepenseFixe;
import com.homa.dao.AppDataBase;
import com.homa.dao.Connexion;
import com.homa.ihm.adapter.SpinnerAdapter;
import com.homa.utils.HomaToastUtils;
import com.homa.utils.HomaUtils;

import java.util.Date;
import java.util.LinkedList;

public class AjouterDepenseAnnexeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_depense_annexe);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinkedList<String> typeDepense = new LinkedList<>();
        typeDepense.addAll(HomaUtils.MAP_TYPE_DEPENSE.keySet());

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this,R.layout.ligne_spinner, typeDepense);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner_depense_annexe);
        spinner.setAdapter(spinnerAdapter);
    }

    public void ajouterDepenseAnnexe(View view) {
        Spinner spTypeDepense = findViewById(R.id.spinner_depense_annexe);
        String typeDepense = spTypeDepense.getSelectedItem().toString().equals("") ? HomaUtils.EMPTY : spTypeDepense.getSelectedItem().toString();
        int idTypeDepense = 0;

        if (!typeDepense.equals(HomaUtils.EMPTY)) {
            idTypeDepense = HomaUtils.MAP_TYPE_DEPENSE.get(typeDepense);
        }

        EditText etLibelle = findViewById(R.id.et_libelle_depense_annexe);
        String libelle = etLibelle.getText().toString().equals("") ? HomaUtils.EMPTY : etLibelle.getText().toString();

        EditText etMontant = findViewById(R.id.et_montant_depense_annexe);
        float montant = etMontant.getText().toString().equals("") ? 0f : Float.parseFloat(etMontant.getText().toString());

        EditText etDatePrelevement = findViewById(R.id.et_reception_depense_annexe);
        String datePrelevement = etDatePrelevement.getText().toString().equals("")? HomaUtils.NON_RENSEIGNE : etDatePrelevement.getText().toString();

        if (!(libelle.equals(HomaUtils.EMPTY ) && montant == 0f && idTypeDepense == 0)) {
            Log.i(HomaUtils.TAG, HomaUtils.DEBUT + HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_DEPENSE_ANNEXE);
            Date date = new Date();
            String today = date.toString();

            DepenseAnnexe depenseAnnexe = new DepenseAnnexe();
            depenseAnnexe.setLibelle(libelle);
            depenseAnnexe.setMontant(montant);
            depenseAnnexe.setDateDeCreation(today);
            depenseAnnexe.setDateDePrelevement(datePrelevement);
            depenseAnnexe.setIdTypeDepense(idTypeDepense);

            try {
                new Thread(() -> {
                    AppDataBase bdd = Connexion.getConnexion(AjouterDepenseAnnexeActivity.this);
                    bdd.depenseAnnexeDao().insert(depenseAnnexe);
                }).start();
                Toast.makeText(this, HomaToastUtils.DEPENSE_ANNEXE_AJOUTER, Toast.LENGTH_SHORT).show();
                Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_DEPENSE_ANNEXE + HomaUtils.RESULTAT + HomaUtils.SUCCESS);
                Intent intention = new Intent(this, MainActivity.class);
                startActivity(intention);
            } catch (Exception e) {
                Toast.makeText(this, HomaToastUtils.ECHEC_DEPENSE_ANNEXE_AJOUTER, Toast.LENGTH_SHORT).show();
                Log.e(HomaUtils.TAG, HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_DEPENSE_ANNEXE + HomaUtils.ERREUR + e.getMessage() + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            }
        } else {
            Log.e(HomaUtils.TAG, HomaUtils.CHAMPS_VIDE);
            Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_DEPENSE_ANNEXE + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            Toast.makeText(this, HomaToastUtils.REMPLIR_CHAMPS, Toast.LENGTH_SHORT).show();
        }
    }

    public void clickRetourAjouterDepenseAnnexe(View view) {
        Intent intention = new Intent(this, MainActivity.class);
        startActivity(intention);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}