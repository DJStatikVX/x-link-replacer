package xyz.djstatikvx.xlinkreplacer.domain.usecase

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Build
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import xyz.djstatikvx.xlinkreplacer.util.Constants.IMAGE_MIME_TYPE
import xyz.djstatikvx.xlinkreplacer.util.Constants.LINK_MIME_TYPE

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.VANILLA_ICE_CREAM])
class ReplaceLinkUseCaseTest {

    val mockXSourceLink =
        "https://x.com/Android/status/1897004092901446076?t=Bw-8_4-h24daA24f_96deA&s=19"
    val mockXTargetLink = "https://fixupx.com/Android/status/1897004092901446076"

    val mockYouTubeSourceLink = "https://www.youtube.com/watch?v=ni2pGkTPLDo"

    private lateinit var useCase: ReplaceLinkUseCase

    @Before
    fun setUp() {
        useCase = ReplaceLinkUseCase()
    }

    private fun mockShareIntent(text: String?, intentType: String = LINK_MIME_TYPE): Intent {
        return mockk<Intent>().apply {
            every { action } returns ACTION_SEND
            every { type } returns intentType
            every { getStringExtra(Intent.EXTRA_TEXT) } returns text
        }
    }

    @Test
    fun `should return intent with replaced X link when input is valid`() {
        // Given
        val mockIntent = mockShareIntent(mockXSourceLink)

        // When
        val resultIntent = useCase.processShareIntent(mockIntent)

        // Then
        assertNotNull(resultIntent)
        assertEquals(
            mockXTargetLink,
            resultIntent?.getStringExtra(Intent.EXTRA_TEXT)
        )
        assertEquals(ACTION_SEND, resultIntent?.action)
        assertEquals(LINK_MIME_TYPE, resultIntent?.type)
    }

    @Test
    fun `should return null when EXTRA_TEXT is missing`() {
        // Given
        val mockIntent = mockShareIntent(null)

        // When
        val resultIntent = useCase.processShareIntent(mockIntent)

        // Then
        assertNull(resultIntent)
    }

    @Test
    fun `should return null when input is not an X link`() {
        // Given
        val mockIntent = mockShareIntent(mockYouTubeSourceLink)

        // When
        val resultIntent = useCase.processShareIntent(mockIntent)

        // Then
        assertNull(resultIntent)
    }

    @Test
    fun `should return null when intent type is not plain text`() {
        // Given
        val mockIntent = mockShareIntent(mockXSourceLink, intentType = IMAGE_MIME_TYPE)

        // When
        val resultIntent = useCase.processShareIntent(mockIntent)

        // Then
        assertNull(resultIntent)
    }
}
