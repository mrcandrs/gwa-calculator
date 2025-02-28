package com.example.gradecalculator;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gradecalculator.databinding.ActivityComputationGwaBinding;

public class ComputationGwa extends AppCompatActivity {
    private ActivityComputationGwaBinding root;

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

        // Display the converted GWA inside parentheses
        root.txtConvertedGwa.setText(String.format("(%.2f)", preciseConvertedGwa));

        // Change the text color of GWA
        if (exactGwa > 89.50) {
            root.txtGwa.setTextColor(Color.BLUE);
        } else if (exactGwa < 69.50) {
            root.txtGwa.setTextColor(Color.RED);
        } else {
            root.txtGwa.setTextColor(Color.BLACK);
        }

        // Display the alert dialog and set text colors correctly
        if (preciseConvertedGwa <= 1.75) {
            root.txtConvertedGwa.setTextColor(Color.BLUE);  // Green for <= 1.75
            showCustomDialog(R.layout.dialog_sucess);  // Success Dialog
        } else if (preciseConvertedGwa <= 3.00) {
            root.txtConvertedGwa.setTextColor(Color.BLACK);  // Blue for <= 3.00
            showCustomDialog(R.layout.dialog_normal);  // Passed Dialog
        } else {
            root.txtConvertedGwa.setTextColor(Color.RED);  // Red for > 3.01
            showCustomDialog(R.layout.dialog_failed);  // Failed Dialog
        }

        root.btnAnotherCalculate.setOnClickListener(view -> {
            Intent intent = new Intent(ComputationGwa.this, HomePage.class);
            startActivity(intent);
            finish();
        });

        root.btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(ComputationGwa.this, ComputationPage.class);
            startActivity(intent);
            finish();
        });
    }

    // Function to show custom success, passed, or failed dialog
    private void showCustomDialog(int layoutId) {
        // Inflate the custom dialog layout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(layoutId, null);
        builder.setView(dialogView);

        // Create the dialog
        AlertDialog alertDialog = builder.create();

        // Find the OK button and set its click listener
        Button btnOkay = dialogView.findViewById(R.id.btnOkay);
        btnOkay.setOnClickListener(v -> alertDialog.dismiss());

        // Show the dialog
        alertDialog.show();
    }
}
