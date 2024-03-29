package com.github.drewstephensdesigns.astrofeed.utils

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

fun Fragment.openWebLink(link: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
}