enum class Outcomes(val displayName: String) {
    STRIKE("Strike") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            val currentPlayer = outcomeExecuteRequest.player
            val carromBoard = outcomeExecuteRequest.carromBoard

            if (carromBoard.getBlackCoinsOnBoard() > 0) {
                currentPlayer.increasePoints(GAIN_POINTS_FOR_STRIKE)
                currentPlayer.resetSuccessivePocketMiss()

                carromBoard.reduceBlackCoinsOnBoard(BOARD_COINS_REDUCED_FOR_STRIKE)
                return true
            }

            println("Oops!! Black coins are not enough to play this outcome.")
            return false
        }
    },
    MULTI_STRIKE("Multistrike") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            val currentPlayer = outcomeExecuteRequest.player
            val carromBoard = outcomeExecuteRequest.carromBoard

            if (carromBoard.getBlackCoinsOnBoard() > 1) {
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

            if (carromBoard.getRedCoinsOnBoard() > 0) {
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
            val coinType = outcomeExecuteRequest.coinType
            val currentPlayer = outcomeExecuteRequest.player
            val carromBoard = outcomeExecuteRequest.carromBoard

            if (coinType == CoinType.BLACK && carromBoard.getBlackCoinsOnBoard() == 0) {
                println("Oops!! Black coins are not enough to play this outcome.")
                return false
            } else if (coinType == CoinType.RED && carromBoard.getRedCoinsOnBoard() == 0) {
                println("Oops!! Red coins are not enough to play this outcome.")
                return false
            } else {
                executePocketMiss(currentPlayer)
                executeFoulMiss(currentPlayer)
                currentPlayer.decreasePoints(POINTS_LOSE_FOR_DEFUNCT_COIN)
                carromBoard.reduceCoinOnBoard(coinType)
                return true
            }
        }
    },
    NONE("None") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            val currentPlayer = outcomeExecuteRequest.player

            executePocketMiss(currentPlayer)
            return true
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
            player.resetFouls()
        }
    }
}