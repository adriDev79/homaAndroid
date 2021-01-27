package com.homa.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.homa.MainActivity;
import com.homa.R;
import com.homa.bll.asyncTask.AsyncTaskDepenseAnnexe;
import com.homa.bll.asyncTask.AsyncTaskDepenseFixe;
import com.homa.bll.asyncTask.AsyncTaskRevenu;
import com.homa.bll.asyncTask.AsyncTaskSolde;
import com.homa.bo.Revenu;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomaUtils {

    public static final String EMPTY = "empty";
    public static final String NON_RENSEIGNE = "Pas de date";
    public static final String AUCUNE = "Aucune";
    public static final int DELAY_LAUNCHER_PAGE = 4300;
    // LOG ACTIVITY
    public static final String TAG = "HOMALOG";

    public static final String DEBUT = "Début";
    public static final String FIN = "fin";
    public static final String SUCCESS = "success";
    public static final String ECHEC = "echec";
    public static final String ACTION = " | action : ";
    public static final String ERREUR = " | erreur : ";
    public static final String RESULTAT = " | résultat : ";

    public static final String CHAMPS_VIDE = "Champs vide";

    //Action
    public static final String ACTION_AJOUTER_REVENU = "Ajouter un revenu";
    public static final String ACTION_AJOUTER_SOLDE = "Ajouter un solde";
    public static final String ACTION_AJOUTER_DEPENSE_FIXE = "Ajouter une dépense fixe";
    public static final String ACTION_AJOUTER_DEPENSE_ANNEXE = "Ajouter une dépense annexe";

    public static final String ACTION_MODIFIER_REVENU = "Modifier un revenu";
    public static final String ACTION_MODIFIER_SOLDE = "Modifier un solde";
    public static final String ACTION_MODIFIER_DEPENSE_FIXE = "Modifier une dépense fixe";
    public static final String ACTION_MODIFIER_DEPENSE_ANNEXE = "Modifier une dépense annexe";

    public static final String ACTION_SUPPRIMER_REVENU = "Supprimer un revenu";
    public static final String ACTION_SUPPRIMER_SOLDE = "Supprimer un solde";
    public static final String ACTION_SUPPRIMER_DEPENSE_FIXE = "Supprimer une dépense fixe";
    public static final String ACTION_SUPPRIMER_DEPENSE_ANNEXE = "Supprimer une dépense annexe";

    //AUTRE
    public static final LinkedHashMap<String, Integer> MAP_TYPE_DEPENSE = mapTypeDepense();

    private static LinkedHashMap<String, Integer> mapTypeDepense() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("Loyer", 1);
        map.put("Nourriture", 2);
        map.put("Essence", 3);
        map.put("Elec/ gaz", 4);
        map.put("Eau", 5);
        map.put("Box internet", 6);
        map.put("Forfait mobile", 7);
        map.put("Impôt", 8);
        map.put("Crédit", 9);
        map.put("Assurance", 10);
        map.put("Mutuelle", 11);
        map.put("Entretien voiture", 12);
        map.put("Loisir", 13);
        map.put("Vacance", 14);
        map.put("Aménagement de la maison", 15);
        map.put("Soin", 16);
        map.put("Autres", 17);

        return map;
    }

    public static void calendar(Context context, TextView textView) {
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                if (textView != null) {
                    textView.setText(sdf.format(myCalendar.getTime()));
                }

            }
        };
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void visibilityListViewRevenu(ListView listView, Button button, AsyncTaskRevenu asyncTask) {
        if (listView.getVisibility() == View.VISIBLE) {
            button.setText(R.string.add);
            listView.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = 0;
            listView.setLayoutParams(params);
        } else {
            asyncTask.execute();
            button.setText(R.string.moins);
            listView.setVisibility(View.VISIBLE);
        }
    }

    public static void visibilityListViewDepenseFixe(ListView listView, Button button, AsyncTaskDepenseFixe asyncTask) {
        if (listView.getVisibility() == View.VISIBLE) {
            button.setText(R.string.add);
            listView.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = 0;
            listView.setLayoutParams(params);
        } else {
            asyncTask.execute();
            button.setText(R.string.moins);
            listView.setVisibility(View.VISIBLE);
        }
    }

    public static void visibilityListViewDepenseAnnexe(ListView listView, Button button, AsyncTaskDepenseAnnexe asyncTask) {
        if (listView.getVisibility() == View.VISIBLE) {
            button.setText(R.string.add);
            listView.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = 0;
            listView.setLayoutParams(params);
        } else {
            asyncTask.execute();
            button.setText(R.string.moins);
            listView.setVisibility(View.VISIBLE);
        }
    }

    public static void visibilityListViewSolde(ListView listView, Button button, AsyncTaskSolde asyncTask) {
        if (listView.getVisibility() == View.VISIBLE) {
            button.setText(R.string.add);
            listView.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = 0;
            listView.setLayoutParams(params);
        } else {
            asyncTask.execute();
            button.setText(R.string.moins);
            listView.setVisibility(View.VISIBLE);
        }
    }

    public static String dateFormat(String pattern, Object date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.FRANCE);
        return sdf.format(date);
    }
}
