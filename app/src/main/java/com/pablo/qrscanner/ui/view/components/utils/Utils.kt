package com.pablo.qrscanner.ui.view.components.utils


import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.pablo.qrscanner.ui.view.components.utils.QrScannerApplication.Companion.prefs
import java.text.SimpleDateFormat
import java.util.*


class Utils {
    companion object {

        fun getCurrentDate(): String {
            return SimpleDateFormat("d MMM yyyy HH:mm").format(Date())
        }

        fun copyToClipboard(context: Context, text: String) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("txtClipboard", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show()
        }

        fun shareTo(context: Context, text: String) {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(shareIntent, "Compartir por"))
        }

        fun goTo(context: Context, clave: String, valor: String, clase: Activity) {
            val intent = Intent(context, clase::class.java).apply { putExtra(clave, valor) }
            context.startActivity(intent)

            // Si la opción está activada
            if (prefs.isClipboardEnabled()) copyToClipboard(context, valor)
        }

        fun generateQrCode(text: String): Bitmap {
            val barcodeEncoder = BarcodeEncoder()
            return barcodeEncoder.encodeBitmap(
                text,
                BarcodeFormat.QR_CODE,
                800,
                800
            )
        }

        fun isPermissionAccepted(context: Context, permiso: String): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                permiso
            ) == PackageManager.PERMISSION_GRANTED
        }

        fun requestPermission(permiso: String, activity: Activity, requestCode: Int) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permiso)) {
                // Usuario ha rechazado el permiso
                Toast.makeText(
                    activity.applicationContext,
                    "Debes aceptar el permiso desde ajustes",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // Pedimos permiso
                ActivityCompat.requestPermissions(activity, arrayOf(permiso), requestCode)
            }
        }

        fun openPhoneIntent(context: Context, telefono: String) {
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel:$telefono")
            context.startActivity(intent)
        }

        fun openEmailIntent(context: Context, email: String) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Necesitas una app para enviar correo", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}