def permutations(array):
    if len(array) == 0:
        return [[]]  # Pokud je seznam prázdný, vrátíme prázdný seznam jako jedinou permutaci
    if len(array) == 1:
        return [array]  # Pokud je seznam jednoelementový, vrátíme jej
    result = []
    for i in range(len(array)):
        # Vybereme prvek na indexu i
        i_item = array[i]
        # Zbytek seznamu bez i-tého prvku
        rest = array[:i] + array[i+1:]
        # Získáme permutace zbytku
        for perm in permutations(rest):
            # Připojíme aktuální prvek k permutaci zbytku
            result.append([i_item] + perm)
    return result

# Příklad použití
array = ['a', 'b', 'c', 'd']
print(permutations(array))
