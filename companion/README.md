# Programmer's Handbook — Companion Projects

Every major code example from the handbook lives here as a standalone source file, organized by level. Each project is referenced from the handbook with a direct link.

## Structure

```
companion/
  level-1/MatchScorer/          Pure Java (no hardware)
  level-2/IntakeSubsystem/      FTC SDK (LinearOpMode)
  level-3/CoordinatedRobot/     FTC SDK (multi-subsystem)
  level-4/CommandIntake/        FRC WPILib command-based
  level-5/ElevatorAuto/         FRC WPILib + CTRE + Choreo
  level-6/VisionAssistedAuto/   FRC WPILib + CTRE + Limelight
```

## Link Convention

Handbook files reference companion projects with:

```markdown
> **Full project:** [`companion/level-N/ExampleName`](https://github.com/CyberCoyotes/Programmers-Handbook/tree/main/companion/level-N/ExampleName)
```

## Notes

- Source files use the package structure they would have inside a real project (`frc.robot.*`, `org.firstinspires.ftc.teamcode.*`).
- Files are not part of a full Gradle project — they are standalone reference sources. A full deployable project skeleton is a future addition.
- CI verifies that expected files exist on every push.
