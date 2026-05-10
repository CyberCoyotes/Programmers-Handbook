# **Guide to Java \- Level 3: Advanced FTC Patterns**

## **Who Is This For?**

FTC students who have the basics down and want to write cleaner, more reliable code. If you've ever had teleop code that became a tangled mess of if/else statements, this guide is for you.

**Prerequisites:** Level 2: Java for FTC (or equivalent experience)

**Time to Complete:** 2-3 weeks alongside competition prep

---

## **Your Learning Path**

```
┌─────────────────────────────────────────────────────────────┐
│                 LEVEL 2: Java for FTC                       │
│                    (Basic FTC skills)                       │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              LEVEL 2.5: Advanced FTC Patterns               │
│                      (You are here)                         │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
              Ready for competition-quality code!
                    (or Level 3 for FRC transition)
```

---

## **Why This Matters**

As your robot gets more complex, basic if/else code becomes unmanageable:

```java
// This seemed fine at first...
if (gamepad1.a && !hasSample && !isScoring && armPosition < 100) {
    intake.setPower(1.0);
} else if (hasSample && gamepad1.b && armPosition > 500 && !isMoving) {
    outtake.setPower(1.0);
} else if (isScoring && !hasSample && armPosition > 400) {
    // wait, what state are we in again?
}
// 🤯 This is impossible to debug
```

The patterns in this guide solve this problem by giving your code **structure**.

---

## **Part 1: State Machines**

### **What's a State Machine?**

A state machine is a way to organize code where:

1. Your mechanism is always in exactly **one state**  
2. Each state has **clear behavior**  
3. **Transitions** between states follow defined rules

Think of it like a flowchart that your code follows.

### **The Problem: Tangled Booleans**

Watch for these warning signs in your code:

```java
// Multiple booleans tracking what's happening
boolean isIntaking = false;
boolean hasSample = false;
boolean isScoring = false;
boolean isResetting = false;

// Confusing checks
if (isIntaking && hasSample) {
    // Wait, can both be true? What do we do?
}

// Logic scattered everywhere
if (gamepad1.a) {
    isIntaking = true;
    isScoring = false;  // Don't forget this!
    isResetting = false; // Or this!
}
```

When you see this pattern, it's time for a state machine.

### **The Solution: One State Variable**

```java
// Define all possible states
public enum IntakeState {
    IDLE,       // Doing nothing
    INTAKING,   // Running intake, looking for sample
    HOLDING,    // Have a sample, waiting
    SCORING,    // Pushing sample out
    RESETTING   // Returning to idle position
}

// Only ONE variable tracks everything
private IntakeState currentState = IntakeState.IDLE;
```

Now your mechanism can only be in one state at a time. No more conflicting booleans\!

### **Complete Example: Intake State Machine**

