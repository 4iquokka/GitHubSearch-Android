package com.shinyj.githubsearch.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shinyj.githubsearch.R
import com.shinyj.githubsearch.databinding.FragmentSplashBinding
import com.shinyj.githubsearch.presentation.util.UIConstants
import com.shinyj.githubsearch.presentation.util.rotate

class SplashFragment : Fragment() {

    private lateinit var splashHandler : Handler

    private var _binding : FragmentSplashBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashHandler = Handler(Looper.getMainLooper());
        setUIComponents()
    }

    private fun setUIComponents(){
        binding?.imgSplash?.rotate()
    }

    private val splashRunnable = Runnable {
        findNavController().navigate(R.id.action_splashFragment_to_repositoryListFragment)
    }

    override fun onResume() {
        super.onResume()
        splashHandler.postDelayed(splashRunnable, UIConstants.SPLASH_TIME)
    }

    override fun onPause() {
        super.onPause()
        splashHandler.removeCallbacks(splashRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}