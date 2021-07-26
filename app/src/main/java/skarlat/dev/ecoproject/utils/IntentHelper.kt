package skarlat.dev.ecoproject.utils

import android.content.Intent
import android.net.Uri

object IntentHelper {
    fun writeFromTo(subject: String, to: String) : Intent {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.run {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            Intent.CATEGORY_APP_EMAIL
            data = Uri.parse("mailto:")
        }
        return intent
    }
}