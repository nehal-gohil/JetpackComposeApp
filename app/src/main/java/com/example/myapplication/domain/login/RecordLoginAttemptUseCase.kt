package com.example.myapplication.domain.login

/** Domain action that records one login-button submission through [counter]. */
class RecordLoginAttemptUseCase(private val counter: LoginAttemptCounter) {
    /** Increments the counter and returns its updated value. */
    operator fun invoke(): Int = counter.increment()
}
