package com.adrian.quizapppersonalproject.data.model


data class Result(
    val id: String? = null,
    val name: String,
    val correctScore: String,
    val quizID: String
) {

    fun toHashMap(): Map<String, String> {
        return mapOf(
            "name" to name,
            "correctScore" to correctScore,
            "quizID" to quizID
        )
    }

    companion object {
        fun fromHashMap(hash: Map<String, Any>): Result{
            return Result(
                id = hash["id"].toString(),
                name = hash["name"].toString(),
                correctScore = hash["correctScore"].toString(),
                quizID = hash["quizID"].toString()
            )
        }
    }
}