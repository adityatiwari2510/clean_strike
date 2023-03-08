enum class Outcomes(val displayName: String) {
    STRIKE("Strike") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            TODO("Not yet implemented")
        }
    },
    MULTI_STRIKE("Multistrike") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            TODO("Not yet implemented")
        }
    },
    RED_STRIKE("Red strike") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            TODO("Not yet implemented")
        }
    },
    STRIKER_STRIKE("Striker strike") {
        override fun execute(outcomeExecuteRequest: OutcomeExecuteRequest): Boolean {
            TODO("Not yet implemented")
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
}