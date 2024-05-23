package com.example.navbarre.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navbarre.R;
import com.example.navbarre.fragment.Histopower.AppDatabase;
import com.example.navbarre.fragment.Histopower.DatabaseClient;
import com.example.navbarre.fragment.Histopower.Translation;
import com.example.navbarre.fragment.Histopower.TranslationAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHisto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHisto extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private TranslationAdapter adapter;

    public FragmentHisto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentUser.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHisto newInstance(String param1, String param2) {
        FragmentHisto fragment = new FragmentHisto();
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_histo, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadTranslations();
        return view;
    }

    private void loadTranslations() {
        new Thread(() -> {
            AppDatabase db = DatabaseClient.getInstance(getActivity().getApplicationContext()).getAppDatabase();
            List<Translation> translations = db.translationDao().getAllTranslations();

            getActivity().runOnUiThread(() -> {
                adapter = new TranslationAdapter(getActivity(), translations);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

}