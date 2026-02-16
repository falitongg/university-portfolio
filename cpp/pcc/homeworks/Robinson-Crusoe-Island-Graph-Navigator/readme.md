## Project 2: Robinson Crusoe – Island Graph Navigator

A C++ assignment focused on graph theory and object-oriented programming (OOP). Students model Robinson Crusoe's map as an undirected graph to identify islands (connected components) and find optimal paths between locations.

### Overview
* **Goal:** Implement a graph structure using classes to represent vertices and edges.
* **Algorithms:** * **DFS (Depth-First Search):** To identify isolated islands/components.
    * **BFS (Breadth-First Search):** To find the shortest path between two points.
* **Data Representation:** Map locations are vertices with attributes (name, coordinates, color).



### Key Learning Objectives
* **OOP Fundamentals:** Class definitions, constructors, private members, and nested classes.
* **Graph Implementation:** Storing vertices in `std::vector` and managing adjacency lists.
* **Algorithm Implementation:** Recursive DFS and queue-based BFS (`std::queue`).
* **Path Reconstruction:** Using "parent" pointers to trace back the shortest path.

### Project Structure
* `crusoe.hpp`: Header with class definitions and Doxygen documentation (do not modify).
* `crusoe.cpp`: Implementation of logic for `vertex` and `graph` classes.
* `image.svg`: Automatically generated visualization for debugging tests.

### Grading & Stages
| Stage | Description | Points |
| :--- | :--- | :---: |
| **Stage 1** | Basic class definitions and member access | 3 |
| **Stage 2** | DFS (Component search) & BFS (Pathfinding) | 4 |
| **Stage 3** | Archipelago statistics and graph analysis | 3 |
| **Total** | | **10** |

## Requirements
* No memory leaks.
* Passing all unit tests.
* Do not modify the provided stream operators in `crusoe.hpp`.
