# bouceka-aoc-1

# [Advent of Code 2016 - Day 12: Leonardo's Monorail](https://adventofcode.com/2016/day/12)

## Overview of problem

My last AOC task is [day 12](https://adventofcode.com/2016/day/12) - 2016. I want to read the instruction from an
assembunny code and get value input of `a.`
We have four registers `a`, `b`, `c`, and `d`. All registers start at `0,` and  can hold only integers.

The types of instruction we can get are only these:

> - `cpy x y` **copies** `x` (either an integer or the **value** of a register) into register y.
> - `inc x` **increases** the value of register `x` by one.
> - `dec x` **decreases** the value of register `x` by one.
> - `jnz x y` **jumps** to an instruction `y` away (positive means forward; negative means backward), but only if `x` is
	not zero.

The set of instructions might look like this:

```
cpy 1 a
cpy 1 b
cpy 26 d
cpy 7 c
inc d
dec c
jnz c -2
cpy a c
```

The instructions tell us to shift the numbers throughout the register.

## Solution

The solution for the first and second part is almost identical, so I combined them into one section.

Firstly, I created the register with the four letters that hold the integer 0 at the start. I used a map with keys of
strings and values of integers. I decided to use `String` over `Char` because I wanted to avoid having a problem with the
file input that returns a string.

Also, I have to keep track of steps (iterations), since instruction `jnz` can jump away.

```kotlin
val register = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)
```

Secondly, I iterate through the instruction text file line by line. This time I cannot use `.forEach()` as used in
previous AoC, because of the jump operation, we might (and we will) end up iterating more times than the input
size/length.

We will iterate the set of instructions based on the steps. It cannot jump below 0 or greater than the length of the
input file. We will use a string split for each iteration to prepare the instructions for further operations.

```kotlin
while (steps <= 0 && steps < input.size) {
	val words = input[steps].split(" ")
	...
}
```

Then I take the first word from the instruction array and decide whether we copy a number, decrease, increase or jump
away. The first three instructions are pretty straightforward. `cpy` copies an integer from the input and pastes it to the
register or copies value between registers. Increase and decrease changes value by one. After each operations, we
add one to the `steps` variable. Like so:

```kotlin
when (words.first()) {
	"cpy" -> {
		if (words[1] in "a".."d") register[words[2]] = register[words[1]]!!.toInt()
		else register[words[2]] = words[1].toInt()
		steps++
	}
	"inc" -> {
		register[words[1]] = register[words[1]]!! + 1
		steps++
	}

	"dec" -> {
		register[words[1]] = register[words[1]]!! - 1
		steps++
	}
		...
}
```

The last part is a little more complicated. We check if the jump instruction points to a register first. If it does, we
check whether the register is not zero. If it is not a zero, we add the number to the number of steps we "skip" or "
rollback" in our sets of instructions. If the register holds zero, we add only 1. Lastly, if the first instruction
is not a key of a register, we automatically jump by the second number.

```kotlin
  "jnz" -> {
	steps += if (words[1] in "a".."d") {
		if (register[words[1]]!! > 0) words[2].toInt() else 1
	} else words[2].toInt()
}
```

The second part was almost identical to the first part. We only start with register `c` to be `0`.

## Reflection

The project took me two afternoons (~6 hours). I struggled with the jump operation the most. Now looking at it, it looks
really simple and straightforward, but it starched my brain.

I am glad I learned how to implement if condition with range of strings by `if (letter in "a".."d")`. It was very
helpful. The next thing I earned was implementing a map. Normally, if I used JavaScript, I would create an object with
these key values. However, this collection type was even easier to operate with than I expected.


---

Welcome to the Advent of Code[^aoc] Kotlin project created by [bouceka][github] using
the [Advent of Code Kotlin Template][template] delivered by JetBrains.

In this repository, bouceka is about to provide solutions for the puzzles using [Kotlin][kotlin] language.

If you're stuck with Kotlin-specific questions or anything related to this template, check out the following resources:

- [Kotlin docs][docs]
- [Kotlin Slack][slack]
- Template [issue tracker][issues]

[^aoc]:
[Advent of Code][aoc] – An annual event of Christmas-oriented programming challenges started December 2015.
Every year since then, beginning on the first day of December, a programming puzzle is published every day for
twenty-five days.
You can solve the puzzle and provide an answer using the language of your choice.

[aoc]: https://adventofcode.com

[docs]: https://kotlinlang.org/docs/home.html

[github]: https://github.com/bouceka

[issues]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/issues

[kotlin]: https://kotlinlang.org

[slack]: https://surveys.jetbrains.com/s3/kotlin-slack-sign-up

[template]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template
