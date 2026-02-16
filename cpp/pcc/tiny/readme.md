# <a href="https://cw.fel.cvut.cz/b251/courses/b6b36pcc/ukoly/hwjednohubky">Tiny assignments</a>
"The bite-sized challenges are not mandatory, but we strongly recommend completing them. If you work on them continuously, they will allow you to continuously verify your programming knowledge." - B6B36PCC's course page

## Task 00 – Formatted Stream Output

A simple function, `write_stats`, is to be implemented. This function accepts a dataset and an output stream, into which three statistical values are to be written: minimum, maximum, and mean.

Data must be recorded in a specific format. For the input `1.23, 5.44, -23`, the output is required to appear as follows:

```text
min: -23.00
max: 5.44
mean: -5.44
```

## Task 01 – Copies and References

Incomplete code implementing two overloads of the `pluralize` function is provided:

```cpp
std::string pluralize(std::string const& str);
std::vector<std::string> pluralize(std::vector<std::string> const& str);
```
The function is intended to convert a word (or a vector of words) into the plural form. The provided implementation contains errors; the code is to be modified to ensure all tests pass.

## Task 02 – Working with Dynamically Allocated Memory

Completed code is provided where, despite all tests passing, a defect in dynamic memory management exists. The implementation is to be corrected to resolve this error.

**Functionality:**
The `summarize_data` function returns statistics for each valid line in the input stream; invalid lines are skipped. Each line begins with a number `n`, indicating the quantity of subsequent numbers. These following numbers are required to be within the range [0, 255].

## Task 03 – Object Lifetime

The `summarize_data` function has been updated to prevent previous memory errors by introducing a `fixed_array` class responsible for resource management. However, implementation flaws persist and are to be corrected.

**Output Format Change:**
The output format has been modified to account for invalid lines. Instead of being skipped, invalid lines now result in a returned statistic indicating an element count of `std::numeric_limits<size_t>::max()`.
