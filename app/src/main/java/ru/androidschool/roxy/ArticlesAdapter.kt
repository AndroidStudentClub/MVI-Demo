package ru.androidschool.roxy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.androidschool.data.FeedArticle
import ru.androidschool.mvi.databinding.ItemArticleBinding

class ArticlesAdapter : ListAdapter<FeedArticle, ArticlesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val entity = getItem(position)
        entity?.let {
            holder.bind(entity)
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<FeedArticle>() {

    override fun areItemsTheSame(oldItem: FeedArticle, newItem: FeedArticle): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedArticle, newItem: FeedArticle): Boolean {
        return oldItem == newItem
    }
}