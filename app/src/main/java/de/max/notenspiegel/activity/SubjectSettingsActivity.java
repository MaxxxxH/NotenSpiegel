package de.max.notenspiegel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.Serializable;

import de.max.notenspiegel.R;
import de.max.notenspiegel.gui.SettingField;
import de.max.notenspiegel.gui.SubjectField;
import de.max.notenspiegel.structure.Setting;
import de.max.notenspiegel.structure.Subject;
import de.max.notenspiegel.util.Util;

public class SubjectSettingsActivity extends AppCompatActivity {
    public static final String SUBJECT_SETTING = "subject_setting";
    public static final String DEFAULT_SETTING = "default_setting";
    private LinearLayout layout;
    private Subject subject;
    private Setting setting;

    private SettingField nameField;
    private SettingField maxPaperField;
    private SettingField goodField;
    private SettingField sufficientField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.taskbar_black));
        }
        layout = findViewById(R.id.settingsLayout);

        nameField = new SettingField(this, this.getString(R.string.subject_name), InputType.TYPE_CLASS_TEXT);
        maxPaperField = new SettingField(this, this.getString(R.string.paper_amount), InputType.TYPE_CLASS_NUMBER);
        goodField = new SettingField(this, this.getString(R.string.good_max_number), InputType.TYPE_CLASS_NUMBER);
        sufficientField = new SettingField(this, this.getString(R.string.good_medium_number), InputType.TYPE_CLASS_NUMBER);

        addAll(nameField, maxPaperField, goodField, sufficientField);

        if (getIntent().hasExtra(SUBJECT_SETTING)) {
            Serializable serializable = getIntent().getSerializableExtra(SUBJECT_SETTING);
            if (serializable instanceof Subject) {
                subject = (Subject) serializable;
            }
            System.out.println("has subject setting " + serializable);
        } else if (getIntent().hasExtra(DEFAULT_SETTING)) {
            Serializable serializable = getIntent().getSerializableExtra(DEFAULT_SETTING);
            if (serializable instanceof Setting) {
                setting = (Setting) serializable;
            }
            System.out.println("has default setting " + serializable);
        }
        if (this.setting != null) {
            loadSettings(this.setting);
        } else if (subject != null && this.subject.getSetting() != null) {
            loadSettings(this.subject.getSetting());
        }

        Button acceptButton = findViewById(R.id.settingsAcceptButton);
        acceptButton.setOnClickListener(acceptListener);

        findViewById(R.id.settingsCancelButton).setOnClickListener((v) -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(intent);
        });

    }


    private final View.OnClickListener acceptListener = v -> {
        String name = nameField.getValue();
        int maxPaper;
        int good;
        int sufficient;
        try {
            maxPaper = Integer.parseInt(maxPaperField.getValue());
            good = Integer.parseInt(goodField.getValue());
            sufficient = Integer.parseInt(sufficientField.getValue());
        } catch (NumberFormatException e) {
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        Setting newSetting = new Setting(name, maxPaper, good, sufficient);
        if (!newSetting.validGood()) {
            Toast.makeText(this, getString(R.string.error_max_value_invalid, goodField.getDescription()), Toast.LENGTH_LONG).show();
            return;
        }
        if (maxPaper < 0) {
            Toast.makeText(this, R.string.error_max_paper_smaller_than_zero, Toast.LENGTH_LONG).show();
            return;
        }
        if (!newSetting.validSufficient()) {
            Toast.makeText(this, getString(R.string.error_max_value_invalid, sufficientField.getDescription()), Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent;
        if (setting != null) {
            intent = new Intent(this, MainActivity.class);
            Util.save(newSetting, newSetting.getKey(), getSharedPreferences(Setting.KEY, MODE_PRIVATE));
        } else if (subject != null) {
            if (name.isEmpty()) {
                Toast.makeText(this, R.string.error_invalid_name, Toast.LENGTH_LONG);
                return;
            }
            subject.setSetting(newSetting);
            Util.save(subject, subject.getKey(), getSharedPreferences(Subject.PREFERENCES, MODE_PRIVATE));
            intent = new Intent(this, SubjectActivity.class);
            intent.putExtra(SubjectField.EXTRA_SUBJECT, this.subject.getKey());
        } else {
            intent = new Intent(this, MainActivity.class);
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(intent);
    };

    private void loadSettings(Setting setting) {
        if (setting != null) {
            nameField.setText(setting.getName());
            maxPaperField.setText(setting.getMaxPaper() + "");
            goodField.setText(setting.getGood() + "");
            sufficientField.setText(setting.getSufficient() + "");
        }
        if (this.setting != null) {
            removeAll(nameField, maxPaperField);
        }
    }

    private void removeAll(View... views) {
        for (View view : views) {
            layout.removeView(view);
        }
    }

    private void addAll(View... views) {
        for (View view : views) {
            layout.addView(view);
        }
    }

}