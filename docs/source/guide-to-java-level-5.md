# **Guide to Java \- Level 5: Advanced FRC Patterns**

## **Who Is This For?**

FRC students who understand the basics of Command-Based and want to level up. This guide covers command compositions, advanced triggers, state machines, and patterns used by competitive teams.

**Prerequisites:** Level 3: Java for FRC (comfortable with subsystems, basic commands, button bindings)

**Time to Complete:** Ongoing reference throughout the season

## **Your Learning Path**

```java
┌─────────────────────────────────────────────────────────────┐
│                LEVEL 3: Java for FRC                        │
│              (Subsystems, Commands, Bindings)               │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              LEVEL 4: Advanced FRC Patterns                 │
│                      (You are here)                         │
│                                                             │
│  Part 1: Command Compositions                               │
│  Part 2: Advanced Triggers                                  │
│  Part 3: State Machines                                     │
│  Part 4: Lambdas and Method References                      │
│  Part 5: Enums with Data                                    │
│  Part 6: Autonomous Patterns                                │
└─────────────────────────────────────────────────────────────┘
```

## **Part 1: Command Compositions**

Simple commands are building blocks. Compositions combine them into complex behaviors.

### **Sequential: One After Another**

```java
Command scoreSequence = Commands.sequence(
    ArmCommands.goToPosition(arm, Position.SCORE),
    Commands.waitUntil(() -> arm.isAtTarget()),
    IntakeCommands.feed(intake),
    Commands.waitSeconds(0.3),
    ArmCommands.goToPosition(arm, Position.STOW)
);
```

Each step completes before the next begins.

### **Parallel: All At Once**

```java
// Ends when ALL commands finish
Command prepareToScore = Commands.parallel(
    ShooterCommands.spinUp(shooter),
    ArmCommands.goToPosition(arm, Position.SCORE),
    DriveCommands.alignToTarget(drivetrain)
);
```

### **Race: First One Wins**

```java
// Ends when FIRST command finishes — great for timeouts!
Command intakeWithTimeout = Commands.race(
    IntakeCommands.run(intake).until(() -> intake.hasPiece()),
    Commands.waitSeconds(3.0)  // Give up after 3 seconds
);
```

### **Deadline: One Sets the Pace**

```java
// Run other commands, but only until the deadline finishes
Command timedPrep = Commands.deadline(
    Commands.waitSeconds(2.0),        // The deadline
    ShooterCommands.spinUp(shooter),  // These run until deadline
    ArmCommands.aim(arm)
);
```

### **Composition Quick Reference**

| Method | Ends When | Use For |
| :---- | :---- | :---- |
| `sequence(a, b, c)` | Last finishes | Step-by-step actions |
| `parallel(a, b, c)` | ALL finish | Simultaneous prep |
| `race(a, b, c)` | FIRST finishes | Timeouts |
| `deadline(main, others)` | Main finishes | Timed groups |

## **Command Modifiers**

Modify any command's behavior:

### **.until() — Stop on Condition**

```java
IntakeCommands.run(intake)
    .until(() -> intake.hasPiece());
```

### **.withTimeout() — Time Limit**

```java
IntakeCommands.run(intake)
    .withTimeout(3.0);
```

### **.unless() — Skip If True**

```java
IntakeCommands.run(intake)
    .unless(() -> intake.hasPiece());  // Skip if already have piece
```

### **.onlyIf() — Only Run If True**

```java
ShooterCommands.shoot(shooter)
    .onlyIf(() -> shooter.isAtSpeed());  // Only if ready
```

### **.andThen() — Chain Commands**

```java
IntakeCommands.run(intake)
    .until(() -> intake.hasPiece())
    .andThen(Commands.runOnce(() -> leds.setGreen()));
```

### **.alongWith() — Run Together**

```java
IntakeCommands.run(intake)
    .alongWith(Commands.runOnce(() -> leds.setPurple()));
```

### **.finallyDo() — Always Cleanup**

```java
Commands.run(() -> motor.set(1.0), subsystem)
    .finallyDo(() -> motor.set(0));  // Runs even if interrupted
```

## **Part 2: Advanced Triggers**

