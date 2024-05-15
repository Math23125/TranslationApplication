package com.example.navbarre.fragment.Homepower;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import com.example.navbarre.fragment.Homepower.Histo.AppDatabase;
import com.example.navbarre.fragment.Homepower.Histo.DatabaseClient;
import com.example.navbarre.fragment.Homepower.Histo.Translation;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class TranslationCall {
    private Context context;
    private AppDatabase db;
    private String currentLang = "fr-en";  // Direction de la langue par défaut
    private Handler handler = new Handler();
    private Runnable workRunnable;

    public TranslationCall(Context context) {
        this.context = context;
        db = DatabaseClient.getInstance(context).getAppDatabase();
    }


    public String getCurrentLang() {
        return currentLang;
    }

    public void setLanguage(String lang) {
        currentLang = lang;
    }

    public void debounceTranslate(final String text, final TextView textView) {
        handler.removeCallbacks(workRunnable);
        workRunnable = () -> translateText(text, textView);
        handler.postDelayed(workRunnable, 500); // Délai de 500 ms
    }

    public void translateText(final String text, final TextView textView) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("text", text);
        } catch (JSONException e) {
            Log.e("TranslationCall", "Error creating JSON object.", e);
            return;
        }

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url("http://192.168.1.15:5000/translate/" + currentLang)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("TranslationCall", "Network Call Failed", e);
                if (context instanceof Activity) {
                    ((Activity) context).runOnUiThread(() ->
                            Toast.makeText(context, "Erreur de réseau: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(() ->
                                Toast.makeText(context, "Erreur serveur: " + response.code(), Toast.LENGTH_LONG).show()
                        );
                    }
                    return;
                }

                String responseData = response.body().string();
                try {
                    JSONObject jsonResponse = new JSONObject(responseData);
                    String translatedText = jsonResponse.getString("translated_text");
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(() -> {
                            textView.setText(translatedText);
                            saveTranslation(text, translatedText); // Sauvegarde la traduction ici
                        });
                    }
                } catch (JSONException e) {
                    Log.e("TranslationCall", "Erreur de parsing JSON", e);
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(() ->
                                Toast.makeText(context, "Erreur de parsing JSON", Toast.LENGTH_SHORT).show()
                        );
                    }
                }
            }
        });
    }

    private void saveTranslation(String original, String translated) {
        new Thread(() -> {
            Translation translation = new Translation();
            translation.originalText = original;
            translation.translatedText = translated;

            // Formatage de la date et de l'heure actuelles
            /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String timestamp = sdf.format(new Date());
            translation.timestamp = timestamp; */

                        // Obtenir l'instance de la base de données via DatabaseClient
            AppDatabase db = DatabaseClient.getInstance(context).getAppDatabase();

            db.translationDao().insert(translation);
            Log.d("TranslationCall", "Translation saved: " + original + " -> " + translated);
        }).start();
    }
}



