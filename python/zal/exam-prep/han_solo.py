planets = ["Tatooine", "Naboo", "Hotch", "Devaron", "Dantooine", "Alderaan"]

hyperTable = [
    [-1, 40, 20, 150, 130, 218],
    [40, -1, 135, 70, 45, 198],
    [20, 135, -1, 20, 60, 166],
    [150, 70, 20, -1, 112, 62],
    [130, 45, 60, 112, -1, 15],
    [218, 198, 166, 62, 15, -1]
]
#1
def getPlanetIndex(planets:list[str], name:str):
    for planet in range (len(planets)):
        if planets[planet] == name:
            return planet
    return "error"
        
# print(getPlanetIndex(planets, "Hotch"))
def getPlanetName(planets:list[str], numb:int):
    for planet in range(len(planets)):
        if planet == numb:
            return planets[planet]
    return "error"
# print(getPlanetName(planets, 0))
#2

# def getMinEnergyTargetPlanet(hyperTable:list[list[int]], planets:list[str], planet:str):
#     table = hyperTable[getPlanetIndex(planets, planet)]
#     min_energy = float('inf')
#     for i in range (len(table)):
#         for j in range(len(table)):
#             if table[i] < table[j] and table[i] != -1 and table[i] != table[j] and table[i] < min_energy:
#                 min_energy = table[i]
#     return min_energy
# print(getMinEnergyTargetPlanet(hyperTable, planets, "Naboo"))

def getMinEnergyTargetPlanet(hyperTable:list[list[int]], planets:list[str], planet:str):
    index = getPlanetIndex(planets, planet)
    if index is None:
        return "Error"
    table = hyperTable[index]
    min_energy = float('inf')
    for planet in table:
        if planet != -1 and planet < min_energy:
            min_energy = planet
    for i in range(len(table)):
        if table[i] == min_energy:
            index = i
            break
    return planets[i]
# print(getMinEnergyTargetPlanet(hyperTable, planets, "Alderaan"))
#3
class Jump:
    def __init__(self, sourceID, targetID, energyConsumed):
        self.sourceID = sourceID
        self.targetID = targetID
        self.energyConsumed = energyConsumed

def getAllPossibleJumps(planets, sourceID, energyConsumed):
    if sourceID < 0 or sourceID >= len(planets):
        raise ValueError("sourceID out of range")
    jumpArray = []
    table = hyperTable[sourceID]
    for targetID in range(len(table)):
        if targetID != sourceID and table[targetID] != -1:
            jump = Jump(sourceID, targetID, table[targetID] + energyConsumed)
            jumpArray.append(jump)
    return jumpArray


def printJumps(planets, jumpArray):
    if not jumpArray:
        print("Error")
        return
    
    print("Jumps:")
    for jump in jumpArray:
        sourcePlanet = planets[jump.sourceID]
        targetPlanet = planets[jump.targetID]
        energy = jump.energyConsumed
        print(f"{targetPlanet} via {sourcePlanet} for {energy}")
# printJumps(planets, getAllPossibleJumps(planets, 1, 100))

#4
def getMinEnregyJump(jumps:list[Jump]):
    if not jumps:
        return None
    bestJump = jumps[0]
    for jump in jumps:
        if jump.energyConsumed < bestJump.energyConsumed:
            bestJump = jump
    return bestJump

bestJump = getMinEnregyJump([Jump(1, 2, 30), Jump(1, 3, 10), Jump(3, 1, 15)])
# print(str(bestJump.sourceID)+", "+str(bestJump.targetID)+", "+str(bestJump.energyConsumed))

#5
def removeJump(jumps, jump):
    new_jumps = []
    for i in jumps:
        if i != jump:
            new_jumps.append(i)
    return new_jumps

jumps = [Jump(1,2,30), Jump(1,3,10), Jump(3,1,15)]
jump = jumps[0]

# printJumps(planets, removeJump(jumps, jump))

#6
def getMeThere(hyperTable, sourceID, targetID):
    pos_jumps = [Jump(sourceID, targetID, 0)]
    pass