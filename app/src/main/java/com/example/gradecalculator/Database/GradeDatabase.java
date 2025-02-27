package com.example.gradecalculator.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gradecalculator.DAO.SubjectDAO;
import com.example.gradecalculator.HelperClass.SubjectClass;

@Database(entities = {SubjectClass.class}, version = 1)
public abstract class GradeDatabase extends RoomDatabase {

private static GradeDatabase instance;
public abstract SubjectDAO getSubjectDAO();


    public static synchronized GradeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            GradeDatabase.class, "Grades")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
