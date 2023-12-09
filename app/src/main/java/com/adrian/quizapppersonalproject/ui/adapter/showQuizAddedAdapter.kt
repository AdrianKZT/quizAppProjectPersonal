package com.adrian.quizapppersonalproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrian.quizapppersonalproject.data.model.Quiz
import com.adrian.quizapppersonalproject.databinding.ShowQuizLayoutBinding

class showQuizAddedAdapter(
    private var quizModel: List<Quiz>
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ShowQuizLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShowAddedViewHolder(binding)
    }

    override fun getItemCount() = quizModel.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val quizItem = quizModel[position]
        if (holder is ShowAddedViewHolder) {
            holder.bind(quizItem)
        }
    }

    fun showQuiz(quizModel: List<Quiz>){
        this.quizModel = quizModel
        notifyDataSetChanged()
    }

    inner class ShowAddedViewHolder(
        private val binding: ShowQuizLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding.run {
                tvQuizID.text = quiz.quizId
                tvTitle.text = quiz.title
            }
        }
    }
}