package com.example.koszty_podrozy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    EditText distanceET, consumptionET, passengersET;
    RadioGroup fuelRG;
    Button resultBtn;
    TextView fullCostTV, costPerPassengerTV, errorTV;

    HashMap<Integer, Float> btnIdPrice = new HashMap<>() {{
        put(R.id.benzyna, 6.50f);
        put(R.id.diesel, 6.70f);
        put(R.id.lpg, 3.10f);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        distanceET = findViewById(R.id.distance);
        consumptionET = findViewById(R.id.consumption);
        passengersET = findViewById(R.id.passengers);
        fuelRG = findViewById(R.id.fuel);
        resultBtn = findViewById(R.id.resultBtn);
        fullCostTV = findViewById(R.id.fullCost);
        costPerPassengerTV = findViewById(R.id.costPerPassenger);
        errorTV = findViewById(R.id.error);
        resultBtn.setOnClickListener(new BtnHandler());
    }

    private static String getValueFromEdit(EditText e) {
        return e.getText().toString();
    }

    private class BtnHandler implements View.OnClickListener {
        int distance, consumption, passengers;
        @Override
        public void onClick(View v) {
            try {
                validate();
            } catch (NumberFormatException err) {
                errorTV.setText("Błędne Dane!");
                return;
            }
            errorTV.setText("");
            int checkedId = fuelRG.getCheckedRadioButtonId();
            float fuelCostPerL = btnIdPrice.get(checkedId);
            float fullCost = (fuelCostPerL / 100) * consumption * distance;
            float costPerPassenger = fullCost / passengers;
            fullCostTV.setText("Całkowity kosz: " + fullCost + "zł");
            costPerPassengerTV.setText("Na osobę: " + costPerPassenger + "zł");
        }

        private void validate() throws NumberFormatException {
            distance = Integer.parseInt(getValueFromEdit(distanceET));
            consumption = Integer.parseInt(getValueFromEdit(consumptionET));
            passengers = Integer.parseInt(getValueFromEdit(passengersET));
            if (distance <= 0 || consumption <= 0 || passengers <= 0)
                throw new NumberFormatException();
        }
    }
}

