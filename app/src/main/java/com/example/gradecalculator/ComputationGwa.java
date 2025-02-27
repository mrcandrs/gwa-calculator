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


        // Get values from Intent
        float exactGwa = getIntent().getFloatExtra("RAW_GWA", 1.00f);
        float preciseConvertedGwa = getIntent().getFloatExtra("EXACT_CONVERTED_GWA", 1.00f);

        // Display the exact GWA normally
        root.txtGwa.setText(String.format("%.2f", exactGwa));

        // Display the **EXACT CONVERTED GRADE (not table-based) inside parentheses**
        root.txtConvertedGwa.setText(String.format("(%.2f)", preciseConvertedGwa));



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
