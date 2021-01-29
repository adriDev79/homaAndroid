package com.homa.ihm.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.homa.R;

import org.jetbrains.annotations.NotNull;

public class EnTeteFragment extends Fragment {

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_en_tete, container, false);
        clickContact(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void clickContact(View view) {
        ImageButton btnContact = view.findViewById(R.id.btn_contact);
        btnContact.setOnClickListener(view1 -> {
            // Compile a Uri with the 'mailto' schema
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","homa.app.contact@gmail.com", null));
            // Subject
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Aide ou demande d'informations");

            // Check if the device has an email client
            startActivity(Intent.createChooser(emailIntent,"Choisissez votre application"));
        });
    }
}