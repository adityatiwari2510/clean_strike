class GameManager {
    private val playersFoulStats = mutableMapOf<Player, Int>()
    private val playersSuccessivePocketMissStats = mutableMapOf<Player, Int>()

    fun promptOutcomes(playerName: String): Outcomes {
        var consoleResponse: Int
        val consolePromptMessage = buildOutcomesPromptMessage(playerName)
        val outcomes = Outcomes.values()

        do {
            try {
                print(consolePromptMessage)
                consoleResponse = readlnOrNull()?.toInt()!!

                if (consoleResponse <= 0 || consoleResponse > outcomes.size) {
                    println("Invalid outcome.")
                }
            } catch (exception: NumberFormatException) {
                println("Enter outcome in numbers assigned to the respective option.\ne.g.: For Strike enter 1")
                consoleResponse = 0
            }
        } while (consoleResponse <= 0 || consoleResponse > outcomes.size)

        return Outcomes.valueOf(outcomes[consoleResponse].toString())
    }

    private fun buildOutcomesPromptMessage(playerName: String): String {
        var promptMessage = "$playerName: Choose an outcome from the list below\n"
        for (outcome in Outcomes.values()) {
            promptMessage += (outcome.ordinal + 1).toString() + ". " + outcome.displayName + "\n"
        }
        promptMessage += ">"
        return promptMessage
    }

    fun findPlayerHavingTurn(players: List<Player>): Player {
        val firstPlayer = players[0]
        val secondPlayer = players[1]
        if (firstPlayer.checkIfPlaying()) {
            firstPlayer.markWaitForNextTurn()
            secondPlayer.markPlaying()
            return secondPlayer
        }
        secondPlayer.markWaitForNextTurn()
        firstPlayer.markPlaying()
        return firstPlayer
    }

    fun promptCoinTypes(playerName: String): CoinType {
        var consoleResponse: Int
        val consolePromptMessage = buildCoinTypesPromptMessage(playerName)
        val coinTypes = CoinType.values()

        do {
            try {
                print(consolePromptMessage)
                consoleResponse = readlnOrNull()?.toInt()!!

                if (consoleResponse > coinTypes.size) {
                    println("Invalid selection.")
                }
            } catch (exception: NumberFormatException) {
                println("Enter outcome in numbers assigned to the respective option.\ne.g.: For Black enter 1")
                consoleResponse = 0
            }
        } while (consoleResponse <= 0 || consoleResponse > coinTypes.size)

        return CoinType.valueOf(coinTypes[consoleResponse].toString())
    }

    private fun buildCoinTypesPromptMessage(playerName: String): String {
        var promptMessage = "$playerName: Select the Defunct Coin Type from list below\n"
        for (coin in CoinType.values()) {
            promptMessage += (coin.ordinal + 1).toString() + ". " + coin.displayName + "\n"
        }
        promptMessage += ">"
        return promptMessage
    }

    fun checkIfGameIsWon(players: List<Player>): Boolean {
        val highestScorePlayer = getHighestScorePlayer(players)
        val lowestScorePlayer = getLowestScorePlayer(players)

        val leadPointByHighestScorePlayerWithRespectToLowestScorePlayer =
            highestScorePlayer.getGamePoints() - lowestScorePlayer.getGamePoints()
        if (highestScorePlayer.getGamePoints() >= MINIMUM_GAME_POINTS_FOR_HIGHEST_SCORE_PLAYER || leadPointByHighestScorePlayerWithRespectToLowestScorePlayer >= MINIMUM_LEAD_POINTS_FOR_HIGHEST_SCORE_PLAYER) {
            return true
        }
        return false
    }

    private fun getHighestScorePlayer(players: List<Player>): Player {
        val playerFirst = players[0]
        val playerSecond = players[1]
        if (playerFirst.getGamePoints() > playerSecond.getGamePoints()) {
            return playerFirst
        }
        return playerSecond
    }

    private fun getLowestScorePlayer(players: List<Player>): Player {
        val playerFirst = players[0]
        val playerSecond = players[1]
        if (playerFirst.getGamePoints() <= playerSecond.getGamePoints()) {
            return playerFirst
        }
        return playerSecond
    }

}