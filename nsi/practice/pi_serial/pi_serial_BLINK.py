#PI PICO
from machine import Pin, Timer

led = Pin('LED', Pin.OUT)
timer = Timer()

def blink()
    led.toggle()
    print("Pi pico says hello")

timer.init(freq=1, mode=Timer.PERIODIC, callback=blink)
