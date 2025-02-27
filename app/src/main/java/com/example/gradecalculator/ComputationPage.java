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

        float totalFinalGrades = 0;  // Sum of raw grades
        float totalConvertedGrades = 0; // Sum of converted grades
        int totalSubjects = gradeList.size();

        for (SubjectClass subject : gradeList) {
            float finalGrade = subject.getFinal_grade();
            float convertedGrade = convertGWA(finalGrade);  // Convert individual grades

            totalFinalGrades += finalGrade;
            totalConvertedGrades += convertedGrade;
        }

        // Compute the raw GWA
        float rawGwa = totalFinalGrades / totalSubjects;

        // Compute the converted GWA (average of converted grades)
        float exactConvertedGwa = totalConvertedGrades / totalSubjects;

        // Pass values to ComputationGwa Activity
        Intent intent = new Intent(ComputationPage.this, ComputationGwa.class);
        intent.putExtra("RAW_GWA", rawGwa);
        intent.putExtra("EXACT_CONVERTED_GWA", exactConvertedGwa);
        startActivity(intent);
    }

    // Conversion function (based on predefined ranges)
    private float convertGWA(float gwa) {
        if (gwa >= 97.5) return 1.00f;
        if (gwa >= 94.5) return 1.25f;
        if (gwa >= 91.5) return 1.50f;
        if (gwa >= 88.5) return 1.75f;
        if (gwa >= 85.5) return 2.00f;
        if (gwa >= 81.5) return 2.25f;
        if (gwa >= 77.5) return 2.50f;
        if (gwa >= 73.5) return 2.75f;
        if (gwa >= 69.5) return 3.00f;
        return 5.00f;  // Failing grade
    }

    private void clearDatabase() {
        db.getSubjectDAO().deleteAllGrades();
        adapter.updateData(new ArrayList<>());
        root.recycleViewer.setAdapter(adapter);
        Toast.makeText(this, "All records cleared", Toast.LENGTH_SHORT).show();
    }
}
