package com.adrian.quizapppersonalproject.ui.screens.tabcontainer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.adrian.quizapppersonalproject.R
import com.adrian.quizapppersonalproject.databinding.FragmentTabContainerBinding
import com.adrian.quizapppersonalproject.databinding.FragmentTeacherTabContainerBinding
import com.adrian.quizapppersonalproject.ui.adapter.FragmentAdapter
import com.adrian.quizapppersonalproject.ui.screens.AddQuiz.AddQuizFragment
import com.adrian.quizapppersonalproject.ui.screens.Leaderboard.LeaderBoardFragment
import com.adrian.quizapppersonalproject.ui.screens.Student.StudentHomeFragment
import com.adrian.quizapppersonalproject.ui.screens.Teacher.TeacherHomeFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class TeacherTabContainerFragment : Fragment() {

    private lateinit var binding: FragmentTeacherTabContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeacherTabContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            vpContainer.adapter = FragmentAdapter(
                this@TeacherTabContainerFragment,
                listOf(TeacherHomeFragment(), AddQuizFragment(), LeaderBoardFragment())
            )

            vpContainer.isUserInputEnabled = false

            TabLayoutMediator(tlTabs, vpContainer) { tab, position ->
                val customTab = layoutInflater.inflate(R.layout.custom_tab_layout, null) as LinearLayout
                val tabText = customTab.findViewById<TextView>(R.id.tabText)
                val tabIcon = customTab.findViewById<ImageView>(R.id.tabIcon)

                when(position){
                    0 -> {
                        tabIcon.setImageResource(R.drawable.ic_home)
                        tabText.text = "Home"
                    }
                    1 -> {
                        tabIcon.setImageResource(R.drawable.outline_quiz_24)
                        tabText.text = "Add Quiz"
                    }
                    else -> {
                        tabIcon.setImageResource(R.drawable.trophy__1_)
                        tabText.text = "Leaderboard"
                    }
                }
                tab.customView = customTab
            }.attach()
        }
    }
}