package com.homa.ihm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.homa.R;
import com.homa.bo.Revenu;

import java.util.List;

public class ListRevenuAdapter extends ArrayAdapter<Revenu> {

    private int idPresentationLigne;

    public ListRevenuAdapter(@NonNull Context context, int resource, @NonNull List<Revenu> objects) {
        super(context, resource, objects);
        idPresentationLigne = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ligneView = inflater.inflate(idPresentationLigne, parent, false);

        TextView libelle = ligneView.findViewById(R.id.tv_ligne_list_revenu_libelle);
        TextView montant = ligneView.findViewById(R.id.tv_ligne_list_revenu_montant);
        TextView dateReception = ligneView.findViewById(R.id.tv_date_reception);

        Revenu revenu = getItem(position);

        libelle.setText(revenu.getLibelle());
        montant.setText(String.valueOf(revenu.getMontant()) + " €");
        dateReception.setText(revenu.getDateDeReception());

        return ligneView;
    }


}
