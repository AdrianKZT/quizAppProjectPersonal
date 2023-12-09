package com.adrian.quizapppersonalproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrian.quizapppersonalproject.data.model.Quiz
import com.adrian.quizapppersonalproject.databinding.QuizAddedLayoutBinding

class quizAddedAdapter(
    private var quizModel: List<Quiz>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = QuizAddedLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuizItemViewHolder(binding)
    }

    override fun getItemCount() = quizModel.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val quizItem = quizModel[position]
        if (holder is QuizItemViewHolder) {
            holder.bind(quizItem)
        }
    }

    fun setQuiz(quizModel: List<Quiz>) {
        this.quizModel = quizModel
        notifyDataSetChanged()
    }

    inner class QuizItemViewHolder(
        private val binding: QuizAddedLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding.run {
                tvQuizID.text = quiz.quizId
                tvTitle.text = quiz.title
//                tvCreated.text = quiz.time


                ivDelete.setOnClickListener {
                    listener?.onDelete(quiz)
                }

//                cvItems.setOnClickListener{
//                    listener?.onClick(quiz)
//                }
            }
        }
    }

    interface Listener {
        fun onDelete(quiz: Quiz)

//        fun onClick(quiz: Quiz)
    }
}