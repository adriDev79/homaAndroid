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
import com.homa.bo.Solde;

import java.util.List;

public class ListSoldeAdapter extends ArrayAdapter<Solde> {

    private int idPresentationLigne;

    public ListSoldeAdapter(@NonNull Context context, int resource, @NonNull List<Solde> objects) {
        super(context, resource, objects);
        idPresentationLigne = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ligneView = inflater.inflate(idPresentationLigne, parent, false);

        TextView libelle = ligneView.findViewById(R.id.tv_ligne_list_solde_libelle);
        TextView montant = ligneView.findViewById(R.id.tv_ligne_list_solde_montant);

        Solde solde = getItem(position);

        if (solde.getMontant() < 0f) {
            montant.setTextColor(Color.parseColor("#CB4335"));
        } else {
            montant.setTextColor(Color.parseColor("#27AE60"));
        }

        libelle.setText(solde.getLibelle());
        montant.setText(String.valueOf(solde.getMontant()) + " €");

        return ligneView;
    }


}
