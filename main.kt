import kotlin.system.exitProcess

val levels = listOf(1, 2, 3, 4)
const val secondsToBeatLevel = 8.0

fun instructions() {
    println("SPEED TYPING GAME")
    println("You will face a series of levels attempting to type the appropriate keyboard key as fast as possible.")
    println("(Press ENTER to continue...)")
    readlnOrNull()
    println(
            """Each level will have different keys that may appear: 
        Easy:                  abcdefghijklmnopqrstuvwxyz
        Intermediate:          abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
        Hard:                  bcdfghjklmnpqrvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890
        Expert:                BCDFGHJKLMNPQRSTVWXYZ1234567890!@#$%^&*()`~-_=+[]{}\\|;:'",.<>/?"""
    )
    println("(Press ENTER to continue...)")
    readlnOrNull()
    println("Press the appropriate series of keys within $secondsToBeatLevel seconds in order to advance to the next level.")
    println("After pressing a key you must press Enter to submit your guess")
    println("Press Enter to begin level 1...")
    readlnOrNull()
}

fun endGame(totalGameTime: Double, level: Int) {
    val medianTime = totalGameTime / levels.size
    println("Fantastic! You completed each of the $level levels with a total time of %.2f seconds".format(totalGameTime))
    println("Average time: %.2f seconds".format(medianTime))
}

fun gameFoundation(keyOptions: String, level: Int): Double {
    var count = 0
    var timeTakenForLevel = 0.0
    var totalGameTime = 0.0
    // The player must guess correctly 5 keys to proceed.
    while (count < 5) {
        val targetKey = keyOptions.random()
        println("Press the key: $targetKey")

        // Start the timer when the target key appears on screen.
        val startTime = System.currentTimeMillis()
        // Ensures that userInput is never null.
        val userInput = readlnOrNull() ?: ""

        // After they guess stop the timer and calculate the time it took for their guess.
        val endTime = System.currentTimeMillis()
        val timeTakenForGuess = (endTime - startTime) / 1000.0
        // Check and see if the player presses the correct key.
        // If they press correct key show them how much time that guess took.
        if (userInput == targetKey.toString()) {
            count++
            timeTakenForLevel += timeTakenForGuess
            println("Correct! Time taken: %.2f sec".format(timeTakenForGuess))
        } else {
            timeTakenForLevel += timeTakenForGuess
            println("Incorrect! Try again.")
        }

        // Calculate if the player exceeded the seconds required to guess during the level.
        // If they exceed the time limit have them try again.
        if (count > 4 && timeTakenForLevel > secondsToBeatLevel) {
            println("Level $level FAILED. You exceeded the time requirement.")
            println("Total time taken: %.2f sec".format(timeTakenForLevel))
            println("Press Enter to try again...")
            readlnOrNull()
            // Reset level time and count
            timeTakenForLevel = 0.0
            count = 0
        }
    }
    // After the player has finished the level calculate how much time it took them and determine if they can advance
    // to the next level.
    totalGameTime += timeTakenForLevel
    // If there are still levels left to be played show total level time and allow them to continue
    if (level < 4) {
        println("Level $level COMPLETE! Total time taken: %.2f sec".format(timeTakenForLevel))
        println("Press Enter to begin level ${level + 1}...")
        readlnOrNull()
        // If the user completed each of the levels just show time and allow them to continue to final screen.
    } else {
        println("Level $level complete! Total time taken: %.2f sec".format(timeTakenForLevel))
        println("Press Enter to continue...")
        readlnOrNull()
    }

    return totalGameTime
}

fun easyGame(level: Int): Double = gameFoundation("abcdefghijklmnopqrstuvwxyz", level)

fun intermediateGame(level: Int): Double = gameFoundation("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", level)

fun hardGame(level: Int): Double = gameFoundation("bcdfghjklmnpqrvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12345678901234567890", level)

fun expertGame(level: Int): Double = gameFoundation("BCDFGHJKLMNPQRSTVWXYZ1234567890!@#$%^&*()`~-_=+[]{}\\|;:'\",.<>/?", level)

fun main() {
    while (true) {
        instructions()
        var totalGameTime = 0.0
        // Iterate through each of the levels in the levels list.
        for (level in levels) {
            totalGameTime += when (level) {
                1 -> easyGame(level)
                2 -> intermediateGame(level)
                3 -> hardGame(level)
                4 -> expertGame(level)
                else -> {
                    println("Invalid option, please try again.")
                    0.0
                }
            }
        }
        // After the player finishes each of the levels proceed to the end game.
        endGame(totalGameTime, levels.last())
        // Allow the player to play again or quit.
        println("Do you want to play again?")
        println("Enter 'Y' to play again, Enter any other key to quit: ")
        if (readlnOrNull()?.lowercase() != "y") {
            break
        }
    }
    exitProcess(0)
}