### **Controller Button Reference**

`CommandXboxController` gives you triggers for every button:

```java
// Face buttons
controller.a()
controller.b()
controller.x()
controller.y()

// Bumpers
controller.leftBumper()
controller.rightBumper()

// Triggers (analog, but can be used as buttons)
controller.leftTrigger()           // Default threshold (0.5)
controller.leftTrigger(0.3)        // Custom threshold
controller.rightTrigger()
controller.rightTrigger(0.3)

// Stick buttons (pressing the stick down)
controller.leftStick()
controller.rightStick()

// Center buttons
controller.start()
controller.back()

// D-pad
controller.pov(0)      // Up
controller.pov(90)     // Right
controller.pov(180)    // Down
controller.pov(270)    // Left
controller.povUp()     // Convenience method
controller.povDown()
controller.povLeft()
controller.povRight()
```

### **Reading Analog Values**

Joysticks and triggers give analog values:

```java
// In a command or default command:
double forward = -driver.getLeftY();    // Forward/backward
double strafe = -driver.getLeftX();     // Left/right strafe
double rotation = -driver.getRightX();  // Rotation

// Note: Y axes are inverted (pushing forward gives negative value)
// That's why we negate them

// Triggers give 0.0 to 1.0
double throttle = driver.getRightTriggerAxis();
```

### **Deadzones**

Controllers have drift near the center. Apply deadzones:

```java
private double applyDeadzone(double value, double deadzone) {
    if (Math.abs(value) < deadzone) {
        return 0.0;
    }
    // Scale remaining range to 0-1
    return Math.signum(value) * (Math.abs(value) - deadzone) / (1.0 - deadzone);
}

// Usage
double forward = applyDeadzone(-driver.getLeftY(), 0.1);
```

Or use WPILib's MathUtil:

```java
import edu.wpi.first.math.MathUtil;

double forward = MathUtil.applyDeadband(-driver.getLeftY(), 0.1);
```

### **Custom Triggers from Sensors**

Any boolean can be a trigger:

```java
// Sensor-based triggers
Trigger hasPiece = new Trigger(() -> intake.hasPiece());
Trigger targetVisible = new Trigger(() -> limelight.hasTarget());
Trigger armReady = new Trigger(() -> arm.isAtTarget());

// Bind commands to sensor events
hasPiece.onTrue(Commands.runOnce(() -> leds.setGreen()));
hasPiece.onFalse(Commands.runOnce(() -> leds.setOff()));
```

### **Combining Triggers**

```java
// AND — both must be true
Trigger readyToShoot = shooter.atSpeed()
    .and(() -> arm.isAtTarget())
    .and(() -> intake.hasPiece());

// OR — either true
Trigger needsAttention = intake.isJammed()
    .or(() -> shooter.overheating());

// NOT — inverted
Trigger noPiece = hasPiece.negate();
```

### **Complex Button Patterns**

```java
// Modifier buttons
Trigger xWithModifier = operator.x().and(operator.leftBumper());
Trigger xAlone = operator.x().and(operator.leftBumper().negate());

xAlone.onTrue(ArmCommands.goToPreset1(arm));
xWithModifier.onTrue(ArmCommands.goToPreset2(arm));
```

```java
// Hold to charge, release to fire
operator.a().whileTrue(ShooterCommands.spinUp(shooter));
operator.a().onFalse(
    ShooterCommands.feed(intake)
        .onlyIf(() -> shooter.isAtSpeed())
);
```

### **Debouncing**

```java
// Require button held for 1 second (prevents accidental triggers)
driver.y()
    .debounce(1.0)
    .onTrue(Commands.runOnce(() -> drivetrain.resetGyro()));
```

### **Pattern: Confirm Before Action**

```java
// Hold Y for 1 second to reset gyro (prevents accidental resets)
driver.y()
    .and(() -> driver.y().getAsBoolean())  // Still holding
    .debounce(1.0)                          // For 1 second
    .onTrue(Commands.runOnce(() -> drivetrain.resetGyro()));
```

### **Pattern: Different Actions for Tap vs Hold**

