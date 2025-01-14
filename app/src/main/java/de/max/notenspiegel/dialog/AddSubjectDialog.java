package de.max.notenspiegel.dialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import de.max.notenspiegel.R;
import de.max.notenspiegel.structure.Setting;
import de.max.notenspiegel.structure.Subject;
import de.max.notenspiegel.activity.MainActivity;
import de.max.notenspiegel.util.Util;

/**
 * This Dialog lets the user add a new Subject.
 *
 * @author Max Hecht
 * @version 1.0
 */
public class AddSubjectDialog extends DialogFragment {
    private final MainActivity mainActivity;
    private SharedPreferences preferences;

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
        //load views
        Button apply = view.findViewById(R.id.confirmDialogButton);
        Button cancel = view.findViewById(R.id.dialogCancelButton);
        TextView nameTextView = view.findViewById(R.id.addSubjectDialogName);
        EditText maxAmountTextView = view.findViewById(R.id.addSubjectDialogNumber);
        Setting defaultSettings = Setting.getDefaultSettings(view.getContext());
        maxAmountTextView.setText("" + defaultSettings.getMaxPaper());
        apply.setOnClickListener((v) -> {
            //load and add new subject
            if (mainActivity == null) {
                return;
            }
            String identifier = nameTextView.getText().toString();
            if (!mainActivity.isNewIdentifier(identifier)) {
                Toast.makeText(mainActivity, R.string.error_identifier_not_unique, Toast.LENGTH_SHORT).show();
                return;
            }
            if (identifier.isEmpty()) {
                Toast.makeText(mainActivity, R.string.error_invalid_name, Toast.LENGTH_SHORT).show();
                return;
            }
            int maxAmount = getValue(maxAmountTextView);
            defaultSettings.setName(identifier);
            defaultSettings.setMaxPaper(maxAmount >= 0 ? maxAmount : 0);
            Subject subject = new Subject(identifier, defaultSettings.getMaxPaper(), defaultSettings);

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
