package com.example.navbarre.fragment.Histopower;

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
    //List<Translation> getAll();

    @Delete
    void deleteTranslation(Translation translation);
}
