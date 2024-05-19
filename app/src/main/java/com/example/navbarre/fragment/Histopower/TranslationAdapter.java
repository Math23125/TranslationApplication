package com.example.navbarre.fragment.Histopower;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navbarre.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.ViewHolder> {

    private List<Translation> translations;
    private Context context;

    public List<Translation> getTranslations() {
        return translations;
    }

    public TranslationAdapter(Context context, List<Translation> translations) {
        this.translations = translations;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.translation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Translation translation = translations.get(position);
        holder.originalText.setText(translation.getText());
        holder.translatedText.setText(translation.getTranslatedText());

        // Directly set the date and time as strings
        holder.translationDate.setText(translation.getDate());
        holder.translationTime.setText(translation.getTime());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullTextActivity.class);
            intent.putExtra("original_text", translation.getText());
            intent.putExtra("translated_text", translation.getTranslatedText());
            intent.putExtra("translation_date", translation.getDate());
            intent.putExtra("translation_time", translation.getTime());
            context.startActivity(intent);
        });

        holder.deleteIcon.setOnClickListener(v -> removeItem(position));
    }

    @Override
    public int getItemCount() {
        return translations.size();
    }

    private void removeItem(int position) {
        Translation translation = translations.get(position);
        new Thread(() -> {
            DatabaseClient.getInstance(context).getAppDatabase()
                    .translationDao().deleteTranslation(translation);
            ((Activity) context).runOnUiThread(() -> {
                translations.remove(position);
                notifyItemRemoved(position);
            });
        }).start();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView originalText;
        TextView translatedText;
        TextView translationDate; // Ajouter un TextView pour la date
        TextView translationTime; // Ajouter un TextView pour l'heure
        ImageView deleteIcon;

        public ViewHolder(View view) {
            super(view);
            originalText = view.findViewById(R.id.original_text);
            translatedText = view.findViewById(R.id.translated_text);
            translationDate = view.findViewById(R.id.translation_date); // Link the view for the date
            translationTime = view.findViewById(R.id.translation_time); // Link the view for the time
            deleteIcon = view.findViewById(R.id.delete_icon);
        }
    }
}
