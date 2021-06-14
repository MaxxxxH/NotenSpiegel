package de.max.notenspiegel.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;

import de.max.notenspiegel.R;
import de.max.notenspiegel.databinding.ActivityMainBinding;
import de.max.notenspiegel.dialog.AddSubjectDialog;
import de.max.notenspiegel.structure.Subject;
import de.max.notenspiegel.gui.SubjectField;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final List<Subject> subjects;
    private LinearLayout linearLayout;

    public MainActivity() {
        this.subjects = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Button addSubjectButton = findViewById(R.id.addSubjectButton);
        linearLayout = findViewById(R.id.subjectListLayout);

        addSubjectButton.setOnClickListener((v) -> {
            FragmentManager fm = getSupportFragmentManager();
            AddSubjectDialog dialogFragment = new AddSubjectDialog(this);
            dialogFragment.show(fm, "fragment_edit_name");
        });


        addSubject(new Subject("cooler name", 3));

    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
        linearLayout.addView(new SubjectField(this, subject));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}