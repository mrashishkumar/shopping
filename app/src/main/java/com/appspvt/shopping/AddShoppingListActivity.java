package com.appspvt.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class AddShoppingListActivity extends AppCompatActivity {

    private TextInputEditText itemTextInput, quantityTextInput;
    private Spinner unitsSpinner;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);

        databaseReference = FirebaseDatabase.getInstance().getReference("Shopping_List");

        // Initializing view
        itemTextInput = findViewById(R.id.item);
        quantityTextInput = findViewById(R.id.quantity);
        unitsSpinner = findViewById(R.id.unitSpinner);
        Button addItemButton = findViewById(R.id.addItemButton);

        // Units
        final ArrayList<String> UnitList = new ArrayList<>();
        UnitList.add(0, "");
        UnitList.add(1, "Kilogram(kg)");
        UnitList.add(2, "Gram(g)");
        UnitList.add(3, "Milligram(mg)");
        UnitList.add(4, "Liter(l)");
        UnitList.add(5, "Milliliter(ml)");

        ArrayAdapter<String> unitsArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, UnitList);
        unitsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitsSpinner.setAdapter(unitsArrayAdapter);
        unitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    private void addItem(){
        String itemName = Objects.requireNonNull(itemTextInput.getText()).toString().trim();
        String quantity = Objects.requireNonNull(quantityTextInput.getText()).toString().trim();
        String unit = unitsSpinner.getSelectedItem().toString().trim();

        if (itemName.isEmpty()) {
            itemTextInput.setError("Please! Enter Item's Name");
            itemTextInput.requestFocus();
            return;
        }

        if (quantity.isEmpty()) {
            quantityTextInput.setError("Please! Enter Item's Quantity");
            quantityTextInput.requestFocus();
            return;
        }

        ShoppingItem shoppingItem = new ShoppingItem(itemName, quantity+"  "+unit);

        // Getting upload ID.
        final String uploadId = databaseReference.push().getKey();
        //save customer info to firebase database
        assert uploadId != null;
        databaseReference.child(uploadId).setValue(shoppingItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(AddShoppingListActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    AddShoppingListActivity.this.finish();
                } else {
                    // display error message
                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
