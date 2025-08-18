package com.example.dictionaryarabic_english.Main.util;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.example.dictionaryarabic_english.core.database.DictionaryDatabase;
import java.util.List;
import java.util.concurrent.Executors;
public class DBHelper {
    private final DictionaryDatabase dictionaryDatabase;
    private final MutableLiveData<List<String>> wordsLiveData = new MutableLiveData<>();
    public DBHelper(Context context) {
        dictionaryDatabase = new DictionaryDatabase(context);
        loadWords();
    }
    private void loadWords() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<String> words = dictionaryDatabase.getAllWords();
            wordsLiveData.postValue(words);
        });
    }
    public MutableLiveData<List<String>> getWordsLiveData() {
        return wordsLiveData;
    }
    public String getMeaning(String word) {
        return dictionaryDatabase.getMeaning(word);
    }
    public long insert(String word, String meaning) {
        long result = dictionaryDatabase.insert(word, meaning);
        if(result > 0){
            loadWords();
        }
        return result;
    }
}