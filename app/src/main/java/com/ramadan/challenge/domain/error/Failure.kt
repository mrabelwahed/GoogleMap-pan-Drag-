package com.ramadan.challenge.domain.error

sealed class Failure {
    object NetworkConnection : Failure()
    object Unknown : Failure()
    sealed class ServerError : Failure() {
        object NotFound : ServerError()
        object AccessDenied : ServerError()
        object ServiceUnavailable : ServerError()
    }
}
