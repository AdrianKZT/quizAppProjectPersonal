package com.adrian.quizapppersonalproject.data.model

import com.google.firebase.auth.FirebaseUser

data class Quiz(
    val id: String? = null,
    val title: String,
    val quizId: String,
    val time: Long,
    val csv: String = "",
//    val correctAns: Int = 0,
    val totalQuestion: Int = 0,
    val QuizTitles: List<String>,
    val options: List<String>,
    val answers: List<String>,
    val createdBy: String
) {

    fun toHashMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "quizId" to quizId,
            "time" to time,
            "csv" to csv,
            "totalQuestion" to totalQuestion,
            "QuizTitles" to QuizTitles,
            "options" to options,
            "answers" to answers,
            "createdBy" to createdBy
        )
    }

    companion object {
        fun fromHashMap(hash: Map<String, Any>): Quiz {
            return Quiz(
                id = hash["id"].toString(),
                title = hash["title"].toString(),
                quizId = hash["quizId"].toString(),
                time = hash["time"]?.toString()?.toLong() ?: 0,
                csv = hash["csv"].toString(),
                QuizTitles = (hash["QuizTitles"] as ArrayList<*>?)?.map {
                    it.toString()
                }?.toList() ?: emptyList(),
                options = (hash["options"] as ArrayList<*>?)?.map {
                    it.toString()
                }?.toList() ?: emptyList(),
                answers = (hash["answers"] as ArrayList<*>?)?.map {
                    it.toString()
                }?.toList() ?: emptyList(),
                createdBy = hash["createdBy"].toString()
            )
        }
    }
}