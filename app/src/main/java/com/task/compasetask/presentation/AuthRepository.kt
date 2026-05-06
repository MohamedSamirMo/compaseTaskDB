package com.task.compasetask.presentation

import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthRepository @Inject constructor() {
    suspend fun signIn(email: String, password: String): Result<Unit> {
        delay(1500)
        return if (email.isNotBlank() && password.isNotBlank()) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Invalid email or password"))
        }
    }
}