package de.max.notenspiegel.gui;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.jetbrains.annotations.NotNull;

import de.max.notenspiegel.R;
import de.max.notenspiegel.activity.SubjectActivity;
import de.max.notenspiegel.structure.Paper;
import de.max.notenspiegel.structure.Subject;

public class PaperField extends ConstraintLayout {
    private static final String PAPER_NUMBER = "paper n.%d";
    private final Paper paper;
    private final Subject mother;
    private int number;
    private final TextView paperNumberTextView;
    private final TextView percentTextView;
    private final TextView proportionTextView;
    private Resources resources;

    public PaperField(Context context) {
        this(context, null, null);
    }

    public PaperField(@NonNull @NotNull Context context, Paper paper, Subject mother) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.paper_layout, this, true);

        this.paper = paper;
        this.mother = mother;
        paperNumberTextView = findViewById(R.id.paperNumberTextView);
        percentTextView = findViewById(R.id.textViewPaperPercent);
        proportionTextView = findViewById(R.id.textViewPaperNumber);

        if (!(context instanceof SubjectActivity)) {
            return;
        }
        SubjectActivity subjectActivity = (SubjectActivity) context;
        ImageButton button = findViewById(R.id.removePaperImageButton);
        button.setOnClickListener(v -> {
            subjectActivity.removePaperField(this);
        });
        update();
    }

    public void update() {
        paperNumberTextView.setText(String.format(getResources().getString(R.string.paper_number), paper.getNumber()));
        System.out.println(getResources().getString(R.string.paper_number));
        percentTextView.setText(paper.getPercent() + "%");
        percentTextView.setTextColor(Subject.getColor(mother.warnColor(paper.getPercent()), getContext()));
        proportionTextView.setText(String.format(getResources().getString(R.string.paper_proportion), paper.getActualPoints(), paper.getMaxPoint()));

    }


    public Paper getPaper() {
        return paper;
    }

    @Override
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
    }
}
