## 03 – Object Lifetime

The `summarize_data` function has been updated to prevent previous memory errors by introducing a `fixed_array` class responsible for resource management. However, implementation flaws persist and are to be corrected.

**Output Format Change:**
The output format has been modified to account for invalid lines. Instead of being skipped, invalid lines now result in a returned statistic indicating an element count of `std::numeric_limits<size_t>::max()`.
