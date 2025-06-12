package learningprogramming.academy.reviewrabbit.util

object PasswordChecker {
    fun checkIfPasswordsMatch(newPassword: String, confirmPassword: String): Boolean {
        while (newPassword != confirmPassword) {
            return false
        }
        return true
    }
}