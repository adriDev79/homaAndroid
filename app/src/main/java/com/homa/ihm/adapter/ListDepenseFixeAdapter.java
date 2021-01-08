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
import com.homa.bo.DepenseFixe;
import com.homa.utils.HomaUtils;

import java.util.List;

public class ListDepenseFixeAdapter extends ArrayAdapter<DepenseFixe> {

    private int idPresentationLigne;

    public ListDepenseFixeAdapter(@NonNull Context context, int resource, @NonNull List<DepenseFixe> objects) {
        super(context, resource, objects);
        idPresentationLigne = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ligneView = inflater.inflate(idPresentationLigne, parent, false);

        TextView tvTypeDepense = ligneView.findViewById(R.id.tv_type_depense_fixe);
        TextView libelle = ligneView.findViewById(R.id.tv_ligne_list_depense_fixe_libelle);
        TextView montant = ligneView.findViewById(R.id.tv_ligne_list_depense_fixe_montant);
        TextView datePrelevement = ligneView.findViewById(R.id.tv_label_prelevement_depense_fixe);
        TextView payer = ligneView.findViewById(R.id.tv_label_is_payer_depense_fixe);

        DepenseFixe depenseFixe = getItem(position);

        String typeDepense = null;
        for (String item : HomaUtils.MAP_TYPE_DEPENSE.keySet()) {
            if (HomaUtils.MAP_TYPE_DEPENSE.get(item) == depenseFixe.getIdTypeDepense()) {
                typeDepense = item;
            }
        }

        if (depenseFixe.isPayer()) {
            payer.setText("Payé: oui");
        }else {
            payer.setText("Payé: non");
        }

        tvTypeDepense.setText(typeDepense);
        libelle.setText(depenseFixe.getLibelle());
        montant.setText(String.valueOf(depenseFixe.getMontant()) + " €");
        datePrelevement.setText("Prélévement: " + depenseFixe.getDateDePrelevement());

        return ligneView;
    }


}
