package de.max.notenspiegel.gui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import de.max.notenspiegel.R;

/**
 * A Setting display field
 */
public class SettingField extends ConstraintLayout {
    private final EditText editText;
    private Drawable background;
    private final String description;

    public SettingField(Context context) {
        super(context);
        editText = null;
        description = "";
    }

    public SettingField(@NonNull @NotNull Context context, String description) {
        this(context, description, InputType.TYPE_TEXT_VARIATION_NORMAL);
    }

    public SettingField(@NonNull @NotNull Context context, String description, int inputType) {
        super(context);
        this.description = description;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.setting_field, this, true);
        TextView textView = findViewById(R.id.settingFieldLayoutTextView);
        textView.setText(description);
        editText = findViewById(R.id.settingFieldLayoutEditText);
        background = editText.getBackground();

        editText.setBackground(ContextCompat.getDrawable(context, R.color.dark_gray));
        editText.setFocusableInTouchMode(false);
        editText.setFocusable(false);
        editText.setClickable(false);
        editText.setInputType(inputType);

        editText.setOnLongClickListener(v -> {
            System.out.println("long click");
            editText.setBackground(background);
            editText.setFocusableInTouchMode(true);
            editText.setFocusable(true);
            editText.setClickable(true);
            return false;
        });
    }

    public String getValue() {
        return editText.getText().toString();
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public String getDescription() {
        return description;
    }
}
