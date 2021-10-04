package de.max.notenspiegel.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import de.max.notenspiegel.R;

public class YesNoDialog extends DialogFragment {
    private final String text;
    private final String cancel;
    private final String confirm;
    private final Runnable runnable;

    public YesNoDialog(@Nullable String text, @Nullable String cancel, @Nullable String confirm, Runnable runnable) {
        this.text = text;
        this.cancel = cancel;
        this.confirm = confirm;
        this.runnable = runnable;
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.yes_no_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (text != null) {
            TextView textView = view.findViewById(R.id.yesNoText);
            textView.setText(text);
        }
        Button buttonCancel = view.findViewById(R.id.yesNoDialogCancelButton);
        Button buttonConfirm = view.findViewById(R.id.yesNoDialogConfirmButton);
        if (cancel != null) {
            buttonCancel.setText(text);
        }
        if (confirm != null) {
            buttonConfirm.setText(text);
        }
        buttonCancel.setOnClickListener((v) -> {
            dismiss();
        });
        buttonConfirm.setOnClickListener((v) -> {
            runnable.run();
            dismiss();
        });

    }
}
