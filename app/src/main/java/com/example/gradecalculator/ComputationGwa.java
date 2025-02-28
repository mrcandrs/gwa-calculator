package com.example.gradecalculator;

import android.content.Intent;
import android.graphics.Color;
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



        // Get values from Intent
        float exactGwa = getIntent().getFloatExtra("RAW_GWA", 1.00f);
        float preciseConvertedGwa = getIntent().getFloatExtra("EXACT_CONVERTED_GWA", 1.00f);

        // Display the exact GWA normally
        root.txtGwa.setText(String.format("%.2f", exactGwa));

        // Display the **EXACT CONVERTED GRADE (not table-based) inside parentheses**
        root.txtConvertedGwa.setText(String.format("(%.2f)", preciseConvertedGwa));


        // Set the status text and color based on the GWA
        if (exactGwa < 69.50f) {
            root.txtStatus.setText("Your GWA is below the passing threshold.");
            root.txtStatus.setTextColor(Color.RED); // Set text color to red for failing grades
        } else {
            root.txtStatus.setText("Congratulations! Your GWA is passed.");
            root.txtStatus.setTextColor(Color.GREEN); // Set text color to green for passing grades
        }


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
