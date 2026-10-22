package com.example.myapplication.domain.login

/** Domain-facing contract for recording login button submissions. */
interface LoginAttemptCounter {
    /** Adds one attempt and returns the resulting total. */
    fun increment(): Int
}
