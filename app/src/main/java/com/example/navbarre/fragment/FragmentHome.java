package com.example.navbarre.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.util.Log;
import androidx.fragment.app.Fragment;

import com.example.navbarre.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    // Constantes pour les clés d'arguments du bundle
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView frenchFlag;
    private ImageView britishFlag;
    private TextView frenchText;
    private TextView englishText;
    private ImageView arrow;
    private Handler handler = new Handler();
    private Runnable workRunnable;

    public FragmentHome() {
        // Required empty public constructor
    }

    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        EditText inputText = view.findViewById(R.id.inputText);
        TextView translatedText = view.findViewById(R.id.translatedText);
        LinearLayout frenchContainer = view.findViewById(R.id.frenchContainer);
        LinearLayout englishContainer = view.findViewById(R.id.englishContainer);

        frenchText = frenchContainer.findViewById(R.id.frenchText);
        englishText = englishContainer.findViewById(R.id.englishText);
        frenchFlag = frenchContainer.findViewById(R.id.frenchflag);
        britishFlag = englishContainer.findViewById(R.id.britishflag);
        arrow = view.findViewById(R.id.arrowIcon);

        // Setup language switching logic
        arrow.setOnClickListener(v -> switchLanguages());

        // Setup TextWatcher for automatic translation
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                debounceTranslate(s.toString(), translatedText);
            }
        });
    }

    private void switchLanguages() {
        Drawable tempFlag = frenchFlag.getDrawable();
        frenchFlag.setImageDrawable(britishFlag.getDrawable());
        britishFlag.setImageDrawable(tempFlag);

        CharSequence tempText = frenchText.getText();
        frenchText.setText(englishText.getText());
        englishText.setText(tempText);
    }

    private void translateText(final String text, final TextView textView) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("text", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url("http://192.168.1.18:5000/translate")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("FragmentHome", "Network Call Failed", e); // Ceci loguera l'erreur dans Logcat

                getActivity().runOnUiThread(() -> {
                    // Affichez le message d'erreur dans un Toast.
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    // Optionnel : Affichez le message d'erreur dans le TextView du fragment.
                    // translatedText.setText("Error: " + e.getMessage()); // Assurez-vous que translatedText est accessible ici.
                });
            }


            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                getActivity().runOnUiThread(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        String translatedText = jsonResponse.getString("translated_text");
                        textView.setText(translatedText);
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Failed to parse JSON", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void debounceTranslate(final String text, final TextView textView) {
        handler.removeCallbacks(workRunnable);
        workRunnable = () -> translateText(text, textView);
        handler.postDelayed(workRunnable, 500); // Delay of 500 ms
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}
