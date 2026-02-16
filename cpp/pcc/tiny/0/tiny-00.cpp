#include "tiny-00.hpp"

#include <ostream>
#include <algorithm>
#include <iomanip>
#include <numeric>

void write_stats(std::vector<double> const& data, std::ostream& out) {
    if (data.empty()) return;

    double min = *std::min_element(data.begin(), data.end());
    double max = *std::max_element(data.begin(), data.end());
    double mean = std::accumulate(data.begin(), data.end(), 0.0) / data.size();

    out << "min: " << std::fixed << std::setprecision(2)<< min << "\nmax: " << std::fixed << std::setprecision(2)<< max << "\nmean: " << std::fixed << std::setprecision(2)<< mean << "\n";
}
