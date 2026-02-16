#include "list.hpp"

#include <utility>
#include <algorithm>

namespace pcc {
//list
    //kopirovaci konstuktor
    list::list(const list& rhs) {
        for (auto it = rhs.begin(); it != rhs.end(); ++it) {
            push_back(*it);
        }
    }

    //kopirovaci prirazeni
    list& list::operator=(const list& rhs) {
        if (this != &rhs) {
            list temp(rhs);
            swap(temp);
        }
        return *this;
    }

    //move konstruktor
    list::list(list&& rhs) : head(rhs.head), tail(rhs.tail), num_elements(rhs.num_elements) {
        rhs.head = nullptr;
        rhs.tail = nullptr;
        rhs.num_elements = 0;
    }

    //move prirazeni (hardcore kopirovani)
    list& list::operator=(list&& rhs) {
        if (this != &rhs) {
            swap(rhs);
        }
        return *this;
    }

    //size
    size_t list::size() const {
        return num_elements;
    }

    //empty
    bool list::empty() const {
        return num_elements == 0;
    }


    //destruktor
    list::~list() {
        node* current = head;
        while (current != nullptr) {
            node* next = current->next;
            delete current;
            current = next;
        }
    }
//const_iterator
    //constructor
    list::const_iterator::const_iterator(node *ptr, const list *gen) : current_ptr(ptr), o_list(gen){

    }

    //definovani operatorů
    bool list::const_iterator::operator==(const const_iterator &rhs) const {
        return current_ptr == rhs.current_ptr;
    }

    bool list::const_iterator::operator!=(const const_iterator &rhs) const {
        return !(*this == rhs);          //opakovane pouziti jiz def operatoru
    }

    list::const_iterator::reference list::const_iterator::operator*() const {
        return current_ptr->val;
    }

    list::const_iterator::pointer list::const_iterator::operator->() const {
        return &(current_ptr->val);
    }

    //prefix
    list::const_iterator& list::const_iterator::operator++() {
        current_ptr = current_ptr->next;
        return *this;
    }

    list::const_iterator& list::const_iterator::operator--() {
        if (current_ptr == nullptr) {           //jsme na konci, hledame tail
            current_ptr = o_list->tail;
        } else {
            current_ptr = current_ptr->prev;
        }
        return *this;
    }

    //postfix
    list::const_iterator list::const_iterator::operator++(int) {
        const_iterator temp = *this;
        ++(*this);  //zavola prefix verzi
        return temp;
    }

    list::const_iterator list::const_iterator::operator--(int) {
        const_iterator temp = *this;
        --(*this);
        return temp;
    }

    list::const_iterator list::begin() const {
        return {head, this};
    }

    list::const_iterator list::cbegin() const {
        return {head, this};
    }

    list::const_iterator list::end() const {
        return {nullptr, this};
    }

    list::const_iterator list::cend() const {
        return {nullptr, this};
    }



//iterator
    //constructor
    list::iterator::iterator(node *ptr, const list *gen) : current_ptr(ptr), o_list(gen){

    }

    //definovani operatorů
    bool list::iterator::operator==(const iterator& rhs) const {
        return current_ptr == rhs.current_ptr;
    }

    bool list::iterator::operator!=(const iterator& rhs) const {
        return !(*this == rhs);
    }

    list::iterator::reference list::iterator::operator*() const {
        return current_ptr->val;
    }

    list::iterator::pointer list::iterator::operator->() const {
        return &(current_ptr->val);
    }

    //prefix
    list::iterator& list::iterator::operator++() {
        current_ptr = current_ptr->next;
        return *this;
    }

    list::iterator& list::iterator::operator--() {
        if (current_ptr == nullptr) {           //jsme na konci, hledame tail
            current_ptr = o_list->tail;
        } else {
            current_ptr = current_ptr->prev;
        }
        return *this;
    }

