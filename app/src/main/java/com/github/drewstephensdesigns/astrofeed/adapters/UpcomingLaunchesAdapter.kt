package com.github.drewstephensdesigns.astrofeed.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.data.local.model.LaunchResponse
import com.github.drewstephensdesigns.astrofeed.databinding.UpcomingLaunchesListBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config.loadImage
import com.github.drewstephensdesigns.astrofeed.utils.formatDate
import com.github.drewstephensdesigns.astrofeed.utils.toMillis
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso


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
        //notifyDataSetChanged()
        notifyItemChanged(launches.size)
    }


    inner class LaunchVH(binding: UpcomingLaunchesListBinding) : RecyclerView.ViewHolder(binding.root){
        private var missionPatchIV : ShapeableImageView = binding.missionPatchIv
        private var textMissionName : TextView = binding.textMissionName
        private var textMissionWindow : TextView = binding.textLaunchWindowStart
        private var textMissionLaunchPad : TextView = binding.textLaunchPadLocation
        private var textMissionLaunchStatus : TextView = binding.textLaunchStatus

        fun bind(launches: LaunchResponse){
            textMissionName.text = launches.mission!!.name
            textMissionName.setTextColor(ContextCompat.getColor(context, R.color.blue_100))

            textMissionWindow.text = launches.net

            launches.net?.formatDate()
            launches.net?.toMillis()


            textMissionLaunchPad.text = launches.pad!!.name
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
                onClickListener.onLaunchItemClick(upcomingLaunches[bindingAdapterPosition])
            }
        }
    }

    interface LaunchClickListener{
        fun onLaunchItemClick(launch: LaunchResponse)
    }
}