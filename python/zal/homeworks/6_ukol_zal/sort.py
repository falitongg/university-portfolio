def sortNumbers(data, condition):
    n = len(data)
    if condition == "ASC":
        for run in range (n):
            for i in range (n-1-run):
                if data[i]>data[i+1]:
                    data[i], data[i+1]=data[i+1], data[i]
    if condition == "DESC":
        for run in range (n):
            for i in range (n-1-run):
                if data[i]<data[i+1]:
                    data[i], data[i+1]=data[i+1], data[i]
    return data
def sortData(cisla, auta, condition):
    n = len(cisla)
    if len(cisla) != len(auta):
        raise ValueError('Invalid input data')
    if condition == "ASC":
        for j in range(n):
            for i in range(n-1-j):
                if cisla[i]>cisla[i+1]:
                    cisla[i], cisla[i+1]=cisla[i+1],cisla[i]
                    auta[i], auta[i+1] = auta[i+1],auta[i]
        return auta
    if condition == "DESC":
        for j in range(n):
            for i in range(n-1-j):
                if cisla[i]<cisla[i+1]:
                    cisla[i], cisla[i+1]=cisla[i+1],cisla[i]
                    auta[i], auta[i+1] = auta[i+1],auta[i]
        return auta