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
    for employee in employees:
        if employee.id == id:
            return f"{employee.name} {employee.surname} ({employee.performance})"
        

# print(str(findBestEmployee([e1, e2, e3])))
# print(str(findBestEmployee([e1, e3])))
# print(str(findBestEmployee([e2, e3])))
# print(str(findBestEmployee([e1, e2, e3, e4])))
# print(str(findBestEmployee([e4, e1, e2, e3])))

# print(getEmployee(employees, 32))
# print(getEmployee(employees, 12))
# print(getEmployee(employees, 24))


#2
def removeEmployee(employees, id):
    return [emp for emp in employees if emp.id != id]

# remaining_employees = removeEmployee(employees, 32)
# remaining_employees = removeEmployee(employees, 32)
# for emp in remaining_employees:
#     print(f"{emp.name} {emp.surname} ({emp.performance})")
    
#3 DESC
def sortEmployee(employees)->list[Employee]:
    emps = []
    sorted_emps = []
    for emp in employees:
        emps.append(Employee(emp.id, emp.name, emp.surname, emp.performance))
    for emp in emps:
        bestEmployee = emps[0]
        if emp.performance > bestEmployee.performance:
            bestEmployee = emp
        sorted_emps.append(Employee(bestEmployee.id, bestEmployee.name, bestEmployee.surname, bestEmployee.performance))
        emps = removeEmployee(emps, bestEmployee.id)
    return sorted_emps

sorted_Employees = sortEmployee([e4, e2, e1, e3])
for emp in sorted_Employees:
    print(f"{emp.name} {emp.surname} ({emp.performance})")
print("///")
sorted_Employees = sortEmployee([e4, e1, e3])
for emp in sorted_Employees:
    print(f"{emp.name} {emp.surname} ({emp.performance})")
print("///")
sorted_Employees = sortEmployee([e1])
for emp in sorted_Employees:
    print(f"{emp.name} {emp.surname} ({emp.performance})")
