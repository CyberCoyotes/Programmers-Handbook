# **Guide to Java \- Level 2: FTC Basics**

## **Who Is This For?**

Middle school FTC students who have completed **Level 1: Java Foundations**. This guide bridges pure Java knowledge into FTC robot programming.  
**Prerequisites:** Java basics (Level 1 or equivalent — a class, self-taught, or prior experience)  
**Time to Complete:** 2-3 weeks alongside robot build

## **Your Learning Path**

```
┌─────────────────────────────────────────────────────────────┐
│                 LEVEL 1: Java Foundations                   │
│            (Recommended if new to Java)                     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                   LEVEL 2: Java for FTC                     │
│                      (You are here)                         │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
                    Ready to program your robot!
```

**Already know Java?** Skip to Part 2 and start learning FTC-specific patterns.

---

## **Part 1: FTC Project Structure**

### **How FTC Code is Organized**

```
TeamCode/
├── java/
│   └── org/firstinspires/ftc/teamcode/
│       ├── TeleOp/           ← Driver-controlled programs
│       │   └── MainTeleOp.java
│       ├── Autonomous/       ← Self-driving programs
│       │   └── RedLeft.java
│       └── Subsystems/       ← Reusable mechanism code (optional)
│           └── Intake.java
```

### **The OpMode: Your Robot's Brain**

Every FTC program is an **OpMode**. There are two types:

| Type | When to Use | How It Works |
| :---- | :---- | :---- |
| `LinearOpMode` | Most programs | Runs top-to-bottom like normal code |
| `OpMode` | Advanced (iterative) | Runs in a loop like a game engine |

**Start with `LinearOpMode`** — it's easier to understand.

---

## **Part 2: LinearOpMode Structure**

### **The Basic Template**

```java
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "My First TeleOp")
public class MyFirstTeleOp extends LinearOpMode {
    
    // Declare hardware here
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    
    @Override
    public void runOpMode() {
        // INIT PHASE: Runs once when you press INIT
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        // Wait for driver to press START
        waitForStart();
        
        // RUN PHASE: Runs after START is pressed
        while (opModeIsActive()) {
            // Your teleop code goes here
            // This loop runs over and over until STOP
            
            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            
            leftMotor.setPower(drive + turn);
            rightMotor.setPower(drive - turn);
            
            telemetry.addData("Drive", drive);
            telemetry.addData("Turn", turn);
            telemetry.update();
        }
    }
}
```

### **Breaking It Down**

**1\. Annotations tell FTC what kind of program this is:**

```java
@TeleOp(name = "My First TeleOp")    // Shows up in TeleOp menu
// OR
@Autonomous(name = "Red Left Auto")  // Shows up in Autonomous menu
```

**2\. Hardware declaration (before runOpMode):**

```java
private DcMotor leftMotor;  // Declare the variable
```

**3\. Hardware initialization (inside runOpMode, before waitForStart):**

```java
leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
// The string "leftMotor" must match the name in your robot configuration!
```

**4\. The main loop:**

```java
while (opModeIsActive()) {
    // This runs over and over (about 50 times per second)
    // Put your teleop control code here
}
```

---

## **Part 3: Hardware Basics**

### **Motors**

```java
// Declaration
private DcMotor motor;

// Initialization
motor = hardwareMap.get(DcMotor.class, "motorName");

// Basic control
motor.setPower(0.5);   // 50% forward
motor.setPower(-0.5);  // 50% backward
motor.setPower(0);     // Stop

// Direction
motor.setDirection(DcMotor.Direction.REVERSE);  // Flip direction

// Braking behavior
motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);  // Stops quickly
motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);  // Coasts to stop
```

### **Servos**

```java
// Declaration
private Servo servo;

// Initialization
servo = hardwareMap.get(Servo.class, "servoName");

// Control (position 0.0 to 1.0)
servo.setPosition(0.0);   // One extreme
servo.setPosition(0.5);   // Middle
servo.setPosition(1.0);   // Other extreme
```

### **Gamepad Input**

