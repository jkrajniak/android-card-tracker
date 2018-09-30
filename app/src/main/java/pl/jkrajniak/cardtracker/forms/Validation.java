package pl.jkrajniak.cardtracker.forms;

import android.widget.EditText;

/**
 * Basic validation class
 */
public class Validation {
    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText) {

        // clear
        editText.setError(null);

        // text required and editText is blank, so return false
        if (!hasText(editText))
            return false;

        return true;
    }

    public static boolean hasText(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);  // clear error

        if (text.isEmpty()) {
            editText.setError("required");
            return false;
        }
        return true;
    }

    
}
