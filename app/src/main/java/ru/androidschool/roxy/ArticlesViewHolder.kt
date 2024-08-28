package ru.androidschool.roxy

import androidx.recyclerview.widget.RecyclerView
import ru.androidschool.data.FeedArticle
import ru.androidschool.mvi.databinding.ItemArticleBinding

class ArticlesViewHolder(private val binding: ItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: FeedArticle) {
        binding.title.text = article.title
    }
}