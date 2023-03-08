class Player(val name: String) {
    private var points = INITIAL_POINTS_WITH_PLAYER
    private var isPlaying: Boolean = false
    private var fouls: Int = 0
    private var succesivePocketMiss = 0

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

    fun resetSuccessivePocketMiss() {
        succesivePocketMiss = 0
    }

    fun updatePocketMiss() {
        succesivePocketMiss += 1
    }

    fun getSuccesivePocketMiss(): Int {
        return succesivePocketMiss
    }

    fun updateFouls() {
        fouls += 1
    }

    fun getFouls(): Int {
        return fouls
    }

    fun resetFouls() {
        fouls = 0
    }

}