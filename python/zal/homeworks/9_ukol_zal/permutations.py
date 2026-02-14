def permutations(array):
    if len(array) == 0: 
        return [[]]
    if len(array) == 1:
        return [array]
    result = []
    if len(array) > 1:
        for i in range (len(array)):
            i_item = array[i]
            rest_items = array[:i] + array[i+1:]
            for j in permutations(rest_items):
                result.append([i_item] + j)
            
            
        return result
            
            
        
    

list = ['a', 'b', 'c']

print(permutations(list))