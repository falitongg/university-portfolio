import math
def leibnizPi(iterations:int)->float:      #iterations - pocet clenu
    p = 0.0
    for i in range (iterations):
        p+=((-1)**i/(2*i+1))
    return 4*p
def nilakanthaPi(iterations:int)->float:
    p = 0.0
    for i in range (1, iterations):
        p+=(-1)**(i+1)/((2*i)*(2*i+1)*(2*i+2))
    return 3 + 4*p
def newtonPi(x:float)->float:
    x_new:float=0
    while True:
        x_new = x - (math.sin(x)/math.cos(x))
        if x_new == x:
            break
        x = x_new
    return x
print(newtonPi(3.0))