    //postfix
    list::iterator list::iterator::operator++(int) {
        iterator temp = *this;
        ++(*this);
        return temp;
    }

    list::iterator list::iterator::operator--(int) {
        iterator temp = *this;
        --(*this);
        return temp;
    }

    //konverze iterator -> const_iterator
    list::iterator::operator const_iterator() const {
        return const_iterator(current_ptr, o_list);
    }

    list::iterator list::begin() {
        return iterator(head, this);
    }
    list::iterator list::end() {
        return iterator(nullptr, this);
    }


//functions
    list::list(const std::vector<double>& vec) {
        for (double v : vec) {
            push_back(v);
        }
    }

    void list::push_back(double elem) {
        node* new_node = new node{elem, tail, nullptr};

        if (tail != nullptr) {
            tail->next = new_node;
        } else {
            head = new_node;
        }

        tail = new_node;
        ++num_elements;
    }

    void list::pop_back() {
        if (tail != nullptr) {
            node* temp = tail;
            tail = tail->prev;

            if (tail != nullptr) {
                tail->next = nullptr;
            } else {
                head = nullptr;         //byl to posledni prvek, list je prazdny
            }
            delete temp;
            --num_elements;
        }
    }

    double &list::back() {
        return tail->val;
    }

    double const &list::back() const {
        return tail->val;
    }

    void list::push_front(double elem) {
        node* new_node = new node{elem, nullptr, head};
        if (head != nullptr) {
            head->prev = new_node;
        } else {
            tail = new_node;        //prvni prvek v listu
        }
        head = new_node;
        ++num_elements;
    }

    void list::pop_front() {
        if (head != nullptr) {
            node* temp = head;
            head = head->next;

            if (head != nullptr) {
                head->prev = nullptr;
            } else {
                tail = nullptr;         //byl to posledni prvek, list je prazdny
            }
            delete temp;
            --num_elements;
        }
    }

    double &list::front() {
        return head->val;
    }

    double const &list::front() const {
        return head->val;
    }

    void list::reverse() {
        if (head != nullptr) {
            node* curr = head;
            node* temp = nullptr;
            //projdi kazdy uzel a prohod odkazy prev<->next
            while (curr != nullptr) {
                temp = curr->prev;
                curr->prev = curr->next;
                curr->next = temp;
                curr = curr->prev;
            }

            temp = head;
            head = tail;
            tail = temp;
        }
    }

    void list::remove(double value) {
        node* curr = head;
        while (curr != nullptr) {
            node* next = curr->next;
            if (curr->val == value) {
                if (curr->prev != nullptr) {
                    curr->prev->next = curr->next;
                } else {
                    head = curr->next;
                }
                if (curr->next != nullptr) {
                    curr->next->prev = curr->prev;
                } else {
                    tail = curr->prev;
                }
                delete curr;
                --num_elements;
            }

            curr = next;
        }
    }

    void list::swap(list &rhs) {
        std::swap(head, rhs.head);
        std::swap(tail, rhs.tail);
        std::swap(num_elements, rhs.num_elements);
    }

    bool list::operator==(const list &rhs) const {
        if (num_elements != rhs.num_elements) {
            return false;
        }
        auto it1 = begin();
        auto it2 = rhs.begin();

        while (it1 != end() && it2 != rhs.end()) {
            if (*it1 != *it2) {
                return false;
            }
            ++it1;
            ++it2;
        }
        return true;            //jsme na konci obou listů, listy jsou stejne
    }

    bool list::operator<(const list &rhs) const {
        auto it1 = begin();
        auto it2 = rhs.begin();

        while (it1 != end() && it2 != rhs.end()) {
            if (*it1 < *it2) {
                return true;
            }
            if (*it2 < *it1) {
                return false;
            }
            ++it1;
            ++it2;
        }
        return (it1 == end()) && (it2 != rhs.end());
    }

