"""
Homer's fridge
Course: B0B36ZAL
"""

#nasledujici kod nijak nemodifikujte!
class Food:
    def __init__(self, name, expiration):
        self.name = name
        self.expiration = expiration
#predesly kod nijak nemodifikujte!

def openFridge(fridge):
    print("Following items are in Homer's fridge:")
    for food in fridge:
        print(f"{food.name} (expires in: {food.expiration} days)")
    print("")

# fridge = [Food("beer", 10), Food("steak", 222), Food("hamburger", 111), Food("donut", 33)]
# print(openFridge(fridge))
# # test vypisu - pri odevzdani smazte, nebo zakomentujte
fridge = [Food("beer", 4), Food("steak", 1), Food("hamburger", 1), Food("donut", 3)]

"""
Task #1
"""
def maxExpirationDay(fridge):
    if not fridge:
        return -1
    else:
        days = []
        for food in fridge:
            days.append(food.expiration)
        # print(days)
        for i in range(len(days)):
            # print(days[i])
            for j in range(len(days)):
                if days[i] > days[j]:
                    days[i], days[j] = days[j], days[i]
        return days[0]
# print(maxExpirationDay(fridge))
# import random
# days = []
# for x in range (10):
#     days.append(random.randint(1, 100))
# print(days)

# for i in range(len(days)):
#     print(days[i])
#     for j in range(len(days)):
#         if days[i] > days[j]:
#             days[i], days[j] = days[j], days[i]
# print(days[0])
# test vypisu - pri odevzdani smazte, nebo zakomentujte
# print(maxExpirationDay(fridge))
# The command should print 4


"""
Task #2
"""
def histogramOfExpirations(fridge):
    if not fridge:
        return []
    max_exp_day = maxExpirationDay(fridge)
    histogram = [0] * (max_exp_day +1)
    for food in fridge:
        histogram[food.expiration] +=1
    return histogram

# histogram = histogramOfExpirations(fridge)
# print(histogram)
# test vypisu - pri odevzdani smazte, nebo zakomentujte
# print(histogramOfExpirations(fridge))
# The command should print [0, 2, 0, 1, 1]


"""
Task #3
"""

def cumulativeSum(histogram):
    if not histogram:
        return []
    sum = [0] * len(histogram)
    sum[0] = histogram[0]
    for i in range (1, len(histogram)):
        sum[i] = sum[i-1] + histogram[i]
    return sum
# print(cumulativeSum(histogram))

# test vypisu - pri odevzdani smazte, nebo zakomentujte
# print(cumulativeSum([0, 2, 0, 1, 1]))
# The command should print [0, 2, 2, 3, 4]


"""
Task #4
"""
def sortFoodInFridge(fridge):
    if not fridge:
        return []
    sorted_food = [None] * len(fridge)
    cum = cumulativeSum(histogramOfExpirations(fridge))
    for food in fridge:
        print(food)
        posld = cum[food.expiration] - 1
        print(posld)
        cum[food.expiration] -= 1
        print(cum)
        sorted_food[posld] = food
    return sorted_food
        
# openFridge(sortFoodInFridge(fridge))

# test vypisu - pri odevzdani smazte, nebo zakomentujte
# openFridge(sortFoodInFridge(fridge))
# The command should print
# Following items are in Homer's fridge:
# hamburger (expires in: 1 days)
# steak (expires in: 1 days)
# donut (expires in: 3 days)
# beer (expires in: 4 days)


"""
Task #5
"""
def reverseFridge(fridge):
    r_fridge = fridge[::-1]
    return r_fridge
# openFridge(reverseFridge(fridge))


# test vypisu - pri odevzdani smazte, nebo zakomentujte
# openFridge(reverseFridge(fridge))
# The command should print
# Following items are in Homer's fridge:
# donut (expires in: 3 days)
# hamburger (expires in: 1 days)
# steak (expires in: 1 days)
# beer (expires in: 4 days)

# test vypisu - pri odevzdani smazte, nebo zakomentujte
# openFridge(sortFoodInFridge(reverseFridge(fridge)))
# The command should print
# Following items are in Homer's fridge:
# steak (expires in: 1 days)
# hamburger (expires in: 1 days)
# donut (expires in: 3 days)
# beer (expires in: 4 days)


"""
Task #6
"""
def eatFood(name, fridge):
    fridge_copy = fridge[:]
    foods_to_remove = [food for food in fridge_copy if food.name == name]
    if foods_to_remove:
        food_to_remove = min(foods_to_remove, key=lambda food:food.expiration)
        fridge_new = [food for food in fridge_copy if food != food_to_remove]
        return fridge_new
    return fridge_copy
# test vypisu - pri odevzdani smazte, nebo zakomentujte
# openFridge(
#     eatFood("donut",
#         [Food("beer", 4), Food("steak", 1), Food("hamburger", 1),
#         Food("donut", 3), Food("donut", 1), Food("donut", 6)]
#     ))
# The command should print
# Following items are in Homer's fridge:
# beer (expires in: 4 days)
# steak (expires in: 1 days)
# hamburger (expires in: 1 days)
# donut (expires in: 3 days)
# donut (expires in: 6 days)
