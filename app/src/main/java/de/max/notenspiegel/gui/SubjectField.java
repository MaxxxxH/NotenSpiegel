package de.max.notenspiegel.gui;


import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import de.max.notenspiegel.R;

public class SubjectField extends ConstraintLayout {
    private final Subject subject;
    private final TextView nameTextView;
    private final TextView maxValueTextView;
    private final TextView percentageTextView;

    public SubjectField(Context context, Subject subject) {
        super(context);
        this.subject = subject;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.subject_field, this, true);

        this.nameTextView = findViewById(R.id.subjectNameTextView);
        this.maxValueTextView = findViewById(R.id.maxAmountTextView);
        this.percentageTextView = findViewById(R.id.percentTextView);
        update();
    }

    public void update() {
        nameTextView.setText(subject.getName());
        maxValueTextView.setText(subject.getMaxAmount() + "");
        percentageTextView.setText(subject.getPercent() + "%");
    }

}
