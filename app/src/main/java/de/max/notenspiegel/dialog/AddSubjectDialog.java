package de.max.notenspiegel.dialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import de.max.notenspiegel.R;
import de.max.notenspiegel.structure.Subject;
import de.max.notenspiegel.activity.MainActivity;

public class AddSubjectDialog extends DialogFragment {
    private final MainActivity mainActivity;

    public AddSubjectDialog() {
        super();
        mainActivity = null;
    }

    public AddSubjectDialog(MainActivity mainActivity) {
        super();
        this.mainActivity = mainActivity;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_subject_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert (getDialog() != null);
        Button apply = view.findViewById(R.id.confirmDialogButton);
        Button cancel = view.findViewById(R.id.dialogCancelButton);
        TextView nameTextView = view.findViewById(R.id.addSubjectDialogName);
        EditText maxAmountTextView = view.findViewById(R.id.addSubjectDialogNumber);
        apply.setOnClickListener((v) -> {
            if (mainActivity == null) {
                return;
            }
            int maxAmount = getValue(maxAmountTextView);
            Subject subject = new Subject(nameTextView.getText().toString(), maxAmount >= 0 ? maxAmount : 0);
            mainActivity.addSubject(subject);
            getDialog().cancel();
        });
        cancel.setOnClickListener((v) -> {
            getDialog().cancel();
        });
    }

    private int getValue(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
