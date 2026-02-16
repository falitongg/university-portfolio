# Homeworks
## <a href="https://cw.fel.cvut.cz/b242/courses/b0b36pjv/hw/01">HW01 - Calculator</a>
Create a simple calculator. The program will gradually ask the user for the operation, where 1 corresponds to addition, 2 to subtraction, 3 to multiplication, and 4 to division. After selecting the operation, the program will ask for two operands, correctly labeling them according to the selected operation - i.e., for addition, it will ask for addends, for subtraction for minuend and subtrahend, for multiplication for factors, and for division for dividend and divisor. The last input of the program is the number of decimal places to be used for printing the result (not for the actual calculation). The operation selection expects an integer (1, 2, 3, 4). The operands are real numbers. The number of decimal places is a whole positive non-negative number. The program must handle division by zero and the selection of unsupported operations. All output should be printed to stdout. The output format is: “operand1 operator operand2 = result” where operands and result are formatted to the specified number of decimal places, and the output is terminated with a newline character. Remember that when your program is finished, you must properly close input reading with the method close(), otherwise your solution may not pass correctly!

## <a href="https://cw.fel.cvut.cz/b242/courses/b0b36pjv/hw/02">HW02 - Calculation of the statistics of a numerical sequence</a>
Write a program that calculates the average value and standard deviation of a sequence of numbers entered through standard input. In the implementation, use the prepared interface StatsInterface and its implementation Stats; you will then use it in the Lab02 class. Follow the provided documentation and the following requirements:

The statistics calculation (average and deviation) is performed from every 10 input numbers. Print these two values on one line of standard output to three decimal places, and separate the numbers with a space, i.e., formatting “%.3f %.3f”. At the start of the line, print the count of values from which the average and deviation are calculated, formatted to two places, i.e., formatting “%2d”.

When the end of the input file is detected, print the partial result from the corresponding count of values, but only if the number of values used for the calculation is greater than 1. 

Indicate the detection of a line that is not a valid input (a number) by printing A number has not been parsed from line X, where X is the line number, to standard error output. 

Indicate the end of input detection by printing End of input detected! to standard error output. 

Thoroughly distinguish between output to standard output and standard error output!

## <a href="https://cw.fel.cvut.cz/b242/courses/b0b36pjv/hw/03">HW03 - Circular queue</a>

Write a program that will represent a circular queue storing values of type String. The capacity of the queue will be a parameter of the constructor. If the no-argument constructor is used, create a queue of constant size 5. Furthermore, implement methods in the provided class CircularArrayQueue. You will find out what each method should do in the documentation of the interface Queue.java. Note that the documentation for some methods is very similar (sometimes even the same) to the documentation of methods in the Queue class in the standard Java framework. This is not coincidental; the authors of the assignment were inspired by that Javadoc.

## <a href="https://cw.fel.cvut.cz/b242/courses/b0b36pjv/hw/04">HW04 - Password cracking</a>
Your task consists of creating classes `Test`, `Thief`, and `BruteForceAttacker`. You will only work with the `BruteForceAttacker` class. Here, you will complete the code in the method `public void breakPassword(int sizeOfPassword)` which attempts to crack a password of length `sizeOfPassword` (the password is exactly the specified number of characters, neither shorter nor longer).

You have access to the following methods:
- `char[] getCharacters()`: This method returns a list of characters from which the password's subset is composed.
- `boolean tryOpen(char[] password)`: This method attempts to open the safe. If successful, it prints a message and returns true; otherwise, it returns false. Once the safe is opened, no further attempts are needed. Additionally, if you try other passwords, the safe will lock again.

The theme of the exercise indicates that you should solve this task using recursion. However, it is quite possible that the `void breakPassword(int sizeOfPassword)` method will not be recursive and will call another method that is recursive.

You can test your algorithm using the code in `Test.java`. In this code, the safe is set to the password `abcdaaaddb`, and the character set is `{'a', 'b', 'c', 'd'}`. You can test other passwords following this pattern.

## <a href="https://cw.fel.cvut.cz/b242/courses/b0b36pjv/hw/05">HW05 - Binary tree</a>
Implement the provided interfaces `Tree` and `Node` with the classes `TreeImpl` and `NodeImpl`. The `TreeImpl` class must contain a default constructor (without parameters). Name methods and variables in English. Do not use Java collections; you only need an array provided as a parameter to `setTree`. Do not modify the `Tree` and `Node` interfaces. Use the template `hw5_template.zip` for your implementation.

Place the implemented classes `TreeImpl` and `NodeImpl` in the package `cz.cvut.fel.pjv.impl`. The `Tree` class represents a binary tree that contains integer data in all nodes. Each node of the tree is represented by a class implementing the `Node` interface. The `Tree` contains the following methods:

- `void setTree(int[] values)`: sets the tree so that it contains values from the `values` array. If the length of the array is odd, the root contains the middle number; otherwise, it contains the first number after the middle of the sequence (the actual values do not matter, only their positions in the array). The left part of the subtree contains the elements before the middle element, and the right contains those after it. This also applies to subtrees.

- `Node getRoot()`: returns the root of the tree.

- `String toString()`: returns a string representation of the tree suitable for output in the following format: each value is on a new line, preceded by a number of spaces corresponding to the depth of the node (0 for the root) and '- '. The first line contains the value of the root. The value of a node is followed by the printout of the left subtree and then the right subtree. Each line (including the last one) ends with a newline character ('\n').

Example for a tree created for the array [1, 2, 3, 4, 5, 6, 7]:
