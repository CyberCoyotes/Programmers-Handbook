# **Guide to Java \- Level 4: FRC Basics**

## **Who Is This For?**

High school FRC students transitioning from FTC, or students with Java basics who are new to FRC. This guide bridges what you already know into FRC's Command-Based framework.  
**Prerequisites:** Java basics (Level 1\) and ideally FTC experience (Level 2/2.5)  
**Time to Complete:** 1-2 weeks before build season

## **Your Learning Path**

```
┌─────────────────────────────────────────────────────────────┐
│            LEVEL 1: Java Foundations                        │
│            LEVEL 2: Java for FTC (if applicable)            │
│            LEVEL 2.5: Advanced FTC Patterns (if applicable) │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                LEVEL 3: Java for FRC                        │
│                    (You are here)                           │
│         Transition from FTC → FRC Command-Based             │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    LEVEL 4: Advanced FRC                    │
│     Commands Deep Dive • Triggers • State Machines          │
└─────────────────────────────────────────────────────────────┘
```

## **The Big Shift: Iterative → Command-Based**

In FTC, you wrote code like this:

```java
// FTC: You check everything in a loop
while (opModeIsActive()) {
    if (gamepad1.a) {
        intake.setPower(1.0);
    } else {
        intake.setPower(0);
    }
}
```

In FRC Command-Based, you **declare relationships once**:

```java
// FRC: Declare it once, framework handles the checking
operator.a().whileTrue(IntakeCommands.run(intake));
```

**That's it.** No loop. The framework checks the button and runs the command for you.

This feels weird at first, but it's powerful:

* No more giant if/else chains  
* No more forgetting to stop a motor  
* Commands automatically handle cleanup  
* Easy to compose complex behaviors

## **FTC → FRC Translation Table**

| FTC Concept | FRC Equivalent | Notes |
| :---- | :---- | :---- |
| `LinearOpMode` | `Robot.java` \+ `RobotContainer.java` | Split into framework and your code |
| `opModeIsActive()` loop | Command Scheduler | Framework runs the loop for you |
| `hardwareMap.get()` | Constructor injection | Motors created in subsystem constructors |
| `gamepad1.a` | `controller.a()` | Returns a Trigger, not a boolean |
| `motor.setPower()` | Same, but inside subsystems | Hardware stays private to subsystem |
| Subsystem class with `update()` | `SubsystemBase` with `periodic()` | Very similar pattern\! |
| State machine enum | Same pattern works\! | Commands can also manage state |
| `sleep()` in auto | `Commands.waitSeconds()` | Non-blocking, composable |
| Time-based auto | Path following (Choreo) | Much more precise |

## **Project Structure: Where Things Live**

### **FTC Structure (What You Know)**

```
TeamCode/
└── org/firstinspires/ftc/teamcode/
    ├── TeleOp/
    ├── Autonomous/
    └── Subsystems/
```

### **FRC Structure (What's New)**

```
src/main/java/frc/robot/
├── Robot.java              ← Framework entry point (don't touch much)
├── RobotContainer.java     ← YOUR MAIN FILE: create subsystems, bind buttons
├── Constants.java          ← All configuration values
├── subsystems/             ← One file per mechanism
│   ├── Intake.java
│   ├── Shooter.java
│   └── Drivetrain.java
└── commands/               ← Command factory classes
    ├── IntakeCommands.java
    └── DriveCommands.java
```

### **The Key Files**

**RobotContainer.java** — This is like your FTC OpMode, but split differently:

* Creates all subsystems (like your FTC `init` section)  
* Binds buttons to commands (like your FTC `loop`, but declarative)  
* Sets up autonomous routines

**Subsystem files** — Very similar to your FTC subsystem classes:

* Own their hardware (motors, sensors)  
* Provide methods for what they can do  
* Have a `periodic()` method (like your `update()`)

**Constants.java** — All your magic numbers in one place:

* Motor IDs  
* Speeds and limits  
* PID values

## **Subsystems: Almost the Same\!**

If you wrote good FTC subsystems, FRC subsystems will feel familiar:

### **FTC Subsystem (What You Know)**

```java
public class Intake {
    private DcMotor motor;
    
    public Intake(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, "intake");
    }
    
    public void run() {
        motor.setPower(1.0);
    }
    
    public void stop() {
        motor.setPower(0);
    }
    
    public void update() {
        // Called every loop
    }
}
```

### **FRC Subsystem (What's New)**

