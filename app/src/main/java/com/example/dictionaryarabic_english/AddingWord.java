package com.example.dictionaryarabic_english;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class AddingWord extends AppCompatActivity {
    Button btnBack, btnAdd;
    EditText etEnglish, etArabic;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adding_word);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        etEnglish = findViewById(R.id.etEnglish);
        etArabic = findViewById(R.id.etArabic);
        btnBack.setOnClickListener(v -> finish());
        btnAdd.setOnClickListener(v -> {
            String word = etEnglish.getText().toString().trim();
            String meaning = etArabic.getText().toString().trim();
            if (TextUtils.isEmpty(word) || TextUtils.isEmpty(meaning)) {
                Toast.makeText(this, "Please enter both English and Arabic word", Toast.LENGTH_SHORT).show();
                return;
            }
            DictionaryDatabase db = new DictionaryDatabase(this);
            db.insert(word, meaning);
            Toast.makeText(this, "Word added successfully ✅", Toast.LENGTH_SHORT).show();
            etEnglish.setText("");
            etArabic.setText("");
            finish();
        });
    }
}