package com.delog.server.aggregate.user.application.interceptor

object UserContextHolder {
    private val USER_CONTEXT = ThreadLocal<String>()

    fun setUser(username: String?) {
        val name =
            username?.trim().takeIf { !it.isNullOrEmpty() }
                ?: throw IllegalArgumentException("Username cannot be null or empty")
        USER_CONTEXT.set(name)
    }

    fun getCurrentUser(): String? = USER_CONTEXT.get()

    fun getCurrentUserOrDefault(defaultUser: String): String = USER_CONTEXT.get() ?: defaultUser

    fun clearUser() {
        USER_CONTEXT.remove()
    }

    fun isUserSet(): Boolean = USER_CONTEXT.get() != null
}
