class Node:
    def __init__(self, value:int):
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
        current = None
        if self.root == None:
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
        self.array = array
        l = len(self.array)
        for i in range (l):
            self.insert(array[i])
    def search(self, value):
        current = self.root
        self.visited_nodes = 0
        while current is not None:
            self.visited_nodes +=1
            if current.value == value:
                return True
            elif current.value < value:
                current = current.right
            elif current.value > value:
                current = current.left
        return False
        

    def min(self):
        self.visited_nodes = 0
        current = self.root 
        if current is None:
            return None
        while current.left is not None:
            current = current.left
            self.visited_nodes +=1
        self.visited_nodes +=1
        return current.value
        

    def max(self):
        self.visited_nodes = 0
        current = self.root
        if current is None:
            return None
        while current.right is not None:
            current = current.right
            self.visited_nodes +=1
        self.visited_nodes +=1
        return current.value

    def visitedNodes(self):
        return self.visited_nodes
    def __str__(self):
       return "Inserted values: " + "".join(str(self.inserted_values))


# bst2 = BinarySearchTree()

# print(bst2)

# bst2 = BinarySearchTree()
# print(bst2.search(1))
# print(bst2.search(1))
# print(bst2.search(3))
# print(bst2.search(10))
# print(bst2.min())
# print(bst2.max())
# bst2 = BinarySearchTree()
# bst2.fromArray([5, 3, 1, 4, 7, 6, 8,7])

# bst3 = BinarySearchTree()
# bst3.fromArray([1, 3, 4, 5, 6, 7, 8])

# print(str(bst3.min()))
# print(bst3.visitedNodes())
# print(str(bst3.max()))
# print(bst3.visitedNodes()) 

# bst1 = BinarySearchTree()

# print(bst1.search(10))
# print(bst1.visitedNodes())
# print(bst1.min())
# print(bst1.max())

bst2 = BinarySearchTree()
bst2.fromArray([5, 3, 1, 4, 7, 6, 8])

print(bst2.search(5))
print(bst2.visitedNodes())
print(bst2.search(7))
print(bst2.visitedNodes())
print(bst2.search(6))
print(bst2.visitedNodes())
print(bst2.search(10))
print(bst2.visitedNodes())
print("min: " + str(bst2.min()))
print(bst2.visitedNodes())
print("max: " + str(bst2.max()))
print(bst2.visitedNodes()) 


# bst3 = BinarySearchTree()
# bst3.fromArray([1, 3, 4, 5, 6, 7, 8])

# print( str(bst3.min()))
# print(bst3.visitedNodes())
# print( str(bst3.max()))
# print(bst3.visitedNodes()) 