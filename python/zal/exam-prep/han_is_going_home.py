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
def getPlanetIndex(planets, planet):
    if planet in planets:
        return planets.index(planet)
    if isinstance(planet, int) or isinstance(planet, str) and planet.isdigit() :
        planet = int(planet)
        if 0<=planet<len(planets):
            return planets[planet]
        return "Error"
    return "Error"
# print(getPlanetIndex(planets, "Naboo"))
# print(getPlanetIndex(planets, 1))

#2
def getMinEnergyTargetPlanet(table, planets, planet:str):
    index = getPlanetIndex(planets, planet)
    if isinstance(index, str):
        return "Error, planet not found"
    energy_row = table[index]
    min_energy = float('inf')
    target_index = -1
    for i in range(len(energy_row)):
        energy = energy_row[i]
        if energy !=-1 and energy<min_energy:
            min_energy = energy
            target_index = i
    if target_index != -1:
        return planets[target_index]
    return "No available planets"
# print(getMinEnergyTargetPlanet(hyperTable, planets, "Naboo"))

#3
class Jump:
    
    def __init__(self, sourceID, targetID, energyConsumed):
        self.sourceID = sourceID
        self.targetID = targetID
        self.energyConsumed = energyConsumed

    # def __check__(self):
    #     return f"Jump({planets[self.sourceID]} -> {planets[self.targetID]}, Energy: {self.energyConsumed})"

def getallPossibleJumps(planets, hyperTable,sourceID, energyConsumed ):
    jumps = []
    for targetID, energy in enumerate (hyperTable[sourceID]):
        if energy !=-1:
            jumps.append(Jump(sourceID,targetID, energyConsumed+energy))
    return jumps


def printJumps (planets, jumpArray):
    print(f"Jumps:") 
    for jump in jumpArray:
        print(f"{planets[jump.sourceID]} via {planets[jump.targetID]} for {jump.energyConsumed}")


print(getPlanetIndex(planets, "Naboo"))
print(getPlanetIndex(planets, 1))

print(getMinEnergyTargetPlanet(hyperTable,planets,"Naboo"))

currentPlanet = "Naboo"
currentEnergy =100
sourceID = getPlanetIndex(planets, currentPlanet)

jumpsArray = getallPossibleJumps(planets,hyperTable,  sourceID, currentEnergy)
printJumps(planets, jumpsArray)








# printJumps(getAllPossibleJumps(planets, 1, 100))

# def getAllPossibleJumps(planets, sourceID, energyConsumed)->list[Jump]:
#     jumpsArray = []
#     energy_row = hyperTable[sourceID]
#     sourceID = planets[sourceID]
#     for i in len(energy_row):
#         if energy_row[i] != -1:
#            energyConsumed +=energy_row[i]
#            targetID = planets[i]
#     jumpsArray.append(Jump(targetID, sourceID, energyConsumed))
#     return jumpsArray
# def printJumps(planets, jumpsArray):
#     for jump in jumpsArray:
#         print(f"{jump.targetID} via {jump.sourceID} for {jump.energyConsumed}")
# printJumps(getAllPossibleJumps(planets, 1, 100))