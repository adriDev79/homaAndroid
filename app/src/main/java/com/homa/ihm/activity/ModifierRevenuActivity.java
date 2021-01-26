package com.homa.ihm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.Date;

public class ModifierRevenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_revenu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();

        EditText etLibelle = findViewById(R.id.et_libelle_modif_revenu);
        etLibelle.setText(intent.getStringExtra("libelle"));

        EditText etMontant = findViewById(R.id.et_montant_modif_revenu);
        etMontant.setText(intent.getStringExtra("montant"));

        TextView etDateReception = findViewById(R.id.tv_modif_reception);
        etDateReception.setText(intent.getStringExtra("dateReception"));
    }

    public void clickValiderModifRevenu(View view) {
        EditText etLibelle = findViewById(R.id.et_libelle_modif_revenu);
        String libelle = etLibelle.getText().toString().equals("") ? HomaUtils.EMPTY : etLibelle.getText().toString();

        EditText etMontant = findViewById(R.id.et_montant_modif_revenu);
        float montant = etMontant.getText().toString().equals("") ? 0f : Float.parseFloat(etMontant.getText().toString());

        TextView etDateReception = findViewById(R.id.tv_modif_reception);
        String dateReception = etDateReception.getText().toString().equals("")? HomaUtils.NON_RENSEIGNE : etDateReception.getText().toString();

        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("id"));

        if (!HomaUtils.EMPTY.equals(libelle) && montant != 0f) {
            Log.i(HomaUtils.TAG, HomaUtils.DEBUT + HomaUtils.ACTION + HomaUtils.ACTION_MODIFIER_REVENU);
            Date date = new Date();
            String today = date.toString();

            try {
                new Thread(() -> {
                    AppDataBase bdd = Connexion.getConnexion(ModifierRevenuActivity.this);
                    bdd.revenuDao().update(id, libelle, montant, today, dateReception);
                }).start();
                Toast.makeText(this, HomaToastUtils.REVENU_MODIFIER, Toast.LENGTH_SHORT).show();
                Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_MODIFIER_REVENU + HomaUtils.RESULTAT + HomaUtils.SUCCESS);
                Intent intention = new Intent(this, MainActivity.class);
                startActivity(intention);
            } catch (Exception e) {
                Toast.makeText(this, HomaToastUtils.ECHEC_REVENU_MODIFIER, Toast.LENGTH_SHORT).show();
                Log.e(HomaUtils.TAG, HomaUtils.ACTION + HomaUtils.ACTION_MODIFIER_REVENU + HomaUtils.ERREUR + e.getMessage() + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            }
        } else {
            Log.e(HomaUtils.TAG, HomaUtils.CHAMPS_VIDE);
            Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_MODIFIER_REVENU+ HomaUtils.RESULTAT + HomaUtils.ECHEC);
            Toast.makeText(this, HomaToastUtils.REMPLIR_CHAMPS, Toast.LENGTH_SHORT).show();
        }
    }

    public void clickSupprimerRevenu(View view) {
        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("id"));

        if (!(id == 0f)) {
            Log.i(HomaUtils.TAG, HomaUtils.DEBUT + HomaUtils.ACTION + HomaUtils.ACTION_SUPPRIMER_REVENU);

            try {
                new Thread(() -> {
                    AppDataBase bdd = Connexion.getConnexion(ModifierRevenuActivity.this);
                    bdd.revenuDao().delete(id);
                }).start();
                Toast.makeText(this, HomaToastUtils.REVENU_SUPPRIMER, Toast.LENGTH_SHORT).show();
                Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_SUPPRIMER_REVENU + HomaUtils.RESULTAT + HomaUtils.SUCCESS);
                Intent intention = new Intent(this, MainActivity.class);
                startActivity(intention);
            } catch (Exception e) {
                Toast.makeText(this, HomaToastUtils.ECHEC_REVENU_SUPPRIMER, Toast.LENGTH_SHORT).show();
                Log.e(HomaUtils.TAG, HomaUtils.ACTION + HomaUtils.ACTION_SUPPRIMER_REVENU + HomaUtils.ERREUR + e.getMessage() + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            }
        } else {
            Log.e(HomaUtils.TAG, HomaUtils.CHAMPS_VIDE);
            Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_SUPPRIMER_REVENU+ HomaUtils.RESULTAT + HomaUtils.ECHEC);
            Toast.makeText(this, HomaToastUtils.REMPLIR_CHAMPS, Toast.LENGTH_SHORT).show();
        }
    }

    public void clickRetourModifRevenu(View view) {
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

    public void clickCalendar(View view) {
        TextView textView = findViewById(R.id.tv_modif_reception);
        HomaUtils.calendar(view.getContext(), textView);
    }
}