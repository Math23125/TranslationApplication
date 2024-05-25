package com.example.navbarre.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.navbarre.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FragmentSettings extends Fragment {

    private Switch themeSwitch;
    private Switch notifSwitch;
    private ImageView notificationIcon;

    public FragmentSettings() {
        // Required empty public constructor
    }

    public static FragmentSettings newInstance(String param1, String param2) {
        FragmentSettings fragment = new FragmentSettings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private final Map<String, String> profiles = new HashMap<>();

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initProfiles();

        themeSwitch = view.findViewById(R.id.themeSwitch);
        notifSwitch = view.findViewById(R.id.notif);
        notificationIcon = view.findViewById(R.id.notificationIcon);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE);
        boolean isDarkModeEnabled = sharedPreferences.getBoolean("isDarkModeEnabled", false);
        themeSwitch.setChecked(isDarkModeEnabled);

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveDarkModeState(isChecked);
                applyTheme(isChecked);
            }
        });

        notifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notificationIcon.setImageResource(R.drawable.baseline_notifications_active_24);
                } else {
                    notificationIcon.setImageResource(R.drawable.baseline_notifications_none_24);
                }
            }
        });

        ImageButton infoButton = view.findViewById(R.id.InfoButton);
        infoButton.setOnClickListener(v -> showInfoDialog());

        ImageButton editProfileButton = view.findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(v -> showProfileDialog());

        return view;
    }

    private void initProfiles() {
        profiles.put("John", "John,Doe,MysticDreamer,01/01/1990");
        profiles.put("Alice", "Alice,Smith,SilverPhoenix,02/02/1995");
        profiles.put("Bob", "Bob,Johnson,FrostByte,03/03/1985");
        profiles.put("Mahouna", "Mahouna,Vayssieres,Mikey,06/06/2003");
        profiles.put("Mathieu", "Mathieu,Gilles,ShadowNinja,12/12/2003");
        profiles.put("Mathieu", "Mathieu,Habelski,PédroCiTdor,23/12/2003");
    }

    // Méthode pour récupérer aléatoirement un profil du dictionnaire
    private String getRandomProfile() {
        Random random = new Random();
        int randomIndex = random.nextInt(profiles.size());
        Object[] keys = profiles.keySet().toArray();
        String randomKey = (String) keys[randomIndex];
        return profiles.get(randomKey);
    }

    @SuppressLint("SetTextI18n")
    private void showProfileDialog() {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);

        TextView titleTextView = dialogView.findViewById(R.id.dialogTitle);
        TextView messageTextView = dialogView.findViewById(R.id.message);
        Button btnOK = dialogView.findViewById(R.id.btnOK);

        String randomProfile = getRandomProfile();
        String[] profileInfo = randomProfile.split(",");

        titleTextView.setText("Profil");
        messageTextView.setText(
                "Nom: " + profileInfo[1] + "\n" +
                        "Prénom: " + profileInfo[0] + "\n" +
                        "Pseudo: " + profileInfo[2] + "\n" +
                        "Date de naissance: " + profileInfo[3]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        btnOK.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void showInfoDialog() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_custom, null);
        TextView textViewTitle = dialogView.findViewById(R.id.dialogTitle);
        TextView textViewMessage = dialogView.findViewById(R.id.message);
        Button btnOK = dialogView.findViewById(R.id.btnOK);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .create();

        textViewTitle.setText("À Propos de IdiomAi");
        textViewMessage.setText(
                "Date de Création : 2023-2024\n" +
                        "\n" +
                        "Équipe :\n" +
                        "\n" +
                        "Mahouna V., Mathieu G., Mathieu H.\n" +
                        "\n" +
                        "Contact : Besoin d'aide ? Contactez-nous à MahMAtMat@esme.fr.\n" +
                        "\n" +
                        "Notre application à pour but d'aider à la compréhension et à l'inclusion à l'internationnale.\n" +
                        "\n" +
                        "Merci d'utiliser IdiomAi !");

        btnOK.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }

    private void saveDarkModeState(boolean isEnabled) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDarkModeEnabled", isEnabled);
        editor.apply();
    }

    private void applyTheme(boolean isDarkModeEnabled) {
        if (isDarkModeEnabled) {
            requireActivity().setTheme(R.style.Theme_NavBarre_Dark);
        } else {
            requireActivity().setTheme(R.style.Theme_NavBarre_Light);
        }
        requireActivity().recreate(); // applique le theme en recréant l'activité
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
}
