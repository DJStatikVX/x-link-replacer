package xyz.djstatikvx.xlinkreplacer

import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.createChooser
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import xyz.djstatikvx.xlinkreplacer.ui.ShareViewModel

@AndroidEntryPoint
class ShareActivity : ComponentActivity() {

    private val viewModel: ShareViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finish()
        processShareIntent()
    }

    private fun processShareIntent() {
        val newIntent = viewModel.handleIntent(intent)
        if (newIntent == null) {
            return showToastError()
        }

        val chooser =
            createChooser(newIntent, ContextCompat.getString(this, R.string.title_share_intent))
        chooser.flags = FLAG_ACTIVITY_NEW_TASK

        startActivity(chooser)
    }

    private fun showToastError() {
        Toast.makeText(
            this,
            ContextCompat.getString(this, R.string.error_invalid_link),
            LENGTH_SHORT
        ).show()
    }
}