```java
@TeleOp(name = "State Machine TeleOp")
public class StateMachineTeleOp extends LinearOpMode {
    
    // --- STATE DEFINITION ---
    public enum IntakeState {
        IDLE,
        INTAKING,
        HOLDING,
        SCORING
    }
    
    // --- HARDWARE ---
    private DcMotor intakeMotor;
    private DigitalInput beamBreak;  // Sensor to detect sample
    
    // --- STATE TRACKING ---
    private IntakeState currentState = IntakeState.IDLE;
    
    @Override
    public void runOpMode() {
        // Initialize hardware
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        beamBreak = hardwareMap.get(DigitalInput.class, "beamBreak");
        
        waitForStart();
        
        while (opModeIsActive()) {
            // --- HANDLE INPUT (requests to change state) ---
            handleInput();
            
            // --- RUN CURRENT STATE ---
            runState();
            
            // --- DISPLAY INFO ---
            telemetry.addData("State", currentState);
            telemetry.addData("Has Sample", hasSample());
            telemetry.update();
        }
    }
    
    private void handleInput() {
        // A button: Request intake (only works from IDLE)
        if (gamepad1.a && currentState == IntakeState.IDLE) {
            currentState = IntakeState.INTAKING;
        }
        
        // B button: Request score (only works from HOLDING)
        if (gamepad1.b && currentState == IntakeState.HOLDING) {
            currentState = IntakeState.SCORING;
        }
        
        // X button: Emergency stop (works from any state)
        if (gamepad1.x) {
            currentState = IntakeState.IDLE;
        }
    }
    
    private void runState() {
        switch (currentState) {
            case IDLE:
                intakeMotor.setPower(0);
                // No automatic transitions from IDLE
                break;
                
            case INTAKING:
                intakeMotor.setPower(1.0);
                // Auto-transition when sample detected
                if (hasSample()) {
                    currentState = IntakeState.HOLDING;
                }
                break;
                
            case HOLDING:
                intakeMotor.setPower(0.1);  // Slight grip to hold sample
                // If sample falls out, go back to IDLE
                if (!hasSample()) {
                    currentState = IntakeState.IDLE;
                }
                break;
                
            case SCORING:
                intakeMotor.setPower(-1.0);  // Reverse to eject
                // Auto-transition when sample is gone
                if (!hasSample()) {
                    currentState = IntakeState.IDLE;
                }
                break;
        }
    }
    
    private boolean hasSample() {
        return !beamBreak.getState();  // Beam breaks are usually active-low
    }
}
```

### **Why This Is Better**

| Before (Booleans) | After (State Machine) |
| :---- | :---- |
| Multiple variables to track | One variable |
| Can get into impossible states | Always in exactly one state |
| Scattered transition logic | Transitions in one place |
| Hard to debug | Easy to see current state |
| Hard to add features | Add a new state, done |

### **State Diagram**

Visualize your state machine before coding:

```
                    ┌─────────────────────────────┐
                    │                             │
                    ▼                             │
              ┌──────────┐                        │
     ┌───────►│   IDLE   │◄──────────────┐       │
     │        └────┬─────┘               │       │
     │             │                     │       │
     │             │ A button            │       │
     │             ▼                     │       │
     │        ┌──────────┐               │       │
     │        │ INTAKING │───────────────┤       │
     │        └────┬─────┘  sample lost  │       │
     │             │                     │       │
     │             │ sample detected     │       │
     │             ▼                     │       │
     │        ┌──────────┐               │       │
     │        │ HOLDING  │───────────────┘       │
     │        └────┬─────┘  sample lost          │
     │             │                             │
     │             │ B button                    │
     │             ▼                             │
     │        ┌──────────┐                       │
     └────────│ SCORING  │───────────────────────┘
   X button   └──────────┘  sample ejected
  (any state)
```

Draw this BEFORE you write code. It makes everything clearer.

---

## **Part 2: Organized Subsystems**

### **Why Organize?**

As your robot grows, one giant OpMode becomes hard to manage:

```java
// Everything in one file = chaos
public class MessyTeleOp extends LinearOpMode {
    // 10 motors declared here
    // 5 servos declared here
    // 3 sensors declared here
    // 500 lines of mixed logic
    // Good luck finding anything!
}
```

### **The Solution: Subsystem Classes**

Break your robot into logical pieces:

```
Robot
├── Drivetrain (4 motors)
├── Intake (1 motor, 1 sensor)
├── Arm (1 motor, 1 encoder)
└── Claw (1 servo)
```

Each becomes its own class:

### **Example: Intake Subsystem with State Machine**

