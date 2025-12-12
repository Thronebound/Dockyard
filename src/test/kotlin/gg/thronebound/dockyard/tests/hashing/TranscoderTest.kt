package gg.thronebound.dockyard.tests.hashing

import gg.thronebound.dockyard.codec.transcoder.CRC32CTranscoder
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.components.UseCooldownComponent
import io.github.dockyardmc.tide.codec.Codec
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TranscoderTest {

    data class TestHash(val component: DataComponent, val codec: Codec<out DataComponent>, val expectedHash: Int)

    val expectedHashes = listOf<TestHash>(
        TestHash(UseCooldownComponent(1.6f, "minecraft:test"), UseCooldownComponent.CODEC, 493336604),
    )

    @Suppress("UNCHECKED_CAST")
    @Test
    fun test() {
        expectedHashes.forEach { testHash ->
            val codec = testHash.codec as Codec<DataComponent>
            assertEquals(testHash.expectedHash, codec.encode(CRC32CTranscoder, testHash.component))
        }
    }
}