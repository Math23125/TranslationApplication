package com.example.navbarre.fragment.Histopower;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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



    public TranslationAdapter(Context context, List<Translation> translations) {
        this.translations = translations;
        this.context = context;

    }

    public void clearTranslations() {
        translations.clear();
        notifyDataSetChanged();
    }


    public void setTranslations(List<Translation> newTranslations) {
        this.translations = newTranslations;
        notifyDataSetChanged();
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


        // Gestion de la suppression par appui long
        holder.itemView.setOnLongClickListener(v -> {


            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Confirmer la suppression")
                    .setMessage("Voulez-vous vraiment supprimer cette traduction ?")
                    .setPositiveButton("Supprimer", (dialogInterface, i) -> {
                        // Logique pour supprimer l'élément
                    })
                    .setNegativeButton("Annuler", null)
                    .show();

// Modifier la couleur du bouton positif
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.BLACK);  // Utilisez Color.BLACK ou une autre couleur définie

// Modifier la couleur du bouton négatif
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            negativeButton.setTextColor(Color.BLACK);  // Assurez-vous que c'est bien appliqué


            return true;
        });


        holder.deleteIcon.setOnClickListener(v -> removeItem(position));
    }

    @Override
    public int getItemCount() {
        return translations.size();
    }

    public void removeItem(int position) {
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
        TextView translationDate;
        TextView translationTime;
        ImageView deleteIcon;

        public ViewHolder(View view) {
            super(view);
            originalText = view.findViewById(R.id.original_text);
            translatedText = view.findViewById(R.id.translated_text);
            translationDate = view.findViewById(R.id.translation_date);
            translationTime = view.findViewById(R.id.translation_time);
            deleteIcon = view.findViewById(R.id.delete_icon);
        }
    }
}
