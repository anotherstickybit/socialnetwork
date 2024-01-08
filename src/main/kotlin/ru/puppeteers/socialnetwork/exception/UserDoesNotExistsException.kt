package ru.puppeteers.socialnetwork.exception

class UserDoesNotExistsException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)