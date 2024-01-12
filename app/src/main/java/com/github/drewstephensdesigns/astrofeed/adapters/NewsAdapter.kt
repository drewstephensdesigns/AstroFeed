package com.github.drewstephensdesigns.astrofeed.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.data.local.model.News
import com.github.drewstephensdesigns.astrofeed.databinding.ArticleListItemBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config.loadImage
import com.google.android.material.button.MaterialButton

class NewsAdapter(
    private val context: Context
) : RecyclerView.Adapter<NewsAdapter.NewsArticleVH>() {

    private var newsArticlesObjects : List<News> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): NewsAdapter.NewsArticleVH {
        val binding = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsArticleVH(binding)
    }

    override fun getItemCount(): Int {
        return newsArticlesObjects.size
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsArticleVH, position: Int) {
        holder.bind(newsArticlesObjects[position])
    }

    fun setNewsArticles(news: List<News>){
        newsArticlesObjects = news
        notifyDataSetChanged()
    }

    inner class NewsArticleVH(binding: ArticleListItemBinding): RecyclerView.ViewHolder(binding.root){
        var articleImage: ImageView = binding.newsIv
        var textNewsHeadline: TextView = binding.textNewsHeadline
        var textNewsSummary: TextView = binding.textNewsSummary
        var textNewsPublished: TextView = binding.textNewsPublished
        var newsViewArticle: MaterialButton = binding.newsViewArticle

        fun bind(newsArticles: News){
            textNewsHeadline.text = newsArticles.title
            textNewsSummary.text = newsArticles.summary
            textNewsPublished.text = context.resources.getString(R.string.article_published_at, newsArticles.publishedAt)

            newsViewArticle.text = context.resources.getString(R.string.action_view_article)
            newsViewArticle.setTextColor(ContextCompat.getColor(context, R.color.blue_100))

            newsViewArticle.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = (Uri.parse(newsArticles.url))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.applicationContext.startActivity(intent)
            }


            articleImage.loadImage(newsArticles.image_url)
        }
    }
}