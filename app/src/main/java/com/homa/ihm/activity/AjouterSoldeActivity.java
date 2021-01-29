package com.homa.ihm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.homa.MainActivity;
import com.homa.R;
import com.homa.bo.Solde;
import com.homa.dao.SqlService;
import com.homa.utils.HomaToastUtils;
import com.homa.utils.HomaUtils;

public class AjouterSoldeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_solde);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
     * fonction déclencher au clique du boutton valider et qui ajoute un nouveau solde.
     *
     * @param view {@code View}
     */
    public void validerAjoutSolde(View view) {
        EditText etLibelle = findViewById(R.id.et_libelle_solde);
        String libelle = etLibelle.getText().toString().equals("") ? HomaUtils.EMPTY : etLibelle.getText().toString();

        EditText etMontant = findViewById(R.id.et_montant_solde);
        float montant = etMontant.getText().toString().equals("") ? 0f : Float.parseFloat(etMontant.getText().toString());

        if (!HomaUtils.EMPTY.equals(libelle) && montant != 0f) {
            Log.i(HomaUtils.TAG, HomaUtils.DEBUT + HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_SOLDE);
            Intent intent = getIntent();
            String dateCreation = intent.getStringExtra("dateAccount");

            Solde solde = new Solde();
            solde.setLibelle(libelle);
            solde.setMontant(montant);
            solde.setDateCreation(dateCreation);

            try {
                new Thread(() -> {
                    SqlService sqlService = new SqlService();
                    sqlService.insertSolde(this, solde);
                }).start();
                Toast.makeText(this, HomaToastUtils.SOLDE_AJOUTER, Toast.LENGTH_SHORT).show();
                Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_SOLDE + HomaUtils.RESULTAT + HomaUtils.SUCCESS);
                Intent intention = new Intent(this, MainActivity.class);
                intention.putExtra("dateAccount", solde.getDateCreation());
                startActivity(intention);
            } catch (Exception e) {
                Toast.makeText(this, HomaToastUtils.ECHEC_SOLDE_AJOUTER, Toast.LENGTH_SHORT).show();
                Log.e(HomaUtils.TAG, HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_SOLDE + HomaUtils.ERREUR + e.getMessage() + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            }
        } else {
            Log.e(HomaUtils.TAG, HomaUtils.CHAMPS_VIDE);
            Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_SOLDE + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            Toast.makeText(this, HomaToastUtils.REMPLIR_CHAMPS, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * fonction déclenché au clique du boutton retour.
     *
     * @param view {@code View}
     */
    public void clickRetourAjouterSolde(View view) {
        Intent intent = getIntent();

        Intent intention = new Intent(this, MainActivity.class);
        intention.putExtra("dateAccount", intent.getStringExtra("dateAccount"));
        startActivity(intention);
    }
}