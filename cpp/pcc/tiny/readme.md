# Tiny assignments
"The bite-sized challenges are not mandatory, but we strongly recommend completing them. If you work on them continuously, they will allow you to continuously verify your programming knowledge." - B6B36PCC's course page

## Task 0 – Formatted Stream Output

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
