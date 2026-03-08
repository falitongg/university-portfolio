from machine import Pin
import sys

led = Pin('LED', Pin.OUT)

while True:
    input = sys.stdin.read(1)
    print("Pressed:", input)
    
    if input == 'a':
        led.toggle()