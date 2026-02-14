#1
def biggestCofeinInFridge(itemsFridge:list[int])->int:
    if not itemsFridge:
        return 0
    biggestItem = itemsFridge[0]
    for item in itemsFridge:
        if item >= biggestItem:
            biggestItem = item
    return biggestItem

# print(biggestCofeinInFridge( [1,34,1,5,23,-1,12,0,324,1,-1000] ))
# print(biggestCofeinInFridge( [1,0,3]))

#2
def isWorkingAllNight(itemsFridge:list[int], cofeinRequired:int):
    if not itemsFridge:
        return "Error"
    cofein = 0
    for item in itemsFridge:
        if item > 0:
            cofein += item
    
    ten = cofeinRequired*0.1
    if cofein > cofeinRequired:
        return 1
    if cofeinRequired <= cofein <= cofeinRequired + ten:
        return 0
    if cofein < cofeinRequired:
        return -1

# print(isWorkingAllNight( [1,-3,1,10],20 ))
# print(isWorkingAllNight( [1,-3,9,10],20 ))
# print(isWorkingAllNight( [13,-3,13,10],20 ))

#3
def whenCallSupplier(itemsFridge:list[int], consumptions:list[int])->int:
    items_sum = 0
    for item in itemsFridge:
        if item > 0:
            items_sum += item
    for i in range(len(consumptions)):
        if items_sum < consumptions[i]:
            return i
        items_sum -= consumptions[i]
    return -1
# print(whenCallSupplier([1,-3,1,10],[20,30,1,2,0,15]))
# print(whenCallSupplier([1,-3,1,10],[5,4,1,2,0,15]))
# print(whenCallSupplier([1,-3,1,10],[7,8,1,2,0,15]))
# print(whenCallSupplier([1,-3,1,10],[2,3,1,2,0,3]))
# print(whenCallSupplier([1,-3,1,2,1],[2,9,1,2]))
# print(whenCallSupplier([1,-3,3,2,1],[2,5,1,2]))

#4
class Item:
    def __init__(self, coffeeCapacity, number):
        self.name = coffeeType if coffeeCapacity>0 else healthyFoodType
        self.coffeeCapacity = coffeeCapacity
        self.number = number
    def __repr__(self):
        return f"{self.name} ({str(self.coffeeCapacity)}, {str(self.number)})"
healthyFoodType = 'HealthyFood'
coffeeType = 'Coffee'

def agregate(items:list[Item], item:Item)->list[Item]:
    items_copy = []
    for i in items:
        items_copy.append(Item(i.coffeeCapacity, i.number))
    found = False
    for i in items_copy:
        if i.coffeeCapacity == item.coffeeCapacity:
            i.number += item.number
            found = True
    if not found:
        items_copy.append(item)
    return items_copy

# itemsToAgregate = [Item(20,1), Item(10,2), Item(-10,3)]
# print(agregate([],Item(20,1)))
# print(agregate(itemsToAgregate, Item(20,2)))
# print(agregate(itemsToAgregate, Item(11,2)))

#5 and 6

def removeItem(Items, coffeeCapacityItem):
    if not Items:
        return "Error"
    items_copy = [Item(i.coffeeCapacity, i.number) for i in Items]
    for item in items_copy:
        if item.coffeeCapacity == coffeeCapacityItem:
            item.number -=1
            if item.number == 0:
                items_copy.remove(item)
    return items_copy
def isEmpty(Items):
    found = False
    for i in Items:
        if i.coffeeCapacity > 0:
            found = True
    if not found:
        return True
    else: 
        return False
def isFridgeHealthy(Items):
    if not Items:
        return "Error"
    items_sum = 0
    for item in Items:
        items_sum += item.coffeeCapacity
    if items_sum > 0:
        return False
    else:
        return True
def countCoffeinCapacity(Items):
    if not Items:
        return "Error"
    items_sum = 0
    for item in Items:
        if item.coffeeCapacity > 0:
            items_sum += item.coffeeCapacity * item.number
    return items_sum
items = [Item(20, 1), Item(10, 2), Item(-10, 3)]
print(removeItem(items, 10))
print(removeItem(items, 20))
print(removeItem(items, 2))

print(isEmpty(items))
print(isEmpty([]))
print(isEmpty([Item(-10,3)]))

print(isFridgeHealthy(items))
print(isFridgeHealthy([Item(10,2), Item(-10,3)]))

print(countCoffeinCapacity(items))