```java
// Tap A = quick action, Hold A = different action
driver.a().onTrue(
    Commands.either(
        IntakeCommands.quickPulse(intake),    // If tap
        IntakeCommands.run(intake),           // If hold
        () -> driver.a().getAsBoolean()       // Check if still held
    ).withTimeout(0.15)  // Decision window
);
```

### **Tips for Button Mapping**

**Driver vs Operator Split**

Typical division of responsibilities:

**Driver (controller 0):**

- Drivetrain control (joysticks)  
- Slow mode  
- Auto-align / vision assist  
- Maybe: intake (if one-person drive team)

**Operator (controller 1):**

- All mechanism control  
- Scoring positions  
- Climb controls  
- Manual overrides

**Use Muscle Memory**

- **Triggers** for continuous actions (intake, outtake)  
- **Face buttons** for discrete actions (shoot, preset positions)  
- **Bumpers** for modifiers or secondary controls  
- **D-pad** for preset positions

**Document Your Bindings\!**

```java
private void configureBindings() {
    // ==================== DRIVER CONTROLS ====================
    // Left Stick:  Drive forward/back, strafe left/right
    // Right Stick: Rotation
    // Left Bumper: Slow mode (hold)
    // Right Bumper: Auto-align to target (hold)
    // Back: Reset gyro
    
    // ==================== OPERATOR CONTROLS ====================
    // Right Trigger: Intake (hold)
    // Left Trigger:  Reverse intake (hold)
    // A: Shoot
    // B: Stow position
    // X: Amp position
    // Y: Speaker position
    // D-pad Up: Arm up manual
    // D-pad Down: Arm down manual
    
    // ... bindings ...
}
```

## **Part 3: State Machines in FRC**

### **When Do You Need a State Machine?**

**Start Simple: Booleans and Helper Methods**

For basic subsystems, simple is better:

```java
public class Intake extends SubsystemBase {
    private final TalonFX motor;
    private final DigitalInput beamBreak;
    
    public void run() { motor.set(0.8); }
    public void stop() { motor.set(0); }
    public void reverse() { motor.set(-0.5); }
    
    public boolean hasPiece() { return beamBreak.get(); }
}
```

**This is fine\!** Don't add complexity you don't need.

### **Warning Signs You Might Need a State Machine**

Watch for these patterns in your code:

**1\. Multiple booleans tracking status:**

```java
// Getting messy...
private boolean isIntaking = false;
private boolean isHolding = false;
private boolean isFeeding = false;
private boolean isEjecting = false;
```

**2\. Booleans that can conflict:**

```java
// Wait, can these both be true? What happens?
if (isIntaking && isHolding) { /* ??? */ }
```

**3\. Complex helper methods checking multiple flags:**

```java
public boolean isReady() {
    return hasPiece && !isFeeding && !isEjecting && isHolding;
}
```

**4\. Transition logic scattered everywhere:**

```java
// In one command...
if (hasPiece()) {
    isIntaking = false;
    isHolding = true;
}

// In another command...
if (!hasPiece()) {
    isHolding = false;
}
```

When you see these patterns, a state machine makes the code **clearer**, not more complex.

### **When to Use State Machines vs Commands**

| Situation | Use |
| :---- | :---- |
| Simple action (run motor while button held) | Command |
| Sequence of actions | Command composition |
| Complex subsystem with multiple sensors | State machine in subsystem |
| Behavior depends on previous state | State machine |
| Multiple interacting conditions | State machine |

### **When NOT to Use State Machines**

**Keep it simple when:**

**Single-purpose mechanisms:**

```java
// Climber that just goes up or down - no state machine needed
public class Climber extends SubsystemBase {
    public void extend() { motor.set(1.0); }
    public void retract() { motor.set(-1.0); }
    public void stop() { motor.set(0); }
}
```

**Mechanisms where commands handle all logic:**

```java
// Commands already sequence everything
Commands.sequence(
    Commands.runOnce(() -> intake.run()),
    Commands.waitUntil(() -> intake.hasPiece()),
    Commands.runOnce(() -> intake.stop())
);
```

**Simple boolean state:**

```java
// Just need to know one thing
public boolean hasPiece() { return beamBreak.get(); }
```

