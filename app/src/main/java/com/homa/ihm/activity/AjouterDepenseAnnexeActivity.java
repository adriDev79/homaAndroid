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
import com.homa.bo.DepenseAnnexe;
import com.homa.dao.SqlService;
import com.homa.ihm.adapter.SpinnerAdapter;
import com.homa.utils.HomaToastUtils;
import com.homa.utils.HomaUtils;

import java.util.LinkedList;
import java.util.Objects;

public class AjouterDepenseAnnexeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_depense_annexe);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinkedList<String> typeDepense = new LinkedList<>(HomaUtils.MAP_TYPE_DEPENSE.keySet());

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this,R.layout.ligne_spinner, typeDepense);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner_depense_annexe);
        spinner.setAdapter(spinnerAdapter);
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
     * fonction déclencher au clique du boutton valider et qui ajoute une nouvelle dépense annexe.
     *
     * @param view {@code View}
     */
    public void ajouterDepenseAnnexe(View view) {
        Spinner spTypeDepense = findViewById(R.id.spinner_depense_annexe);
        String typeDepense = spTypeDepense.getSelectedItem().toString().equals("") ? HomaUtils.EMPTY : spTypeDepense.getSelectedItem().toString();
        int idTypeDepense = 0;

        if (!typeDepense.equals(HomaUtils.EMPTY)) {
            idTypeDepense = Objects.requireNonNull(HomaUtils.MAP_TYPE_DEPENSE.get(typeDepense));
        }

        EditText etLibelle = findViewById(R.id.et_libelle_depense_annexe);
        String libelle = etLibelle.getText().toString().equals("") ? HomaUtils.EMPTY : etLibelle.getText().toString();

        EditText etMontant = findViewById(R.id.et_montant_depense_annexe);
        float montant = etMontant.getText().toString().equals("") ? 0f : Float.parseFloat(etMontant.getText().toString());

        TextView etDatePrelevement = findViewById(R.id.tv_date_prelevement_depense_annexe);
        String datePrelevement = etDatePrelevement.getText().toString().equals("")? HomaUtils.NON_RENSEIGNE : etDatePrelevement.getText().toString();

        TextView etFinPrelevement = findViewById(R.id.tv_date_fin_prelevement_depense_annexe);
        String finPrelevement = etFinPrelevement.getText().toString().equals("")? HomaUtils.AUCUNE : etFinPrelevement.getText().toString();

        CheckBox isPaye = findViewById(R.id.cb_payer_depense_annexe);
        boolean check = isPaye.isChecked();

        if (!HomaUtils.EMPTY.equals(libelle) && montant != 0f) {
            Log.i(HomaUtils.TAG, HomaUtils.DEBUT + HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_DEPENSE_ANNEXE);
            Intent intent = getIntent();
            String dateCreation = intent.getStringExtra("dateAccount");

            DepenseAnnexe depenseAnnexe = new DepenseAnnexe();
            depenseAnnexe.setLibelle(libelle);
            depenseAnnexe.setMontant(montant);
            depenseAnnexe.setDateDeCreation(dateCreation);
            depenseAnnexe.setDateDePrelevement(datePrelevement);
            depenseAnnexe.setIdTypeDepense(idTypeDepense);
            depenseAnnexe.setPayer(check);
            depenseAnnexe.setDateFinPrelevement(finPrelevement);

            try {
                new Thread(() -> {
                    SqlService sqlService = new SqlService();
                    sqlService.insertDA(this, depenseAnnexe);
                }).start();

                Toast.makeText(this, HomaToastUtils.DEPENSE_ANNEXE_AJOUTER, Toast.LENGTH_SHORT).show();
                Log.i(HomaUtils.TAG, HomaUtils.FIN + HomaUtils.ACTION + HomaUtils.ACTION_AJOUTER_DEPENSE_ANNEXE + HomaUtils.RESULTAT + HomaUtils.SUCCESS);

                Intent intention = new Intent(this, MainActivity.class);
                intention.putExtra("dateAccount", depenseAnnexe.getDateDeCreation());
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

    /**
     * fonction déclenché au clique du boutton retour.
     *
     * @param view {@code View}
     */
    public void clickRetourAjouterDepenseAnnexe(View view) {
        Intent intent = getIntent();

        Intent intention = new Intent(this, MainActivity.class);
        intention.putExtra("dateAccount", intent.getStringExtra("dateAccount"));
        startActivity(intention);
    }

    /**
     * Affichage du calendrier pour la date du prélévement.
     *
     * @param view {@code View}
     */
    public void clickCalendarAjoutDepenseAnnexe(View view) {
        HomaUtils.calendar(view.getContext(), findViewById(R.id.tv_date_prelevement_depense_annexe));
    }

    /**
     * Affichage du calendrier pour la date de fin du prélévement.
     *
     * @param view {@code View}
     */
    public void clickCalendarAjoutDateFinDepenseAnnexe(View view) {
        HomaUtils.calendar(view.getContext(), findViewById(R.id.tv_date_fin_prelevement_depense_annexe));
    }
}