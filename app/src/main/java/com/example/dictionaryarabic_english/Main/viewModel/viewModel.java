package com.example.dictionaryarabic_english.Main.viewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.dictionaryarabic_english.Main.util.DBHelper;
import java.util.List;
public class viewModel extends ViewModel {
    private DBHelper dbHelper;
    public viewModel(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    public LiveData<List<String>> getWordsLiveData() {
        return dbHelper.getWordsLiveData();
    }
    public String getMeaning(String word) {
        return dbHelper.getMeaning(word);
    }
    public long insert(String word, String meaning) {
        return dbHelper.insert(word, meaning);
    }
}