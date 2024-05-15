package com.example.navbarre.fragment.Homepower.Histo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TranslationDao {
    @Insert
    void insert(Translation translation);

    @Query("SELECT * FROM translations")
    List<Translation> getAllTranslations();

    @Delete
    void deleteTranslation(Translation translation);
}
