package com.example.froggerclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.froggerclone.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(view1 -> {
            String name = binding.nameField.getText().toString();
            int difficulty = binding.difficultyRadioGroup.getCheckedRadioButtonId();
            int sprite = binding.spriteRadioGroup.getCheckedRadioButtonId();

            // Match the difficulty to the ID of the buttons to get an easy-to-use difficulty int.
            if (difficulty == binding.optionEasy.getId()) {
                difficulty = 1;
            } else if (difficulty == binding.optionMedium.getId()) {
                difficulty = 2;
            } else if (difficulty == binding.optionHard.getId()) {
                difficulty = 3;
            }

            // Match the difficulty to the ID of the buttons to get an easy-to-use integer sprite.
            if (sprite == binding.optionSprite1.getId()) {
                sprite = 1;
            } else if (sprite == binding.optionSprite2.getId()) {
                sprite = 2;
            } else if (sprite == binding.optionSprite3.getId()) {
                sprite = 3;
            }

            if (!name.trim().isEmpty() && difficulty != -1 && sprite != -1) {
                // Create a bundle with the configuration info to send to the GameScreen.
                Bundle b = new Bundle();
                b.putInt("difficulty", difficulty);
                b.putInt("sprite", sprite);
                b.putString("name", name);
                Intent intent = new Intent(getActivity(), GameScreen.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}