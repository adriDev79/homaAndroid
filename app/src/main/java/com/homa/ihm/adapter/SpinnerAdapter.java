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

public class SpinnerAdapter extends ArrayAdapter<String> {

    private int idPresentationLigne;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        idPresentationLigne = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ligneView = inflater.inflate(idPresentationLigne, parent, false);

        TextView tvItemSpinner = ligneView.findViewById(R.id.tv_ligne_spinner);
        String typeDepense = getItem(position);
        tvItemSpinner.setText(typeDepense);

        return ligneView;
    }


}
