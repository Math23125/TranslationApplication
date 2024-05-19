package com.example.navbarre.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.navbarre.R;
import com.example.navbarre.fragment.Homepower.ClipBoardCall;
import com.example.navbarre.fragment.Homepower.SwitchLanguage;
import com.example.navbarre.fragment.Homepower.TranslationCall;
import com.google.android.material.internal.TextWatcherAdapter;



public class FragmentHome extends Fragment {

    private ClipBoardCall clipBoard;
    private SwitchLanguage switchLanguage;
    private TranslationCall translation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    @SuppressLint("RestrictedApi")
    private void init(View view) {
        clipBoard = new ClipBoardCall(requireContext());
        translation = new TranslationCall(requireContext());  // Instanciation correcte de TranslationCall

        // Récupération des composants UI depuis le layout
        EditText inputText = view.findViewById(R.id.inputText);
        TextView translatedText = view.findViewById(R.id.translatedText);
        ImageButton copyButtonInputText = view.findViewById(R.id.copyButtonInputText);
        ImageButton copyButtonTranslatedText = view.findViewById(R.id.copyButtonTranslatedText);
        ImageView frenchFlag = view.findViewById(R.id.frenchflag);
        ImageView britishFlag = view.findViewById(R.id.britishflag);
        TextView frenchText = view.findViewById(R.id.frenchText);
        TextView englishText = view.findViewById(R.id.englishText);

        // Correction de l'instanciation de SwitchLanguage
        switchLanguage = new SwitchLanguage(translation, frenchFlag, britishFlag, frenchText, englishText);

        // Configuration des boutons de copie
        copyButtonInputText.setOnClickListener(v -> clipBoard.copyTextToClipboard(inputText.getText().toString()));
        copyButtonTranslatedText.setOnClickListener(v -> clipBoard.copyTextToClipboard(translatedText.getText().toString()));

        // Configuration de l'icône pour changer de langue
        ImageView arrow = view.findViewById(R.id.arrowIcon);
        arrow.setOnClickListener(v -> switchLanguage.switchLanguages());

        // Ajout d'un TextWatcher pour la traduction dynamique
        inputText.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                translation.debounceTranslate(s.toString(), translatedText);
            }
        });
    }
}

