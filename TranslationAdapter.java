package com.example.navbarre.fragment.Homepower.Histo;
import android.app.Activity;
import android.content.Context;
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

    public TranslationAdapter(Context context, List<Translation> translations)
    {
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
        holder.originalText.setText(translation.originalText);
        holder.translatedText.setText(translation.translatedText);
        holder.deleteIcon.setOnClickListener(v -> removeItem(position));

        /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        holder.translationTime.setText(sdf.format(translation.timestamp)); */
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
        //TextView translationTime;
        ImageView deleteIcon;

        public ViewHolder(View view) {
            super(view);
            originalText = view.findViewById(R.id.original_text);
            translatedText = view.findViewById(R.id.translated_text);
            //translationTime = view.findViewById(R.id.translation_time);
            deleteIcon = view.findViewById(R.id.delete_icon);
        }
    }
}
