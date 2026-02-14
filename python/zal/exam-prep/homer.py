class Food:
    def __init__(self, name, expiration):
        self.name = name
        self.expiration = expiration
fridge = [Food('beer', 4), Food('steak', 1), Food('hamburger', 1), Food('donut', 3)]

def openFridge(fridge):
    print ('Following items are in Homers fridge:')
    for food in fridge:
        print("{0} (expires in: {1} days)".format(
            str(food.name), str(food.expiration))
        )
# openFridge(fridge)

#1
def maxExpirationDay(fridge):
    if not fridge:
        return "Error"
    max_day = fridge[0].expiration
    for food in fridge:
        if food.expiration > max_day:
            max_day = food.expiration
    return max_day

# print(maxExpirationDay(fridge))

#2
def histogramOfExpirations(fridge):
    max_day = maxExpirationDay(fridge)
    histogram = [0]*(max_day+1)
    for food in fridge:
        histogram[food.expiration] +=1
    return histogram

# histogram = histogramOfExpirations(fridge)
# print(histogram)

#3
def cumulativeSum(histogram):
    summa = [0]*len(histogram)
    summa[0] = histogram[0]
    for i in range(1, len(histogram)):
        summa[i] = summa[i-1] + histogram[i]
    return summa
print(cumulativeSum([1,2,3,4]))

#4
def sortFoodInFridge(fridge):
    # Vypočítá histogram expirací
    histogram = histogramOfExpirations(fridge)
    # Vypočítá kumulativní sumu histogramu
    cum_sum = cumulativeSum(histogram)
    # Inicializuje seřazenou lednici
    sorted_fridge = [None] * len(fridge)
    # Počty pro udržení sledování pozic výskytu
    positions = [0] * len(cum_sum)

    # Inicializuje počty na základě kumulativní sumy
    if len(cum_sum) > 1:
        positions[0] = 0
        for i in range(1, len(cum_sum)):
            positions[i] = cum_sum[i - 1]

    # Rozdělení potravin do seřazeného seznamu
    for food in fridge:
        # Najde pozici, kam umístit potravinu
        index = food.expiration
        sorted_fridge[positions[index]] = food
        # Inkrementuje pozici pro danou expiraci
        positions[index] += 1

    return sorted_fridge
openFridge(sortFoodInFridge(fridge))