def addition(x, y): 
    try: 
        x +=y
        return x
    except:
        raise ValueError('This operation is not supported for given input parameters')
def subtraction(x, y):
    try:
        x-=y
        return x
    except:
        raise ValueError('This operation is not supported for given input parameters')
def multiplication(x, y):
    try:
        x*=y
        return x
    except:
        raise ValueError('This operation is not supported for given input parameters')
def division(x, y):
    try:
        x/=y
        return x
    except:
        raise ValueError('This operation is not supported for given input parameters')
def modulo(x,y):
    try:
        if x>=y and y > 0:
            x %= y
            return x
        else:
            raise ValueError('This operation is not supported for given input parameters')
    except:
        raise ValueError('This operation is not supported for given input parameters')
def secondPower (x):
    try:
        x**=2
        return x
    except:
        raise ValueError('This operation is not supported for given input parameters')
def power  (x, y):
    try:
        if y >= 0:
            x**=y
            return float (x)
        else:
            raise ValueError('This operation is not supported for given input parameters')
    except:
        raise ValueError('This operation is not supported for given input parameters')
def secondRadix(x):
    try:
        if x > 0:
            x**=1/2
            return x
        else:
            raise ValueError('This operation is not supported for given input parameters')
    except:
        raise ValueError('This operation is not supported for given input parameters')
# ###############
def magic (x, y, z, k):
    try:
        l=x+k
        m=y+z
        if m == 0:
            raise ValueError('This operation is not supported for given input parameters')
        else:
            n = ((l/m)+1)
            return n
    except:
        raise ValueError('This operation is not supported for given input parameters')
# ###############
def control(a, x, y, z, k):
    try:
        if a == "ADDITION":
            return addition(x, y)
        elif a == "SUBTRACTION":
            return subtraction(x, y)
        elif a == "MULTIPLICATION":
            return multiplication(x, y)
        elif a =="DIVISION":
            return division(x, y)
        elif a == "MOD":
            return modulo(x, y)
        elif a == "POWER":
            return power(x, y)
        elif a == "SECONDRADIX":
            return secondRadix(x)
        elif a == "MAGIC":
            return magic(x, y, z, k)
        else:
            raise ValueError('This operation is not supported for given input parameters')
    except:
        raise ValueError('This operation is not supported for given input parameters')