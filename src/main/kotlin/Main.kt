fun main(args: Array<String>) {
    val players = listOf<Player>(Player("Player - 01"), Player("Player - 02"))
    val game = CleanStrike(players,CarromBoard(),GameManager())
    game.play()
}