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
            val currentPlayer = outcomeExecuteRequest.player
            val carromBoard = outcomeExecuteRequest.carromBoard

            if (carromBoard.getBlackCoinsInGame() > 1) {
                currentPlayer.increasePoints(GAIN_POINTS_FOR_MULTISTRIKE)
                currentPlayer.resetSuccessivePocketMiss()

                carromBoard.reduceBlackCoinsOnBoard(BOARD_COINS_REDUCED_FOR_MULTISTRIKE)
                return true
            }

            println("Oops!! Black coins are not enough to play this outcome.")
            return false
        }
    },
    RED_STRIKE("Red strike") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            val currentPlayer = outcomeExecuteRequest.player
            val carromBoard = outcomeExecuteRequest.carromBoard

            if (carromBoard.getRedCoinsInGame() > 0) {
                currentPlayer.increasePoints(GAIN_POINTS_FOR_RED_STRIKE)
                currentPlayer.resetSuccessivePocketMiss()
                return true
            }

            println("Oops!! Red coins are not enough to play this outcome.")
            return false
        }
    },
    STRIKER_STRIKE("Striker strike") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            val currentPlayer = outcomeExecuteRequest.player

            executePocketMiss(currentPlayer)
            executeFoulMiss(currentPlayer)
            currentPlayer.decreasePoints(POINTS_LOSE_FOR_STRIKER_STRIKE)
            return true
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

    fun executePocketMiss(player: Player) {
        player.updatePocketMiss()
        if (player.getSuccesivePocketMiss() == MAX_CHANCE_FOR_NOT_POCKETING_COIN) {
            player.decreasePoints(POINTS_LOSE_FOR_NOT_POCKETING_COIN)
            player.resetSuccessivePocketMiss()
        }
    }

    fun executeFoulMiss(player: Player) {
        player.updateFouls()
        if (player.getFouls() == MAX_FOULS_ALLOWED) {
            player.decreasePoints(ADDITIONAL_POINTS_LOSE_FOR_FOUL)
            player.resetSuccessivePocketMiss()
        }
    }
}