```java
public class Intake extends SubsystemBase {
    private final TalonFX motor;
    
    public Intake() {
        motor = new TalonFX(Constants.IntakeConstants.MOTOR_ID);
    }
    
    public void run() {
        motor.set(1.0);
    }
    
    public void stop() {
        motor.set(0);
    }
    
    @Override
    public void periodic() {
        // Called every loop — just like update()!
    }
}
```

**Key differences:**

1. `extends SubsystemBase` — Integrates with Command Scheduler  
2. No `HardwareMap` — Motor IDs come from Constants  
3. `periodic()` instead of `update()` — Called automatically by framework  
4. `TalonFX` instead of `DcMotor` — Different hardware library (CTRE Phoenix)

## **Commands: The New Concept**

In FTC, your OpMode directly controlled subsystems:

```java
// FTC: Direct control in loop
if (gamepad1.a) {
    intake.run();
} else {
    intake.stop();
}
```

In FRC, **Commands** are the actions:

```java
// FRC: Command handles the action
Command runIntake = Commands.startEnd(
    () -> intake.run(),    // What to do when starting
    () -> intake.stop(),   // What to do when ending
    intake                 // Which subsystem this uses
);
```

Then you **bind** the command to a button:

```java
// In RobotContainer
operator.a().whileTrue(runIntake);
```

### **Why Commands?**

1. **Automatic cleanup** — `stop()` is called when button released  
2. **No conflicts** — Only one command can use a subsystem at a time  
3. **Composable** — Chain commands together easily  
4. **Reusable** — Same command for teleop and auto

### **Command Factory Pattern**

Instead of creating commands inline, group them in factory classes:

```java
public class IntakeCommands {
    
    private IntakeCommands() {} // Prevent instantiation
    
    public static Command run(Intake intake) {
        return Commands.startEnd(
            () -> intake.run(),
            () -> intake.stop(),
            intake
        );
    }
    
    public static Command reverse(Intake intake) {
        return Commands.startEnd(
            () -> intake.reverse(),
            () -> intake.stop(),
            intake
        );
    }
}
```

Usage:

```java
operator.rightTrigger().whileTrue(IntakeCommands.run(intake));
operator.leftTrigger().whileTrue(IntakeCommands.reverse(intake));
```

---

## **RobotContainer: Your New Home Base**

This is where everything comes together:

```java
public class RobotContainer {
    
    // ===== SUBSYSTEMS (create one of each) =====
    private final Drivetrain drivetrain = new Drivetrain();
    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    
    // ===== CONTROLLERS =====
    private final CommandXboxController driver = new CommandXboxController(0);
    private final CommandXboxController operator = new CommandXboxController(1);
    
    // ===== CONSTRUCTOR =====
    public RobotContainer() {
        configureBindings();
        configureDefaultCommands();
    }
    
    private void configureBindings() {
        // ----- DRIVER CONTROLS -----
        // Left Stick: Drive
        // Right Stick: Rotate
        // Left Bumper: Slow mode
        
        driver.leftBumper().whileTrue(
            DriveCommands.slowMode(drivetrain, driver)
        );
        
        // ----- OPERATOR CONTROLS -----
        // Right Trigger: Intake
        // Left Trigger: Reverse intake
        // A: Shoot
        
        operator.rightTrigger().whileTrue(IntakeCommands.run(intake));
        operator.leftTrigger().whileTrue(IntakeCommands.reverse(intake));
        operator.a().onTrue(ShooterCommands.shoot(shooter, intake));
    }
    
    private void configureDefaultCommands() {
        // Drivetrain always runs teleop drive unless something else needs it
        drivetrain.setDefaultCommand(
            DriveCommands.teleopDrive(drivetrain, driver)
        );
    }
    
    public Command getAutonomousCommand() {
        return AutoCommands.simpleAuto(drivetrain, intake, shooter);
    }
}
```

### **Comparing to FTC**

| FTC Location | FRC Location |
| :---- | :---- |
| Hardware init in `runOpMode()` | Subsystem constructors |
| Button checks in `while` loop | `configureBindings()` method |
| Default behavior in `else` | `setDefaultCommand()` |
| Auto selection | `getAutonomousCommand()` |

---

## **Button Bindings: Triggers**

In FTC, buttons are booleans:

```java
if (gamepad1.a) { }  // true or false
```

In FRC, buttons are **Triggers** that fire commands:

```java
controller.a()           // Returns a Trigger object
    .onTrue(command)     // Run once when pressed
    .whileTrue(command)  // Run while held
    .onFalse(command)    // Run once when released
```

