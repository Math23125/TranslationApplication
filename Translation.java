package com.example.navbarre.fragment.Homepower.Histo;

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

    /*@ColumnInfo(name = "translationTime")
    public String timestamp;*/

    // Getters
    public int getId() {
        return id;
    }


    // Setters
    public void setId(int id) {
        this.id = id;
    }


}