### **State Machine in a Subsystem**

```java
public class Intake extends SubsystemBase {
    
    public enum IntakeState {
        IDLE,
        INTAKING,
        HOLDING,
        FEEDING,
        EJECTING
    }
    
    private IntakeState currentState = IntakeState.IDLE;
    private final TalonFX motor;
    private final DigitalInput beamBreak;
    
    public Intake() {
        motor = new TalonFX(MOTOR_ID);
        beamBreak = new DigitalInput(BEAM_BREAK_PORT);
    }
    
    @Override
    public void periodic() {
        switch (currentState) {
            case IDLE:
                motor.set(0);
                break;
                
            case INTAKING:
                motor.set(INTAKE_SPEED);
                if (hasPiece()) {
                    setState(IntakeState.HOLDING);
                }
                break;
                
            case HOLDING:
                motor.set(HOLD_SPEED);
                if (!hasPiece()) {
                    setState(IntakeState.IDLE);
                }
                break;
                
            case FEEDING:
                motor.set(FEED_SPEED);
                if (!hasPiece()) {
                    setState(IntakeState.IDLE);
                }
                break;
                
            case EJECTING:
                motor.set(EJECT_SPEED);
                break;
        }
        
        // Logging
        SmartDashboard.putString("Intake/State", currentState.toString());
    }
    
    private void setState(IntakeState newState) {
        if (newState != currentState) {
            System.out.println("Intake: " + currentState + " → " + newState);
            currentState = newState;
        }
    }
    
    // Request methods with guards
    public void requestIntake() {
        if (currentState == IntakeState.IDLE) {
            setState(IntakeState.INTAKING);
        }
    }
    
    public void requestFeed() {
        if (currentState == IntakeState.HOLDING) {
            setState(IntakeState.FEEDING);
        }
    }
    
    public void requestEject() {
        setState(IntakeState.EJECTING);
    }
    
    public void requestIdle() {
        setState(IntakeState.IDLE);
    }
    
    // Queries
    public IntakeState getState() { return currentState; }
    public boolean hasPiece() { return !beamBreak.get(); }
    public boolean isHolding() { return currentState == IntakeState.HOLDING; }
}
```

### **Commands for State Machine Subsystems**

```java
public class IntakeCommands {
    
    public static Command intake(Intake intake) {
        return Commands.startEnd(
            () -> intake.requestIntake(),
            () -> intake.requestIdle(),
            intake
        ).until(() -> intake.isHolding());
    }
    
    public static Command feed(Intake intake) {
        return Commands.runOnce(() -> intake.requestFeed(), intake);
    }
    
    public static Command eject(Intake intake) {
        return Commands.startEnd(
            () -> intake.requestEject(),
            () -> intake.requestIdle(),
            intake
        );
    }
}
```

### **Advanced Pattern: Desired vs Current State**

For mechanisms that take time to reach their target (like arms), separate what you *want* from what *is*:

```java
public class Arm extends SubsystemBase {
    
    public enum ArmState {
        STOW,
        INTAKE,
        AMP,
        SPEAKER_CLOSE,
        SPEAKER_FAR
    }
    
    private ArmState currentState = ArmState.STOW;
    private ArmState desiredState = ArmState.STOW;
    
    public void requestState(ArmState state) {
        desiredState = state;
    }
    
    @Override
    public void periodic() {
        // Move toward desired state
        double targetAngle = getTargetAngle(desiredState);
        armMotor.setPosition(targetAngle);
        
        // Update current state when we arrive
        if (isAtTarget()) {
            currentState = desiredState;
        }
        
        // Log both states
        SmartDashboard.putString("Arm/Desired", desiredState.toString());
        SmartDashboard.putString("Arm/Current", currentState.toString());
    }
    
    private double getTargetAngle(ArmState state) {
        switch (state) {
            case STOW: return 0.0;
            case INTAKE: return 15.0;
            case AMP: return 90.0;
            case SPEAKER_CLOSE: return 45.0;
            case SPEAKER_FAR: return 35.0;
            default: return 0.0;
        }
    }
    
    public boolean isAtTarget() {
        return Math.abs(armMotor.getPosition() - getTargetAngle(desiredState)) < 2.0;
    }
    
    public boolean atState(ArmState state) {
        return currentState == state && desiredState == state;
    }
}
```