### **Common Bindings**

| Method | When Command Runs |
| :---- | :---- |
| `.onTrue(cmd)` | Once, when button first pressed |
| `.whileTrue(cmd)` | Continuously while held, stops when released |
| `.onFalse(cmd)` | Once, when button released |
| `.toggleOnTrue(cmd)` | Toggle on/off with each press |

### **Reading Analog Values**

Joysticks and triggers still give analog values:

```java
// In a command or default command
double forward = -driver.getLeftY();   // -1 to 1
double strafe = -driver.getLeftX();    // -1 to 1
double rotate = -driver.getRightX();   // -1 to 1

// Note: Y axis is inverted (forward = negative), so we negate it
```

---

## **Constants: No More Magic Numbers**

In FTC, you might have scattered numbers:

```java
motor.setPower(0.8);  // What is 0.8? Why 0.8?
```

In FRC, everything goes in Constants:

```java
// Constants.java
public final class Constants {
    
    public static final class IntakeConstants {
        public static final int MOTOR_ID = 1;
        public static final double RUN_SPEED = 0.8;
        public static final double REVERSE_SPEED = -0.5;
    }
    
    public static final class ShooterConstants {
        public static final int LEFT_MOTOR_ID = 2;
        public static final int RIGHT_MOTOR_ID = 3;
        public static final double TARGET_RPM = 4500;
    }
}
```

Usage:

```java
import static frc.robot.Constants.IntakeConstants.*;

public class Intake extends SubsystemBase {
    private final TalonFX motor = new TalonFX(MOTOR_ID);
    
    public void run() {
        motor.set(RUN_SPEED);  // Clear what this means!
    }
}
```

---

## **Naming Conventions**

### **Classes (PascalCase)**

```java
public class Intake { }           // Subsystem: noun
public class IntakeCommands { }   // Command factory: noun + Commands
public class RunIntake { }        // Command class: verb + noun
```

### **Methods (camelCase)**

```java
// Actions (verbs)
public void runIntake() { }
public void stopMotor() { }

// Queries (is/has/get)
public boolean hasPiece() { }
public boolean isAtTarget() { }
public double getSpeed() { }
```

### **Variables (camelCase)**

```java
private TalonFX intakeMotor;
private boolean hasPiece;
private double targetSpeed;
```

### **Constants (SCREAMING\_SNAKE\_CASE)**

```java
public static final double MAX_SPEED = 1.0;
public static final int MOTOR_ID = 5;
```

---

## **Your FTC State Machines Still Work\!**

If you learned state machines in Level 2.5, great news — they work the same way in FRC:

```java
public class Intake extends SubsystemBase {
    
    public enum IntakeState {
        IDLE,
        INTAKING,
        HOLDING,
        FEEDING
    }
    
    private IntakeState currentState = IntakeState.IDLE;
    
    @Override
    public void periodic() {
        switch (currentState) {
            case IDLE:
                motor.set(0);
                break;
            case INTAKING:
                motor.set(INTAKE_SPEED);
                if (hasPiece()) {
                    currentState = IntakeState.HOLDING;
                }
                break;
            // ... etc
        }
    }
    
    public void requestIntake() {
        if (currentState == IntakeState.IDLE) {
            currentState = IntakeState.INTAKING;
        }
    }
}
```

The pattern is identical — `periodic()` instead of `update()`, but same idea.

---

## **Common Mistakes When Transitioning**

### **❌ Checking buttons in periodic()**

```java
// BAD — Don't do this in FRC!
@Override
public void periodic() {
    if (someButtonPressed) {  // Where does this come from?
        motor.set(1.0);
    }
}
```

```java
// GOOD — Use commands and bindings
// In RobotContainer:
button.whileTrue(Commands.run(() -> intake.run(), intake));
```

### **❌ Creating multiple subsystem instances**

```java
// BAD — Two Intake objects fighting over one motor!
public class ShooterCommands {
    private static Intake intake = new Intake();  // NO!
}
```

```java
// GOOD — One instance in RobotContainer, passed to commands
public class ShooterCommands {
    public static Command shoot(Shooter shooter, Intake intake) {
        // Use the passed-in intake
    }
}
```

### **❌ Forgetting requirements**

```java
// BAD — Scheduler doesn't know this uses intake
Commands.run(() -> intake.run());

// GOOD — Scheduler can prevent conflicts
Commands.run(() -> intake.run(), intake);
```

### **❌ Using sleep() in commands**

