package de.max.notenspiegel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import de.max.notenspiegel.R;
import de.max.notenspiegel.databinding.ActivitySubjectBinding;
import de.max.notenspiegel.dialog.AddPaperDialog;
import de.max.notenspiegel.dialog.YesNoDialog;
import de.max.notenspiegel.gui.PaperField;
import de.max.notenspiegel.gui.SubjectField;
import de.max.notenspiegel.structure.Paper;
import de.max.notenspiegel.structure.Subject;
import de.max.notenspiegel.util.Util;

/**
 * This activity displays the papers of a subject.
 *
 * @author Max Hecht
 * @version 1.0
 */
public class SubjectActivity extends AppCompatActivity {
    public static final String DELETE_SUBJECT = "delete_subject_extra";
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
        String extraKey = getIntent().getStringExtra(SubjectField.EXTRA_SUBJECT);
        layout = findViewById(R.id.subjectActivityPaperLayout);

        if (extraKey == null) {
            return;
        }

        subject = Util.load(extraKey, subjectPreferences, Subject.class);
        if (subject == null) {
            return;
        }
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

        findViewById(R.id.subjectDeleteImageButton).setOnClickListener((v) -> {
            FragmentManager fm = getSupportFragmentManager();
            YesNoDialog dialogFragment = new YesNoDialog("Bitte bestätigen", null, null, () -> {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(DELETE_SUBJECT, this.subject);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                finish();
            });
            dialogFragment.show(fm, "fragment_delete");

        });
        setSupportActionBar(toolbar);
        toolbar.setTitle(subject.getName());
        update();
        System.out.println("create end");

    }

    /**
     * Update the percent views.
     */
    public void update() {

        if (percentTextView == null || percentTotalTextView == null || subject == null) {
            return;
        }
        percentTextView.setText(" " + subject.getPercent() + "% ");
        percentTextView.setTextColor(subject.warnColor(subject.getPercent()).getColor(this));
        percentTotalTextView.setText(" " + subject.getPercentTotal() + "% ");
        percentTotalTextView.setTextColor(subject.warnColor(subject.getPercentTotal()).getColor(this));
        save();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Saves the current state to preferences.
     */
    private void save() {
        Util.save(subject, subjectPreferences);
    }

    /**
     * Returns the number of the next paper.
     *
     * @return the number of the next paper.
     */
    public int getNext() {
        return subject.getNextNumber();
    }

    /**
     * Adds a paper to the display and to saves it.
     *
     * @param paper the paper getting added.
     */
    public void addPaper(Paper paper) {
        subject.addPaper(paper);
        layout.addView(new PaperField(this, paper, subject));
        update();
        save();
    }

    /**
     * Removes a paper.
     */
    public void removePaperField(PaperField paperField) {
        subject.removePaper(paperField.getPaper());
        layout.removeView(paperField);
        update();
        save();
    }

    /**
     * Adds saved papers to the display.
     */
    private void loadPaperFields() {
        for (Paper paper : subject.getIterablePapers()) {
            layout.addView(new PaperField(this, paper, subject));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SubjectSettingsActivity.class);
            intent.putExtra(SubjectSettingsActivity.SUBJECT_SETTING,
                    subject);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}