### **State Diagram: Visualize Before Coding**

Drawing a state diagram helps design your state machine:

```
                    ┌───────────────────────┐
                    │                       │
                    ▼                       │
              ┌──────────┐                  │
     ┌───────►│   IDLE   │◄────────────┐    │
     │        └────┬─────┘             │    │
     │             │                   │    │
     │             │ requestIntake()   │    │
     │             ▼                   │    │
     │        ┌──────────┐             │    │
     │        │ INTAKING │─────────────┤    │
     │        └────┬─────┘  piece lost │    │
     │             │                   │    │
     │             │ piece detected    │    │
     │             ▼                   │    │
     │        ┌──────────┐             │    │
     │        │ HOLDING  │─────────────┘    │
     │        └────┬─────┘  piece lost      │
     │             │                        │
     │             │ requestFeed()          │
     │             ▼                        │
     │        ┌──────────┐                  │
     │        │ FEEDING  │──────────────────┘
     │        └──────────┘  piece fed
     │
     │        ┌──────────┐
     └────────│ EJECTING │
              └──────────┘
              (from any state via requestEject())
```

**Draw this BEFORE you write code.** It makes everything clearer.

## **Part 4: Lambdas and Method References**

### **Lambda Refresher**

Lambdas are compact ways to define behavior:

```java
// Full anonymous class (verbose)
Runnable r = new Runnable() {
    @Override
    public void run() {
        intake.stop();
    }
};

// Lambda (compact)
Runnable r = () -> intake.stop();

// With parameters
DoubleSupplier speed = () -> controller.getLeftY();
BooleanSupplier hasPiece = () -> intake.hasPiece();
```

### **Method References**

When a lambda just calls one method, use a method reference:

```java
// Lambda
() -> intake.stop()

// Method reference (same thing, cleaner)
intake::stop

// Examples
Commands.runOnce(intake::stop, intake);
new Trigger(intake::hasPiece);
Commands.waitUntil(shooter::isAtSpeed);
```

### **When You Can Use Method References**

```java
// ✅ Works — no parameters, matching signature
Runnable r = intake::stop;
BooleanSupplier b = intake::hasPiece;

// ❌ Doesn't work — lambda needs parameters
// intake::setSpeed  // setSpeed needs a double!

// ✅ Use lambda when you need to pass values
() -> intake.setSpeed(0.5)
```

## **Part 5: Enums with Data**

Enums can carry configuration data:

### **Basic Enum with Values**

```java
public enum ArmPosition {
    STOW(0),
    INTAKE(15),
    AMP(90),
    SPEAKER_CLOSE(45),
    SPEAKER_FAR(35);
    
    public final double angleDegrees;
    
    ArmPosition(double angleDegrees) {
        this.angleDegrees = angleDegrees;
    }
}

// Usage
arm.goToAngle(ArmPosition.SPEAKER_CLOSE.angleDegrees);
```

### **Enum with Multiple Values**

```java
public enum ShooterPreset {
    SUBWOOFER(2000, 45.0),
    PODIUM(3500, 55.0),
    AMP(1000, 90.0),
    PASS(2500, 30.0);
    
    public final double rpm;
    public final double angleDegrees;
    
    ShooterPreset(double rpm, double angleDegrees) {
        // "this.rpm" = the field, "rpm" = the parameter
        this.rpm = rpm;
        this.angleDegrees = angleDegrees;
    }
}

// Usage
ShooterPreset preset = ShooterPreset.PODIUM;
shooter.setTargetRPM(preset.rpm);
arm.setAngle(preset.angleDegrees);
```

### **Using Enums in Commands**

