class CarromBoard {
    private val totalBlackCoins = BLACK_COINS_ON_BOARD
    private val totalRedCoins = RED_COINS_ON_BOARD
    private var blackCoinsOnBoard = totalBlackCoins
    private var redCoinsOnBoard = totalRedCoins
    private var totalBlackCoinsInGame = totalBlackCoins
    private var totalRedCoinsInGame = totalRedCoins

    fun reduceBlackCoinsOnBoard(coinsTobeReduced: Int) {
        blackCoinsOnBoard -= coinsTobeReduced
    }

    fun reduceCoinInGame(coinType: CoinType) {
        if (coinType == CoinType.BLACK) {
            totalBlackCoinsInGame -= 1
        } else if (coinType == CoinType.RED) {
            totalRedCoinsInGame -= 1
        }
    }

    fun isEmpty(): Boolean {
        if (blackCoinsOnBoard == 0 && redCoinsOnBoard == 0) {
            return true
        }
        return false
    }

    fun getBlackCoinsInGame(): Int {
        return totalBlackCoinsInGame
    }

    fun getRedCoinsInGame(): Int {
        return totalRedCoinsInGame
    }
}