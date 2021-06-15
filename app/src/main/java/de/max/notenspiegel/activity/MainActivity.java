package de.max.notenspiegel.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;

import de.max.notenspiegel.R;

import de.max.notenspiegel.dialog.AddSubjectDialog;
import de.max.notenspiegel.databinding.ActivityMainBinding;
import de.max.notenspiegel.structure.Subject;

import de.max.notenspiegel.gui.SubjectField;
import de.max.notenspiegel.util.Util;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_KEY_NAMES = "keys";
    private ActivityMainBinding binding;
    private List<Subject> subjects;
    private Set<String> subjectKeys;
    private LinearLayout linearLayout;
    private SharedPreferences subjectPreferences;

    public MainActivity() {
        this.subjects = new ArrayList<>();
        subjectKeys = new HashSet<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Button addSubjectButton = findViewById(R.id.addSubjectButton);
        linearLayout = findViewById(R.id.subjectListLayout);
        subjectPreferences = getSharedPreferences(Subject.PREFERENCES, MODE_PRIVATE);
        addSubjectButton.setOnClickListener((v) -> {
            FragmentManager fm = getSupportFragmentManager();
            AddSubjectDialog dialogFragment = new AddSubjectDialog(this);
            dialogFragment.show(fm, "fragment_edit_name");
        });


        loadSubjects();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Util.setKeys(subjectKeys, KEY_KEY_NAMES, subjectPreferences);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Util.setKeys(subjectKeys, KEY_KEY_NAMES, subjectPreferences);
    }

    private void loadSubjects() {
        if (subjects == null || subjects.size() <= 0) {
            List<Subject> newList = Util.loadAll(KEY_KEY_NAMES, subjectPreferences, Subject.class);
           for (Subject subject: newList){
               addSubject(subject);
           }
        }
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
        //TextView spacer = new TextView(this);
        //spacer.setHeight(2);
        subjectKeys.add(subject.getKey());
        Util.save(subject, subjectPreferences);
        linearLayout.addView(new SubjectField(this, subject));
        System.out.println(subject);
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