# Glossary

Terms used consistently throughout this handbook. When a word has multiple industry spellings or synonyms, the form listed here is the one used in all code examples and prose.

---

## A

**AprilTag**
A fiducial marker used on FRC and FTC fields. Cameras detect them to estimate the robot's 3D position relative to a known tag location.

**autonomous** (also: *auto*)
The first 15 seconds of an FRC match (or 30 seconds in FTC) during which the robot operates without driver input.

---

## C

**CANcoder**
A CTRE magnetic encoder that communicates over the CAN bus. Used to measure absolute swerve module angle in Phoenix 6 swerve drives.

**CAN bus** (Controller Area Network)
The wired network that connects the roboRIO to CTRE and REV motor controllers, the Pigeon 2 IMU, and other devices. Device IDs must be unique.

**Choreo**
A trajectory authoring app for FRC swerve drives. Exports `.traj` files that ChoreoLib reads at runtime to follow the path.

**command** (FRC)
A unit of robot behavior in WPILib's command-based framework. Commands declare which subsystems they require and implement `initialize()`, `execute()`, `isFinished()`, and `end()`.

**command-based**
A WPILib programming paradigm where robot behavior is organized into Commands and Subsystems. Commands encode actions; the `CommandScheduler` manages their lifecycle and resolves conflicts. The primary framework used in FRC.

**`CommandScheduler`**
The WPILib singleton that runs every scheduled command once per 20 ms loop and resolves requirement conflicts between commands.

**constructor**
A special Java method called when a new object is created. It has the same name as the class and initializes the object's fields. In FRC, subsystem constructors typically configure motor controllers and sensors.

**CTRE** (Cross The Road Electronics)
The vendor that makes Talon FX motor controllers, CANcoders, and the Pigeon 2 IMU. Their API is called Phoenix 6 in current FRC.

---

## D

**data types**
The category of a value in Java, which determines what kind of data it can hold and what operations can be performed on it. Primitive types include `int`, `double`, and `boolean`; reference types include classes like `String` and subsystem classes.

**dead-wheel odometry**
Position tracking using unpowered wheels (contact wheels or omni wheels) connected to encoders, not affected by motor slip.

**debounce**
A technique that prevents a rapidly toggling input (e.g., a noisy sensor or button) from triggering repeated actions. WPILib's `Debouncer` requires a signal to be stable for a set duration before registering a state change.

**default command**
A command that runs on a subsystem whenever no other command requiring that subsystem is scheduled. Set with `subsystem.setDefaultCommand()`.

**driver station**
The FRC software application running on the driver laptop that connects to the robot's roboRIO over Wi-Fi or Ethernet. It enables/disables the robot and selects the operating mode (autonomous, teleop, or test).

---

## E

**encoder**
A sensor that measures motor or wheel rotation. Relative encoders reset on power-up; absolute encoders (e.g., CANcoder) remember position across power cycles.

---

## F

**feedforward**
A control term calculated from a model of the mechanism (gravity, friction, velocity) rather than from sensor error. Added to PID output to reduce steady-state error.

**field-relative**
Describes drive commands or poses defined in the fixed coordinate frame of the field, not the robot. "Forward" always means toward the opposing alliance wall.

**FRC** (FIRST Robotics Competition)
The senior-level FIRST program. Uses WPILib, Java or C++, and 120-pound robots competing in a 54×27 ft field.

**FTC** (FIRST Tech Challenge)
The middle-school/high-school FIRST program. Uses the FTC SDK, Java, and 18×18 in robots competing in a 12×12 ft field.

---

## G

**gamepad** (also: *controller*)
The driver input device. FTC: any Android-compatible USB gamepad via the Driver Hub. FRC: Xbox-compatible controller via a USB port on the Driver Station laptop.

---

## H

**hardwareMap**
The FTC SDK object used to retrieve hardware devices by name. Example: `hardwareMap.get(DcMotor.class, "intake")`. The name must match the robot configuration exactly.

---

## I

**IMU** (Inertial Measurement Unit)
A sensor that measures rotation rate and acceleration. The Pigeon 2 (FRC) and built-in REV Control Hub IMU (FTC) provide heading data used by odometry and field-relative drive.

**iterative-based**
A WPILib programming style where the robot class overrides `autonomousPeriodic()`, `teleopPeriodic()`, and similar methods called repeatedly in a loop. An alternative with less abstraction than the command-based framework, but requires manual state management for complex behaviors.

---

## K

**Kalman filter**
A statistical algorithm that blends multiple noisy measurements (e.g., wheel odometry + vision) weighted by their expected noise. `SwerveDrivePoseEstimator` uses one internally.

**Kraken X60**
A brushless motor made by WestCoast Products / Vex. Paired with a Talon FX motor controller. The primary drive and mechanism motor in current CTRE-stack FRC robots.

---

## L

**latency compensation**
Adjusting a vision pose measurement for the time it took the camera to capture and process the frame, so the correction is applied to the robot's past position rather than its current position.

