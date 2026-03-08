import rp2
from machine import Pin

led = Pin("LED", Pin.OUT)

while True:
    if rp2.bootsel_button() == 1:
        led.on()
    else:
        led.off()
