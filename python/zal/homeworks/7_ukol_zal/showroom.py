class Node:
    def __init__(self, data, nextNode = None, prevNode = None):
        self.nextNode = nextNode
        self.prevNode = prevNode
        self.data = data



class LinkedList:
    def __init__(self):
        self.head = None
    def __str__(self):
        cars = []
        current = self.head
        while current is not None:
            cars.append(f"{current.data.name} - {current.data.price}")
            current = current.nextNode
        return "\n".join(cars)

class Car:
    def __init__(self, identification, name, brand, price, active):
        self.identification = identification
        self.name = name
        self.brand = brand
        self.price = price
        self.active = active
    def __str__(self):
        return (f"Car id: {self.identification}, name: {self.name}, brand: {self.brand}, price: {self.price}, stav: {self.active}")

db = LinkedList()

# car1 = Car("123", "Toyota", "Corolla", "120000", "Jede")
# car2 = Car("124", "TAVRIA", "KOZAK", "20000", "LETI")
# cars = [car1, car2]
# node1 = Node(None, None, car1)
# node2 = Node(None, None, car2)
# node2.prevNode = node1
# node1.nextNode = node2
# print(node1.nextNode)
# print(node2.prevNode)



def init(cars): 
    global db
    for car in cars:
        add(car)
    return db


def add(car):
    global db
    new_node = Node(car, None, None)
    if db.head is None:
        db.head = new_node
    else:
        current = db.head
        if car.price < db.head.data.price:#start
            new_node.nextNode = db.head
            db.head.prevNode = new_node
            db.head = new_node
        else:
            while current.nextNode is not None and new_node.data.price > current.nextNode.data.price:
                current = current.nextNode
            new_node.nextNode = current.nextNode
            new_node.prevNode = current
            if current.nextNode is not None:
                current.nextNode.prevNode = new_node
            current.nextNode = new_node


def updateName(identification, name):
    global db
    current = db.head
    while current is not None and current.data.identification != identification:
        current = current.nextNode
    if current is not None:
        current.data.name = str(name)
        return current.data
    else:
        print("chyba")

def updateBrand(identification, brand):
    global db
    current = db.head
    while current is not None and current.data.identification != identification:
        current = current.nextNode
    if current is not None:
        current.data.brand = str(brand)
        return current.data
    else:
        print("chyba")


def activateCar(identification):
    global db
    current = db.head
    while current is not None and current.data.identification != identification:
        current = current.nextNode
    if current is not None:
        current.data.active = True
    else:
        print("chyba")


def deactivateCar(identification):
    global db
    current = db.head
    while current is not None and current.data.identification != identification:
        current = current.nextNode
    if current is not None:
        current.data.active = False
    else:
        print("chyba")


def getDatabaseHead():
    global db
    return db.head


def getDatabase():
    global db
    return db


def calculateCarPrice():
    global db
    current = db.head
    total = 0
    while current is not None:
        if current.data.active == True:
            total += int(current.data.price)
        current = current.nextNode
    return total


def clean():
    global db
    db.head = None

l = []
l.append(Car(1001, 'Model X', 'Tesla', 80000, True))
l.append(Car(1002, 'Civic', 'Honda', 25000, True))
l.append(Car(1003, 'Corolla', 'Toyota', 22000, False))
l.append(Car(1004, 'Mustang', 'Ford', 55000, True))
l.append(Car(1005, 'Cayenne', 'Porsche', 95000, False))
for el in l:
    add(el)
temp = db.head
while(temp!=None):
    print(temp.data.name,temp.data.price)
    temp = temp.nextNode
updateName(1002, "TEST")
print(updateName(1002, "TEST"))
print(calculateCarPrice())
print(db)
clean()
print(db)
