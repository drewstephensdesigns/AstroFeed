package com.github.drewstephensdesigns.astrofeed.adapters.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.data.local.model.RedditPost
import com.github.drewstephensdesigns.astrofeed.databinding.RedditListItemBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config.loadImage
import com.github.drewstephensdesigns.astrofeed.ui.HTMLView
import com.github.drewstephensdesigns.astrofeed.utils.convertDate
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView

class RedditPostAdapter(
    private val ct: Context, private val openLink: (String) -> Unit
) : RecyclerView.Adapter<RedditPostAdapter.RedditPostVH>() {

    companion object {
        const val REDDIT = "https://www.reddit.com"
    }

    private var redditPosts: List<RedditPost> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditPostVH {
        val binding =
            RedditListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RedditPostVH(binding)
    }

    override fun getItemCount(): Int {
        return redditPosts.size
    }

    override fun onBindViewHolder(holder: RedditPostVH, position: Int) {
        holder.bind(redditPosts[position])
    }

    fun setRedditPosts(posts: List<RedditPost>) {
        redditPosts = posts
        notifyDataSetChanged()
    }

    inner class RedditPostVH(binding: RedditListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var redditCard: MaterialCardView = binding.listItemRedditCard

        // Images
        var thumbail: ShapeableImageView = binding.listItemRedditThumbnail
        var previewImage: ShapeableImageView = binding.listItemRedditPreview

        // Textfields
        val redditAuthor: TextView = binding.listItemRedditAuthor
        var redditTitle: TextView = binding.listItemRedditTitle
        var redditPostDate: TextView = binding.listItemRedditText
        var upDoots: TextView = binding.listItemRedditScore
        var comments: TextView = binding.listItemRedditComments
        var thumnailLink: TextView = binding.listItemRedditThumbnailLink
        var postSticky: ImageView = binding.listItemRedditPinned
        var redditDescription: HTMLView = binding.listItemRedditText
        var redditSubReddit: TextView = binding.listItemSubreddit

        // Layout
        var thumbnailCard: ConstraintLayout = binding.listItemRedditThumbnailCard
        var redditContent: ConstraintLayout = binding.listItemRedditContent

        fun bind(redditPosts: RedditPost) {

            if (redditPosts.redditDomain || redditPosts.isSelf) {
                thumbnailCard.visibility = View.GONE
            }else if (redditPosts.thumbnail.isNotEmpty()) {
                thumbnailCard.visibility = View.VISIBLE
                thumbail.loadImage(redditPosts.thumbnail)
                thumnailLink.text = redditPosts.domain
                redditAuthor.text = ct.resources.getString(R.string.date_placeholder, redditPosts.created.toLong().convertDate())
            }

            thumbnailCard.visibility = View.VISIBLE
            thumbail.loadImage(redditPosts.thumbnail)

            if (redditPosts.redditDomain && redditPosts.preview != null && redditPosts.description.isNullOrEmpty()) {
                redditPosts.preview?.let {
                    previewImage.visibility = View.VISIBLE
                    val image = it.images[0].resolutions[it.images[0].resolutions.size - 1]
                    previewImage.loadImage(image.url)

                    ConstraintSet().apply {
                        clone(redditContent)
                        setDimensionRatio(
                            previewImage.id, "${image.width}:${image.height}"
                        )
                        applyTo(redditContent)
                    }

                    thumbail.visibility = View.GONE
                }
            } else {
                previewImage.visibility = View.GONE
            }

            redditTitle.text = redditPosts.title

            redditDescription.plainText = false
            redditPosts.description?.let {
                redditDescription.setHtmlText(it)
            }

            redditAuthor.text = ct.resources.getString(R.string.date_placeholder, redditPosts.created.toLong().convertDate())
            redditSubReddit.text =
                ct.resources.getString(R.string.subreddit_placeholder, redditPosts.subreddit)
            redditPostDate.text = ct.resources.getString(R.string.username_placeholder, redditPosts.author)
            redditPostDate.setTextColor(ContextCompat.getColor(ct, R.color.blue_100))

            upDoots.text = redditPosts.score.toString()
            comments.text = redditPosts.commentsCount.toString()

            redditDescription.visibility =
                if (redditPosts.description.isNullOrEmpty()) View.GONE else View.VISIBLE
            postSticky.visibility = if (redditPosts.stickied) View.VISIBLE else View.GONE


            redditCard.setOnClickListener {
                openLink("$REDDIT${redditPosts.permalink}")
            }
        }
    }
}