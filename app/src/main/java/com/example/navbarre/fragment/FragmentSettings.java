package com.example.navbarre.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import androidx.fragment.app.Fragment;

import com.example.navbarre.R;

public class FragmentSettings extends Fragment {

    private Spinner languageSpinner;
    private String selectedLanguage = "";

    public FragmentSettings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialisation du Spinner
        languageSpinner = rootView.findViewById(R.id.languageSpinner);

        // Configuration du Spinner avec les options de langue depuis les ressources de chaînes
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.language_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        // Écouteur de sélection pour le Spinner
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = parent.getItemAtPosition(position).toString();
                // Faites ce que vous devez faire avec la langue sélectionnée ici
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Faites quelque chose si rien n'est sélectionné
            }
        });

        return rootView;
    }

    // Méthode pour récupérer la langue sélectionnée
    public String getSelectedLanguage() {
        return selectedLanguage;
    }
}
