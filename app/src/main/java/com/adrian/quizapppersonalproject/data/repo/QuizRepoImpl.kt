package com.adrian.quizapppersonalproject.data.repo

import android.util.Log
import com.adrian.quizapppersonalproject.core.service.AuthService
import com.adrian.quizapppersonalproject.data.model.Quiz
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuizRepoImpl(
    private val authService: AuthService,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : QuizRepo {

    private fun getDbRef(): CollectionReference {
        val firebaseUser = authService.getCurrentUser()
        return firebaseUser?.uid?.let {
            db.collection("quizzes")
        }?: throw Exception("No user found")
    }

    override suspend fun getQuiz() = callbackFlow {
        val listener = getDbRef().addSnapshotListener{ value, error ->
            if (error != null) {
                throw error
            }

            val quizzes = mutableListOf<Quiz>()
            value?.documents?.let { docs ->
                for (doc in docs) {
                    doc.data?.let {
                        it["id"] = doc.id
                        quizzes.add(Quiz.fromHashMap(it))
                    }
                }
                trySend(quizzes)
            }
        }
        awaitClose{
            listener.remove()
        }
    }

    override suspend fun addQuiz(quiz: Quiz) {
        getDbRef().add(quiz.toHashMap()).await()
    }

    override suspend fun getQuizByID(quizID: String): Quiz? {
//        val doc = getDbRef().document().wh.get().await()
//        return doc.data?.let {
//            it["quizId"] = doc.id
//            Quiz.fromHashMap(it)
//        }
        val querySnapshot = getDbRef().whereEqualTo("quizId", quizID).get().await()

        // Check if any documents match the query
        if (!querySnapshot.isEmpty) {
            val doc = querySnapshot.documents[0]
            return doc.data?.let {
                it["id"] = doc.id
                Quiz.fromHashMap(it)
            }
        } else {
            // No matching document found
            return null
        }
    }

    override suspend fun deleteQuiz(id: String) {
        getDbRef().document(id).delete().await()
    }

    override suspend fun updateQuiz(id: String, quiz: Quiz) {
        getDbRef().document(id).set(quiz.toHashMap()).await()
    }
}