#include "telescope.hpp"

#include <algorithm>
#include <sstream>
#include <iomanip>
#include <stdexcept>

std::pair<size_t, size_t> parse_matrix(std::istream& in) {
    size_t radky = 0;
    size_t sloupce = 0;
    std::string radek;
    bool first_radek = true;

    while (std::getline(in, radek)) {
        if (radek.empty()) {
            continue;
        }
        radky++;

        std::istringstream iss(radek);
        size_t aktualni_sloupce = 0;
        int cislo;

        while (iss >> cislo) {
            aktualni_sloupce++;
        }

       if (first_radek) {
           sloupce = aktualni_sloupce;
           first_radek = false;
       }else {
               if (aktualni_sloupce != sloupce) {
                   throw std::invalid_argument("Matice s nekonzistentnim poctem prvku na radcich");
               }
           }
       }

    return std::make_pair(radky, sloupce);
}

std::vector<int> parse_matrix(std::istream& in, const std::pair<size_t, size_t>& m_size) {
    size_t expected_rows = m_size.first;
    size_t expected_cols = m_size.second;
    std::string radek;
    std::vector<int> data;

    if (expected_rows == 0 || expected_cols == 0) {
        return data;
    }
    //radky jsou od nuly
    //index = aktualni_radek*pocet_sloupců + aktualni_slopec

    size_t aktualni_radek = 0;

    while (std::getline(in, radek)) {
        if (aktualni_radek >= expected_rows) {
            throw std::invalid_argument("Prilis mnoho radku");
        }

        std::istringstream iss(radek);
        size_t aktualni_sloupce = 0;
        int cislo;

        while (iss >> cislo) {
            if (aktualni_sloupce >= expected_cols) {
                throw std::invalid_argument("Prilis mnoho sloupců");
            }
            data.push_back(cislo);
            aktualni_sloupce++;
        }
        if (aktualni_sloupce != expected_cols) {
            throw std::invalid_argument("Neodpovida ocekavanemu poctu sloupců");
        }

        aktualni_radek++;
    }

    if (aktualni_radek != expected_rows) {
        throw std::invalid_argument("Prilis malo radku");
    }

    return data;
}

void print_matrix(std::ostream& out, const std::pair<size_t, size_t>& m_size, const std::vector<int>& vec) {
    size_t rows = m_size.first;
    size_t cols = m_size.second;

    if (rows == 0 || cols == 0) {
        return;
    }

    int max_width = 0;
    for (int number : vec) {
        std::ostringstream oss;
        oss << number;
        int width = oss.str().length();
        if (width > max_width) {
            max_width = width;
        }
    }
    max_width += 2;

    //delka cary = sirka_sloupce*pocet_sloupcu + pocet_sloupcu+1

    size_t wide_line = max_width*cols + cols+1;
    std::string line(wide_line,'-');

    out << line << "\n";

    //matice
    for (size_t row = 0; row < rows; row++) {
        out << '|';
        for (size_t col = 0; col < cols; col++) {
            //index = aktualni_radek*pocet_sloupců + aktualni_slopec
            int value = vec[row*cols + col];
            out << ' ' << std::setw(max_width-2) << std::right << value << ' ' << '|';
        }
        out << "\n";
    }
    out << line << "\n";
}

std::vector<unsigned char> parse_stream(std::istream& in, const std::pair<size_t, size_t>& m_size) {
    size_t expected_rows = m_size.first;
    size_t expected_cols = m_size.second;
    std::string radek;
    std::vector<unsigned char> data;
    if (expected_rows == 0 || expected_cols == 0) {
        return data;
    }
    size_t expected_total = expected_rows * expected_cols;
    size_t aktualni_radek = 0;
    while (std::getline(in, radek)) {
        if (aktualni_radek >= expected_rows) {
            throw std::invalid_argument("Prilis mnoho radku");
        }
        std::istringstream iss(radek);
        char c;
        size_t aktualni_sloupce = 0;
        while (iss.get(c)) {
            // if (aktualni_sloupce >= expected_cols) {
            //     throw std::invalid_argument("Prilis mnoho sloupců");
            // }
            data.push_back(c);
            aktualni_sloupce++;
        }
        // if (aktualni_sloupce != expected_cols) {
        //     throw std::invalid_argument("Neodpovida ocekavanemu poctu sloupců");
        // }
        aktualni_radek++;
    }
    // if (aktualni_radek != expected_rows) {
    //     throw std::invalid_argument("Prilis malo radku");
    // }
    if (data.size() != expected_total) {
        throw std::invalid_argument("Neodpovida ocekavanemu poctu dat");
    }
    return data;
}

void rotate_down(const std::pair<size_t, size_t>& m_size, std::vector<unsigned char>& vec) {
    size_t rows = m_size.first;
    size_t cols = m_size.second;
    if (rows == 0 || cols == 0) {
        return;
    }
    std::rotate(vec.begin(), vec.end() - cols, vec.end());
}

