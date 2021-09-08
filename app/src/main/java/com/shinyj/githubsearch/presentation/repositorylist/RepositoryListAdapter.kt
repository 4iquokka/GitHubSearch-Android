package com.shinyj.githubsearch.presentation.repositorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shinyj.githubsearch.databinding.LayoutRepoListItemBinding
import com.shinyj.githubsearch.domain.model.Repository
import com.shinyj.githubsearch.util.DateUtils
import com.shinyj.githubsearch.util.StringUtils.withSuffix

class RepositoryListAdapter(
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repository>() {

        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepositoryItemViewHolder(
            LayoutRepoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            interaction
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RepositoryItemViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(repositoryList: List<Repository>?) {
        val commitCallback = Runnable {
            interaction?.restoreListPosition()
        }
        differ.submitList(repositoryList, commitCallback)
    }

    class RepositoryItemViewHolder
    constructor(
        private val itemViewBinding: LayoutRepoListItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(item: Repository) = with(itemViewBinding) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            txtName.text = item.name
            txtDescription.text = item.description
            txtDate.text = DateUtils.formatDateString(item.updatedAt ?: item.createdAt)
            txtStars.text = withSuffix(item.numOfStars)
            txtWatchers.text = withSuffix(item.numOfWatchers)
            txtForks.text = withSuffix(item.numOfForks)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Repository)
        fun restoreListPosition()
    }
}