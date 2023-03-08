enum class Outcomes(val displayName: String) {
    STRIKE("Strike") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            val currentPlayer = outcomeExecuteRequest.player
            val carromBoard = outcomeExecuteRequest.carromBoard

            if (carromBoard.getBlackCoinsInGame() > 0) {
                currentPlayer.increasePoints(GAIN_POINTS_FOR_STRIKE)
                currentPlayer.resetSuccessivePocketMiss()

                carromBoard.reduceBlackCoinsOnBoard(BOARD_COINS_REDUCED_FOR_STRIKE)
                return true
            }

            println("Black coins are not available on board.")
            return false
        }
    },
    MULTI_STRIKE("Multistrike") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            TODO("Not yet implemented")
        }
    },
    RED_STRIKE("Red strike") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            TODO("Not yet implemented")
        }
    },
    STRIKER_STRIKE("Striker strike") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            TODO("Not yet implemented")
        }
    },
    DEFUNCT_COIN("Defunct coin") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            TODO("Not yet implemented")
        }
    },
    NONE("None") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            TODO("Not yet implemented")
        }
    };

    abstract fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean
}