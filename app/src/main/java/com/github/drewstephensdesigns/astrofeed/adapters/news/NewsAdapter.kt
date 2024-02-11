package com.github.drewstephensdesigns.astrofeed.adapters.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.data.local.model.News
import com.github.drewstephensdesigns.astrofeed.databinding.ArticleListItemBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config.loadImage
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class NewsAdapter(
    private val context: Context
) : RecyclerView.Adapter<NewsAdapter.NewsArticleVH>() {

    private var newsArticlesObjects : List<News> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): NewsArticleVH {
        val binding = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsArticleVH(binding)
    }

    override fun getItemCount(): Int {
        return newsArticlesObjects.size
    }

    override fun onBindViewHolder(holder: NewsArticleVH, position: Int) {
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
        var newsPublisher: TextView = binding.textNewsPublisher

        fun bind(newsArticles: News){
            textNewsHeadline.text = newsArticles.title
            textNewsSummary.text = newsArticles.summary

            // Assuming launches.net is a String in the format "2024-01-23T04:03:00Z"
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("GMT")

            val launchTime = dateFormat.parse(newsArticles.publishedAt!!)

            // Convert to local time zone
            val localDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            localDateFormat.timeZone = TimeZone.getDefault()

            val localPublishedTime = localDateFormat.format(launchTime!!)

            textNewsPublished.text = context.resources.getString(R.string.article_published_at, localPublishedTime)
            newsPublisher.text = context.resources.getString(R.string.article_published_by,newsArticles.newsSite)

            newsViewArticle.text = context.resources.getString(R.string.action_view_article)

            newsViewArticle.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = (Uri.parse(newsArticles.url))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.applicationContext.startActivity(intent)
            }

            articleImage.loadImage(newsArticles.image_url)
        }
    }
}