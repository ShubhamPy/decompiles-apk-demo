package in.linus.busmate.Utility;

import android.widget.EditText;
import java.util.regex.Pattern;

public class RegexExpressions {
    private static final String EMAIL_MSG = "invalid email";
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_MSG = "###-#######";
    private static final String PHONE_REGEX = "^[7-9][0-9]{9}$";
    private static final String REQUIRED_MSG = "required";

    public static boolean isEmailAddress(EditText editText, Boolean bool) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, bool);
    }

    public static boolean isPhoneNumber(EditText editText, Boolean bool) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, bool);
    }

    public static boolean isValid(EditText editText, String str, String str2, Boolean bool) {
        String trim = editText.getText().toString().trim();
        editText.setError((CharSequence) null);
        if (bool.booleanValue() && !hasText(editText)) {
            return false;
        }
        if (!bool.booleanValue() || Pattern.matches(str, trim)) {
            return true;
        }
        editText.setError(str2);
        return false;
    }

    public static boolean hasText(EditText editText) {
        String trim = editText.getText().toString().trim();
        editText.setError((CharSequence) null);
        if (trim.length() != 0) {
            return true;
        }
        editText.setError(REQUIRED_MSG);
        return false;
    }
}
