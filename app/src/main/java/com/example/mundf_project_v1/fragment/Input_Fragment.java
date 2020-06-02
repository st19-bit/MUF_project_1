package com.example.mundf_project_v1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mundf_project_v1.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Input_Fragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.input_fragment,container,false);

        final EditText eingabe_v = v.findViewById(R.id.ziel_eingabe);
        final Button start = v.findViewById(R.id.start);
        final Button stop = v.findViewById(R.id.stop);


        return v;
    }
}
