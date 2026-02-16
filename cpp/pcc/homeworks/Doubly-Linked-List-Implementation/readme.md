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
