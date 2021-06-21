package de.max.notenspiegel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;

import de.max.notenspiegel.R;

import de.max.notenspiegel.databinding.ActivityMainBinding;
import de.max.notenspiegel.dialog.AddSubjectDialog;
import de.max.notenspiegel.gui.SettingField;
import de.max.notenspiegel.structure.Setting;
import de.max.notenspiegel.structure.Subject;

import de.max.notenspiegel.gui.SubjectField;
import de.max.notenspiegel.util.Util;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;


import java.io.Serializable;
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
        if (getIntent().hasExtra(SubjectActivity.DELETE_SUBJECT)) {

            Serializable serializable = getIntent().getSerializableExtra(SubjectActivity.DELETE_SUBJECT);
            System.out.println("has delete subject " + serializable);
            removeSubject((Subject) serializable);
        } else if (getIntent().hasExtra(SubjectSettingsActivity.DEFAULT_SETTING)) {
            Serializable serializable = getIntent().getSerializableExtra(SubjectSettingsActivity.DEFAULT_SETTING);
            System.out.println("extra " + serializable);
        }

    }

    private void updateView() {
        linearLayout.removeAllViews();
        for (Subject subject : subjects) {
            linearLayout.addView(new SubjectField(this, subject));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        List<Subject> newList = Util.loadAll(KEY_KEY_NAMES, subjectPreferences, Subject.class);
        subjects.clear();
        subjectKeys.clear();
        for (Subject subject : newList) {
            addSubject(subject);
        }
        updateView();
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
        subjectKeys.add(subject.getKey());
        Util.save(subject, subjectPreferences);
        linearLayout.addView(new SubjectField(this, subject));
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
        subjectKeys.remove(subject.getKey());
        SharedPreferences.Editor edit = subjectPreferences.edit();
        edit.remove(subject.getKey());
        edit.apply();
        updateView();
        System.out.println("removed" + subject);

    }

    public boolean isNewIdentifier(String name) {
        if (name == null) {
            return false;
        }
        for (Subject subject : subjects) {
            if (name.equals(subject.getName())) {
                return false;
            }
        }
        return true;
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
            Intent intent = new Intent(this, SubjectSettingsActivity.class);
            intent.putExtra(SubjectSettingsActivity.DEFAULT_SETTING,
                    Setting.getDefaultSettings(this));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        if (id == R.id.reload) {
            for (Subject subject : subjects) {
                if (subject.getSetting() == null) {
                    subject.setSetting(Setting.getDefaultSettings(this));

                }
                subject.synchroniseSetting();
                Util.save(subject, subject.getKey(), subjectPreferences);
            }
            loadSubjects();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}