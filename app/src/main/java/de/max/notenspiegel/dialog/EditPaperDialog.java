package de.max.notenspiegel.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import de.max.notenspiegel.R;
import de.max.notenspiegel.activity.SubjectActivity;
import de.max.notenspiegel.gui.PaperField;
import de.max.notenspiegel.structure.Paper;

public class EditPaperDialog extends DialogFragment {
    private PaperField paperField;

    public EditPaperDialog(PaperField paperField) {
        this.paperField = paperField;
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_paper_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText editTextName = view.findViewById(R.id.editTextEditPaperName);
        EditText editTextNumberMax = view.findViewById(R.id.editTextNumberMax);
        EditText editTextNumberActual = view.findViewById(R.id.editTextPointActual);
        Button cancel = view.findViewById(R.id.editPaperCancelButton);
        Button confirm = view.findViewById(R.id.editPaperConfirmlButton);
        Paper paper = paperField.getPaper();
        String name = "" + paper.getNumber();
        editTextName.setText(name);
        editTextNumberMax.setText("" + paper.getMaxPoint());
        editTextNumberActual.setText("" + paper.getActualPoints());

        cancel.setOnClickListener((v) -> {
            dismiss();
        });
        confirm.setOnClickListener((v) -> {
            if (!editTextName.getText().toString().equals(name)) {
                Toast.makeText(getContext(), "Not editing name for now! ;)", Toast.LENGTH_SHORT).show();
            }
            paper.setActualPoints(Double.parseDouble(editTextNumberActual.getText().toString()));
            paper.setMaxPoint(Double.parseDouble(editTextNumberMax.getText().toString()));
            paperField.update();
            if (getContext() instanceof SubjectActivity) {
                SubjectActivity activity = (SubjectActivity) getContext();
                activity.update();
            }
            dismiss();
        });


    }
}
