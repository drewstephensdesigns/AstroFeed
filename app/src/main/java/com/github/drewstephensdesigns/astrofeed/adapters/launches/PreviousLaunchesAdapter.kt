package com.github.drewstephensdesigns.astrofeed.adapters.launches


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.data.local.model.LaunchResponse
import com.github.drewstephensdesigns.astrofeed.databinding.PreviousLaunchesListBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config.loadImage
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


class PreviousLaunchesAdapter(
    private val context: Context,
    private val onClickListener: LaunchClickListener
) : RecyclerView.Adapter<PreviousLaunchesAdapter.LaunchVH>(){

    private var previousLaunches: List<LaunchResponse> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchVH {
        val binding = PreviousLaunchesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LaunchVH(binding)
    }

    override fun getItemCount(): Int {
        return previousLaunches.size
    }

    override fun onBindViewHolder(holder: LaunchVH, position: Int) {
        holder.bind(previousLaunches[position])
    }

    fun setPreviousLaunches(launches: List<LaunchResponse>) {
        val previousSize = previousLaunches.size
        previousLaunches = launches
        val newSize = launches.size

        // Calculate the range of items that were inserted
        val itemCountInserted = newSize - previousSize

        if (itemCountInserted > 0) {
            // Use notifyItemRangeInserted to inform the adapter about the specific range of inserted items
            notifyItemRangeInserted(previousSize, itemCountInserted)
        } else {
            notifyDataSetChanged()
        }
    }


    inner class LaunchVH(binding: PreviousLaunchesListBinding) : RecyclerView.ViewHolder(binding.root){
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
            //if (launches.program!!.isNotEmpty() && launches.program[0].missionPatches?.isNotEmpty()!!) {
              if (launches.missionPatches!!.isNotEmpty()){
                  

                // Load the first mission patch image for the current LaunchResult
               // val missionPatchUrl = launches.program[0].missionPatches!![0].imageUrl
                val missionPatchUrl = launches.missionPatches.firstOrNull()?.imageUrl

                if (missionPatchUrl!!.isNotBlank()) {
                    // Use your preferred image loading library (e.g., Glide, Picasso) to load the image
                    Picasso.get()
                        .load(missionPatchUrl)
                        .into(missionPatchIV)

                    if(launches.mission.name!!.contains("Axiom Space Mission 3")){
                        missionPatchIV.loadImage("https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/mission_patch_images/ax-32520patch_mission_patch_20231019065301.png")
                    }
                } else {
                    // Handle the case where missionPatchUrl is null or blank
                    // You can set a placeholder or handle it based on your requirement

                   // missionPatchIV.setImageResource(R.drawable.mission_patch)

                }
            } else {
                // Handle the case where MissionPatches list is empty for the current LaunchResult
                // You can set a placeholder or handle it based on your requirement

                // Hardcodes patches
                when(launches.mission.name){
                    "Cygnus CRS-2 NG-20 (S.S. Patricia “Patty” Hilliard Robertson)" ->{
                        missionPatchIV.loadImage("https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/mission_patch_images/cygnus2520ng-2_mission_patch_20231206095838.png")
                    }
                    "PACE (Plankton, Aerosol, Cloud, ocean Ecosystem)" ->{
                        missionPatchIV.loadImage("https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/logo/national2520aeronautics2520and2520space2520administration_logo_20190207032448.png")
                    }
                    "Integrated Flight Test 3" ->{
                        missionPatchIV.loadImage("https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/mission_patch_images/starship2520if_mission_patch_20230414221655.png")
                    }
                    else -> {
                        missionPatchIV.setImageResource(R.drawable.mission_patch)
                    }
                }
            }

            itemView.setOnClickListener {
                onClickListener.onLaunchItemClick(previousLaunches[bindingAdapterPosition])
            }
        }
    }

    interface LaunchClickListener{
        fun onLaunchItemClick(launch: LaunchResponse)
    }
}