```java
// Intake.java
package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalInput;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    
    // --- STATES ---
    public enum State {
        IDLE,
        INTAKING,
        HOLDING,
        SCORING
    }
    
    // --- HARDWARE ---
    private final DcMotor motor;
    private final DigitalInput beamBreak;
    
    // --- STATE ---
    private State currentState = State.IDLE;
    
    // --- CONSTANTS ---
    private static final double INTAKE_POWER = 1.0;
    private static final double HOLD_POWER = 0.1;
    private static final double SCORE_POWER = -1.0;
    
    // --- CONSTRUCTOR ---
    public Intake(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, "intake");
        beamBreak = hardwareMap.get(DigitalInput.class, "intakeBeam");
    }
    
    // --- CALL THIS EVERY LOOP ---
    public void update() {
        switch (currentState) {
            case IDLE:
                motor.setPower(0);
                break;
                
            case INTAKING:
                motor.setPower(INTAKE_POWER);
                if (hasSample()) {
                    currentState = State.HOLDING;
                }
                break;
                
            case HOLDING:
                motor.setPower(HOLD_POWER);
                if (!hasSample()) {
                    currentState = State.IDLE;
                }
                break;
                
            case SCORING:
                motor.setPower(SCORE_POWER);
                if (!hasSample()) {
                    currentState = State.IDLE;
                }
                break;
        }
    }
    
    // --- STATE REQUESTS ---
    public void requestIntake() {
        if (currentState == State.IDLE) {
            currentState = State.INTAKING;
        }
    }
    
    public void requestScore() {
        if (currentState == State.HOLDING) {
            currentState = State.SCORING;
        }
    }
    
    public void requestIdle() {
        currentState = State.IDLE;
    }
    
    // --- QUERIES ---
    public State getState() {
        return currentState;
    }
    
    public boolean hasSample() {
        return !beamBreak.getState();
    }
    
    public boolean isHolding() {
        return currentState == State.HOLDING;
    }
}
```

### **Example: Arm Subsystem**

```java
// Arm.java
package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    
    // --- STATES ---
    public enum State {
        IDLE,
        MOVING_TO_TARGET,
        AT_TARGET
    }
    
    // --- PRESET POSITIONS ---
    public enum Position {
        INTAKE(0),
        TRAVEL(200),
        SCORE_LOW(400),
        SCORE_HIGH(800);
        
        public final int ticks;
        Position(int ticks) {
            this.ticks = ticks;
        }
    }
    
    // --- HARDWARE ---
    private final DcMotor motor;
    
    // --- STATE ---
    private State currentState = State.IDLE;
    private Position targetPosition = Position.INTAKE;
    
    // --- CONSTANTS ---
    private static final double MOVE_POWER = 0.8;
    private static final int POSITION_TOLERANCE = 20;  // encoder ticks
    
    // --- CONSTRUCTOR ---
    public Arm(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, "arm");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    
    // --- CALL THIS EVERY LOOP ---
    public void update() {
        switch (currentState) {
            case IDLE:
                motor.setPower(0);
                break;
                
            case MOVING_TO_TARGET:
                // Simple proportional control
                int error = targetPosition.ticks - motor.getCurrentPosition();
                
                if (Math.abs(error) < POSITION_TOLERANCE) {
                    currentState = State.AT_TARGET;
                    motor.setPower(0);
                } else {
                    double power = Math.signum(error) * MOVE_POWER;
                    motor.setPower(power);
                }
                break;
                
            case AT_TARGET:
                // Hold position (brake mode handles this)
                motor.setPower(0);
                
                // If we drifted, go back to moving
                int drift = Math.abs(targetPosition.ticks - motor.getCurrentPosition());
                if (drift > POSITION_TOLERANCE * 2) {
                    currentState = State.MOVING_TO_TARGET;
                }
                break;
        }
    }
    
    // --- STATE REQUESTS ---
    public void goToPosition(Position position) {
        targetPosition = position;
        currentState = State.MOVING_TO_TARGET;
    }
    
    public void stop() {
        currentState = State.IDLE;
    }
    
    // --- QUERIES ---
    public State getState() {
        return currentState;
    }
    
    public Position getTargetPosition() {
        return targetPosition;
    }
    
    public boolean isAtTarget() {
        return currentState == State.AT_TARGET;
    }
    
    public int getCurrentTicks() {
        return motor.getCurrentPosition();
    }
}
```

