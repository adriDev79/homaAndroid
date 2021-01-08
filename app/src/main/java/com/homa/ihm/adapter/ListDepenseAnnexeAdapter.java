package com.homa.ihm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.homa.R;
import com.homa.bo.DepenseAnnexe;
import com.homa.bo.DepenseFixe;
import com.homa.utils.HomaUtils;

import java.util.List;

public class ListDepenseAnnexeAdapter extends ArrayAdapter<DepenseAnnexe> {

    private final int idPresentationLigne;

    public ListDepenseAnnexeAdapter(@NonNull Context context, int resource, @NonNull List<DepenseAnnexe> objects) {
        super(context, resource, objects);
        idPresentationLigne = resource;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ligneView = inflater.inflate(idPresentationLigne, parent, false);

        TextView tvTypeDepense = ligneView.findViewById(R.id.tv_type_depense_annexe);
        TextView libelle = ligneView.findViewById(R.id.tv_ligne_list_depense_annexe_libelle);
        TextView montant = ligneView.findViewById(R.id.tv_ligne_list_depense_annexe_montant);
        TextView datePrelevement = ligneView.findViewById(R.id.tv_label_prelevement_depense_annexe);
        TextView payer = ligneView.findViewById(R.id.tv_label_is_payer_depense_annexe);
        TextView finPrelevement = ligneView.findViewById(R.id.tv_label_fin_depense_annexe);

        DepenseAnnexe depenseAnnexe = getItem(position);

        String typeDepense = null;
        for (String item : HomaUtils.MAP_TYPE_DEPENSE.keySet()) {
            if (HomaUtils.MAP_TYPE_DEPENSE.get(item) == depenseAnnexe.getIdTypeDepense()) {
                typeDepense = item;
            }
        }

        if (depenseAnnexe.isPayer()) {
            payer.setText("Payé: oui");
        }else {
            payer.setText("Payé: non");
        }

        tvTypeDepense.setText(typeDepense);
        libelle.setText(depenseAnnexe.getLibelle());
        montant.setText(depenseAnnexe.getMontant() + " €");
        datePrelevement.setText("Prélévement: " + depenseAnnexe.getDateDePrelevement());
        finPrelevement.setText("Date de fin: " +depenseAnnexe.getDateFinPrelevement());

        return ligneView;
    }
}
