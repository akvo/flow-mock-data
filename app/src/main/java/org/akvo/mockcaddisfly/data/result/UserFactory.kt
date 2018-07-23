package org.akvo.mockcaddisfly.data.result

import java.util.Locale

object UserFactory {

    fun createDefaultUser(): User {
        return User(true, Locale.getDefault().language)
    }
}
