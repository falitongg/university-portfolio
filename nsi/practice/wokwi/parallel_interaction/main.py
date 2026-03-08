from machine import Pin, Timer
import time

led = Pin('LED', Pin.OUT)
led_r = Pin(0, Pin.OUT)
btn = Pin(15, Pin.IN, Pin.PULL_UP)

timer = Timer()
last_pressed=0

timer.init(freq=5, mode=Timer.PERIODIC, callback=lambda t:led_r.toggle())

def led_handler(pin):
    global last_pressed
    now = time.ticks_ms()
    if time.ticks_diff(now, last_pressed)>200:
        led.toggle()
        last_pressed = now


btn.irq(trigger=Pin.IRQ_FALLING, handler=led_handler)