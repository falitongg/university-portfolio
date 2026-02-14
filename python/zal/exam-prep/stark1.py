class Fridge:
    def __init__(self, name, minimalCofeinRequired):
        self.name = name
        self.minimalCofeinRequired = minimalCofeinRequired
        self.items = []
class Item:
    def __init__(self, name, coffeeCapacity, number):
        self.name = name
        self.coffeeCapacity = coffeeCapacity
        self.number = number
    def __repr__(self):
        return f"Item(name={self.name}, coffeeCapacity={self.coffeeCapacity}, number={self.number})"
#1
def biggestCofeinInFridge(itemsFridge:list[int])->int:
    if not itemsFridge:
        return 'Error'
    biggestItem = itemsFridge[0]
    for item in itemsFridge:
        if item > biggestItem:
            biggestItem = item
    return biggestItem

# print(str(biggestCofeinInFridge([1,34,1,5,23,-13,12,0,324,1,-1000])))
# print(str(biggestCofeinInFridge([1,0,3])))

#2
def isWorkingAllNight(itemsFridge, cofeinRequired):
    if not itemsFridge:
        return "Error"
    total_cofein = 0
    tolerance = cofeinRequired*0.1
    for item in itemsFridge:
        if item>0:
            total_cofein += item
    if total_cofein > cofeinRequired:
        return 1
    if cofeinRequired <= total_cofein <= cofeinRequired+tolerance:
        return 0
    if total_cofein < cofeinRequired:
        return -1
# print(str(isWorkingAllNight([1,-3,1,10], 20)))
# print(str(isWorkingAllNight([1,-3,9,10], 20)))
# print(str(isWorkingAllNight([13,-3,13,10], 20)))

#3
def whenCallSupplier(itemsFridge, consumptions):
    if not itemsFridge:
        return "Error"
    total_cofein = 0
    for item in itemsFridge:
        if item > 0:
            total_cofein += item
    for i in range(len(consumptions)):
        if total_cofein < consumptions[i]:
            return i
        total_cofein -= consumptions[i]
    return -1
# print(whenCallSupplier([1,-3,1,10],[20,30,1,2,0,15]))
# print(whenCallSupplier([1,-3,1,10],[5,4,1,2,0,15]))
# print(whenCallSupplier([1,-3,1,10],[7,8,1,2,0,15]))
# print(whenCallSupplier([1,-3,1,10],[2,3,1,2,0,3]))
# print(whenCallSupplier([1,-3,1,2,1],[2,9,1,2]))
# print(whenCallSupplier([1,-3,3,2,1],[2,5,1,2]))

#4

def convertFridge(itemsInFridge:list[int])->list[Item]:
    items = []
    for i in range(len(itemsInFridge)):
        current = itemsInFridge[i]
        name = 'Coffee' if current>0  else 'Healthy Food'
        found = False
        for item in items:
            if item.name == name and item.coffeeCapacity == current:
                item.number += 1
                found = True
        if not found:
            items.append(Item(name, current, 1))
    return items
def agregate(items, new_item):
    for item in items:
        if item.name == new_item.name and item.coffeeCapacity == new_item.coffeeCapacity:
            item.number += new_item.number
            return items
    items.append(new_item)
    return items
itemsInFridge = [10, -5, 10, -5, 5, 10]
converted_items = convertFridge(itemsInFridge)
for item in converted_items:
    print(item)