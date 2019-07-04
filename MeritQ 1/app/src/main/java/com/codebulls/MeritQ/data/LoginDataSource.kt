package com.codebulls.MeritQ.data

import com.codebulls.MeritQ.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Manojkumar Arokkiasamy")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error Logging In", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

