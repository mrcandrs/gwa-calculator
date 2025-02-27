package com.example.gradecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gradecalculator.databinding.ActivityComputationGwaBinding;

public class ComputationGwa extends AppCompatActivity {
    private TextView txtGwa;

    private ActivityComputationGwaBinding root;
    //SuperJ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityComputationGwaBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());


        // Get raw final grade and specific result from Intent
        float rawGwa = getIntent().getFloatExtra("RAW_GWA", 5.00f); // Default is 5.00
        float specificResult = getIntent().getFloatExtra("SPECIFIC_RESULT", 5.00f); // Default is 5.00

        // Display raw final grade in txtGwa
        root.txtGwa.setText(String.format("%.2f", rawGwa)); // Example: 95.75

        // Display specific result in txtConvertedGwa
        root.txtConvertedGwa.setText(String.format("(%.2f)", specificResult)); // Example: 1.68



        root.btnAnotherCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComputationGwa.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        root.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComputationGwa.this, ComputationPage.class);
                startActivity(intent);
                finish();
            }
        });
}
}
