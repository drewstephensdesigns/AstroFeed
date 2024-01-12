package com.github.drewstephensdesigns.astrofeed.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.github.drewstephensdesigns.astrofeed.MainActivity
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.data.local.model.Rocket
import com.github.drewstephensdesigns.astrofeed.databinding.RocketListItemBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config.loadImage
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import kotlin.random.Random


class RocketAdapter(
    private val ct: Context,
    private val onClickListener: RocketClickListener
) : RecyclerView.Adapter<RocketAdapter.RocketObjectVH>(){

    private var rocketObjects: List<Rocket> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): RocketObjectVH {
        val binding = RocketListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RocketObjectVH(binding)
    }

    override fun getItemCount(): Int {
        return rocketObjects.size
    }

    override fun onBindViewHolder(holder: RocketObjectVH, position: Int) {
        holder.bind(rocketObjects[position])
    }

    fun setRocketData(rockets: List<Rocket>){
        rocketObjects = rockets
        notifyDataSetChanged()
    }

    inner class RocketObjectVH(binding: RocketListItemBinding): RecyclerView.ViewHolder(binding.root){
        var rocketImage: ImageView = binding.rocketIv
        var textRocketName : TextView = binding.textRocketName
        var textRocketDescription : TextView = binding.textRocketDescription
        var vehicleSpecsButton: MaterialButton = binding.vehicleSpecs

        /*
        init {
            binding.root.setOnClickListener {
                onClickListener.onRocketItemClick(rocketObjects[bindingAdapterPosition])
            }
        }

         */

        fun bind(rockets: Rocket){
            textRocketName.text = rockets.name
            textRocketDescription.text = rockets.description

            // Opens Wikipedia Article
            textRocketDescription.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = (Uri.parse(rockets.wikipedia))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ct.applicationContext.startActivity(intent)

            }

            vehicleSpecsButton.text = ct.resources.getString(R.string.action_vehicle_specs)
            vehicleSpecsButton.setTextColor(ContextCompat.getColor(ct, R.color.blue_100))
            vehicleSpecsButton.setOnClickListener {
                onClickListener.onRocketItemClick(rocketObjects[bindingAdapterPosition])
            }

            // Shuffle the list of image URLs
            val randomImageUrl = rockets.flickrImages.random()

            //println("Rocket Name:" + rockets.name + "RocketImage" + randomImageUrl)

            rocketImage.loadImage(randomImageUrl)

        }
    }

    interface RocketClickListener{
        fun onRocketItemClick(rocket: Rocket)
    }
}