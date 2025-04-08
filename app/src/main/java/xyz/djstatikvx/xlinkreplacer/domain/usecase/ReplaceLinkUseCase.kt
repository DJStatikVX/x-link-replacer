package xyz.djstatikvx.xlinkreplacer.domain.usecase

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_TEXT
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.core.net.toUri
import xyz.djstatikvx.xlinkreplacer.util.Constants.LINK_MIME_TYPE
import xyz.djstatikvx.xlinkreplacer.util.Constants.X_SOURCE_HOST
import xyz.djstatikvx.xlinkreplacer.util.Constants.X_TARGET_HOST
import javax.inject.Inject

class ReplaceLinkUseCase @Inject constructor() {
    fun processShareIntent(intent: Intent): Intent? {
        if (intent.action != ACTION_SEND || intent.type != LINK_MIME_TYPE) {
            return null
        }

        val originalLink = intent.getStringExtra(EXTRA_TEXT)
        if (originalLink == null) {
            return null
        }

        val modifiedLink = replaceXLink(originalLink)
        if (modifiedLink == null) {
            return null
        }

        val newIntent = Intent(ACTION_SEND).apply {
            type = LINK_MIME_TYPE
            flags = FLAG_ACTIVITY_NEW_TASK
            putExtra(EXTRA_TEXT, modifiedLink)
        }

        return newIntent
    }

    private fun replaceXLink(originalLink: String): String? {
        try {
            val uri = originalLink.toUri()
            if (uri.host != X_SOURCE_HOST) {
                throw Exception()
            }

            return uri
                .buildUpon()
                .authority(X_TARGET_HOST)
                .build()
                .toString()
        } catch (_: Exception) {
            return null
        }
    }
}