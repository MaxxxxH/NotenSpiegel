package de.max.notenspiegel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;

import de.max.notenspiegel.databinding.ActivitySubjectBinding;
import de.max.notenspiegel.structure.Subject;
import de.max.notenspiegel.gui.SubjectField;

public class SubjectActivity extends AppCompatActivity {

    private ActivitySubjectBinding binding;
    private Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Serializable extra = getIntent().getSerializableExtra(SubjectField.EXTRA_SUBJECT);
        TextView textView = findViewById(R.id.textView4);
        textView.setText(extra == null ? "null" : extra.toString());
        if (!(extra instanceof Subject)) {
            return;

        }
        subject = (Subject) extra;
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(subject.getName());


    }
}