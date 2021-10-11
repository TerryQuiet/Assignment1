package tk.quietdev.level1.utils

/**
 * Thrown when there was a error registering a new user
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class UserRegisterError(message: String) : Exception(message)