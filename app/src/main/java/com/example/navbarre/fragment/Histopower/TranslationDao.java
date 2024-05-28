package com.example.navbarre.fragment.Histopower;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TranslationDao {
    @Insert
    void insert(Translation translation);

    @Query("SELECT * FROM translations ORDER BY id DESC")
    LiveData<List<Translation>> getAllTranslations();

    @Query("DELETE FROM translations")
    void deleteAllTranslations();

    @Delete
    void deleteTranslation(Translation translation);
}
