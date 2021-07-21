package ie2a_2200078.eventwork05

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager

fun Context.checkMultiplePermission(permissions: List<String>) : Boolean {
    return permissions.all {
        checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
    }

}