```java
public static Command aimAndShoot(
        Shooter shooter, 
        Arm arm, 
        Intake intake,
        ShooterPreset preset) {
    
    return Commands.sequence(
        Commands.parallel(
            Commands.runOnce(() -> shooter.setTargetRPM(preset.rpm)),
            Commands.runOnce(() -> arm.setAngle(preset.angleDegrees))
        ),
        Commands.waitUntil(() -> shooter.atSpeed() && arm.atTarget()),
        IntakeCommands.feed(intake)
    );
}

// Bindings
operator.a().onTrue(aimAndShoot(shooter, arm, intake, ShooterPreset.SPEAKER_CLOSE));
operator.b().onTrue(aimAndShoot(shooter, arm, intake, ShooterPreset.AMP));
```

## **Part 6: Autonomous Patterns**

### **Basic Auto with Commands**

```java
public static Command simpleAuto(Drivetrain drivetrain, Intake intake, Shooter shooter) {
    return Commands.sequence(
        // Shoot preload
        ShooterCommands.shoot(shooter, intake),
        
        // Drive forward
        DriveCommands.driveDistance(drivetrain, 2.0),  // meters
        
        // Intake
        IntakeCommands.run(intake)
            .until(() -> intake.hasPiece())
            .withTimeout(3.0),
        
        // Drive back
        DriveCommands.driveDistance(drivetrain, -2.0),
        
        // Shoot again
        ShooterCommands.shoot(shooter, intake)
    );
}
```

### **Parallel Actions in Auto**

```java
public static Command efficientAuto(Drivetrain drivetrain, Intake intake, Shooter shooter) {
    return Commands.sequence(
        // Shoot preload
        ShooterCommands.shoot(shooter, intake),
        
        // Drive to piece WHILE running intake
        Commands.deadline(
            DriveCommands.followPath(drivetrain, "ToFirstPiece"),
            IntakeCommands.run(intake)
        ),
        
        // Spin up shooter WHILE driving back
        Commands.deadline(
            DriveCommands.followPath(drivetrain, "ToShootingPosition"),
            ShooterCommands.spinUp(shooter)
        ),
        
        // Shoot (already spun up!)
        IntakeCommands.feed(intake)
    );
}
```

### **Conditional Auto**

```java
public static Command adaptiveAuto(Drivetrain drivetrain, Intake intake, Shooter shooter) {
    return Commands.sequence(
        // Try to get first piece
        Commands.deadline(
            DriveCommands.followPath(drivetrain, "ToFirstPiece"),
            IntakeCommands.run(intake)
        ),
        
        // Check if we got it
        Commands.either(
            // If we got piece: go score
            Commands.sequence(
                DriveCommands.followPath(drivetrain, "ToScore"),
                ShooterCommands.shoot(shooter, intake)
            ),
            // If no piece: try second location
            Commands.sequence(
                DriveCommands.followPath(drivetrain, "ToSecondPiece"),
                IntakeCommands.run(intake).withTimeout(2.0)
            ),
            () -> intake.hasPiece()  // Condition to check
        )
    );
}
```

### **Auto Selector**

```java
// In RobotContainer
private final SendableChooser<Command> autoChooser = new SendableChooser<>();

public RobotContainer() {
    // Add auto options
    autoChooser.setDefaultOption("Simple Auto", 
        AutoCommands.simpleAuto(drivetrain, intake, shooter));
    autoChooser.addOption("Two Piece", 
        AutoCommands.twoPieceAuto(drivetrain, intake, shooter));
    autoChooser.addOption("Three Piece", 
        AutoCommands.threePieceAuto(drivetrain, intake, shooter));
    autoChooser.addOption("Do Nothing", 
        Commands.none());
    
    SmartDashboard.putData("Auto Chooser", autoChooser);
}

public Command getAutonomousCommand() {
    return autoChooser.getSelected();
}
```

## **Debugging Patterns**

### **Logging State Changes**

```java
private void setState(State newState) {
    if (newState != currentState) {
        System.out.println("Intake: " + currentState + " → " + newState);
        currentState = newState;
    }
}
```

### **Dashboard Logging**

```java
@Override
public void periodic() {
    SmartDashboard.putString("Intake/State", currentState.toString());
    SmartDashboard.putBoolean("Intake/HasPiece", hasPiece());
    SmartDashboard.putNumber("Intake/MotorOutput", motor.get());
}
```

### **Command Names**

