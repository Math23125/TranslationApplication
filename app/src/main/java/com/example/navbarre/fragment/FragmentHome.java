package com.example.navbarre.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navbarre.R;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.navbarre.R;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    private ImageView frenchFlag;
    private ImageView britishFlag;
    private TextView frenchText;
    private TextView englishText;
    private ImageView arrow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Trouver les conteneurs de langue
        LinearLayout frenchContainer = view.findViewById(R.id.frenchContainer);
        LinearLayout englishContainer = view.findViewById(R.id.englishContainer);

        // Trouver les textes et drapeaux
        TextView frenchText = frenchContainer.findViewById(R.id.frenchText);
        TextView englishText = englishContainer.findViewById(R.id.englishText);
        ImageView frenchFlag = frenchContainer.findViewById(R.id.frenchflag);
        ImageView englishFlag = englishContainer.findViewById(R.id.britishflag);
        ImageView arrow = view.findViewById(R.id.arrowIcon);

        // Configurer l'écouteur de clics sur la flèche
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Échanger les drapeaux
                Drawable tempFlag = frenchFlag.getDrawable();
                frenchFlag.setImageDrawable(englishFlag.getDrawable());
                englishFlag.setImageDrawable(tempFlag);

                // Échanger les textes
                CharSequence tempText = frenchText.getText();
                frenchText.setText(englishText.getText());
                englishText.setText(tempText);
            }
        });
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

}