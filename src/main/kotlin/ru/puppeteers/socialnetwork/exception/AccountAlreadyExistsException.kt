package ru.puppeteers.socialnetwork.exception

import lombok.experimental.StandardException

@StandardException
class AccountAlreadyExistsException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)