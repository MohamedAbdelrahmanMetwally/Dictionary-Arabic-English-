package com.example.dictionaryarabic_english;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView autoTextView;
    private TextView tvMeaning;
    private Button btnAddition;
    private DictionaryDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoTextView = findViewById(R.id.autoTextView);
        tvMeaning = findViewById(R.id.tvMeaning);
        btnAddition = findViewById(R.id.btnAddition);
        db = new DictionaryDatabase(this);
        loadWords();
        autoTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedWord = parent.getItemAtPosition(position).toString();
            String meaning = db.getMeaning(selectedWord);
            if (meaning != null && !meaning.isEmpty()) {
                tvMeaning.setText(meaning);
            } else {
                tvMeaning.setText("Meaning not found");
            }
        });
        btnAddition.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddingWord.class));
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadWords();
    }
    private void loadWords() {
        List<String> words = db.getAllWords();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, words);
        autoTextView.setAdapter(adapter);
        autoTextView.setThreshold(1);
    }
}