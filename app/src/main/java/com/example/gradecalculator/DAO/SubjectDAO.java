package com.example.gradecalculator.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gradecalculator.HelperClass.SubjectClass;

import java.util.List;

@Dao
public interface SubjectDAO {

    @Insert
    public void addGrade(SubjectClass subject);

    @Query("select * from Grades")
    public List<SubjectClass> getCalculate();

    @Query("DELETE FROM Grades") // Assuming your table name is "Grades"
    void deleteAllGrades();

    @Query("select * from Grades where grade_id==:grade_id")
    public SubjectClass getGrade(int grade_id);

}
