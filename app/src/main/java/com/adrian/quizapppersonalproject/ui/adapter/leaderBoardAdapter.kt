package com.adrian.quizapppersonalproject.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrian.quizapppersonalproject.data.model.Result
import com.adrian.quizapppersonalproject.databinding.ItemLayoutBinding

class leaderBoardAdapter(
    private var results: List<Result>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LeaderBoardItemViewHolder(binding)
    }

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val res = results[position]
        if (holder is LeaderBoardItemViewHolder) {
            holder.bind(res)
        }
    }

    fun showBoard(res: List<Result>) {
        this.results = res
        notifyDataSetChanged()
    }

    inner class LeaderBoardItemViewHolder(
        private val binding: ItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.run {
                tvName.text = result.name
                tvScore.text = result.correctScore
                Log.d("debugging_adapter", result.correctScore)
                tvShowID.text = result.quizID
            }
        }
    }

}