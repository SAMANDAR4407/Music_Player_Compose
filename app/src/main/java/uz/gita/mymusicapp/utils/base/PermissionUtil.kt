package uz.gita.mymusicapp.utils.base

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 13:12
 */

fun Context.checkPermissions(array: List<String>, blockSuccess: () -> Unit){
    Dexter.withContext(this).withPermissions(array).withListener(object : MultiplePermissionsListener{
        override fun onPermissionsChecked(report: MultiplePermissionsReport) {
            if (report.areAllPermissionsGranted()){
                blockSuccess.invoke()
            } else {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:${this@checkPermissions.packageName}")
                startActivity(intent)
            }
        }
        override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, p1: PermissionToken?) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:${this@checkPermissions.packageName}")
            startActivity(intent)
        }
    }).check()
}