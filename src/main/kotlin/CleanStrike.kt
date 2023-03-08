class CleanStrike(
    private val players: List<Player>,
    val carromBoard: CarromBoard,
    private val gameManager: GameManager
) {
    private var gameStatus = GameStatus.NOT_STARTED

    fun play() {
        initializeGame()
        while (checkIfGameIsRunning()) {
            val currentPlayer = gameManager.findPlayerHavingTurn(players)
            val selectedOutcome = gameManager.promptOutcomes(currentPlayer.name)
            executeOutcome(selectedOutcome, currentPlayer)
            updateGameStatus()
        }
        if (gameStatus == GameStatus.WIN) {
            promptGameWinMessage()
        } else if (gameStatus == GameStatus.DRAW) {
            promptGameDrawMessage()
        }
    }

    private fun initializeGame() {
        gameStatus = GameStatus.RUNNING
    }

    private fun checkIfGameIsRunning(): Boolean {
        return gameStatus == GameStatus.RUNNING
    }

    private fun promptGameWinMessage() {
        val winner = findWinner()
        val loser = findLoser()
        println("${winner.name} won the game. Final Score: ${winner.getPoints()}-${loser.getPoints()}")
    }

    private fun findWinner(): Player {
        return Player("Player - 01")
    }

    private fun findLoser(): Player {
        return Player("Player - 02")
    }

    private fun promptGameDrawMessage() {
        println("Draw Game!!")
    }

    private fun executeOutcome(selectedOutcome: Outcomes, currentPlayer: Player) {
        var outcomeResponse: Boolean
        do {
            if (selectedOutcome == Outcomes.DEFUNCT_COIN) {
                val selectedCoinType = gameManager.promptCoinTypes(currentPlayer.name)
                outcomeResponse =
                    selectedOutcome.execute(OutcomeExecuteRequest(currentPlayer, carromBoard, selectedCoinType))
            } else {
                outcomeResponse =
                    selectedOutcome.execute(OutcomeExecuteRequest(currentPlayer, carromBoard, CoinType.BLACK))
            }
        } while (!outcomeResponse)
    }

    private fun updateGameStatus() {
        if (gameManager.checkIfGameIsWon(players)) {
            gameStatus = GameStatus.WIN
        }
        if (carromBoard.isEmpty()) {
            gameStatus = GameStatus.DRAW
        }
    }
}