package com.example.dictionaryarabic_english.Main.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dictionaryarabic_english.Main.util.DBHelper;
import com.example.dictionaryarabic_english.Main.util.FactoryViewModel;
import com.example.dictionaryarabic_english.Main.viewModel.viewModel;
import com.example.dictionaryarabic_english.R;
import com.example.dictionaryarabic_english.databinding.FragmentAddingWordBinding;


public class AddingWordFragment extends Fragment {
    private FragmentAddingWordBinding binding;
    private DBHelper dbHelper;
    private FactoryViewModel factoryViewModel;
    private viewModel vm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddingWordBinding.inflate(inflater, container, false);
        dbHelper = new DBHelper(requireContext());
        factoryViewModel = new FactoryViewModel(dbHelper);
        vm = new ViewModelProvider(this, factoryViewModel).get(viewModel.class);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_navigation_adding_word_to_navigation_main);
        });
        binding.btnAdd.setOnClickListener(v -> {
            String english = binding.etEnglish.getText().toString().trim();
            String arabic = binding.etArabic.getText().toString().trim();
            if(english.isEmpty() || arabic.isEmpty()){
                if(english.isEmpty()){
                    binding.etEnglish.setError("Enter English Word");
                }
                if(arabic.isEmpty()){
                    binding.etArabic.setError("Enter Arabic Meaning");
                }
                return;
            }
            if(vm.insert(english, arabic)!=-1){
                Toast.makeText(requireContext(), "Word added successfully", Toast.LENGTH_SHORT).show();
                binding.etEnglish.setText("");
                binding.etArabic.setText("");
            }else{
                Toast.makeText(requireContext(), "Error adding word", Toast.LENGTH_SHORT).show();
            }
        });
    }
}