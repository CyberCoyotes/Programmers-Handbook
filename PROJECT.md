# Programmers Handbook — Project Description

**Project owner:** FRC Team 3603 Cyber Coyotes
**Format:** Progressive, leveled handbook (Levels 1–6)
**Audience:** Grade 6 (age 11) through Grade 12 (age 18) FIRST robotics students
**Language & ecosystem:** Java, with a forward-looking bias toward the unified FIRST/WPILib ecosystem expected to land for FTC within the next ~12 months

---

## 1. Purpose

This handbook takes a student with no prior coding experience and walks them, level by level, to writing the kind of Java a top-10% FRC team writes today: command-based, state-machine driven, and vision-assisted with 3D field-relative positioning.

It is written by coaches, for students, in a teaching voice. It is *not* a reference manual and *not* an API dump. Every concept is introduced with a short worked example, the example follows the same best practices the student will use on a competition robot, and the explanation comes before the code — never after.

The handbook serves a second purpose as well: it is a deliberate bridge across the FTC-to-FRC transition. A Grade 6 FTC programmer and a Grade 11 FRC programmer should be able to start at the level appropriate to them and arrive at the same destination — fluent, idiomatic Java in the modern WPILib-style ecosystem.

## 2. Audience and prerequisites

- **Primary readers:** Students ages 11–18 on FTC and FRC teams.
- **Assumed background at Level 1:** Can use a computer, can type, can follow written instructions. No math beyond Grade 6 arithmetic. No prior programming.
- **Assumed background at Level 4:** Comfortable with Java syntax, has written and debugged a working FTC-style robot program, understands sensors and basic feedback control.
- **Secondary readers:** Mentors and rookie coaches who need a shared curriculum to teach from.

## 3. Pedagogical approach

The handbook is built on four rules:

1. **Exemplar-first.** Every concept is anchored to a small, complete, working code example that follows the same conventions a student would use on a real robot. No throwaway "hello world" patterns that have to be unlearned later.
2. **Explain, then show, then extend.** Prose introduces the *why*. Code shows the *how*. A short "try this" extension invites the student to modify the example. Reference material lives at the end of each level, not the beginning.
3. **One new idea at a time.** Each example introduces exactly one new concept on top of what the student already knows. New syntax and new robotics concepts are never introduced in the same example.
4. **Foundational knowledge is forward-compatible.** Anything taught in Levels 1–3 must remain true and useful in the post-convergence FTC/FRC ecosystem. Where current (2026) FTC syntax differs from where the ecosystem is heading, the handbook teaches the underlying concept in WPILib-aligned terms and notes the current FTC variant in a clearly-labeled sidebar.

## 4. The six levels

Levels 1–3 are foundational and currently map to FTC. Levels 4–6 are FRC-focused and assume Levels 1–3 as background. The handbook is designed so that a student progressing through current (2026) FTC, future (post-convergence) FTC, and FRC will all walk the same path.

### Level 1 — First Code on a Robot
**Goal:** Write Java that makes a motor turn, on purpose, and stop on purpose.
**Concepts:** Variables, methods, classes, the robot program lifecycle (init / loop / stop), basic motor control, simple gamepad input, telemetry as a learning tool.
**Best-practice habits introduced:** Meaningful names, constants in one place, no magic numbers, comments that explain *why* not *what*.

### Level 2 — Sensors, Feedback, and Structure
**Goal:** Make the robot react to the world — drive a known distance, turn to a heading, hold an arm at a position, and run a first simple autonomous.
**Concepts:** Encoders, IMU/gyro, simple closed-loop control (P, then PID at a conceptual level), units and unit safety, splitting code into small focused classes ("subsystems" in spirit, even if not yet in name), basic logging. First autonomous routines: time-based and encoder-based "drive forward, turn, score" sequences, with an honest discussion of why these are brittle and what we'll do about it later.
**Best-practice habits introduced:** One class, one responsibility. Public methods describe *what the robot does*, not *what the motor does*. Constants live in a dedicated class.

### Level 3 — Seeing the Field (Vision, Part 1)
**Goal:** Use a Limelight 3A to align to a target, estimate distance, and drive a vision-assisted autonomous routine that out-performs anything time-based.
**Concepts:** Pipelines and pipeline switching, AprilTag detection, the camera coordinate frame, simple proportional control on a vision error, and the difference between *seeing* a target and *knowing where you are*. First vision-assisted auto: drive until the tag fills a known fraction of the frame, align, score.
**Best-practice habits introduced:** Wrap the camera in its own class. Never let vision code reach into drivetrain code. Always handle the "no target" case explicitly.

> **Bridge note at end of Level 3:** Everything up to this point is taught in a way that survives the FTC ecosystem transition. The student now has the foundation to step into either current FTC, future FTC, or FRC without re-learning fundamentals.

