package com.example.myapplication.data.login

import com.example.myapplication.domain.login.LoginAttemptCounter

/** In-memory implementation of the login attempt counter, scoped to the app process. */
class LoginAttemptRepository : LoginAttemptCounter {
    /** Current number of attempts recorded by this repository instance. */
    private var attempts = 0

    /** Increments the attempt total and returns the updated count. */
    override fun increment(): Int {
        attempts += 1
        return attempts
    }
}
