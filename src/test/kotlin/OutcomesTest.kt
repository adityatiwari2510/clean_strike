import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class OutcomesTest {
    private lateinit var outcomeExecuteRequest: OutcomeExecuteRequest
    private lateinit var bo: ByteArrayOutputStream
    private fun buildOutcomeExecutionRequest(coinType: CoinType): OutcomeExecuteRequest {
        val player = Player("Player - 01")
        val carromBoard = CarromBoard()
        return OutcomeExecuteRequest(player, carromBoard, coinType)
    }

    private fun setupToRedirectSystemOutputToBuffer() {
        bo = ByteArrayOutputStream()
        System.setOut(PrintStream(bo))
    }

    private fun reduceAllBlackCoinsFromBoard() {
        for (i in 1..BLACK_COINS_ON_BOARD) {
            outcomeExecuteRequest.carromBoard.reduceCoinOnBoard(CoinType.BLACK)
        }
    }

    private fun reduceAllRedCoinsFromBoard() {
        for (i in 1..RED_COINS_ON_BOARD) {
            outcomeExecuteRequest.carromBoard.reduceCoinOnBoard(CoinType.RED)
        }
    }

    private fun increasePlayersFoulsToOneLessThanMaxLimit() {
        val player = outcomeExecuteRequest.player
        for(foul in 1..(MAX_FOULS_ALLOWED-1)){
            player.updateFouls()
        }
    }

    private fun increasePlayersPocketMissToOneLessThanMaxLimit() {
        val player = outcomeExecuteRequest.player
        for(foul in 1..(MAX_CHANCE_FOR_NOT_POCKETING_COIN-1)){
            player.updatePocketMiss()
        }
    }

    @Test
    fun `execute for strike should return false and if black coins are not present on board`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.BLACK)
        reduceAllBlackCoinsFromBoard()
        setupToRedirectSystemOutputToBuffer()
        val expectedErrMsg = "Oops!! Black coins are not enough to play this outcome.\n"

        val response = Outcomes.STRIKE.execute(outcomeExecuteRequest)

        bo.flush()
        val errMsg = String(bo.toByteArray())
        assertEquals(expectedErrMsg, errMsg)
        assertFalse(response)
    }

    @Test
    fun `execute for strike should return true if black coins are present on board and increase the player points by GAIN_POINTS_FOR_STRIKE`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.BLACK)
        val expectedPlayerPoints = GAIN_POINTS_FOR_STRIKE

        val response = Outcomes.STRIKE.execute(outcomeExecuteRequest)

        val player = outcomeExecuteRequest.player
        assertEquals(expectedPlayerPoints, player.getPoints())
        assertTrue(response)
    }

    @Test
    fun `execute for multistrike should return false if black coins are not present on board`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.BLACK)
        reduceAllBlackCoinsFromBoard()
        setupToRedirectSystemOutputToBuffer()
        val expectedErrMsg = "Oops!! Black coins are not enough to play this outcome.\n"

        val response = Outcomes.MULTI_STRIKE.execute(outcomeExecuteRequest)

        bo.flush()
        val errMsg = String(bo.toByteArray())
        assertEquals(expectedErrMsg, errMsg)
        assertFalse(response)
    }

    @Test
    fun `execute for multistrike should return true if black coins are present on board and increase the player points by GAIN_POINTS_FOR_MULTISTRIKE`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.BLACK)
        val expectedPlayerPoints = GAIN_POINTS_FOR_MULTISTRIKE

        val response = Outcomes.MULTI_STRIKE.execute(outcomeExecuteRequest)

        val player = outcomeExecuteRequest.player
        assertEquals(expectedPlayerPoints, player.getPoints())
        assertTrue(response)
    }

    @Test
    fun `execute for red strike should return false if red coins are not present on board`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.RED)
        reduceAllRedCoinsFromBoard()
        setupToRedirectSystemOutputToBuffer()
        val expectedErrMsg = "Oops!! Red coins are not enough to play this outcome.\n"

        val response = Outcomes.RED_STRIKE.execute(outcomeExecuteRequest)

        bo.flush()
        val errMsg = String(bo.toByteArray())
        assertEquals(expectedErrMsg, errMsg)
        assertFalse(response)
    }

    @Test
    fun `execute for red strike should return true if red coins are present on board and increase the player points by GAIN_POINTS_FOR_RED_STRIKE`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.BLACK)
        val expectedPlayerPoints = GAIN_POINTS_FOR_RED_STRIKE

        val response = Outcomes.RED_STRIKE.execute(outcomeExecuteRequest)

        val player = outcomeExecuteRequest.player
        assertEquals(expectedPlayerPoints, player.getPoints())
        assertTrue(response)
    }

    @Test
    fun `execute for striker strike should return true and decrease player points by POINTS_LOSE_FOR_STRIKER_STRIKE if player does not have any foul and successive pocket miss`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.BLACK)
        val player = outcomeExecuteRequest.player
        player.increasePoints(POINTS_LOSE_FOR_STRIKER_STRIKE)
        val expectedPlayerPoints = 0
        val expectedSuccessivePocketMiss = 1
        val expectedFouls = 1

        val response = Outcomes.STRIKER_STRIKE.execute(outcomeExecuteRequest)

        assertEquals(expectedPlayerPoints, player.getPoints())
        assertEquals(expectedSuccessivePocketMiss, player.getSuccesivePocketMiss())
        assertEquals(expectedFouls, player.getFouls())
        assertTrue(response)
    }

    @Test
    fun `execute for striker strike should return true and decrease player points by (POINTS_LOSE_FOR_STRIKER_STRIKE + POINTS_LOSE_FOR_NOT_POCKETING_COIN + ADDITIONAL_POINTS_LOSE_FOR_FOUL) if player has foul equal to MAX_FOULS_ALLOWED and successive pocket miss equal to MAX_CHANCE_FOR_NOT_POCKETING_COIN`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.BLACK)
        increasePlayersFoulsToOneLessThanMaxLimit()
        increasePlayersPocketMissToOneLessThanMaxLimit()
        val player = outcomeExecuteRequest.player
        player.increasePoints(POINTS_LOSE_FOR_STRIKER_STRIKE + POINTS_LOSE_FOR_NOT_POCKETING_COIN + ADDITIONAL_POINTS_LOSE_FOR_FOUL)
        val expectedPlayerPoints = 0
        val expectedSuccessivePocketMiss = 0
        val expectedFouls = 0

        val response = Outcomes.STRIKER_STRIKE.execute(outcomeExecuteRequest)

        assertEquals(expectedSuccessivePocketMiss, player.getSuccesivePocketMiss())
        assertEquals(expectedFouls, player.getFouls())
        assertEquals(expectedPlayerPoints, player.getPoints())
        assertTrue(response)
    }

    @Test
    fun `execute for defunct coin should return false if black coins are not present on board and input request has coinType BLACK`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.BLACK)
        reduceAllBlackCoinsFromBoard()
        setupToRedirectSystemOutputToBuffer()
        val expectedErrMsg = "Oops!! Black coins are not enough to play this outcome.\n"

        val response = Outcomes.DEFUNCT_COIN.execute(outcomeExecuteRequest)

        bo.flush()
        val errMsg = String(bo.toByteArray())
        assertEquals(expectedErrMsg, errMsg)
        assertFalse(response)
    }

    @Test
    fun `execute for defunct coin should return false if red coins are not present on board and input request has coinType RED`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.RED)
        reduceAllRedCoinsFromBoard()
        setupToRedirectSystemOutputToBuffer()
        val expectedErrMsg = "Oops!! Red coins are not enough to play this outcome.\n"

        val response = Outcomes.DEFUNCT_COIN.execute(outcomeExecuteRequest)

        bo.flush()
        val errMsg = String(bo.toByteArray())
        assertEquals(expectedErrMsg, errMsg)
        assertFalse(response)
    }

    @Test
    fun `execute for defunct coin should return true and decrease player points by POINTS_LOSE_FOR_DEFUNCT_COIN if player does not have any foul and successive pocket miss and coinType should be reduced from board if red coins are present on board and input request has coinType RED`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.RED)
        val player = outcomeExecuteRequest.player
        player.increasePoints(POINTS_LOSE_FOR_DEFUNCT_COIN)
        val expectedPlayerPoints = 0
        val expectedSuccessivePocketMiss = 1
        val expectedFouls = 1

        val response = Outcomes.DEFUNCT_COIN.execute(outcomeExecuteRequest)

        assertEquals(expectedPlayerPoints, player.getPoints())
        assertEquals(expectedSuccessivePocketMiss, player.getSuccesivePocketMiss())
        assertEquals(expectedFouls, player.getFouls())
        assertTrue(response)
    }

    @Test
    fun `execute for defunct coin should return true and decrease player points by POINTS_LOSE_FOR_DEFUNCT_COIN if player does not have any foul and successive pocket miss and coinType should be reduced from board if black coins are present on board and input request has coinType BLACK`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.BLACK)
        val player = outcomeExecuteRequest.player
        player.increasePoints(POINTS_LOSE_FOR_DEFUNCT_COIN)
        val expectedPlayerPoints = 0
        val expectedSuccessivePocketMiss = 1
        val expectedFouls = 1

        val response = Outcomes.DEFUNCT_COIN.execute(outcomeExecuteRequest)

        assertEquals(expectedPlayerPoints, player.getPoints())
        assertEquals(expectedSuccessivePocketMiss, player.getSuccesivePocketMiss())
        assertEquals(expectedFouls, player.getFouls())
        assertTrue(response)
    }

    @Test
    fun `execute for none should return true if player does not have successive pocket miss`() {
        outcomeExecuteRequest = buildOutcomeExecutionRequest(CoinType.BLACK)
        val player = outcomeExecuteRequest.player
        val expectedPlayerPoints = 0
        val expectedSuccessivePocketMiss = 1

        val response = Outcomes.NONE.execute(outcomeExecuteRequest)

        assertEquals(expectedPlayerPoints, player.getPoints())
        assertEquals(expectedSuccessivePocketMiss, player.getSuccesivePocketMiss())
        assertTrue(response)
    }
}