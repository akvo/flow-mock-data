package org.akvo.mockcaddisfly.data.result;

import java.util.Locale;

public class UserFactory {

    public static User createDefaultUser() {
        return new User(true, Locale.getDefault().getLanguage());
    }
}
