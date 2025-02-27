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

        float totalFinalGrades = 0;
        int totalSubjects = gradeList.size();

        for (SubjectClass subject : gradeList) {
            totalFinalGrades += subject.getFinal_grade();  // Raw final grades (e.g., 99.00, 95.75, etc.)
        }

        float rawGwa = totalFinalGrades / totalSubjects; // Example: 95.75
        float specificResult = calculateSpecificResult(rawGwa); // Example: 1.68

        // Pass both values to ComputationGwa
        Intent intent = new Intent(ComputationPage.this, ComputationGwa.class);
        intent.putExtra("RAW_GWA", rawGwa);         // Send raw final grade (e.g., 95.75)
        intent.putExtra("SPECIFIC_RESULT", specificResult); // Send specific result (e.g., 1.68)
        startActivity(intent);
    }

    private float calculateSpecificResult(float rawGwa) {
        return 1.00f + ((100.0f - rawGwa) / 20.0f);
    }




    private void clearDatabase() {
        db.getSubjectDAO().deleteAllGrades();
        adapter.updateData(new ArrayList<>());
        root.recycleViewer.setAdapter(adapter);
        Toast.makeText(this, "All records cleared", Toast.LENGTH_SHORT).show();
    }
}