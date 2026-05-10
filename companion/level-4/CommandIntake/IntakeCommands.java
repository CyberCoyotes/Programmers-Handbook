// Level 4 capstone — FRC command-based with CTRE hardware.
// See: docs/source/guide-to-java-level-4.md — Capstone: Command-Based Intake (CTRE Hardware)

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.IntakeSubsystem;

public final class IntakeCommands {

    private IntakeCommands() {}

    // Runs the intake while active; third arg registers the requirement
    public static Command run(IntakeSubsystem intake) {
        return Commands.startEnd(
            intake::run,
            intake::stop,
            intake
        );
    }

    // Default command — motor stopped, requirement held so nothing else sneaks in
    public static Command idle(IntakeSubsystem intake) {
        return Commands.run(intake::stop, intake);
    }
}
