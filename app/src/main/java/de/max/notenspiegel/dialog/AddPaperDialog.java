package de.max.notenspiegel.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import de.max.notenspiegel.R;
import de.max.notenspiegel.activity.SubjectActivity;
import de.max.notenspiegel.structure.Paper;

/**
 * This Dialog lets the user add a new paper to a subject.
 *
 * @author Max Hecht.
 * @version 1.0
 */
public class AddPaperDialog extends DialogFragment {
    private final SubjectActivity subjectActivity;

    public AddPaperDialog() {
        super();
        subjectActivity = null;
    }

    public AddPaperDialog(SubjectActivity subjectActivity) {
        super();
        this.subjectActivity = subjectActivity;
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_paper_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert (getDialog() != null);
        Button apply = view.findViewById(R.id.applyButtonDialogAddPaper);
        Button cancel = view.findViewById(R.id.cancelButtonDialogAddPaper);

        TextView maxTextView = view.findViewById(R.id.maxPointTextDialogAddPaper);
        TextView pointTextView = view.findViewById(R.id.reachedPointTextDialogAddPaper);
        apply.setOnClickListener((v) -> {
            if (subjectActivity == null) {
                getDialog().cancel();
                return;
            }
            String maxPointText = maxTextView.getText().toString();
            String reachedPointText = pointTextView.getText().toString();
            System.out.println(maxPointText + "re" + reachedPointText);
            if (maxPointText.isEmpty() || reachedPointText.isEmpty()) {
                Toast.makeText(view.getContext(), getString(R.string.field_empty, "Punktzahl"), Toast.LENGTH_SHORT).show();
                return;
            }
            double maxValue = getValue(maxTextView);

            if (maxValue < 1) {
                Toast.makeText(view.getContext(), R.string.error_max_value_smaller_than_one, Toast.LENGTH_SHORT).show();
                return;
            }
            Paper paper = new Paper(getValue(pointTextView), maxValue, subjectActivity.getNext());
            subjectActivity.addPaper(paper);
            getDialog().cancel();

        });
        cancel.setOnClickListener((v) -> {
            getDialog().cancel();
        });
    }

    private double getValue(TextView editText) {
        try {
            return Double.parseDouble(editText.getText().toString());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
