import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PlayerTest {
    @Test
    fun `increasePoints should increase the points by given noOfPoints provided`() {
        val player = Player("Player-01")

        player.increasePoints(3)

        val expectedPoints = 3
        assertEquals(expectedPoints, player.getPoints())
    }

    @Test
    fun `decreasePoints should decrease the points by given noOfPoints provided`() {
        val player = Player("Player-01")

        player.increasePoints(3)

        val expectedPoints = 3
        assertEquals(expectedPoints, player.getPoints())
    }

    @Test
    fun `markPlaying should modify the state of player to playing`() {
        val player = Player("Player-01")

        player.markPlaying()

        assertTrue(player.checkIfPlaying())
    }

    @Test
    fun `markWaitForNextTurn should modify the state of player to not playing`() {
        val player = Player("Player-01")

        player.markWaitForNextTurn()

        assertFalse(player.checkIfPlaying())
    }

    @Test
    fun `resetSuccessivePocketMiss should modify the successivePocketMiss of player to ZERO`() {
        val player = Player("Player-01")
        player.updatePocketMiss()

        player.resetSuccessivePocketMiss()

        assertTrue(player.getSuccesivePocketMiss() == 0)
    }

    @Test
    fun `updatePocketMiss should modify the successivePocketMiss of player by One`() {
        val player = Player("Player-01")

        player.updatePocketMiss()

        assertTrue(player.getSuccesivePocketMiss() == 1)
    }

    @Test
    fun `resetFouls should modify the fouls of player to ZERO`() {
        val player = Player("Player-01")
        player.updateFouls()

        player.resetFouls()

        assertTrue(player.getFouls() == 0)
    }

    @Test
    fun `updateFouls should modify the fouls of player by One`() {
        val player = Player("Player-01")

        player.updateFouls()

        assertTrue(player.getFouls() == 1)
    }

    @Test
    fun `getGamePoints should return the points of player`() {
        val player = Player("Player-01")

        val response = player.getGamePoints()

        assertTrue(response == 0)
    }
}