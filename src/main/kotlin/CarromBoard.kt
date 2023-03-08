class CarromBoard {
    private val totalBlackCoins = BLACK_COINS_ON_BOARD
    private val totalRedCoins = RED_COINS_ON_BOARD
    private var blackCoinsOnBoard = totalBlackCoins
    private var redCoinsOnBoard = totalRedCoins

    fun reduceBlackCoinsOnBoard(coinsTobeReduced: Int) {
        blackCoinsOnBoard -= coinsTobeReduced
    }

    fun reduceCoinOnBoard(coinType: CoinType) {
        if (coinType == CoinType.BLACK) {
            blackCoinsOnBoard -= 1
        } else if (coinType == CoinType.RED) {
            redCoinsOnBoard -= 1
        }
    }

    fun isEmpty(): Boolean {
        if (blackCoinsOnBoard == 0 && redCoinsOnBoard == 0) {
            return true
        }
        return false
    }

    fun getBlackCoinsOnBoard(): Int {
        return blackCoinsOnBoard
    }

    fun getRedCoinsOnBoard(): Int {
        return redCoinsOnBoard
    }
}