```java
// Joysticks (return -1.0 to 1.0)
double leftY = gamepad1.left_stick_y;    // Up = negative, Down = positive
double leftX = gamepad1.left_stick_x;    // Left = negative, Right = positive
double rightY = gamepad1.right_stick_y;
double rightX = gamepad1.right_stick_x;

// Buttons (return true or false)
boolean aPressed = gamepad1.a;
boolean bPressed = gamepad1.b;
boolean xPressed = gamepad1.x;
boolean yPressed = gamepad1.y;

// Bumpers and triggers
boolean leftBumper = gamepad1.left_bumper;
boolean rightBumper = gamepad1.right_bumper;
float leftTrigger = gamepad1.left_trigger;   // 0.0 to 1.0
float rightTrigger = gamepad1.right_trigger; // 0.0 to 1.0

// D-pad
boolean dpadUp = gamepad1.dpad_up;
boolean dpadDown = gamepad1.dpad_down;
boolean dpadLeft = gamepad1.dpad_left;
boolean dpadRight = gamepad1.dpad_right;
```

### **Telemetry (Driver Station Display)**

```java
// Add data to display
telemetry.addData("Label", value);
telemetry.addData("Motor Power", motor.getPower());
telemetry.addData("Button A", gamepad1.a);

// Must call update() to actually show it!
telemetry.update();
```

---

## **Part 4: Common Patterns**

### **Tank Drive**

Each joystick controls one side of the robot:

```java
while (opModeIsActive()) {
    double leftPower = -gamepad1.left_stick_y;
    double rightPower = -gamepad1.right_stick_y;
    
    leftMotor.setPower(leftPower);
    rightMotor.setPower(rightPower);
}
```

### **Arcade Drive**

One joystick controls forward/back and turning:

```java
while (opModeIsActive()) {
    double drive = -gamepad1.left_stick_y;  // Forward/back
    double turn = gamepad1.right_stick_x;   // Turning
    
    leftMotor.setPower(drive + turn);
    rightMotor.setPower(drive - turn);
}
```

### **Button-Controlled Mechanism**

```java
while (opModeIsActive()) {
    if (gamepad1.a) {
        intakeMotor.setPower(1.0);  // Run intake while A held
    } else if (gamepad1.b) {
        intakeMotor.setPower(-1.0); // Reverse while B held
    } else {
        intakeMotor.setPower(0);    // Stop when nothing pressed
    }
}
```

### **Toggle with Button Press**

```java
boolean clawOpen = false;
boolean lastButtonState = false;

while (opModeIsActive()) {
    boolean currentButtonState = gamepad1.x;
    
    // Detect when button is first pressed (not held)
    if (currentButtonState && !lastButtonState) {
        clawOpen = !clawOpen;  // Toggle the state
    }
    lastButtonState = currentButtonState;
    
    // Set servo based on state
    if (clawOpen) {
        clawServo.setPosition(0.7);
    } else {
        clawServo.setPosition(0.3);
    }
}
```

---

## **Part 5: Basic Autonomous**

### **Time-Based Movement**

```java
@Autonomous(name = "Simple Auto")
public class SimpleAuto extends LinearOpMode {
    
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    
    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
        
        waitForStart();
        
        // Drive forward for 2 seconds
        leftMotor.setPower(0.5);
        rightMotor.setPower(0.5);
        sleep(2000);  // Wait 2000 milliseconds (2 seconds)
        
        // Stop
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        
        // Turn right for 1 second
        leftMotor.setPower(0.5);
        rightMotor.setPower(-0.5);
        sleep(1000);
        
        // Stop
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }
}
```

### **Helper Methods for Cleaner Code**

```java
@Autonomous(name = "Better Auto")
public class BetterAuto extends LinearOpMode {
    
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    
    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
        
        waitForStart();
        
        // Now autonomous reads like a story!
        driveForward(0.5, 2000);
        turnRight(0.5, 1000);
        driveForward(0.5, 1500);
        stopMotors();
    }
    
    // Helper methods
    private void driveForward(double power, long milliseconds) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
        sleep(milliseconds);
    }
    
    private void turnRight(double power, long milliseconds) {
        leftMotor.setPower(power);
        rightMotor.setPower(-power);
        sleep(milliseconds);
    }
    
    private void turnLeft(double power, long milliseconds) {
        leftMotor.setPower(-power);
        rightMotor.setPower(power);
        sleep(milliseconds);
    }
    
    private void stopMotors() {
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }
}
```

---

## **Part 6: Organizing Your Code**

### **Creating a Subsystem Class**

As your robot gets more complex, organize mechanisms into their own classes:

```java
// Intake.java
package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private DcMotor intakeMotor;
    
    // Constructor - sets up the hardware
    public Intake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
    }
    
    // Methods - what the intake can do
    public void intake() {
        intakeMotor.setPower(1.0);
    }
    
    public void outtake() {
        intakeMotor.setPower(-1.0);
    }
    
    public void stop() {
        intakeMotor.setPower(0);
    }
}
```

### **Using the Subsystem in an OpMode**

```java
@TeleOp(name = "Organized TeleOp")
public class OrganizedTeleOp extends LinearOpMode {
    
    private Intake intake;  // Use your subsystem class
    
    @Override
    public void runOpMode() {
        intake = new Intake(hardwareMap);  // Create the subsystem
        
        waitForStart();
        
        while (opModeIsActive()) {
            // Clean, readable code!
            if (gamepad1.a) {
                intake.intake();
            } else if (gamepad1.b) {
                intake.outtake();
            } else {
                intake.stop();
            }
        }
    }
}
```

---

## **Common Mistakes to Avoid**

### **❌ Forgetting telemetry.update()**

```java
// BAD - nothing shows on screen
telemetry.addData("Power", motor.getPower());

// GOOD - data actually displays
telemetry.addData("Power", motor.getPower());
telemetry.update();
```

### **❌ Wrong hardware names**

```java
// If your config says "left_drive", use exactly that!
motor = hardwareMap.get(DcMotor.class, "left_drive");  // GOOD
motor = hardwareMap.get(DcMotor.class, "leftDrive");   // BAD - won't find it
```

### **❌ Forgetting the while loop in TeleOp**

```java
// BAD - only runs once
waitForStart();
leftMotor.setPower(gamepad1.left_stick_y);

// GOOD - runs continuously
waitForStart();
while (opModeIsActive()) {
    leftMotor.setPower(gamepad1.left_stick_y);
}
```

### **❌ Not inverting joystick Y-axis**

```java
// Pushing forward gives NEGATIVE values!
double drive = -gamepad1.left_stick_y;  // Negate to make forward = positive
```

---

## **Practice Exercises**

1. **Basic TeleOp:** Create a tank drive that uses both joysticks  

2. **Add an Intake:** Add a motor controlled by the A and B buttons  

3. **Add a Claw:** Add a servo controlled by X (open) and Y (close)  

4. **Create a Toggle:** Make the claw toggle open/closed with one button  

5. **Simple Auto:** Drive forward, turn 90°, drive forward again  

6. **Subsystem Practice:** Create an `Arm` subsystem class with `raise()`, `lower()`, and `stop()` methods

---

## **Check Your Understanding**

Before moving on, you should be able to:

- [ ] Create a new OpMode with the correct annotations  
- [ ] Initialize motors and servos from the hardware map  
- [ ] Read gamepad inputs (joysticks, buttons, triggers)  
- [ ] Control motors based on joystick input  
- [ ] Control servos based on button input  
- [ ] Display information using telemetry  
- [ ] Create a basic time-based autonomous  
- [ ] Create helper methods to organize your code  
- [ ] Create a simple subsystem class

---

## **What's Next?**

Once you're comfortable with these basics:

1. **Learn about encoders** — More precise autonomous movement  
2. **Learn about sensors** — Color sensors, distance sensors, touch sensors  
3. **Learn about roadrunner/pedropathing** — Advanced autonomous path following

And when you move to FRC in high school, you'll be ready for **Level 3: Java Refresher for FRC**, which shows you how these concepts translate to the Command-Based framework\!

---

## **Quick Reference**

### **OpMode Template**

```java
@TeleOp(name = "Name Here")
public class MyOpMode extends LinearOpMode {
    private DcMotor motor;
    
    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotor.class, "name");
        
        waitForStart();
        
        while (opModeIsActive()) {
            // Your code here
        }
    }
}
```

### **Motor Control**

```java
motor.setPower(0.5);     // 50% forward
motor.setPower(-0.5);    // 50% backward
motor.setPower(0);       // Stop
```

### **Servo Control**

```java
servo.setPosition(0.0);  // One end
servo.setPosition(1.0);  // Other end
```

### **Gamepad**

```java
gamepad1.left_stick_y    // -1 to 1 (inverted!)
gamepad1.right_stick_x   // -1 to 1
gamepad1.a               // true/false
gamepad1.left_trigger    // 0 to 1
```

### **Telemetry**

```java
telemetry.addData("Label", value);
telemetry.update();  // Don't forget this!
```