**Limelight**
A smart camera system for FRC/FTC. The Limelight 4 (with Hailo coprocessor) performs AprilTag 3D pose estimation onboard and publishes results via NetworkTables.

**`LinearOpMode`**
The FTC SDK base class for programs that run top-to-bottom in a single thread. Most FTC programs use this. Contrast with the iterative `OpMode`.

---

## M

**mechanism** (avoid in handbook prose)
Generic term for a robot mechanism. The handbook prefers *subsystem* to match WPILib naming.

**MegaTag2**
Limelight's multi-tag, IMU-assisted pose solving mode. Produces a tighter XY estimate than single-tag solving. Do not trust its rotation output — use the gyro directly.

**method**
A named block of code in a Java class that performs a specific task. Defined with a return type, a name, and optional parameters. In FRC subsystems, methods like `intake()` or `stop()` express the robot's intent.

---

## O

**object**
An instance of a Java class that bundles data (fields) and behavior (methods). In FRC, every subsystem, command, and hardware device is an object created from its respective class.

**odometry**
Estimating the robot's field position by integrating wheel encoder and gyro readings over time. Accumulates error; corrected by vision in Level 6.

**OpMode** (FTC)
A top-level FTC program, annotated with `@TeleOp` or `@Autonomous`. Appears on the Driver Hub menu. Equivalent concept to an FRC `Robot` class.

---

## P

**`periodic()`**
The WPILib method called every 20 ms on every registered subsystem. Equivalent to the `update()` pattern used in FTC subsystem classes in this handbook.

**Phoenix 6**
CTRE's current Java API for Talon FX, CANcoder, and Pigeon 2. Uses control-request objects (`DutyCycleOut`, `PositionVoltage`) passed to `motor.setControl()`.

**Pigeon 2**
A CTRE IMU that provides heading and rotation rate via CAN. Used as the gyro source for swerve odometry and field-relative drive.

**pose** (also: *Pose2d*)
A robot's position and heading on the field, represented as `(x meters, y meters, rotation)`. The WPILib class is `Pose2d`.

**`ProfiledPIDController`**
A WPILib PID controller with a built-in `TrapezoidProfile` that limits commanded velocity and acceleration, preventing mechanical shock on position-controlled mechanisms.

---

## R

**requirement**
The subsystem a command declares it needs exclusive access to. The `CommandScheduler` prevents two commands from requiring the same subsystem simultaneously. Set via `addRequirements()` or the subsystem argument to factory methods.

**`RobotContainer`**
The WPILib class where subsystems are instantiated and button bindings are configured. Created once by the framework at startup.

---

## S

**standard deviation** (in pose estimation)
A measure of sensor noise passed to `addVisionMeasurement()`. Lower values mean "trust this source more." Never trust vision rotation — pass a large value (e.g., 9999) for that axis.

**state machine**
A programming pattern where a system is always in exactly one state (e.g., `IDLE`, `INTAKING`, `HOLDING`), and transitions between states are triggered by conditions or events.

**subsystem**
A class that owns all hardware for one robot mechanism and exposes intent-based methods (`intake()`, `stop()`). In FRC, extends `SubsystemBase`. In FTC (this handbook), a plain class with an `update()` method.

**swerve drive**
A drivetrain where each wheel module can rotate independently, allowing the robot to translate in any direction while facing any heading.

**`SwerveDrivePoseEstimator`**
A WPILib class that fuses wheel odometry with periodic vision corrections using a Kalman filter. Drop-in replacement for `SwerveDriveOdometry` when vision is available.

---

## T

**Talon FX**
A CTRE brushless motor controller with a built-in encoder, compatible with Kraken X60 and Falcon 500 motors. Controlled via Phoenix 6 in current FRC.

**teleop**
The 2:15 driver-controlled period of an FRC match (or 1:30 in FTC) following autonomous.

**telemetry**
Data displayed on the Driver Hub screen (FTC) or SmartDashboard/Shuffleboard (FRC) during a match. Used for debugging and driver feedback.

**`TrapezoidProfile`**
A WPILib motion profile that limits acceleration and velocity to produce a smooth trapezoidal velocity curve, preventing mechanical shock on position-controlled mechanisms.

**`Trigger`**
A WPILib class representing a boolean condition (button press, sensor reading, etc.) that can schedule commands when it becomes active or inactive. Composed with `.whileTrue()`, `.onTrue()`, `.toggleOnTrue()`, etc.

---

## V

**variable**
A named storage location in Java that holds a value of a specific data type. Variables can be local (inside a method), instance fields (belonging to an object), or static (shared across all instances of a class).

**vendor dependency** (also: *vendordep*)
A JSON file that tells the WPILib Gradle build where to download a third-party library (CTRE Phoenix 6, ChoreoLib, etc.). Placed in `vendordeps/` in the project root.

**vision dropout**
A period when the camera cannot detect any AprilTags. The pose estimator falls back to wheel odometry automatically; vision corrections resume when tags reappear.

---

## W

**WPILib**
The official Java/C++ library for FRC robot programming. Provides the command-based framework, hardware abstractions, mathematics utilities, and simulation tools used in Levels 4–6.
