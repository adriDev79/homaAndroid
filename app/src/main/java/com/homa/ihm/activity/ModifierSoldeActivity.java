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
import com.homa.dao.AppDataBase;
import com.homa.dao.Connexion;
import com.homa.utils.HomaToastUtils;
import com.homa.utils.HomaUtils;

import java.util.Date;

public class ModifierSoldeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_solde);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        EditText etLibelle = findViewById(R.id.et_libelle_modif_solde);
        etLibelle.setText(intent.getStringExtra("libelleSolde"));

        EditText etMontant = findViewById(R.id.et_montant_modif_solde);
        etMontant.setText(intent.getStringExtra("montantSolde"));
    }

    public void clickValiderModifSolde(View view) {
        EditText etLibelle = findViewById(R.id.et_libelle_modif_solde);
        String libelle = etLibelle.getText().toString().equals("") ? HomaUtils.EMPTY : etLibelle.getText().toString();

        EditText etMontant = findViewById(R.id.et_montant_modif_solde);
        float montant = etMontant.getText().toString().equals("") ? 0f : Float.parseFloat(etMontant.getText().toString());

        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("idSolde"));

        if (!HomaUtils.EMPTY.equals(libelle) && montant != 0f) {
            Log.i(HomaUtils.TAG, HomaUtils.DEBUT + HomaUtils.ACTION + HomaUtils.ACTION_MODIFIER_SOLDE);

            try {
                new Thread(() -> {
                    AppDataBase bdd = Connexion.getConnexion(ModifierSoldeActivity.this);
                    bdd.soldeDao().update(id, libelle, montant);
                }).start();
                Toast.makeText(this, HomaToastUtils.SOLDE_MODIFIER, Toast.LENGTH_SHORT).show();
                Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_MODIFIER_SOLDE + HomaUtils.RESULTAT + HomaUtils.SUCCESS);
                Intent intention = new Intent(this, MainActivity.class);
                startActivity(intention);
            } catch (Exception e) {
                Toast.makeText(this, HomaToastUtils.ECHEC_SOLDE_MODIFIER, Toast.LENGTH_SHORT).show();
                Log.e(HomaUtils.TAG, HomaUtils.ACTION + HomaUtils.ACTION_MODIFIER_SOLDE + HomaUtils.ERREUR + e.getMessage() + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            }
        } else {
            Log.e(HomaUtils.TAG, HomaUtils.CHAMPS_VIDE);
            Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_MODIFIER_SOLDE+ HomaUtils.RESULTAT + HomaUtils.ECHEC);
            Toast.makeText(this, HomaToastUtils.REMPLIR_CHAMPS, Toast.LENGTH_SHORT).show();
        }
    }

    public void clickSupprimerSolde(View view) {
        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("idSolde"));

        if (!(id == 0f)) {
            Log.i(HomaUtils.TAG, HomaUtils.DEBUT + HomaUtils.ACTION + HomaUtils.ACTION_SUPPRIMER_SOLDE);

            try {
                new Thread(() -> {
                    AppDataBase bdd = Connexion.getConnexion(ModifierSoldeActivity.this);
                    bdd.soldeDao().delete(id);
                }).start();
                Toast.makeText(this, HomaToastUtils.SOLDE_SUPPRIMER, Toast.LENGTH_SHORT).show();
                Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_SUPPRIMER_SOLDE + HomaUtils.RESULTAT + HomaUtils.SUCCESS);
                Intent intention = new Intent(this, MainActivity.class);
                startActivity(intention);
            } catch (Exception e) {
                Toast.makeText(this, HomaToastUtils.ECHEC_SOLDE_SUPPRIMER, Toast.LENGTH_SHORT).show();
                Log.e(HomaUtils.TAG, HomaUtils.ACTION + HomaUtils.ACTION_SUPPRIMER_SOLDE + HomaUtils.ERREUR + e.getMessage() + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            }
        } else {
            Log.e(HomaUtils.TAG, HomaUtils.CHAMPS_VIDE);
            Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_SUPPRIMER_SOLDE + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            Toast.makeText(this, HomaToastUtils.REMPLIR_CHAMPS, Toast.LENGTH_SHORT).show();
        }
    }

    public void clickRetourModifSolde(View view) {
        Intent intention = new Intent(this,MainActivity.class);
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
}