void rotate_down(const std::pair<size_t, size_t>& m_size, std::vector<unsigned char>& vec, int step) {
    size_t rows = m_size.first;
    size_t cols = m_size.second;

    if (rows == 0 || cols == 0) {
        return;
    }

    step = step%static_cast<int>(rows);

    if (step<0) {
        step += rows;
    }
    std::rotate(vec.begin(), vec.end() - step*cols, vec.end());
}

void rotate_right(const std::pair<size_t, size_t>& m_size, std::vector<unsigned char>& vec) {
    size_t rows = m_size.first;
    size_t cols = m_size.second;
    if (rows == 0 || cols == 0) {
        return;
    }
    for (size_t i = 0; i < rows; ++i) {
        auto row_start = vec.begin() + i * cols;
        auto row_end = row_start + cols;

        std::rotate(row_start, row_end - 1, row_end);
    }
}
void rotate_right(const std::pair<size_t, size_t>& m_size, std::vector<unsigned char>& vec, int step) {
    size_t rows = m_size.first;
    size_t cols = m_size.second;
    if (rows == 0 || cols == 0) {
        return;
    }

    step = step%static_cast<int>(cols);
    if (step<0) {
        step += cols;
    }
    for (size_t i = 0; i < rows; ++i) {
        auto row_start = vec.begin() + i * cols;
        auto row_end = row_start + cols;

        std::rotate(row_start, row_end - step, row_end);
    }
}

void swap_points(const std::pair<size_t, size_t>& m_size, std::vector<unsigned char>& vec, const Point& p1, const Point& p2) {
    size_t rows = m_size.first;
    size_t cols = m_size.second;
    if (rows == 0 || cols == 0) {
        return;
    }

    if (p1.x >= cols || p1.y >= rows) {
        throw std::invalid_argument("Bod p1 je mimo matice");
    }
    if (p2.x >= cols || p2.y >= rows) {
        throw std::invalid_argument("Bod p2 je mimo matice");
    }

    size_t index1 = p1.y * cols + p1.x;
    size_t index2 = p2.y * cols + p2.x;

    std::swap(vec[index1], vec[index2]);
            //index = aktualni_radek*pocet_sloupců + aktualni_slopec
            //index = row*cols + col

}

void swap_points(const std::pair<size_t, size_t>& m_size, std::vector<unsigned char>& vec, const Point& p1, const Point& p2, const Point& delta) {
    size_t rows = m_size.first;
    size_t cols = m_size.second;
    if (rows == 0 || cols == 0) {
        return;
    }

    if (p1.x + delta.x > cols || p1.y + delta.y > rows) {
        throw std::invalid_argument("Obdelnik p1 je mimo matice");
    }
    if (p2.x + delta.x > cols || p2.y + delta.y > rows) {
        throw std::invalid_argument("Obdelnik p2 je mimo matice");
    }

    bool separated = (p1.x + delta.x <= p2.x) || (p1.y + delta.y <= p2.y) || (p2.x + delta.x <= p1.x) || (p2.y + delta.y <= p1.y);

    if (!separated) {
        throw std::invalid_argument("Obdelniky se prekryvaji");
    }

    // size_t index1 = p1.y * cols + p1.x;
    // size_t index2 = p2.y * cols + p2.x;

    for (size_t row = 0; row < delta.y; ++row) {
        for (size_t col = 0; col < delta.x; ++col) {
            size_t index1 = (p1.y + row) * cols + (p1.x + col);
            size_t index2 = (p2.y + row) * cols + (p2.x + col);
            std::swap(vec[index1], vec[index2]);
        }
    }

}

void decode_picture(const std::string& file, const std::pair<size_t, size_t>& m_size, std::vector<unsigned char>& vec) {
    std::ifstream ifs(file);
    if (!ifs.is_open()) {
        throw std::invalid_argument("Nevalidny file");
    }
    std::string line;
    while (std::getline(ifs, line)) {
        if (line.empty()) {
            continue;
        }
        std::istringstream iss(line);
        char c;
        int par;
        iss>>c;
        switch (c) {
            case 'r':
                if (iss>>par) {
                    rotate_right(m_size, vec, par);
                } else {
                    rotate_right(m_size, vec);
                }
                break;
            case 'l':
                if (iss>>par) {
                    rotate_right(m_size, vec, -par);
                } else {
                    rotate_right(m_size, vec, -1);
                }
                break;
            case 'd':
                if (iss>>par) {
                    rotate_down(m_size, vec, par);
                } else {
                    rotate_down(m_size, vec);
                }
                break;
            case 'u':
                if (iss>>par) {
                    rotate_down(m_size, vec, -par);
                } else {
                    rotate_down(m_size, vec, -1);
                }
                break;
            case 's' : {
                int x1, y1, x2, y2;
                iss >> x1 >> y1 >> x2 >> y2;
                Point p1(x1, y1);
                Point p2(x2, y2);

                int delta_x, delta_y;
                if (iss>>delta_x>>delta_y) {
                    Point delta(delta_x, delta_y);
                    swap_points(m_size, vec, p1, p2, delta);
                } else {
                    swap_points(m_size, vec, p1, p2);
                }
                break;
            }
        }
    }
}