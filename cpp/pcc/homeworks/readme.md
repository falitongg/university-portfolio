# Homeworks
## <a href="https://cw.fel.cvut.cz/b251/courses/b6b36pcc/ukoly/hw01">Project 1: JWST Image Decoder </a>

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

## <A href="https://cw.fel.cvut.cz/b251/courses/b6b36pcc/ukoly/hw02">Project 2: Robinson Crusoe – Island Graph Navigator</a>

A C++ assignment focused on graph theory and object-oriented programming (OOP). Students model Robinson Crusoe's map as an undirected graph to identify islands (connected components) and find optimal paths between locations.
<img width="486" height="482" alt="image" src="https://github.com/user-attachments/assets/3adcc145-5f01-4752-a833-f75398d0ad55" /> <img width="485" height="485" alt="image" src="https://github.com/user-attachments/assets/492ab5d7-d623-449d-a056-0725d2adcae3" />


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

## <a href="https://cw.fel.cvut.cz/b251/courses/b6b36pcc/ukoly/hw03">Project 3: Doubly Linked List Implementation </a>

A C++ assignment focused on building a custom, non-sorted **Doubly Linked List** container following the standard library (`std::list`) design patterns.

<img width="540" height="54" alt="image" src="https://github.com/user-attachments/assets/4812e4e2-bce3-4b5a-b6b5-74d839dc0bfe" />


### Overview
* **Objective:** Implement a list where each node points to both its predecessor and successor.
* **Core Tasks:** Manage manual memory allocation, implement bidirectional iterators, and optimize sorting.
* **Algorithm:** Use **Merge Sort** for efficient $O(n \log n)$ list sorting.

### Key Learning Objectives
* **Manual Memory Management:** Handling node insertion and deletion without leaks.
* **Modern C++:** Implementing Copy/Move constructors and assignment operators.
* **Iterators:** Creating a nested `const_iterator` class with operator overloading (`++`, `--`, `*`, `==`).
* **Algorithmic Efficiency:** Understanding time complexity for `split`, `merge`, and `sort` operations.

### Grading & Stages
| Stage | Focus Area | Points |
| :--- | :--- | :---: |
| **Stage 1** | Basic functionality (push, pop, clear) | 1.50 |
| **Stage 2** | Bidirectional Iterators | 1.50 |
| **Stage 3** | Copy/Move semantics & comparisons | 1.50 |
| **Stage 4** | List Logic: Reverse, Split, Merge, Sort, Remove | 3.50 |
| **Stage 5** | Algorithmic Complexity (Time limits) | 1.50 |
| **Total** | | **10.00** |
