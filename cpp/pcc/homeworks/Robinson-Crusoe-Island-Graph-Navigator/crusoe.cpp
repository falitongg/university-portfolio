#include "crusoe.hpp"

#include <queue>
#include <stack>
//Trida vertex
vertex::vertex() {

}
vertex::vertex(std::string str, int x, int y, std::string col) : name(str), xy(std::make_pair(x, y)), c_forward(col), neighbours() {

}


bool vertex::add_neighbour(size_t vv, const std::string &col) {
//kontrola duplicity v neighbours
    for (const auto& neighbour : neighbours) {
        if (neighbour.first == vv) {
            return false;
        }
    }
    neighbours.push_back(std::make_pair(vv, col));

    return true;
}

std::vector<std::pair<size_t, std::string>> vertex::get_neighbour() const {
    return neighbours;
}

std::pair<int, int> vertex::get_xy() const {
    return xy;
}

void vertex::set_color(const std::string& col) {
    c_forward = col;
}

std::string vertex::get_color() const {
    return c_forward;
}

void vertex::set_edge_color(size_t vv, const std::string& col) {
    for (auto& neighbour : neighbours) {
        if (neighbour.first == vv) {
            neighbour.second = col;
            return;
        }
    }
}

std::string vertex::get_edge_color(size_t vv) {
    for (auto& neighbour : neighbours) {
        if (neighbour.first == vv) {
            return neighbour.second; //hrana ex
        }
    }
    return "#FFFFFF"; //hrana neex
}
//Trida graph
void graph::add_vertex(int x, int y, const std::string &col) {
    size_t id = vertices.size() + 1;
    std::string name = std::to_string(id);
    vertices.push_back(vertex(name, x, y, col));
}

void graph::add_edge(size_t v1, size_t v2, const std::string &col) {
    //kontrola ze vrcholy existuji

    if (v1 < vertices.size() && v2 < vertices.size()) {
        vertices[v1].add_neighbour(v2, col);
        vertices[v2].add_neighbour(v1, col);
    }
}

bool graph::is_edge(size_t v1, size_t v2) const {
    if (v1 < vertices.size() && v2 < vertices.size()) {
        for (const auto& neighbour : vertices[v1].get_neighbour()) {
            if (neighbour.first == v2) {
                return true;
            }
        }
    }
    return false;
}

std::string graph::edge_color(size_t v1, size_t v2) const {
    if (is_edge(v1, v2)) {
        for (const auto& neighbour : vertices[v1].get_neighbour()) {
            if (neighbour.first == v2) {
                return neighbour.second;
            }
        }
    }
    return "#FFFFFF";
}

std::string graph::vertex_color(size_t v1) const {
    if (v1 < vertices.size()) {
        return vertices[v1].get_color();
    }
    return "#FFFFFF";

}

void graph::set_vertex_color(size_t v1, const std::string& col) {
    if (v1 < vertices.size()) {
        vertices[v1].set_color(col);
    }
}

void graph::set_edge_color(size_t v1, size_t v2, const std::string& col) {
    if (v1 < vertices.size() && v2 < vertices.size()) {
        if (is_edge(v1, v2)) {
            vertices[v1].set_edge_color(v2, col);
            vertices[v2].set_edge_color(v1, col);
        }
    }
}

bool graph::empty() const {
    if (vertices.size() == 0) {
        return true;
    }
    return false;
}

size_t graph::size() const {
    return vertices.size();
}

size_t graph::num_edge() const {
    size_t num = 0;
    for (const auto& vertex : vertices) {
        num += vertex.get_neighbour().size();
    }
    return num/2;
}




vertex graph::get_vertex(size_t num) const {
    return vertices[num];
}

void graph::is_achievable(size_t from, std::vector<size_t> &achieved) {
    if (from < vertices.size()) {
        std::vector<bool> visited(vertices.size(), false);
        visited[from] = true;
        std::queue<size_t> q;
        q.push(from);

        while (!q.empty()) {
            size_t v = q.front();
            q.pop();
            for (const auto& n : vertices[v].get_neighbour()) {
                if (!visited[n.first]) {
                    visited[n.first] = true;
                    q.push(n.first);
                }
            }
        }

        for (size_t i = 0; i < visited.size(); i++) {
            if (visited[i]) {
                achieved.push_back(i);
            }
        }
    }
}

void graph::color_component(std::vector<size_t> cmp, const std::string& col){
    //obarveni vsech vrcholů komponenty
    for (auto& v : cmp) {
        set_vertex_color(v, col);

        //obarveni vsech hran komponenty
        for (auto& v2 : vertices[v].get_neighbour()) {
            set_edge_color(v, v2.first, col);
        }
    }
}

std::vector<size_t> graph::path(size_t v1, size_t v2) {
    if (v1 >= vertices.size() || v2 >= vertices.size()) {
        return {};
    }

    if (v1==v2) {
        return {v1};
    }

    std::vector<size_t> path;
    std::queue<size_t> q;
    std::vector<bool> visited(vertices.size(), false);
    std::vector<size_t> parent(vertices.size(), SIZE_MAX);


    q.push(v1);
    visited[v1] = true;

    while (!q.empty()) {
        size_t v = q.front();
        q.pop();

        if (v == v2) {
            size_t c = v2;
            while (c != SIZE_MAX) {
                path.push_back(c);
                c = parent[c];
            }
            std::reverse(path.begin(), path.end());
            break;
        }

        for (const auto& neighbour : vertices[v].get_neighbour()) {
            if (!visited[neighbour.first]) {
                visited[neighbour.first] = true;
                parent[neighbour.first] = v;
                q.push(neighbour.first);
            }
        }
    }

    return path;
}



