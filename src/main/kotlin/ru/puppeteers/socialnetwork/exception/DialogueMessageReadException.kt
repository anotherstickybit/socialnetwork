package ru.puppeteers.socialnetwork.exception

import lombok.experimental.StandardException

@StandardException
class DialogueMessageReadException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)