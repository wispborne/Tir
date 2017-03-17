package com.thunderclouddev.tirforgoodreads.ui.feed

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.thunderclouddev.tirforgoodreads.BaseRecyclerViewAdapter
import com.thunderclouddev.tirforgoodreads.R
import com.thunderclouddev.tirforgoodreads.SortedListAdapter
import com.thunderclouddev.tirforgoodreads.databinding.FeedItemReadstatusBinding

/**
 * @author David Whitman on 18 Mar, 2017.
 */
class FeedAdapter : SortedListAdapter<ReadStatusViewModel>(ReadStatusViewModel::class.java, ReadStatusViewModel.DefaultComparator) {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BaseRecyclerViewAdapter.ViewHolder<out ReadStatusViewModel> {
        return ViewHolder(DataBindingUtil.inflate<FeedItemReadstatusBinding>(inflater, R.layout.feed_item_readstatus, parent, false))
    }

    override fun areItemsTheSame(item1: ReadStatusViewModel, item2: ReadStatusViewModel)
            = item1.status.get().toString() == item2.status.get().toString()

    override fun areItemContentsTheSame(oldItem: ReadStatusViewModel, newItem: ReadStatusViewModel)
            = oldItem.status.get().toString() == newItem.status.get().toString()

    data class ViewHolder(private val binding: FeedItemReadstatusBinding)
        : BaseRecyclerViewAdapter.ViewHolder<ReadStatusViewModel>(binding) {
        override fun performBind(viewModel: ReadStatusViewModel) {
            binding.viewModel = viewModel
        }
    }
}