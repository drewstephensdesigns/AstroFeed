package com.github.drewstephensdesigns.astrofeed.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.data.local.model.Capsules
import com.github.drewstephensdesigns.astrofeed.data.local.model.Spacecraft
import com.github.drewstephensdesigns.astrofeed.databinding.CapsulesListItemBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config.loadImage
import com.squareup.picasso.Picasso
import kotlin.math.sign

class CapsulesAdapter(
    private val ct: Context) :
    RecyclerView.Adapter<CapsulesAdapter.CapsulesVH>() {

    private var capsuleObjects: List<Spacecraft> = emptyList()

    private var isAscendingOrder = true // Track sorting order


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapsulesVH {
        val binding =
            CapsulesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CapsulesVH(binding)
    }

    override fun onBindViewHolder(holder: CapsulesVH, position: Int) {
        holder.bind(capsuleObjects[position])
    }

    override fun getItemCount(): Int {
        return capsuleObjects.size
    }

    fun setCapsuleData(capsules: List<Spacecraft>) {
        capsuleObjects = capsules
        notifyDataSetChanged()
    }

    // Function to toggle sorting order and apply sorting
    fun sortCapsulesByStatus() {
        isAscendingOrder = !isAscendingOrder // Toggle sorting order

        capsuleObjects = if (isAscendingOrder) {
            capsuleObjects.sortedBy { it.status?.name } // Sort in ascending order
        } else {
            capsuleObjects.sortedByDescending { it.status?.name} // Sort in descending order
        }
        notifyDataSetChanged()
    }

    fun sortCapsulesByType(){
        isAscendingOrder = !isAscendingOrder // Toggle sorting order

        capsuleObjects = if (isAscendingOrder) {
            capsuleObjects.sortedBy { it.name } // Sort in ascending order
        } else {
            capsuleObjects.sortedByDescending { it.name } // Sort in descending order
        }
        notifyDataSetChanged()
    }

    fun sortCapsulesBySerial(){
        isAscendingOrder = !isAscendingOrder // Toggle sorting order

        capsuleObjects = if (!isAscendingOrder) {

            capsuleObjects.sortedByDescending { it.serialNumber } // Sort in descending order

        } else {
            capsuleObjects.sortedBy { it.serialNumber } // Sort in ascending order
        }
        notifyDataSetChanged()
    }


    inner class CapsulesVH(binding: CapsulesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var capsuleIV: ImageView = binding.capsuleIv
        var capsuleType: TextView = binding.textCapsuleType
        var capsuleSerial: TextView = binding.textCapsuleSerial
        var capsuleSummary: TextView = binding.textCapsuleStatusSummary
        var capsuleStatus: TextView = binding.textCapsuleStatus

        fun bind(capsules: Spacecraft) {
            capsuleType.text = capsules.name
            capsuleSerial.text = capsules.serialNumber
            capsuleSummary.text = capsules.description
            capsuleStatus.text = capsules.status?.name

            capsuleIV.loadImage(capsules.spacecraftConfig?.imageUrl)

        }
    }
}
