package com.shinyj.githubsearch.presentation.repositorylist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shinyj.githubsearch.databinding.FragmentRepositoryListBinding
import com.shinyj.githubsearch.domain.model.Repository
import com.shinyj.githubsearch.domain.state.StateMessageCallback
import com.shinyj.githubsearch.presentation.repositorylist.viewmodel.*
import com.shinyj.githubsearch.presentation.util.*
import com.shinyj.githubsearch.util.Utils

class RepositoryListFragment : BaseFragment(), RepositoryListAdapter.Interaction {

    val TAG = Utils.getTag()

    private val viewModel: RepositoryListViewModel by viewModels()

    private var _binding: FragmentRepositoryListBinding? = null
    private val binding get() = _binding

    private lateinit var repositoryListAdapter: RepositoryListAdapter
    private var initialFocusHandler : Handler? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepositoryListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialFocusHandler = Handler(Looper.getMainLooper())
        setDataChannel()
        setUIComponents()
        subscribeObservers()
    }

    private fun setDataChannel() = viewModel.setDataChannel()

    private fun setUIComponents() {
        binding?.apply {
            editSearch.apply{
                setOnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        viewModel.setQuery(v.text.toString()).let{
                            handleNewSearch()
                        }
                        true
                    }
                    false
                }
            }

            nestedScroll.apply {
                setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    if (scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight)) {
                        // Bottom Scroll
                        handleNextLoad()
                    }
                })
            }

            listSearchResults.apply {
                layoutManager = LinearLayoutManager(this@RepositoryListFragment.context)

                val topSpacingItemDecorator = TopSpacingItemDecoration(1)
                removeItemDecoration(topSpacingItemDecorator)
                addItemDecoration(topSpacingItemDecorator)

                repositoryListAdapter = RepositoryListAdapter(this@RepositoryListFragment)
                adapter = repositoryListAdapter
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.apply {
            viewState.observe(viewLifecycleOwner, { viewState ->
                viewState.repositoryList?.let { list ->
                    repositoryListAdapter.apply {
                        submitList(list)
                    }
                }
            })

            shouldDisplayProgressBar.observe(viewLifecycleOwner, { shouldDisplay ->
                if (shouldDisplay)
                    binding?.progressbar?.visible()
                else
                    binding?.progressbar?.invisible()
            })

            stateMessage.observe(viewLifecycleOwner, { stateMessage ->
                stateMessage?.let { message ->
                    uiController.onResponseReceived(
                        response = message.response,
                        stateMessageCallback = object : StateMessageCallback {
                            override fun removeMessageFromQueue() {
                                viewModel.pollStateMessage()
                            }
                        }
                    )
                }
            })
        }
    }

    private fun handleNewSearch() {
        viewModel.loadFirstPage().let{
            uiController.hideSoftKeyboard()
        }
    }

    private fun handleNextLoad() {
        viewModel.loadNextPage()
    }

    private val focusRunnable = Runnable {
        binding?.editSearch?.requestFocus()
    }

    override fun onItemSelected(position: Int, item: Repository) {
        item.url?.let{ url ->
            activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } ?: activity?.displayToast("No URL found")
    }

    override fun restoreListPosition() {
        viewModel.viewState.value?.layoutManagerState?.let { lmState ->
            binding?.listSearchResults?.layoutManager?.onRestoreInstanceState(lmState)
        }
    }

    override fun onResume() {
        super.onResume()
        initialFocusHandler?.postDelayed(focusRunnable, UIConstants.INITIAL_FOCUS_DELAY)
    }

    override fun onPause() {
        super.onPause()
        initialFocusHandler?.removeCallbacks(focusRunnable)
    }

    // Clear references to prevent the memory leak
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