### The autonomous through-line

Autonomous programming is not its own level — it grows alongside everything else, getting smarter at each step:

- **Level 2:** Time-based and encoder-based routines. The student feels the brittleness firsthand.
- **Level 3:** Vision-assisted routines using the Limelight 3A. The student sees how much better closed-loop is than open-loop.
- **Level 4:** Command-composed routines. The same building blocks as teleop, just sequenced differently.
- **Level 5:** Authored trajectories — Choreo for FRC, with odometry-pod feedback for FTC. Paths become version-controlled assets.
- **Level 6:** Pose-fused routines that combine wheel odometry with AprilTag corrections, with graceful degradation when vision drops out.

The handbook deliberately does not lock the FTC autonomous toolchain to one framework (Road Runner, manual, time-based, or otherwise). The team has experimented with several without committing to one, and the ecosystem convergence will reshape the FTC toolchain anyway. Instead, the handbook teaches the *concepts* of autonomous routines (waypoints, feedback, composition) in a way that any current or future tool will fit into.

### Level 4 — Command-Based Programming
**Goal:** Restructure the Level 1–3 mental model into the command-based pattern that FRC and the converged FTC ecosystem use.
**Concepts:** Subsystems, Commands, the CommandScheduler, default commands, command composition (sequential, parallel, race, deadline), bindings to controllers, requirements and resource locking. First command-based autonomous routines composed from named commands ("intake, drive, score") rather than imperative steps.
**Best-practice habits introduced:** Subsystems own their hardware and never expose it. Commands describe intent, not implementation. The robot's binding file reads almost like English.

