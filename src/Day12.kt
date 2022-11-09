fun main() {
	fun part1(input: List<String>) {


		val register = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0) // part one
		//val register = mutableMapOf("a" to 0, "b" to 0, "c" to 1, "d" to 0) // part two

		var steps = 0 // keep track of iteration through the list of instructions

		while (steps < input.size) {
			val words = input[steps].split(" ") // make a list of instructions per line
			when (words.first()) {
				"cpy" -> {
					// if the second instruction is a letter within the range we will assign the number from the other element to the first one
					if (words[1] in "a".."d") register[words[2]] = register[words[1]]!!.toInt()
					else register[words[2]] = words[1].toInt()
					steps++
				}

				"inc" -> {
					// we don't know for 100% we will gat the key we need
					register[words[1]] = register[words[1]]!! + 1
					steps++
				}

				"dec" -> {
					register[words[1]] = register[words[1]]!! - 1
					steps++
				}

				"jnz" -> {
					// check if the instruction is letter. If it is not, add number of steps
					steps += if (words[1] in "a".."d") {
						// if the letter contains zero add only one
						if (register[words[1]]!! > 0) words[2].toInt() else 1
					} else words[2].toInt()
				}

			}
		}
		println(register)
	}


	val input = readInput("Day12")
	part1(input)
}