### **Clean TeleOp Using Subsystems**

```java
@TeleOp(name = "Clean TeleOp")
public class CleanTeleOp extends LinearOpMode {
    
    // Subsystems
    private Intake intake;
    private Arm arm;
    
    @Override
    public void runOpMode() {
        // Initialize subsystems
        intake = new Intake(hardwareMap);
        arm = new Arm(hardwareMap);
        
        waitForStart();
        
        while (opModeIsActive()) {
            // --- HANDLE INPUT ---
            
            // Intake controls
            if (gamepad1.a) {
                intake.requestIntake();
            }
            if (gamepad1.b) {
                intake.requestScore();
            }
            if (gamepad1.x) {
                intake.requestIdle();
            }
            
            // Arm controls (D-pad for presets)
            if (gamepad1.dpad_down) {
                arm.goToPosition(Arm.Position.INTAKE);
            }
            if (gamepad1.dpad_left) {
                arm.goToPosition(Arm.Position.TRAVEL);
            }
            if (gamepad1.dpad_up) {
                arm.goToPosition(Arm.Position.SCORE_HIGH);
            }
            if (gamepad1.dpad_right) {
                arm.goToPosition(Arm.Position.SCORE_LOW);
            }
            
            // --- UPDATE SUBSYSTEMS ---
            intake.update();
            arm.update();
            
            // --- TELEMETRY ---
            telemetry.addData("Intake State", intake.getState());
            telemetry.addData("Has Sample", intake.hasSample());
            telemetry.addData("Arm State", arm.getState());
            telemetry.addData("Arm Target", arm.getTargetPosition());
            telemetry.addData("Arm Position", arm.getCurrentTicks());
            telemetry.update();
        }
    }
}
```

Look how clean that is\! Each subsystem manages itself. The OpMode just handles input and calls `update()`.

---

## **Part 3: State-Based Autonomous**

### **The Problem: Sleep Chains**

Basic autonomous uses `sleep()` to wait:

```java
// Fragile - if anything changes, timings break
driveForward(0.5);
sleep(2000);
stopMotors();
turnRight(0.5);
sleep(1000);
stopMotors();
// What if the robot hits something? It just keeps going...
```

### **The Solution: State-Based Auto**

