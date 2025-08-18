package com.example.dictionaryarabic_english.Main.ui;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.dictionaryarabic_english.Main.util.DBHelper;
import com.example.dictionaryarabic_english.Main.util.FactoryViewModel;
import com.example.dictionaryarabic_english.Main.viewModel.viewModel;
import com.example.dictionaryarabic_english.R;
import com.example.dictionaryarabic_english.core.database.DictionaryDatabase;
import com.example.dictionaryarabic_english.databinding.FragmentMainBinding;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private DBHelper dbHelper;
    private FactoryViewModel factoryViewModel;
    private viewModel vm;
    private ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        dbHelper = new DBHelper(requireContext());
        factoryViewModel = new FactoryViewModel(dbHelper);
        vm = new ViewModelProvider(this, factoryViewModel).get(viewModel.class);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        vm.getWordsLiveData().observe( getViewLifecycleOwner(), words -> {
            String[] wordsArray = words.toArray(new String[0]);
            adapter.clear();
            adapter.addAll(wordsArray);
            binding.autoTextView.setAdapter(adapter);
            binding.autoTextView.setThreshold(1);
        });
        binding.btnAddition.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_navigation_main_to_navigation_adding_word);
        });
        binding.autoTextView.setOnItemClickListener((parent, view1, position, id) -> {
            String word = parent.getItemAtPosition(position).toString();
            showMeaning(word);
        });
        binding.autoTextView.setOnEditorActionListener((v1, actionId, event) -> {
            String word = binding.autoTextView.getText().toString().trim();
            if (!word.isEmpty()) {
                showMeaning(word);
            }
            return true;
        });
    }
    private void showMeaning(String word) {
        String meaning = vm.getMeaning(word);
        if (meaning != null) {
            binding.tvMeaning.setText(meaning);
        } else {
            binding.tvMeaning.setText("Word not found!");
            Toast.makeText(requireContext(), "Word not found!", Toast.LENGTH_SHORT).show();
        }
        binding.autoTextView.setText("");
    }
}