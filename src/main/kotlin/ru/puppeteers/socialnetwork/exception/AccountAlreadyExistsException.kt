package ru.puppeteers.socialnetwork.exception

class AccountAlreadyExistsException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)