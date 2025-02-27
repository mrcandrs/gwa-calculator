package com.example.gradecalculator.HelperClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Grades")
public class SubjectClass {

    @ColumnInfo(name = "grade_id")
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "subject_name")
    public String subject_name;

    @ColumnInfo(name = "prelims")
    public float prelims;

    @ColumnInfo(name = "midterm")
    public float midterm;

    @ColumnInfo(name = "prefinals")
    public float prefinals;

    @ColumnInfo(name = "finals")
    public float finals;

    @ColumnInfo(name = "final_grade")
    public float final_grade;

    @Ignore
    public SubjectClass() {
    }



    // Constructor for Room
    public SubjectClass(int id, String subject_name, float prelims, float midterm, float prefinals, float finals, float final_grade) {
        this.id = id;
        this.subject_name = subject_name;
        this.prelims = prelims;
        this.midterm = midterm;
        this.prefinals = prefinals;
        this.finals = finals;
        this.final_grade = final_grade;
    }

    // Constructor for manual creation (without ID)
    @Ignore
    public SubjectClass(String subject_name, float prelims, float midterm, float prefinals, float finals) {
        this.subject_name = subject_name;
        this.prelims = prelims;
        this.midterm = midterm;
        this.prefinals = prefinals;
        this.finals = finals;
        this.final_grade = (float) ((prelims) + (midterm) + (prefinals) + (finals ));
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public float getPrelims() {
        return prelims;
    }

    public void setPrelims(float prelims) {
        this.prelims = prelims;
    }

    public float getMidterm() {
        return midterm;
    }

    public void setMidterm(float midterm) {
        this.midterm = midterm;
    }

    public float getPrefinals() {
        return prefinals;
    }

    public void setPrefinals(float prefinals) {
        this.prefinals = prefinals;
    }

    public float getFinals() {
        return finals;
    }

    public void setFinals(float finals) {
        this.finals = finals;
    }

    public float getFinal_grade() {
        return final_grade;
    }

    public void setFinal_grade(float final_grade) {
        this.final_grade = final_grade;
    }
}