# <a href="https://cw.fel.cvut.cz/wiki/courses/b6b36dsa/ukoly/hw01">HW01 — Depth-First Search</a>

A gardener maintains a hedge maze. He needs to find all **mandatory passages** —
cells every visitor must walk through — so he knows when to close the maze for maintenance.

## Input

- Rectangular maze read from stdin
- Width = number of chars on the first line; Height = number of lines read
- `#` = hedge (wall), `.` = passable cell
- Entry: top-left, Exit: bottom-right

## Output

Print the maze with mandatory cells marked as `!`.

## Input Validation

Check errors **in this exact order**, print to stderr, exit with code `1`:

| Error message | Condition |
|---|---|
| `Error: Bludiste neni obdelnikove!` | Row length ≠ first row length |
| `Error: Vstup neni vlevo nahore!` | 2nd char of 1st row ≠ `.` |
| `Error: Vystup neni vpravo dole!` | 2nd-to-last char of last row ≠ `.` |
| `Error: Sirka bludiste je mimo rozsah!` | Width not in range [5, 100] |
| `Error: Delka bludiste je mimo rozsah!` | Height not in range [5, 50] |
| `Error: Bludiste obsahuje nezname znaky!` | Char ≠ `#` or `.` (incl. space/tab) |
| `Error: Bludiste neni oplocene!` | Border is not all `#` (except entry/exit) |
| `Error: Cesta neexistuje!` | No path exists |

## Algorithm

Use **DFS with backtracking** to find all paths, then identify cells common to every path.

### Optimization hints

- All mandatory cells already lie on the **first found path**
- Entry and exit are always mandatory
- Cells forming a cycle with an alternative path are **not** mandatory
- Blocking a mandatory cell (with `#`) makes the maze unsolvable; blocking others does not
- Adding fictitious walls reduces the search space significantly
- Explore directions in a fixed order (e.g. left→up→right→down); consider **topological ordering**
- Most efficient solutions receive bonus points

## Constraints & Submission

- **Expected complexity:** better than O(n⁴)
- **Time coefficient:** 3.0× reference solution
- **Languages:** Java, Python, C/C++
- **File names:** `maze.py` / `Maze.java` (class `Maze`) / `maze.c` or `maze.cpp`
- **No program arguments**
- **Max uploads:** 20 (extra uploads incur point penalty)

### Compilation

```sh
# Java
javac *.java && java -classpath . Maze
```

### Example
#### Input:

```text
#.#####
#....##
#.##.##
#.....#
#####.#
```
#### Output:

```text
#!#####
#!...##
#.##.##
#...!!#
#####!#
```