void graph::color_path(std::vector<size_t> pth, const std::string &col) {

    if (pth.size() < 2) {
        return;
    }

    for (size_t i = 1; i < pth.size(); i++) {
        set_edge_color(pth[i-1], pth[i], col);
    }
}


graph::graph_comp::graph_comp(graph &gg) : gg(gg) {

}

void graph::graph_comp::color_componennts() {
    std::vector<std::string> colors{"red",
                                    "olive",
                                    "orange",
                                    "lightblue",
                                    "yellow",
                                    "pink",
                                    "cyan",
                                    "purple",
                                    "brown",
                                    "magenta"};
//evidujeme komponentu vrcholu
    std::vector<bool> visited(gg.size(), false);
    int color_index = 0;
    for (size_t i = 0; i < gg.size(); i++) {
        if (!visited[i]) {
            std::vector<size_t> cmp;
            gg.is_achievable(i, cmp);

            if (cmp.size() > 1) {
                for (auto& v : cmp) {
                    visited[v] = true;
                    //obarveni komponenty
                    gg.color_component(cmp, colors[color_index % colors.size()]);
                    color_index++;
                }
            } else {
                for (auto& v : cmp) {
                    visited[v] = true;
                }
            }
        }
    }
}

size_t graph::graph_comp::count() const {
    size_t num = 0;
    std::vector<bool> visited(gg.size(), false);
    for (size_t i = 0; i < gg.size(); i++) {
        if (!visited[i]) {
            std::vector<size_t> cmp;
            gg.is_achievable(i, cmp);
            for (auto& v : cmp) {
                visited[v] = true;
            }
            num++;
        }
    }
    return num;
}

size_t graph::graph_comp::count_without_one() const {
    size_t num = 0;
    std::vector<bool> visited(gg.size(), false);
    for (size_t i = 0; i < gg.size(); i++) {
        if (!visited[i]) {
            std::vector<size_t> cmp;
            gg.is_achievable(i, cmp);
            for (auto& v : cmp) {
                visited[v] = true;
            }
            if (cmp.size() > 1) {
                num++;
            }
        }
    }
    return num;
}

size_t graph::graph_comp::max_comp() const {
    size_t max_comp = 0;

    std::vector<
        std::vector<size_t>
    > components;
    std::vector<bool> visited(gg.size(), false);
    for (size_t i = 0; i < gg.size(); i++) {
        if (!visited[i]) {
            std::vector<size_t> cmp;
            gg.is_achievable(i, cmp);
            for (auto& v : cmp) {
                visited[v] = true;
            }
            components.push_back(cmp);
        }
    }

    size_t max_size = 0;
    if (components.empty()) {
        max_size = 0;
    }else {
        max_size = components[0].size();
    }
    for (size_t i = 1; i < components.size(); i++) {
        if (components[i].size() > max_size) {
            max_size = components[i].size();
            max_comp = i;
        }
    }
    return max_comp;
}
size_t graph::graph_comp::size_of_comp(size_t i) const {
    std::vector<std::vector<size_t>> cmps;
    std::vector<bool> visited(gg.size(), false);
    for (size_t i = 0; i < gg.size(); i++) {
        if (!visited[i]) {
            std::vector<size_t> cmp;
            gg.is_achievable(i, cmp);
            for (auto& v : cmp) {
                visited[v] = true;
            }
            cmps.push_back(cmp);
        }
    }
    return cmps[i].size();
}

std::vector<size_t> graph::graph_comp::get_component(size_t i) const {
    std::vector<std::vector<size_t>> cmps;
    std::vector<bool> visited(gg.size(), false);
    for (size_t i = 0; i < gg.size(); i++) {
        if (!visited[i]) {
            std::vector<size_t> cmp;
            gg.is_achievable(i, cmp);
            for (auto& v : cmp) {
                visited[v] = true;
            }
            cmps.push_back(cmp);
        }
    }
    return cmps[i];
}

bool graph::graph_comp::same_comp(size_t v1, size_t v2) const {
    std::vector<size_t> cmp;
    gg.is_achievable(v1, cmp);
    for (auto& v : cmp) {
        if (v == v2) {
            return true;
        }
    }
    return false;
}

graph::graph_fence::graph_fence(graph &gg, size_t vv, size_t distance) : gg(gg) {
    std::vector<bool> visited(gg.size(), false);
    std::queue<std::pair<size_t, size_t>> q; //vrchol & vzdalenost od vv
    q.push(std::make_pair(vv, 0));
    visited[vv] = true;
    while (!q.empty()) {
        size_t v = q.front().first;
        size_t d = q.front().second;
        q.pop();

        if (d <= distance) {
            fence.push_back(v);
        }
        for (const auto& n : gg.get_vertex(v).get_neighbour()) {
            if (!visited[n.first]) {
                visited[n.first] = true;
                q.push(std::make_pair(n.first, d + 1));
            }
        }

    }

}

void graph::graph_fence::color_fence(const std::string &col) {
    for (auto& v : fence) {
        gg.set_vertex_color(v, col);
    }
}

size_t graph::graph_fence::count_stake() const {
    size_t num = 0;
    for (auto& v : fence) {
        num++;
    }
    return num;
}

size_t graph::graph_fence::get_stake(size_t i) const {
    return fence[i];
}

