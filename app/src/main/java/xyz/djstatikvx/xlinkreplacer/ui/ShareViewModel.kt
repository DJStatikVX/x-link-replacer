package xyz.djstatikvx.xlinkreplacer.ui

import android.content.Intent
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.djstatikvx.xlinkreplacer.domain.usecase.ReplaceLinkUseCase
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val replaceLinkUseCase: ReplaceLinkUseCase
) : ViewModel() {
    fun handleIntent(intent: Intent): Intent? {
        return replaceLinkUseCase.processShareIntent(intent)
    }
}