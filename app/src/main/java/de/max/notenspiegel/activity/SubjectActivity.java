package de.max.notenspiegel.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import java.io.Serializable;

import de.max.notenspiegel.R;
import de.max.notenspiegel.databinding.ActivitySubjectBinding;
import de.max.notenspiegel.dialog.AddPaperDialog;
import de.max.notenspiegel.gui.PaperField;
import de.max.notenspiegel.structure.Paper;
import de.max.notenspiegel.structure.Subject;
import de.max.notenspiegel.gui.SubjectField;
import de.max.notenspiegel.util.Util;

public class SubjectActivity extends AppCompatActivity {
    private static final String PAPER_SAVE_KEY = "paper_key";
    private ActivitySubjectBinding binding;
    private Subject subject;
    private LinearLayout layout;
    private TextView percentTextView;
    private TextView percentTotalTextView;
    private SharedPreferences subjectPreferences;

    public SubjectActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");

        binding = ActivitySubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        subjectPreferences = getSharedPreferences(Subject.PREFERENCES, MODE_PRIVATE);
        Serializable extra = getIntent().getSerializableExtra(SubjectField.EXTRA_SUBJECT);
        layout = findViewById(R.id.subjectActivityPaperLayout);

        if (!(extra instanceof Subject)) {
            return;
        }

        subject = (Subject) extra;
        Toolbar toolbar = findViewById(R.id.toolbar);
        Button addPaperButton = findViewById(R.id.buttonAddPaper);
        percentTextView = findViewById(R.id.subjectPercentTextView);
        percentTotalTextView = findViewById(R.id.subjectPercentTotalTextView);

        loadPaperFields();
        addPaperButton.setOnClickListener((v) -> {
            FragmentManager fm = getSupportFragmentManager();
            AddPaperDialog dialogFragment = new AddPaperDialog(this);
            dialogFragment.show(fm, "fragment_edit_name");
        });
        setSupportActionBar(toolbar);
        toolbar.setTitle(subject.getName());
        update();

    }

    public void update() {

        if (percentTextView == null || percentTotalTextView == null || subject == null) {
            return;
        }
        percentTextView.setText(subject.getPercent() + "%");
        percentTextView.setTextColor(Subject.getColor(subject.warnColor(subject.getPercent()), this));
        percentTotalTextView.setText(subject.getPercentTotal() + "%");
        percentTotalTextView.setTextColor(Subject.getColor(subject.warnColor(subject.getPercentTotal()), this));

    }

    private void updatePreferences() {
        Util.save(subject, subjectPreferences);
    }

    public int getNext() {
        return subject.getNextNumber();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null || !(savedInstanceState.getSerializable(PAPER_SAVE_KEY) instanceof Subject)) {
            return;
        }

        subject = (Subject) savedInstanceState.getSerializable(PAPER_SAVE_KEY);
        loadPaperFields();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        System.out.println("save");
        outState.putSerializable(PAPER_SAVE_KEY, (Serializable) subject);
        System.out.println("save" + outState.getSerializable(PAPER_SAVE_KEY));
        super.onSaveInstanceState(outState);
    }

    public void addPaper(Paper paper) {
        subject.addPaper(paper);
        layout.addView(new PaperField(this, paper, subject));
        update();
        updatePreferences();
    }

    public void removePaperField(PaperField paperField) {
        subject.removePaper(paperField.getPaper());
        layout.removeView(paperField);
        update();
        updatePreferences();
    }

    private void loadPaperFields() {
        for (Paper paper : subject.getIterablePapers()) {
            layout.addView(new PaperField(this, paper, subject));
        }
    }


}