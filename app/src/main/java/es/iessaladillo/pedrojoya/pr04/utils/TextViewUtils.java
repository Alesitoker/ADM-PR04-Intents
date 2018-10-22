package es.iessaladillo.pedrojoya.pr04.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr04.R;

public class TextViewUtils {
    private TextViewUtils() {
    }

    public static void afterTextChanged(EditText text, TextView view, Context context) {
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (text.getText().toString().isEmpty()) {
                    text.setError(context.getString(R.string.main_invalid_data));
                    view.setTextColor(context.getResources().getColor(R.color.colorError));
                } else {
                    view.setTextColor(context.getResources().getColor(R.color.colorBlack));
                }
            }
        });
    }

    public static void afterTextChanged(EditText text, TextView view, ImageView img, Context context) {
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (text.getText().toString().isEmpty()) {
                    text.setError(context.getString(R.string.main_invalid_data));
                    view.setTextColor(context.getResources().getColor(R.color.colorError));
                    img.setEnabled(false);
                } else {
                    view.setTextColor(context.getResources().getColor(R.color.colorBlack));
                    img.setEnabled(true);
                }
            }
        });
    }

    public static void onTextChanged(EditText txt, TextView lbl, ImageView img, Context context) {
        txt.addTextChangedListener(new TextWatcher() {
            boolean valid = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (R.id.txtEmail == txt.getId())
                    valid = ValidationUtils.isValidEmail(txt.getText().toString());
                 else if (R.id.txtPhonenumber == txt.getId())
                    valid = ValidationUtils.isValidPhone(txt.getText().toString());
                 else if (R.id.txtWeb == txt.getId())
                     valid = ValidationUtils.isValidUrl(txt.getText().toString());
                if (!valid) {
                    txt.setError(context.getString(R.string.main_invalid_data));
                    lbl.setTextColor(context.getResources().getColor(R.color.colorError));
                    img.setEnabled(false);
                } else {
                    lbl.setTextColor(context.getResources().getColor(R.color.colorBlack));
                    img.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public static void changeFocus(EditText txt, TextView lbl) {
        txt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                lbl.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                lbl.setTypeface(Typeface.DEFAULT);
            }
        });
    }

}
