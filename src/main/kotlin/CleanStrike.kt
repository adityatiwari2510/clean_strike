class CleanStrike(players: List<Player>, carromBoard: CarromBoard, gameManager: GameManager) {
    private var gameStatus = GameStatus.NOT_STARTED

    fun play() {
        initializeGame()
        while (checkIfGameIsRunning()) {

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
}