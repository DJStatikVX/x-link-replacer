package xyz.djstatikvx.xlinkreplacer

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_TEXT
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.createChooser
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

class ShareActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finish()
        processShareIntent(this, intent)
    }
}

private fun processShareIntent(context: Context, intent: Intent) {
    if (intent.action != ACTION_SEND || intent.type != "text/plain") {
        return showToastError(context)
    }

    val originalLink = intent.getStringExtra(EXTRA_TEXT)
    if (originalLink == null) {
        return showToastError(context)
    }

    val modifiedLink = replaceXLink(originalLink)
    if (modifiedLink == null) {
        return showToastError(context)
    }

    val newIntent = Intent(ACTION_SEND).apply {
        type = "text/plain"
        flags = FLAG_ACTIVITY_NEW_TASK
        putExtra(EXTRA_TEXT, modifiedLink)
    }

    val chooser =
        createChooser(newIntent, ContextCompat.getString(context, R.string.title_share_intent))
    chooser.flags = FLAG_ACTIVITY_NEW_TASK

    context.startActivity(chooser)
}

private fun replaceXLink(originalLink: String): String? {
    try {
        val uri = originalLink.toUri()
        if (uri.host != "x.com") {
            throw Exception()
        }

        return uri
            .buildUpon()
            .authority("fixupx.com")
            .build()
            .toString()
    } catch (_: Exception) {
        return null
    }
}

private fun showToastError(context: Context) {
    Toast.makeText(
        context,
        ContextCompat.getString(context, R.string.error_invalid_link),
        LENGTH_SHORT
    ).show()
}