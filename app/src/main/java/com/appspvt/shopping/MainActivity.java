package com.appspvt.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<ShoppingItem> shoppingList = new ArrayList<>();
        final ShoppingArrayAdapter shoppingArrayAdapter = new ShoppingArrayAdapter(this, shoppingList);
        final ListView listView = findViewById(R.id.shoppingListView);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Shopping_List");
        //adding an event listener to fetch values
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);

                //iterating through all the values in database
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ShoppingItem shoppingItem = dataSnapshot.getValue(ShoppingItem.class);
                    shoppingList.add(shoppingItem);
                }
                //adding adapter to listView
                listView.setAdapter(shoppingArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });

        FloatingActionButton addShoppingItem = findViewById(R.id.addShoppingListButton);
        addShoppingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddShoppingListActivity.class);
                startActivity(intent);
            }
        });

    }
}
