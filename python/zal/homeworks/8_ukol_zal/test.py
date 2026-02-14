class Node:
    def __init__(self, value: int):
        self.value = value
        self.left = None
        self.right = None

class BinarySearchTree:
    def __init__(self):
        self.root = None
        self.inserted_values = []
        self.visited_nodes = 0

    def insert(self, value):
        new_node = Node(value)
        self.inserted_values.append(value)
        
        if self.root is None:
            self.root = new_node
        else:
            current = self.root
            while current is not None:
                if new_node.value < current.value:
                    if current.left is None:
                        current.left = new_node
                        break
                    current = current.left
                elif new_node.value > current.value:
                    if current.right is None:
                        current.right = new_node
                        break
                    current = current.right
                else:
                    break

    def fromArray(self, array):
        for value in array:
            self.insert(value)

    def search(self, value):
        current = self.root
        self.visited_nodes = 0  # Reset before starting search
        while current is not None:
            self.visited_nodes += 1
            if current.value == value:
                return True
            elif current.value < value:
                current = current.right
            else:
                current = current.left
        return False

    def min(self):
        self.visited_nodes = 0  # Reset before searching for min
        current = self.root
        if current is None:
            return None
        while current.left is not None:
            self.visited_nodes += 1
            current = current.left
        self.visited_nodes += 1  # Count the final node (root or leftmost leaf)
        return current.value

    def max(self):
        self.visited_nodes = 0  # Reset before searching for max
        current = self.root
        if current is None:
            return None
        while current.right is not None:
            self.visited_nodes += 1
            current = current.right
        self.visited_nodes += 1  # Count the final node (root or rightmost leaf)
        return current.value

    def visitedNodes(self):
        return self.visited_nodes

    def __str__(self):
        return "Inserted values: " + ", ".join(str(val) for val in self.inserted_values)


# Testing
bst2 = BinarySearchTree()
bst2.fromArray([5, 3, 1, 4, 7, 6, 8, 7])

print("Searching for 1: ", bst2.search(1))  # Should return True
print("Searching for 10: ", bst2.search(10))  # Should return False
print("Minimum value: ", bst2.min())  # Should return 1
print("Maximum value: ", bst2.max())  # Should return 8
print("Visited nodes during min search: ", bst2.visitedNodes())  # Should show visited count for min
print("Visited nodes during max search: ", bst2.visitedNodes())  # Should show visited count for max

# Testing with another tree
bst3 = BinarySearchTree()
bst3.fromArray([1, 3, 4, 5, 6, 7, 8])

print("Minimum value for bst3: ", bst3.min())  # Should return 1
print("Visited nodes for min search in bst3: ", bst3.visitedNodes())  # Should show visited count
print("Maximum value for bst3: ", bst3.max())  # Should return 8
print("Visited nodes for max search in bst3: ", bst3.visitedNodes())  # Should show visited count