```java
@Autonomous(name = "State-Based Auto")
public class StateMachineAuto extends LinearOpMode {
    
    // --- AUTO STATES ---
    public enum AutoState {
        DRIVE_TO_SPIKE,
        SCORE_SPIKE,
        DRIVE_TO_BACKDROP,
        SCORE_BACKDROP,
        PARK,
        DONE
    }
    
    // --- SUBSYSTEMS ---
    private Drivetrain drivetrain;
    private Intake intake;
    private Arm arm;
    
    // --- STATE ---
    private AutoState currentState = AutoState.DRIVE_TO_SPIKE;
    
    @Override
    public void runOpMode() {
        // Initialize
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        arm = new Arm(hardwareMap);
        
        waitForStart();
        
        while (opModeIsActive() && currentState != AutoState.DONE) {
            // Run current state
            switch (currentState) {
                case DRIVE_TO_SPIKE:
                    runDriveToSpike();
                    break;
                    
                case SCORE_SPIKE:
                    runScoreSpike();
                    break;
                    
                case DRIVE_TO_BACKDROP:
                    runDriveToBackdrop();
                    break;
                    
                case SCORE_BACKDROP:
                    runScoreBackdrop();
                    break;
                    
                case PARK:
                    runPark();
                    break;
                    
                case DONE:
                    // Stop everything
                    break;
            }
            
            // Update subsystems
            drivetrain.update();
            intake.update();
            arm.update();
            
            // Telemetry
            telemetry.addData("Auto State", currentState);
            telemetry.update();
        }
    }
    
    // --- STATE METHODS ---
    
    private void runDriveToSpike() {
        // Start driving if not already
        if (!drivetrain.isMoving()) {
            drivetrain.driveToPosition(24);  // 24 inches forward
        }
        
        // Transition when arrived
        if (drivetrain.isAtTarget()) {
            currentState = AutoState.SCORE_SPIKE;
        }
    }
    
    private void runScoreSpike() {
        // Start scoring if not already
        if (intake.getState() != Intake.State.SCORING) {
            intake.requestScore();
        }
        
        // Transition when done
        if (intake.getState() == Intake.State.IDLE && !intake.hasSample()) {
            currentState = AutoState.DRIVE_TO_BACKDROP;
        }
    }
    
    private void runDriveToBackdrop() {
        // Raise arm while driving
        if (arm.getTargetPosition() != Arm.Position.SCORE_HIGH) {
            arm.goToPosition(Arm.Position.SCORE_HIGH);
        }
        
        if (!drivetrain.isMoving()) {
            drivetrain.driveToPosition(36);  // 36 more inches
        }
        
        // Transition when both arrived
        if (drivetrain.isAtTarget() && arm.isAtTarget()) {
            currentState = AutoState.SCORE_BACKDROP;
        }
    }
    
    private void runScoreBackdrop() {
        intake.requestScore();
        
        if (intake.getState() == Intake.State.IDLE) {
            currentState = AutoState.PARK;
        }
    }
    
    private void runPark() {
        // Lower arm for parking
        if (arm.getTargetPosition() != Arm.Position.INTAKE) {
            arm.goToPosition(Arm.Position.INTAKE);
        }
        
        if (!drivetrain.isMoving()) {
            drivetrain.driveToPosition(12);
        }
        
        if (drivetrain.isAtTarget() && arm.isAtTarget()) {
            currentState = AutoState.DONE;
        }
    }
}
```

### **Why This Is Better**

| Sleep-Based Auto | State-Based Auto |
| :---- | :---- |
| Fixed timing | Reacts to sensors |
| Can't recover from errors | Handles unexpected situations |
| Hard to modify | Add/remove states easily |
| No parallel actions | Multiple things at once |
| Hard to debug | Clear current state |

---

## **Part 4: Coordinating Multiple Subsystems**

Sometimes subsystems need to work together. For example, you shouldn't score until the arm is in position.

### **Pattern: Checking Other Subsystems**

```java
// In your OpMode or a coordinator class

private void handleScoreRequest() {
    // Only allow scoring if:
    // 1. We have a sample
    // 2. Arm is at scoring position
    // 3. Not already scoring
    
    boolean canScore = intake.hasSample() 
                    && arm.isAtTarget() 
                    && arm.getTargetPosition() == Arm.Position.SCORE_HIGH
                    && intake.getState() != Intake.State.SCORING;
    
    if (gamepad1.b && canScore) {
        intake.requestScore();
    }
}
```

### **Pattern: Automatic Coordination**

```java
// In your main loop

private void coordinateSubsystems() {
    // If we just picked up a sample, automatically go to travel position
    if (intake.isHolding() && arm.getTargetPosition() == Arm.Position.INTAKE) {
        arm.goToPosition(Arm.Position.TRAVEL);
    }
    
    // If arm is at intake position, but we have a sample, don't stay there
    if (intake.hasSample() && arm.getTargetPosition() == Arm.Position.INTAKE) {
        arm.goToPosition(Arm.Position.TRAVEL);
    }
    
    // LED feedback based on combined state
    if (intake.isHolding() && arm.isAtTarget()) {
        setLEDColor(GREEN);  // Ready to score!
    } else if (intake.isHolding()) {
        setLEDColor(YELLOW); // Have sample, arm moving
    } else {
        setLEDColor(RED);    // No sample
    }
}
```

---

## **Part 5: Debugging State Machines**

### **Use Telemetry Liberally**

