package com.example.gradecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.gradecalculator.Adapter.ComputationAdapter;
import com.example.gradecalculator.Database.GradeDatabase;
import com.example.gradecalculator.HelperClass.SubjectClass;
import com.example.gradecalculator.databinding.ActivityComputationPageBinding;

import java.util.ArrayList;
import java.util.List;

public class ComputationPage extends AppCompatActivity {
    private ActivityComputationPageBinding root;
    private ComputationAdapter adapter;
    private GradeDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityComputationPageBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());

        db = GradeDatabase.getInstance(this);

        // Setup RecyclerView
        root.recycleViewer.setLayoutManager(new LinearLayoutManager(this));

        // Load data from Room and set it to RecyclerView
        loadGradesFromDB();

        // Set up buttons
        root.btnCalculateSubject.setOnClickListener(view -> {
            Intent homeIntent = new Intent(ComputationPage.this, HomePage.class);
            startActivity(homeIntent);
            finish();
        });

        root.btnCalculateGWA.setOnClickListener(view -> calculateGWA());

        root.btnClear.setOnClickListener(view -> clearDatabase());
    }

    private void loadGradesFromDB() {
        List<SubjectClass> gradeList = db.getSubjectDAO().getCalculate();
        adapter = new ComputationAdapter(gradeList);
        root.recycleViewer.setAdapter(adapter);
    }

    private void calculateGWA() {
        List<SubjectClass> gradeList = db.getSubjectDAO().getCalculate();

        if (gradeList.isEmpty()) {
            Toast.makeText(this, "No subjects available to calculate GWA", Toast.LENGTH_SHORT).show();
            return;
        }

        float totalConvertedGrade = 0;
        int subjectCount = gradeList.size();

        for (SubjectClass subject : gradeList) {
            // Calculate final grade as average
            float finalGrade = (subject.getPrelims() + subject.getMidterm() + subject.getPrefinals() + subject.getFinals()) / 4;

            // Convert final grade to GWA using the conversion scale
            float convertedGrade = (float) convertToGradeScale(finalGrade);
            subject.setFinal_grade(convertedGrade);

            // Add converted grade to total
            totalConvertedGrade += convertedGrade;
        }

        // Compute total GWA by averaging all converted grades
        float gwa = totalConvertedGrade / subjectCount;

        // Pass GWA to ComputationGwa Activity
        Intent intent = new Intent(ComputationPage.this, ComputationGwa.class);
        intent.putExtra("computed_GWA", gwa);  // Send GWA value to next activity
        startActivity(intent);
    }



    private double convertToGradeScale(double gwa) {
        if (gwa >= 97.5) return 1.00;
        if (gwa >= 94.5) return 1.25;
        if (gwa >= 91.5) return 1.50;
        if (gwa >= 88.5) return 1.75;
        if (gwa >= 85.5) return 2.00;
        if (gwa >= 81.5) return 2.25;
        if (gwa >= 77.5) return 2.50;
        if (gwa >= 73.5) return 2.75;
        if (gwa >= 69.5) return 3.00;
        return 5.00;
    }

    private void clearDatabase() {
        db.getSubjectDAO().deleteAllGrades();
        adapter.updateData(new ArrayList<>());
        root.recycleViewer.setAdapter(adapter);
        Toast.makeText(this, "All records cleared", Toast.LENGTH_SHORT).show();
    }
}