```java
IntakeCommands.run(intake)
    .until(() -> intake.hasPiece())
    .withName("IntakeUntilPiece");  // Shows in dashboard/logs
```

### **Seeing Running Commands**

```java
// In Robot.java periodic
SmartDashboard.putString("Running Commands",
    CommandScheduler.getInstance().getScheduledCommands().toString());
```

### **Using AdvantageScope**

States are strings—easy to plot over time. Look for:

- Unexpected transitions  
- States that should happen but don't  
- Rapid oscillation between states (usually a bug\!)

## **Practice Exercises**

### **Triggers Practice**

1. **Basic bindings:** Set up these controls:

   - Right trigger: run intake while held  
   - Left trigger: reverse intake while held  
   - A button: shoot once (on press)  
   - B button: stop everything

2. **Sensor trigger:** Create a trigger that:

   - Turns LEDs green when piece is detected  
   - Turns LEDs off when piece is lost  
   - Vibrates controller briefly on detection

3. **Combined trigger:** Create a "ready to shoot" trigger that requires:

   - Shooter at speed  
   - Arm at target  
   - Target visible

   Bind it to set LEDs green when all conditions met.

4. **Two-stage action:** Make the A button:

   - While held: spin up shooter  
   - On release: feed piece (only if shooter is at speed)

### **State Machine Practice**

1. **Identify the need:** Look at this code and identify why it might benefit from a state machine:

```java
private boolean isExtending = false;
private boolean isRetracted = true;
private boolean atTop = false;
private boolean safeToMove = true;
```

2. **Design a state machine:** Draw a state diagram for a climber that:

   - Starts retracted  
   - Can extend when match is late enough  
   - Can retract once extended  
   - Should stop at limit switches

3. **Implement it:** Write the enum, the periodic() method, and the request methods.

4. **Write commands:** Write factory methods for extending and retracting the climber.

## **Common Patterns Summary**

### **Timeout Pattern**

```java
Commands.race(
    myCommand,
    Commands.waitSeconds(3.0)
);
```

### **Cleanup Pattern**

```java
Commands.startEnd(
    () -> start(),
    () -> cleanup(),
    subsystem
);
```

### **Wait for Condition Pattern**

```java
Commands.sequence(
    startCommand,
    Commands.waitUntil(() -> condition),
    finishCommand
);
```

### **Parallel Prep Pattern**

```java
Commands.parallel(
    prepA,
    prepB,
    prepC
).until(() -> allReady());
```

### **Conditional Execution Pattern**

```java
Commands.either(
    commandIfTrue,
    commandIfFalse,
    () -> condition
);
```

### **Hold to Override Pattern**

```java
// Normal driving, but hold button for slow mode
drivetrain.setDefaultCommand(
    DriveCommands.teleopDrive(drivetrain, driver, 1.0)  // Full speed
);

driver.leftBumper().whileTrue(
    DriveCommands.teleopDrive(drivetrain, driver, 0.3)  // Slow speed
);
// When button released, default command (full speed) resumes
```

## **Key Takeaways**

1. **Compositions are powerful** — sequence, parallel, race, deadline  
2. **Modifiers adjust behavior** — until, withTimeout, onlyIf, finallyDo  
3. **Triggers can be anything** — buttons, sensors, combined conditions  
4. **Document your button map** — your future self will thank you  
5. **Start simple with subsystems** — add state machines when booleans get tangled  
6. **Draw state diagrams first** — visualize before coding  
7. **Enums carry data** — cleaner than separate constants  
8. **Method references are clean** — use when lambda just calls one method  
9. **Auto is just commands** — same patterns as teleop  
10. **Log everything** — state changes, sensor values, running commands

## **What's Next?**

You now have the tools to write competition-quality FRC code. Continue learning by:

1. **Practice** — Build increasingly complex command compositions  
2. **Read team code** — Study how elite teams structure their code  
3. **Experiment** — Try different patterns, see what works  
4. **Teach** — Explaining to teammates solidifies your understanding

Check out these team repositories for inspiration:

- 254 (The Cheesy Poofs)  
- 6328 (Mechanical Advantage)  
- 1678 (Citrus Circuits)  
- 2056 (OP Robotics)

