package ru.puppeteers.socialnetwork.exception

import lombok.experimental.StandardException

@StandardException
class DialogueMessageCreationException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)