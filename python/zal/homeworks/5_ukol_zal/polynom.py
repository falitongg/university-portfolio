# poly1 = [1, 2.5, 3.5, 0, 5.4]
def polyEval(polynom, x):
    result = 0
    for i in range (len(polynom)):
        result += polynom[i]*x**i
    return result

# print(polyEval(poly1, 2))
poly1= [1, 2.5, 3.5, 0, 5.4]
poly2 =  [-23, 0, 0, 0, -2]
def polySum(poly1, poly2):
    poly3 = []
    length = max(len(poly1), len(poly2))
    poly1_extended = poly1 + [0]*(length-len(poly1))
    poly2_extended = poly2 + [0]*(length-len(poly2))
    for i in range (length):
        # print(poly1_extended)
        # print(poly2_extended)
        poly3.append(poly1_extended[i]+poly2_extended[i])
    while len(poly3)>1 and poly3[-1]==0:
        poly3.pop()
    return poly3
print(polySum(poly1,poly2))