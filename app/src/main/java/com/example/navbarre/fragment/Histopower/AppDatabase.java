package com.example.navbarre.fragment.Histopower;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.navbarre.fragment.Histopower.Translation;

@Database(entities = {Translation.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TranslationDao translationDao();

    // Migration from version 1 to version 2
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE translation ADD COLUMN date TEXT");
            database.execSQL("ALTER TABLE translation ADD COLUMN time TEXT");
        }
    };
}

