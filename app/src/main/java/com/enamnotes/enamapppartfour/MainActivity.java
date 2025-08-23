package com.enamnotes.enamapppartfour;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private EditText participantNameEdit;
    private Button addparticipantBtn;
    private DBHandler dbHandler;

    private TextView tvDob;

    private String selectedDob = "";
    private Spinner occupationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        participantNameEdit = findViewById(R.id.idParticipantName);
        addparticipantBtn = findViewById(R.id.idParticipantBtn);
        // Step 3: Reference RadioGroup and get selected gender
        RadioGroup rgGender = findViewById(R.id.rgGender);
        tvDob = findViewById(R.id.tvDob);
        occupationSpinner = findViewById(R.id.spinnerOccupation);

        dbHandler = new DBHandler(MainActivity.this);

        // Set up Occupation spinner
        ArrayAdapter<CharSequence> occAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.occupations_array,
                android.R.layout.simple_spinner_item
        );
        occAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occupationSpinner.setAdapter(occAdapter);
        

        tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show DatePickerDialog
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        (view, year1, month1, dayOfMonth) -> {
                            // Format date as yyyy-MM-dd
                            selectedDob = year1 + "-" + String.format("%02d", month1 + 1) + "-" + String.format("%02d", dayOfMonth);
                            tvDob.setText(selectedDob);
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        addparticipantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1️⃣ Get input from EditText
                String participantName = participantNameEdit.getText().toString();
                int selectedId = rgGender.getCheckedRadioButtonId();

                String gender = null;
                if (selectedId == R.id.rbMale) gender = "Male";
                else if (selectedId == R.id.rbFemale) gender = "Female";

                String occupation = occupationSpinner.getSelectedItem() != null
                        ? occupationSpinner.getSelectedItem().toString()
                        : "";

                //Validate input
                if (participantName.isEmpty() || gender == null  || selectedDob.isEmpty()|| occupation.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Do DB or action call
                dbHandler.addParticipant(participantName, gender,  selectedDob, occupation);

                // Show success message to user
                Toast.makeText(MainActivity.this,"Participant Added Successfully", Toast.LENGTH_SHORT).show();

                participantNameEdit.setText("");
                rgGender.clearCheck();
                tvDob.setText("Select Date");
                selectedDob = ""; 
                occupationSpinner.setSelection(0);
                


            }
        });
        }




    }