```java
// BAD — Blocks the entire robot!
public void execute() {
    motor.set(1.0);
    Thread.sleep(1000);  // NO!
    motor.set(0);
}
```

```java
// GOOD — Use command composition
Commands.sequence(
    Commands.runOnce(() -> motor.set(1.0)),
    Commands.waitSeconds(1.0),
    Commands.runOnce(() -> motor.set(0))
);
```

---

## **Quick Comparison: Full Example**

### **FTC TeleOp**

```java
@TeleOp(name = "Main TeleOp")
public class MainTeleOp extends LinearOpMode {
    private Intake intake;
    private Arm arm;
    
    @Override
    public void runOpMode() {
        intake = new Intake(hardwareMap);
        arm = new Arm(hardwareMap);
        
        waitForStart();
        
        while (opModeIsActive()) {
            // Intake control
            if (gamepad1.a) {
                intake.run();
            } else if (gamepad1.b) {
                intake.reverse();
            } else {
                intake.stop();
            }
            
            // Arm control
            if (gamepad1.dpad_up) {
                arm.goToPosition(Arm.Position.HIGH);
            }
            if (gamepad1.dpad_down) {
                arm.goToPosition(Arm.Position.LOW);
            }
            
            intake.update();
            arm.update();
            
            telemetry.addData("Intake", intake.getState());
            telemetry.update();
        }
    }
}
```

### **FRC Equivalent**

```java
// RobotContainer.java
public class RobotContainer {
    private final Intake intake = new Intake();
    private final Arm arm = new Arm();
    private final CommandXboxController operator = new CommandXboxController(0);
    
    public RobotContainer() {
        configureBindings();
    }
    
    private void configureBindings() {
        // Intake control
        operator.a().whileTrue(IntakeCommands.run(intake));
        operator.b().whileTrue(IntakeCommands.reverse(intake));
        // No "else" needed — commands handle cleanup!
        
        // Arm control
        operator.povUp().onTrue(ArmCommands.goToPosition(arm, Arm.Position.HIGH));
        operator.povDown().onTrue(ArmCommands.goToPosition(arm, Arm.Position.LOW));
    }
}

// IntakeCommands.java
public class IntakeCommands {
    public static Command run(Intake intake) {
        return Commands.startEnd(
            () -> intake.run(),
            () -> intake.stop(),
            intake
        );
    }
    
    public static Command reverse(Intake intake) {
        return Commands.startEnd(
            () -> intake.reverse(),
            () -> intake.stop(),
            intake
        );
    }
}
```

Notice how the FRC version:

* No `while` loop — framework handles it  
* No `else` for stopping — `startEnd` handles cleanup  
* No manual `update()` calls — `periodic()` runs automatically  
* Declarations are separate from logic

---

## **What's Next?**

Once you're comfortable with:

* Subsystems extending `SubsystemBase`  
* Basic command factories  
* Button bindings in RobotContainer  
* Constants organization

Continue to **Level 4: Advanced FRC Patterns**:

* Command compositions (sequence, parallel, race)  
* Complex triggers and bindings  
* State machines in Command-Based  
* Autonomous routines

---

## **Quick Reference**

### **Subsystem Template**

```java
public class MySubsystem extends SubsystemBase {
    private final TalonFX motor;
    
    public MySubsystem() {
        motor = new TalonFX(Constants.MyConstants.MOTOR_ID);
    }
    
    public void doSomething() {
        motor.set(Constants.MyConstants.SPEED);
    }
    
    public void stop() {
        motor.set(0);
    }
    
    @Override
    public void periodic() {
        // Logging, state updates
    }
}
```

### **Command Factory Template**

```java
public class MyCommands {
    private MyCommands() {}
    
    public static Command doSomething(MySubsystem subsystem) {
        return Commands.startEnd(
            () -> subsystem.doSomething(),
            () -> subsystem.stop(),
            subsystem
        );
    }
}
```

### **RobotContainer Template**

```java
public class RobotContainer {
    private final MySubsystem subsystem = new MySubsystem();
    private final CommandXboxController controller = new CommandXboxController(0);
    
    public RobotContainer() {
        configureBindings();
    }
    
    private void configureBindings() {
        controller.a().whileTrue(MyCommands.doSomething(subsystem));
    }
}
```

### **Common Button Bindings**

```java
controller.a().onTrue(cmd);       // Once on press
controller.a().whileTrue(cmd);    // While held
controller.a().onFalse(cmd);      // Once on release
controller.a().toggleOnTrue(cmd); // Toggle
```
