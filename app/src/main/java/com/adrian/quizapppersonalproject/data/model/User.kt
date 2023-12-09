package com.adrian.quizapppersonalproject.data.model

data class User(
    val id: String? = null,
    val name: String,
    val email: String,
    val role: String,
    val profilePicUrl: String = ""
) {
    fun toHashMap(): HashMap<String, String> {
        return hashMapOf(
            "name" to name,
            "email" to email,
            "role" to role,
            "profilePicUrl" to profilePicUrl
        )
    }

    companion object{
        fun fromHashMap(hash: Map<String, Any>): User {
            return User(
                id = hash["id"].toString(),
                name = hash["name"].toString(),
                email = hash["email"].toString(),
                role = hash["role"].toString(),
                profilePicUrl = hash["profilePicUrl"].toString()
            )
        }
    }
}