```java
telemetry.addData("=== INTAKE ===", "");
telemetry.addData("State", intake.getState());
telemetry.addData("Has Sample", intake.hasSample());
telemetry.addData("Motor Power", intakeMotor.getPower());

telemetry.addData("=== ARM ===", "");
telemetry.addData("State", arm.getState());
telemetry.addData("Target", arm.getTargetPosition());
telemetry.addData("Current Ticks", arm.getCurrentTicks());
telemetry.addData("At Target", arm.isAtTarget());

telemetry.update();
```

### **Log State Transitions**

```java
private void setState(State newState) {
    if (newState != currentState) {
        // This shows up in the log
        System.out.println("Intake: " + currentState + " -> " + newState);
        currentState = newState;
    }
}
```

### **Common State Machine Bugs**

| Problem | Likely Cause | Fix |
| :---- | :---- | :---- |
| Stuck in one state | Transition condition never true | Check sensor/condition logic |
| Rapidly switching states | No debouncing or condition too sensitive | Add stability check |
| Wrong state after button press | Multiple states responding to same input | Check state guards |
| Subsystem does nothing | Forgot to call `update()` | Add update() to main loop |

---

## **Practice Exercises**

1. **Basic State Machine:** Create a state machine for a claw with states: OPEN, CLOSED, OPENING, CLOSING

2. **Subsystem Class:** Convert your drivetrain into a subsystem class with `driveToPosition()` and `isAtTarget()` methods

3. **Coordinated Action:** Make the arm automatically move to INTAKE position when the intake starts running

4. **State-Based Auto:** Convert a sleep-based auto to state-based auto

5. **Draw First:** Before coding a new mechanism, draw the state diagram on paper

---

## **Check Your Understanding**

Before moving on, you should be able to:

- [ ] Explain why state machines are better than multiple booleans  
- [ ] Create an enum for states  
- [ ] Write a switch statement that runs different code for each state  
- [ ] Add automatic transitions based on sensor values  
- [ ] Create a subsystem class with its own state machine  
- [ ] Call `update()` on subsystems in the main loop  
- [ ] Coordinate multiple subsystems safely  
- [ ] Debug state machines using telemetry

---

## **Quick Reference**

### **State Machine Template**

```java
public enum State {
    IDLE,
    DOING_SOMETHING,
    DONE
}

private State currentState = State.IDLE;

public void update() {
    switch (currentState) {
        case IDLE:
            // behavior
            break;
        case DOING_SOMETHING:
            // behavior
            if (somethingFinished) {
                currentState = State.DONE;
            }
            break;
        case DONE:
            // behavior
            break;
    }
}
```

### **Subsystem Template**

```java
public class MySubsystem {
    public enum State { IDLE, ACTIVE }
    
    private final DcMotor motor;
    private State currentState = State.IDLE;
    
    public MySubsystem(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, "motor");
    }
    
    public void update() {
        switch (currentState) {
            case IDLE:
                motor.setPower(0);
                break;
            case ACTIVE:
                motor.setPower(1.0);
                break;
        }
    }
    
    public void requestActive() {
        currentState = State.ACTIVE;
    }
    
    public void requestIdle() {
        currentState = State.IDLE;
    }
    
    public State getState() {
        return currentState;
    }
}
```

### **Main Loop Pattern**

```java
while (opModeIsActive()) {
    // 1. Handle input
    handleInput();
    
    // 2. Coordinate subsystems
    coordinateSubsystems();
    
    // 3. Update all subsystems
    intake.update();
    arm.update();
    drivetrain.update();
    
    // 4. Telemetry
    updateTelemetry();
}
```

---

## **What's Next?**

You now have the tools to write competition-quality FTC code:

- State machines to manage complexity  
- Subsystem classes to organize code  
- State-based autonomous for reliability

When you're ready for FRC, these patterns will translate directly — FRC's Command-Based framework is essentially a more sophisticated version of what you just learned\!

Continue to **Level 3: Java Refresher for FRC** when you join a high school team.
