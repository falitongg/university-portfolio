import random

list = [6, 7, 3, 8, 1]
# for i in range (0, 10):
#     list.append(random.randint(1, 1000))
print(list)
for run in range (len(list)):
    for i in range (len(list)-1-run):
        print(f'i: {i}')
        print(f"сравниваем {list[i]} c {list[i+1]}")
        if list[i]>list[i+1]:
            list[i], list[i+1]=list[i+1],list[i]
        print(list)
    print(list)
print(list)