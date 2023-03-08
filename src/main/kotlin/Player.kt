class Player(val name: String) {
    private var points = INITIAL_POINTS_WITH_PLAYER

    fun increasePoints(noOfPointsToBeIncreased: Int) {
        points += noOfPointsToBeIncreased
    }

    fun decreasePoints(noOfPointsToBeDecreased: Int) {
        points -= noOfPointsToBeDecreased
    }

    fun getPoints(): Int {
        return points
    }
}