### Level 5 — State Machines, Real Mechanisms, and Authored Paths
**Goal:** Program the kind of mechanism a top-10% team brings to a regional — an elevator with stowed/intake/L1/L2/L3/L4 positions, a coordinated intake, a multi-stage scoring sequence — and run autonomous routines along trajectories authored in a path-planning GUI.
**Concepts:** Explicit state machines (enum-driven), motion profiling and feedforward, sensor-based transitions, fault handling and fallback states, autonomous routines built as command compositions. **Choreo** for trajectory authoring (the team's standard for FRC), with the underlying ideas — waypoints, constraints, time-parameterized splines — taught in a way that transfers to other tools. Odometry as the feedback signal that makes path-following work.
**Best-practice habits introduced:** A subsystem's state is an enum, not a pile of booleans. Every state has a defined entry, exit, and "what runs while I'm here." Auto routines are *composed*, not copy-pasted. Paths are version-controlled assets, not robot code.

### Level 6 — 3D Vision and Field-Relative Programming
**Goal:** Use a Limelight 4 with a Hailo coprocessor for 3D pose estimation, fuse vision with odometry for the autonomous routines authored in Level 5, and write commands that operate in field coordinates rather than robot coordinates.
**Concepts:** AprilTag-based pose estimation, MegaTag-style multi-tag solving, fusing vision pose with wheel/swerve odometry, field-relative drive and field-relative scoring commands ("drive to the nearest reef branch"), latency compensation, trust/standard-deviation tuning, recovering autonomous routines when a tag is briefly lost.
**Best-practice habits introduced:** The robot's `Pose2d` is the single source of truth. Vision is a *correction*, not a controller. Commands operate on field positions; subsystems translate to motor outputs.

## 5. Ecosystem caveat — the FTC/FRC convergence

As of writing (May 2026), FTC Java and FRC Java use different organizational schemas — different program lifecycle (`OpMode` vs. `TimedRobot`/`Robot`), different hardware abstraction, different patterns for structuring code. This is changing within the next ~12 months as FTC migrates toward the unified WPILib-style ecosystem.

The handbook handles this in three ways:

- **Levels 1–3** are written with the *concepts* WPILib uses (subsystems-in-spirit, command-shaped methods, pose-shaped sensor wrapping) but with code samples that compile and run on **current (2026) FTC**. Each example is annotated where current FTC syntax will shift after convergence.
- **Levels 4–6** are written in modern WPILib command-based Java, the same code a converged FTC team will write and what FRC teams write today.
- **Sidebars labeled "Current FTC (2026)"** show the legacy syntax for any concept that differs, so a student working on a current-season FTC robot can map directly between the two without confusion.

The intent is that a Level 3 graduate, dropped into either current FTC or post-convergence FTC, can read their team's code and recognize what they see. And a Level 6 graduate has only ever learned the converged ecosystem.

## 6. Hardware and code stack

Every code sample in the handbook is written against the team's actual hardware. There is no hardware-agnostic abstraction layer in the examples — students should be able to clone, build, and run them on the robots they actually work on.

**Levels 1–3 (FTC):**
- Motor controllers and motors: **REV Robotics and goBilda, presented side by side.** When the same task has a meaningfully different API in each ecosystem, both versions are shown, with the underlying concept emphasized so the student sees through the syntax.
- Sensors: REV through-bore encoders, built-in IMU, with odometry pods (e.g. goBilda Pinpoint) introduced as the path-feedback mechanism in Level 3.
- Vision: Limelight 3A.

**Levels 4–6 (FRC):**
- Drivetrain: **swerve only.** All examples assume swerve. Tank and West Coast are not covered — the team builds swerve and the handbook reflects that.
- Motor controllers and motors: **CTRE exclusively** — Talon FX / Kraken X60, with CANcoders and Pigeon 2 for sensing. REV is acknowledged where relevant for context but not used in examples.
- Vision: Limelight 4 with Hailo coprocessor.
- Software: WPILib command-based, Choreo for trajectory authoring, AdvantageKit / AdvantageScope for logging where it earns its weight.

**Code samples and the companion repo:**
The handbook does not stand alone. A companion repository hosts every code example as a buildable, runnable project:

- One folder per level, with one project per major example.
- Each project compiles in isolation against the in-season WPILib / CTRE / REV / FTC SDK versions.
- CI builds every project on every commit. A sample that breaks because a library updated is caught the same day, not the next build season.
- The handbook embeds short snippets inline for reading flow and links to the full project for the student to clone, run, and modify.
- Students are encouraged to fork the companion repo, work through examples on their own machines, and submit PRs when they find bugs or improvements — making the repo itself a teaching artifact for the GitHub workflow they'll use on a real team.

The companion repo also doubles as a worked example of how the team organizes a real codebase: proper `Constants` structure, subsystem and command packages, logging conventions, build files. The handbook isn't only teaching syntax; the repo is teaching project structure by example.

## 7. Code style and "best practices" — defined

The handbook treats "best practices" as a set of concrete, checkable rules, not vibes. Every code sample in the book must follow them, so students learn them by osmosis. Examples are not hardware-agnostic — they target the stack defined in Section 6 and run on the team's robots.

- Meaningful names. No `m1`, `x`, `tmp`. Method names are verbs, class names are nouns.
- One source of truth for constants. A `Constants` class (or per-subsystem `Constants` inner class) — never a magic number in the middle of a method.
- Units in names or types. `armAngleDegrees`, not `armAngle`.
- One class, one responsibility. A subsystem owns its hardware; a command owns its behavior; never the other way around.
- No `null` returns where an `Optional` will do. No exceptions used for control flow.
- Public methods describe *robot intent* (`scoreL3()`), not *hardware action* (`setMotor4Power(0.6)`).
- Every example is complete and compiles. No `// ... rest of code here` placeholders in the main exposition.
- Comments explain *why*. The code itself explains *what*.

## 8. Voice and tone

- Second person ("you'll write a class that..."), present tense.
- Warm and encouraging, not chummy. The student is a competent person learning a real skill.
- Assumes the student will eventually be the senior programmer on their team, and writes accordingly.
- Never apologizes for complexity; introduces it on a schedule.
- Never says "just" or "simply." If it were simple, we wouldn't be teaching it.

## 9. Per-level deliverables

Each level contains:

1. A short opening: what this level is for and what the student will be able to do at the end.
2. 4–8 lessons, each with prose, an exemplar, and a "try this" extension.
3. **Coach notes woven throughout** — short callouts aimed at the mentor, not the student. Common misconceptions, where students typically get stuck, what to demo on a real robot, when to let a student fail and learn versus when to intervene. Lives in the same document, visually distinct (a callout box style), so a single artifact serves both audiences.
4. A capstone mini-project that combines the lesson concepts on a realistic robot mechanism.
5. A reference card listing the classes, methods, and patterns introduced.
6. Where applicable, a "Current FTC (2026)" sidebar mapping converged-ecosystem syntax to current FTC syntax.
7. A short bridge to the next level — what's coming, and why this level prepared the student for it.

## 10. Out of scope

- C++, Python, Kotlin, LabVIEW. Java only.
- REV Hardware Client / Phoenix Tuner UI walkthroughs beyond what's needed to verify a code sample works. Hardware setup belongs in a separate document.
- Game-specific strategy. Examples may reference real game elements, but the handbook is evergreen across seasons.
- Mechanical and electrical content beyond the minimum needed to understand what the code is controlling.

## 11. Success criteria

The handbook succeeds when:

- A Grade 6 student with no background can start at Level 1 and, with a mentor, reach a working capstone by the end of Level 1 in a single build season.
- A returning FTC programmer can place into Level 4 and ship a command-based FRC subsystem within a few weeks.
- A Level 6 graduate can read, understand, and contribute to the codebase of a top-10% FRC team without significant retraining.
- Mentors at other teams adopt sections of it because the explanations are clearer than what they'd write themselves.