    std::pair<list, list> list::split(const_iterator place) {
        list first_list;
        list second_list;

        //pokud list je prazdny nebo ma 1 prvek
        if (place == begin()) {
            second_list.head = head;
            second_list.tail = tail;
            second_list.num_elements = num_elements;

            head = nullptr;
            tail = nullptr;
            num_elements = 0;

            return {std::move(first_list), std::move(second_list)};
        }

        //node pred place
        node* split_node = place.current_ptr;
        node* prev_node = nullptr;
        node* curr = head;

        while (curr != split_node) {
            prev_node = curr;
            curr = curr->next;
        }

        //prvni list [begin, place)
        first_list.head = head;
        first_list.tail = prev_node;
        if (prev_node) {
            prev_node->next = nullptr;
        }
        //druhy list [place, end)
        second_list.head = split_node;
        second_list.tail = tail;

        //pocitani velikosti
        size_t size = 0;
        for (node* n = first_list.head; n != nullptr; n = n->next) {
            size++;
        }
        first_list.num_elements = size;
        second_list.num_elements = num_elements-size;

        //vyprazdnovani puvodniho lista
        head = tail = nullptr;
        num_elements = 0;

        return {std::move(first_list), std::move(second_list)};
    }

    void list::merge(list &rhs) {
        if (rhs.num_elements == 0) return;

        if (num_elements == 0) {
            head = rhs.head;
            tail = rhs.tail;
            num_elements = rhs.num_elements;

            rhs.head = rhs.tail = nullptr;
            rhs.num_elements = 0;
            return;
        }

        node* new_head = nullptr;
        node* curr = nullptr;

        node* p1 = head;
        node* p2 = rhs.head;

        if (p1->val <= p2->val) {
            new_head = curr = p1;
            curr->prev = nullptr;
            p1=p1->next;
        } else {
            new_head = curr = p2;
            curr->prev = nullptr;
            p2=p2->next;
        }

        //sluceni dokud oba listy maji prvky
        while (p1 != nullptr && p2 != nullptr) {
            if (p1->val <= p2->val) {
                curr->next = p1;        //pripojeni p1 na konec vysledneho listu
                p1->prev = curr;
                curr = p1;              //posun curr na tento novy konec
                p1 = p1->next;          //posun p1 na dalsi prvek z puvodniho listu
            } else {
                curr->next = p2;
                p2->prev = curr;
                curr = p2;
                p2 = p2->next;
            }
        }

        if (p1 != nullptr) {
            curr->next = p1;            //pripojeni celeho zbytku listu A
            p1->prev = curr;
        } else if (p2 != nullptr) {
            curr->next = p2;
            p2->prev = curr;
            tail = rhs.tail;
        } else {
            //listy byly vycerpany soucasne
            tail = curr;
        }

        num_elements += rhs.num_elements;
        head = new_head;

        rhs.head = rhs.tail = nullptr;
        rhs.num_elements = 0;
    }


    void list::sort() {
        if (num_elements <= 1) {
            return;
        }

        const_iterator middle = begin();
        size_t half_size = num_elements/2;

        for (size_t i = 0; i < half_size; ++i) {
            ++middle;
        }

        std::pair<list, list> result = split(middle);
        list l = std::move(result.first);
        list r = std::move(result.second);

        l.sort();
        r.sort();

        l.merge(r);
        *this = std::move(l);
    }

    //lhs = a, rhs = b
    bool operator!=(const pcc::list& lhs, const pcc::list& rhs) {
        return !(lhs == rhs);
    }
    bool operator>(const list &lhs, const list &rhs) {
        return rhs < lhs;
    }
    bool operator>=(const list &lhs, const list &rhs) {
        return !(lhs < rhs);
    }
    bool operator<=(const list &lhs, const list &rhs) {
        return !(rhs < lhs);
    }

    void swap(list &lhs, list &rhs) {
        lhs.swap(rhs);
    }


} // end namespace pcc
