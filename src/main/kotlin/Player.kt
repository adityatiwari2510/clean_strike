class Player(val name: String) {
    private var points = INITIAL_POINTS_WITH_PLAYER
    private var isPlaying: Boolean = false

    fun increasePoints(noOfPointsToBeIncreased: Int) {
        points += noOfPointsToBeIncreased
    }

    fun decreasePoints(noOfPointsToBeDecreased: Int) {
        points -= noOfPointsToBeDecreased
    }

    fun getPoints(): Int {
        return points
    }

    fun markPlaying() {
        isPlaying = true
    }

    fun markWaitForNextTurn() {
        isPlaying = false
    }

    fun checkIfPlaying(): Boolean {
        return isPlaying
    }

    fun getGamePoints(): Int {
        return points
    }

}