# Homeworks
## Project 1: JWST Image Decoder

A C++ assignment focused on decrypting and processing satellite data from the James Webb Space Telescope (JWST) into PPM image format.

<img width="300" height="703" alt="image" src="https://github.com/user-attachments/assets/caac3838-5ae1-4c8a-ac98-73b1b927f7ef" /> <img width="300" height="703" alt="image" src="https://github.com/user-attachments/assets/57cee985-51f4-4423-b48d-6470bff709a0" />


### Overview
* **Objective:** Implement an image decryption algorithm and handle 2D data using 1D `std::vector`.
* **Input:** Encrypted telemetry data.
* **Output:** Decoded images in `.ppm` format.

### Key Learning Objectives
* Managing 1D arrays to represent 2D structures.
* Working with `std::vector` and `unsigned char` buffers.
* Implementing logic based on Doxygen documentation.
* Unit testing and performance optimization in C++.

### Project Structure
* `telescope.hpp`: Interface and function definitions (do not modify).
* `telescope.cpp`: Your implementation of the decoding logic.
* `images/`: Encrypted source files for testing.
* `CMakeLists.txt`: Build configuration.

### Grading & Stages
| Stage | Description | Points |
| :--- | :--- | :---: |
| **Stage 1** | Basic array manipulation (int) | 2 |
| **Stage 2** | Byte array operations & loading | 4 |
| **Stage 3** | Image decoding logic | 3 |
| **Final** | Complex integration tests | 1 |
| **Total** | | **10** |

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
