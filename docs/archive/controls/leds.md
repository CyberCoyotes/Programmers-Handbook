# LEDs

There (seems to be) two approaches to controlling LEDs on a FRC robot: the REV Blinkin module, or using WPI AddressableLED hooked directly to the RIO. The term **individually addressable** refers to the ability of a program, app, or remote to make very specific changes to individual LEDs, whereas WPI AddressableLED is a program-specific reference.

## Using a Blinkin

1. [Blinkin LED Driver page](https://www.revrobotics.com/rev-11-1105/) including the [PDF manual](https://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf)
2. [REV Robotics Blinkin Overview - YouTube](https://youtu.be/wMdkM2rr1a4)
3. [Cyber Coyotes Blinkin Example](https://github.com/CyberCoyotes/LED-BlinkinExample) — based on Sonic Squirrels example, updated for 2023 codebase
4. [Sonic Squirrels Example](https://github.com/FRC-Sonic-Squirrels/MinibotCode/blob/5b58f10ef78b79384b12a69ffbe3fcba4775bba1/src/main/java/frc/robot/subsystems/blinkin.java)
5. [Mechanical Advantage Example](https://github.com/Mechanical-Advantage/RobotCode2022/tree/main/src/main/java/frc/robot/subsystems/leds) — has both Blinkin and Rio (Addressable) examples
6. Chief Delphi [blinkin-led-programming](https://www.chiefdelphi.com/t/blinkin-led-programming/339003)
7. Chief Delphi [rev-blinkin-led](https://www.chiefdelphi.com/t/rev-blinkin-led/396966)

## Using WPI Addressable

1. [Addressable LEDs - WPILib](https://docs.wpilib.org/en/stable/docs/software/hardware-apis/misc/addressable-leds.html)
2. [Mechanical Advantage](https://github.com/Mechanical-Advantage/RobotCode2022/tree/main/src/main/java/frc/robot/subsystems/leds) — has both Blinkin and Rio (Addressable) examples
3. [Yeti Robotics](https://github.com/Yeti-Robotics/aurora-java-2022)
4. [Ursuline Bearbotics](https://github.com/6391-Ursuline-Bearbotics/2022_UARobotics_Rapid_React/blob/master/src/main/java/frc/robot/subsystems/LEDSubsystem.java)
5. Chief Delphi [addressableleds-and-rev-robotics-blinkin](https://www.chiefdelphi.com/t/addressableleds-and-rev-robotics-blinkin/375753)
