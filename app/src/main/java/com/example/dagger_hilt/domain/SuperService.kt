package com.example.dagger_hilt.domain

import com.example.dagger_hilt.data.datasource.web.util.WebStatus

open class SuperService {
    fun <T> getWebStatus(
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit
    ) = object : WebStatus<T> {
        override fun success(data: T) {
            if (data is List<*>) {
                if (data.isNotEmpty()) onSuccess.invoke(data)
                else onError.invoke("Empty list")
                return
            }
            onSuccess.invoke(data)
        }

        override fun error(e: String) {
            onError.invoke(e)
        }
    }
}