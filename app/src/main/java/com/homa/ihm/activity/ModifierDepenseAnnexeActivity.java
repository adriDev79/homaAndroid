package com.homa.ihm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.homa.MainActivity;
import com.homa.R;

import com.homa.dao.SqlService;
import com.homa.ihm.adapter.SpinnerAdapter;
import com.homa.utils.HomaToastUtils;
import com.homa.utils.HomaUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

public class ModifierDepenseAnnexeActivity extends AppCompatActivity {

    private final SqlService sqlService = new SqlService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_depense_annexe);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinkedList<String> typeDepense = new LinkedList<>(HomaUtils.MAP_TYPE_DEPENSE.keySet());

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this,R.layout.ligne_spinner, typeDepense);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner_modif_depense_annexe);
        spinner.setAdapter(spinnerAdapter);

        Intent intent = getIntent();
        EditText etLibelle = findViewById(R.id.et_libelle_modif_depense_annexe);
        etLibelle.setText(intent.getStringExtra("libelleDepenseAnnexe"));

        EditText etMontant = findViewById(R.id.et_montant_modif_depense_annexe);
        etMontant.setText(intent.getStringExtra("montantDepenseAnnexe"));

        int idTypeDepense = (Integer) Objects.requireNonNull(HomaUtils.MAP_TYPE_DEPENSE.get(intent.getStringExtra("typeDepenseAnnexe"))) -1;
        spinner.setSelection(idTypeDepense);

        TextView etDatePrelevement = findViewById(R.id.tv_modif_date_prelevement_depense_annexe);
        etDatePrelevement.setText(intent.getStringExtra("datePrelevementDA"));

        TextView etFinPrelevement = findViewById(R.id.tv_modif_fin_date_prelevement_depense_annexe);
        etFinPrelevement.setText(intent.getStringExtra("finPrelevement"));

        CheckBox isPayer = findViewById(R.id.cb_payer_modif_depense_annexe);
        isPayer.setChecked("true".equals(intent.getStringExtra("isPayer")));
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
     * Fonction qui valide la modification de la dépense annexe.
     *
     * @param view {@code View}
     */
    public void clickValiderModifDepenseAnnexe(View view) {
        Spinner spTypeDepense = findViewById(R.id.spinner_modif_depense_annexe);
        String typeDepense = spTypeDepense.getSelectedItem().toString().equals("") ? HomaUtils.EMPTY : spTypeDepense.getSelectedItem().toString();
        int idTypeDepense = 0;

        if (!typeDepense.equals(HomaUtils.EMPTY)) {
            idTypeDepense = Objects.requireNonNull(HomaUtils.MAP_TYPE_DEPENSE.get(typeDepense));
        }
        EditText etLibelle = findViewById(R.id.et_libelle_modif_depense_annexe);
        String libelle = etLibelle.getText().toString().equals("") ? HomaUtils.EMPTY : etLibelle.getText().toString();

        EditText etMontant = findViewById(R.id.et_montant_modif_depense_annexe);
        float montant = etMontant.getText().toString().equals("") ? 0f : Float.parseFloat(etMontant.getText().toString());

        TextView etDatePrelevement = findViewById(R.id.tv_modif_date_prelevement_depense_annexe);
        String datePrelevement = etDatePrelevement.getText().toString().equals("")? HomaUtils.NON_RENSEIGNE : etDatePrelevement.getText().toString();

        TextView etFinPrelevement = findViewById(R.id.tv_modif_fin_date_prelevement_depense_annexe);
        String dateFinPrelevement = etFinPrelevement.getText().toString().equals("")? HomaUtils.AUCUNE : etFinPrelevement.getText().toString();

        CheckBox isPayer = findViewById(R.id.cb_payer_modif_depense_annexe);
        boolean check = isPayer.isChecked();

        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("idDepenseAnnexe"));

        if (!HomaUtils.EMPTY.equals(libelle) && montant != 0f && idTypeDepense != 0) {
            Log.i(HomaUtils.TAG, HomaUtils.DEBUT + HomaUtils.ACTION + HomaUtils.ACTION_MODIFIER_DEPENSE_ANNEXE);
            Date date = new Date();
            String today = date.toString();

            try {
                int finalIdTypeDepense = idTypeDepense;
                new Thread(() -> sqlService.updateDA(this, id, libelle, montant, today, finalIdTypeDepense, datePrelevement, check, dateFinPrelevement)).start();

                Toast.makeText(this, HomaToastUtils.DEPENSE_ANNEXE_MODIFIER, Toast.LENGTH_SHORT).show();
                Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_MODIFIER_DEPENSE_ANNEXE + HomaUtils.RESULTAT + HomaUtils.SUCCESS);

                Intent intention = new Intent(this, MainActivity.class);
                intention.putExtra("dateAccount", intent.getStringExtra("dateAccount"));
                startActivity(intention);
            } catch (Exception e) {
                Toast.makeText(this, HomaToastUtils.ECHEC_DEPENSE_ANNEXE_MODIFIER, Toast.LENGTH_SHORT).show();
                Log.e(HomaUtils.TAG, HomaUtils.ACTION + HomaUtils.ACTION_MODIFIER_DEPENSE_ANNEXE + HomaUtils.ERREUR + e.getMessage() + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            }
        } else {
            Log.e(HomaUtils.TAG, HomaUtils.CHAMPS_VIDE);
            Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_MODIFIER_DEPENSE_ANNEXE+ HomaUtils.RESULTAT + HomaUtils.ECHEC);
            Toast.makeText(this, HomaToastUtils.REMPLIR_CHAMPS, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Fonction qui supprime la dépense annexe.
     *
     * @param view {@code View}
     */
    public void clickSupprimerDepenseAnnexe(View view) {
        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("idDepenseAnnexe"));

        if (!(id == 0f)) {
            Log.i(HomaUtils.TAG, HomaUtils.DEBUT + HomaUtils.ACTION + HomaUtils.ACTION_SUPPRIMER_DEPENSE_ANNEXE);

            try {
                new Thread(() -> sqlService.deleteDA(this, id)).start();

                Toast.makeText(this, HomaToastUtils.DEPENSE_ANNEXE_SUPPRIMER, Toast.LENGTH_SHORT).show();
                Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_SUPPRIMER_DEPENSE_ANNEXE + HomaUtils.RESULTAT + HomaUtils.SUCCESS);

                Intent intention = new Intent(this, MainActivity.class);
                intention.putExtra("dateAccount", intent.getStringExtra("dateAccount"));
                startActivity(intention);
            } catch (Exception e) {
                Toast.makeText(this, HomaToastUtils.ECHEC_DEPENSE_ANNEXE_SUPPRIMER, Toast.LENGTH_SHORT).show();
                Log.e(HomaUtils.TAG, HomaUtils.ACTION + HomaUtils.ACTION_SUPPRIMER_DEPENSE_ANNEXE + HomaUtils.ERREUR + e.getMessage() + HomaUtils.RESULTAT + HomaUtils.ECHEC);
            }
        } else {
            Log.e(HomaUtils.TAG, HomaUtils.CHAMPS_VIDE);
            Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_SUPPRIMER_DEPENSE_ANNEXE+ HomaUtils.RESULTAT + HomaUtils.ECHEC);
            Toast.makeText(this, HomaToastUtils.REMPLIR_CHAMPS, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * fonction déclenché au clique du boutton retour.
     *
     * @param view {@code View}
     */
    public void clickRetourModifDepenseAnnexe(View view) {
        Intent intent = getIntent();

        Intent intention = new Intent(this,MainActivity.class);
        intention.putExtra("dateAccount", intent.getStringExtra("dateAccount"));
        startActivity(intention);
    }


    /**
     * Affichage du calendrier pour la date du prélévement.
     *
     * @param view {@code View}
     */
    public void clickCalendarModifDepenseAnnexe(View view) {
        HomaUtils.calendar(view.getContext(), findViewById(R.id.tv_modif_date_prelevement_depense_annexe));
    }

    /**
     * Affichage du calendrier pour la date de fin du prélévement.
     *
     * @param view {@code View}
     */
    public void clickCalendarModifFinPrelevementDepenseAnnexe(View view) {
        HomaUtils.calendar(view.getContext(), findViewById(R.id.tv_modif_fin_date_prelevement_depense_annexe));
    }
}