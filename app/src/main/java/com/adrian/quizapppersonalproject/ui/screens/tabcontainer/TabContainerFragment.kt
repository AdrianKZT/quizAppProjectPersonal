package com.adrian.quizapppersonalproject.ui.screens.tabcontainer

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.adrian.quizapppersonalproject.R
import com.adrian.quizapppersonalproject.databinding.FragmentTabContainerBinding
import com.adrian.quizapppersonalproject.ui.adapter.FragmentAdapter
import com.adrian.quizapppersonalproject.ui.screens.Leaderboard.LeaderBoardFragment
import com.adrian.quizapppersonalproject.ui.screens.Student.StudentHomeFragment
import com.adrian.quizapppersonalproject.ui.screens.Teacher.TeacherHomeFragment
import com.adrian.quizapppersonalproject.ui.screens.login.LoginFragment
import com.adrian.quizapppersonalproject.ui.screens.showAddedQuiz.showAddedQuizFragment
import com.google.android.material.snackbar.BaseTransientBottomBar.AnimationMode
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TabContainerFragment : Fragment() {
    private lateinit var binding: FragmentTabContainerBinding

    val finishLoading: MutableSharedFlow<Unit> = MutableSharedFlow()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setFragmentResultListener("finish_quiz") { _, result ->
            lifecycleScope.launch {

                delay(500)
                val showLeaderBoard = result.getBoolean("showLeaderBoard")
                if (showLeaderBoard) {
                    Log.d("debugging", showLeaderBoard.toString())
                    val tab = binding.tlTabs.getTabAt(1)
                    binding.tlTabs.selectTab(tab, true)
                }
            }
        }

        binding.run {
            vpContainer.adapter = FragmentAdapter(
                this@TabContainerFragment,
                listOf(StudentHomeFragment(), LeaderBoardFragment())
            )

            vpContainer.isUserInputEnabled = false

            TabLayoutMediator(tlTabs, vpContainer) { tab, position ->

                val customTab =
                    layoutInflater.inflate(R.layout.custom_tab_layout, null) as LinearLayout

                val tabText = customTab.findViewById<TextView>(R.id.tabText)
                val tabIcon = customTab.findViewById<ImageView>(R.id.tabIcon)

                when (position) {
                    0 -> {
                        tabIcon.setImageResource(R.drawable.ic_home)
                        tabText.text = "Home"
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