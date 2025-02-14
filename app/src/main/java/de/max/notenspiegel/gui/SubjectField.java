package de.max.notenspiegel.gui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import de.max.notenspiegel.R;
import de.max.notenspiegel.activity.SubjectActivity;
import de.max.notenspiegel.structure.Setting;
import de.max.notenspiegel.structure.Subject;

public class SubjectField extends ConstraintLayout {
    public static final String EXTRA_SUBJECT = "subject";
    private final Subject subject;
    private final TextView nameTextView;
    private final TextView maxValueTextView;
    private final TextView percentageTextView;

    public SubjectField(Context context) {
        this(context, new Subject("", 6, Setting.DEFAULT));
    }

    public SubjectField(Context context, Subject subject) {
        super(context);
        this.subject = subject;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.subject_field_name, this, true);

        this.nameTextView = findViewById(R.id.subjectNameTextView);
        this.maxValueTextView = findViewById(R.id.maxAmountTextView);
        this.percentageTextView = findViewById(R.id.percentTextView);

        this.setOnClickListener((v) -> {
            Intent intent = new Intent(context, SubjectActivity.class);
            intent.putExtra(EXTRA_SUBJECT, this.subject.getKey());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
            if (context instanceof Activity) {
                ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        update();
    }

    public void update() {
        nameTextView.setText(subject.getName());
        maxValueTextView.setText(subject.getMaxAmount() + "");
        percentageTextView.setText(subject.getPercent() + "%");
        percentageTextView.setTextColor(subject.warnColor(subject.getPercent()).getColor( getContext()));
    }

}
