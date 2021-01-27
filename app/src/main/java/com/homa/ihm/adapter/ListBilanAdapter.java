package com.homa.ihm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.homa.R;
import com.homa.bo.Total;

import java.util.Arrays;
import java.util.List;

public class ListBilanAdapter extends ArrayAdapter<Total> {

    private int idPresentationLigne;

    public ListBilanAdapter(@NonNull Context context, int resource, @NonNull List<Total> objects) {
        super(context, resource, objects);
        idPresentationLigne = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ligneView = inflater.inflate(idPresentationLigne, parent, false);

        TextView libelle = ligneView.findViewById(R.id.tv_ligne_list_bilan_libelle);
        TextView montant = ligneView.findViewById(R.id.tv_ligne_list_bilan_montant);

        Total Total = getItem(position);

        if (Total.getMontant() < 0f) {
            montant.setTextColor(Color.parseColor("#CB4335"));
        } else {
            montant.setTextColor(Color.parseColor("#27AE60"));
        }

        for (String lib : Arrays.asList("Total des dépenses fixes", "Total des dépenses annexes", "Total des dépenses")) {
            if (lib.equals(Total.getLibelle())) {
                montant.setTextColor(Color.parseColor("#CB4335"));
            }
        }

        libelle.setText(Total.getLibelle());
        montant.setText(String.valueOf(Total.getMontant()) + " €");

        return ligneView;
    }


}
