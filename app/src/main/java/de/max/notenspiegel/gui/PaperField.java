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

/**
 * An display Slot of paper object.
 *
 * @author Max Hecht
 * @version 1.0
 */
public class PaperField extends ConstraintLayout {
    private final Paper paper;
    private final Subject mother;
    private final TextView paperNumberTextView;
    private final TextView percentTextView;
    private final TextView proportionTextView;

    public PaperField(Context context) {
        this(context, null, null);
    }

    public PaperField(@NonNull @NotNull Context context, Paper paper, Subject mother) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.paper_field_layout, this, true);

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
        percentTextView.setTextColor(mother.warnColor(paper.getPercent()).getColor( getContext()));
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
