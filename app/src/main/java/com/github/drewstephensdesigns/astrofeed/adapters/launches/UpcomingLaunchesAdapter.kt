package com.github.drewstephensdesigns.astrofeed.adapters.launches


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.data.local.model.LaunchResponse
import com.github.drewstephensdesigns.astrofeed.databinding.UpcomingLaunchesListBinding
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


class UpcomingLaunchesAdapter(
    private val context: Context,
    private val onClickListener: LaunchClickListener
) : RecyclerView.Adapter<UpcomingLaunchesAdapter.LaunchVH>(){

    private var upcomingLaunches: List<LaunchResponse> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchVH {
        val binding = UpcomingLaunchesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LaunchVH(binding)
    }

    override fun getItemCount(): Int {
        return upcomingLaunches.size
    }

    override fun onBindViewHolder(holder: LaunchVH, position: Int) {
        holder.bind(upcomingLaunches[position])
    }

    fun setUpcomingLaunches(launches: List<LaunchResponse>){
        upcomingLaunches = launches
        notifyDataSetChanged()
        //notifyItemChanged(launches.size)
    }


    inner class LaunchVH(binding: UpcomingLaunchesListBinding) : RecyclerView.ViewHolder(binding.root){
        private var missionPatchIV : ShapeableImageView = binding.missionPatchIv
        private var textMissionName : TextView = binding.textMissionName
        private var textMissionWindow : TextView = binding.textLaunchWindowStart
        private var textMissionLaunchPad : TextView = binding.textLaunchPadLocation
        private var textMissionLaunchLocation : TextView = binding.textLaunchLocation
        private var textMissionLaunchStatus : TextView = binding.textLaunchStatus


        fun bind(launches: LaunchResponse){
            textMissionName.text = launches.mission!!.name
            textMissionName.setTextColor(ContextCompat.getColor(context, R.color.blue_100))

            // Assuming launches.net is a String in the format "2024-01-23T04:03:00Z"
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("GMT")

            val launchTime = dateFormat.parse(launches.net!!)

            // Convert to local time zone
            val localDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            localDateFormat.timeZone = TimeZone.getDefault()

            val localLaunchTime = localDateFormat.format(launchTime!!)

            // Set the text view
            textMissionWindow.text = context.resources.getString(R.string.launch_time_local, localLaunchTime)

                    //localLaunchTime

            textMissionLaunchPad.text = launches.pad!!.name
            textMissionLaunchLocation.text = launches.pad.location.name
            textMissionLaunchStatus.text = launches.status!!.name

            // Check if MissionPatches list is not empty for the current LaunchResult
              if (launches.missionPatches!!.isNotEmpty()){

                // Load the first mission patch image for the current LaunchResult
                val missionPatchUrl = launches.missionPatches.firstOrNull()?.imageUrl
                Picasso.get()
                  .load(missionPatchUrl)
                  .into(missionPatchIV)
            } else {
                missionPatchIV.setImageResource(R.drawable.mission_patch)
            }

            itemView.setOnClickListener {
                onClickListener.onLaunchItemClick(upcomingLaunches[bindingAdapterPosition])
            }
        }
    }

    interface LaunchClickListener{
        fun onLaunchItemClick(launch: LaunchResponse)
    }
}