import serial

try:
    ser=serial.Serial('COM4', 115200)
    ser.flushInput()
    print("Succesfully connected")
    
    while True:
        data=ser.readline().decode()
        print("We have a message:", data)
except:
    print("Unable to connect")