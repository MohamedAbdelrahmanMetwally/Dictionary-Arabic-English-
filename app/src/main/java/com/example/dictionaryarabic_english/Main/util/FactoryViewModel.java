package com.example.dictionaryarabic_english.Main.util;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.dictionaryarabic_english.Main.viewModel.viewModel;

public class FactoryViewModel implements ViewModelProvider.Factory {
    private DBHelper dbHelper;
    public FactoryViewModel(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(viewModel.class)) {
            return (T) new viewModel(dbHelper);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
