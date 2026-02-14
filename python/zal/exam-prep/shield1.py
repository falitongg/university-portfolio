class Employee:
    def __init__(self, id:int, name:str, surname:str, performance:int):
        self.id = id
        self.name = name
        self.surname = surname
        self.performance = performance
e1 = Employee(12, 'Natasha','Romanova', 233)
e2 = Employee(32, 'Thor','Odinson', 430)
e3 = Employee(1, 'Steven','Rogers', 123)
e4 = Employee(20, 'Anthony','Stark', 444)
employees = [e1, e2, e3, e4]

#1
def findBestEmployee(employees):
    if not employees:
        return -1
    bestEmployee = employees[0]
    for employee in employees:
        if employee.performance > bestEmployee.performance:
            bestEmployee = employee
    return bestEmployee.id
def getEmployee(employees, id):
    if not employees:
        return -1
    for employee in employees:
        if employee.id == id:
            return f"{employee.name} {employee.surname} ({employee.performance})"
        
# print(str(findBestEmployee([e4, e1, e2, e3])))

# print(getEmployee(employees, 322))

#2

def removeEmployee(employees, id):
    if not employees:
        return -1
    return [emp for emp in employees if emp.id != id]
# print(removeEmployee(employees, 1))
# remaining_employees = removeEmployee(employees, 32)
# for emp in remaining_employees:
#     print(f"{emp.name} {emp.surname} ({emp.performance})")
#3 DESC
def sortEmployee(employees)->list:
    tmp_emp = employees[:]
    desc_employees = []
    while tmp_emp:
        bestEmployee_id = findBestEmployee(tmp_emp)
        for emp in tmp_emp:
            if emp.id == bestEmployee_id:
                bestEmployee = emp
        desc_employees.append(bestEmployee)
        tmp_emp = removeEmployee(tmp_emp, bestEmployee_id)
    return desc_employees

sorted_Employees = sortEmployee([e4, e2, e1, e3])
for emp in sorted_Employees:
    print(f"{emp.name} {emp.surname} ({emp.performance})")
    
#4

def assemble(employees, lvl):
    if not employees:
        return -1
    sum_perf = 0
    for emp in employees:
        sum_perf += emp.performance
    if sum_perf > lvl:
        return False
    else:
        return True
    
# print(assemble([e1, e2, e3], 20))

#5
def saveThem(employees, min_lvl):
    return [emp for emp in employees if emp.performance >= min_lvl]

test = saveThem([e1, e2, e3], 300)
for emp in test:
    print(f"{emp.name} {emp.surname} ({emp.performance})")
# print(saveThem([e1, e2, e3], 20))

