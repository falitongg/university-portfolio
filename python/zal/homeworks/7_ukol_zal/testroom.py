class Node:
    def __init__(self, data, nextNode = None, prevNode = None):
        self.nextNode = nextNode
        self.prevNode = prevNode
        self.data = data



class LinkedList:
    def __init__(self):
        self.head = None


class Car:
    def __init__(self, identification, name, brand, price, active):
        self.identification = identification
        self.name = name
        self.brand = brand
        self.price = price
        self.active = active
    # def __str__(self):
    #     return (f"Car id: {self.identification}, name: {self.name}, brand: {self.brand}, price: {self.price}, stav: {self.active}")

db = LinkedList()

car1 = Car("123", "Toyota", "Corolla", "120000", "Jede")
car2 = Car("124", "TAVRIA", "KOZAK", "20000", "LETI")
cars = [car1, car2]
# node1 = Node(None, None, car1)
# node2 = Node(None, None, car2)
# node2.prevNode = node1
# node1.nextNode = node2
# print(node1.nextNode)
# print(node2.prevNode)



def init(cars): 
    for car in cars:
        add(car, db)
    return db


def add(car, db):
    new_node = Node(car, None, None)
    if db.head is None:
        # If the list is empty, make this node the head
        db.head = new_node
    else:
        current = db.head
        # If the new node's price is smaller than the head, insert it before the head
        if car.price < db.head.data.price:
            new_node.nextNode = db.head
            db.head.prevNode = new_node
            db.head = new_node
        else:
            # Traverse the list to find the correct position
            while current.nextNode is not None and new_node.data.price > current.nextNode.data.price:
                current = current.nextNode
            
            # Insert the new node in the correct position
            new_node.nextNode = current.nextNode
            new_node.prevNode = current
            
            if current.nextNode is not None:
                current.nextNode.prevNode = new_node
            
            current.nextNode = new_node

                


def updateName(identification, name):
    pass


def updateBrand(identification, brand):
    pass


def activateCar(identification):
    pass


def deactivateCar(identification):
    pass


def getDatabaseHead():
    return db.head


def getDatabase():
    return db


def calculateCarPrice():
    pass


def clean():
    db.head = None

l = []
l.append(Car(1001, 'Model X', 'Tesla', 80000, True))
l.append(Car(1002, 'Civic', 'Honda', 25000, True))
l.append(Car(1006, 'KIVIK', 'BUnda', 25000, True))

l.append(Car(1003, 'Corolla', 'Toyota', 22000, False))
l.append(Car(1004, 'Mustang', 'Ford', 55000, True))
l.append(Car(1005, 'Cayenne', 'Porsche', 95000, False))
for el in l:
    add(el, db)
temp = db.head
while(temp!=None):
    print(temp.data.name,temp.data.price)
    temp = temp.nextNode