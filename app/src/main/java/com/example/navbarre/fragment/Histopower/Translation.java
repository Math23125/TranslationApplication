package com.example.navbarre.fragment.Histopower;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "translations")
public class Translation {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "original_text")
    public String originalText;

    @ColumnInfo(name = "translated_text")
    public String translatedText;

    @ColumnInfo(name = "date")
    private String date; // Ajouter un champ pour la date

    @ColumnInfo(name = "time")
    private String time; // Ajouter un champ pour l'heure

    // Getters
    public int getId() {
        return id;
    }

    public String getText() {
        return originalText;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setText(String originalText) {
        this.originalText = originalText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}


