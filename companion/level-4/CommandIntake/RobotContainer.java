// Level 4 capstone — FRC command-based with CTRE hardware.
// See: docs/source/guide-to-java-level-4.md — Capstone: Command-Based Intake (CTRE Hardware)

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.IntakeCommands;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {

    // One instance — passed to everything that needs it
    private final IntakeSubsystem intake = new IntakeSubsystem();

    private final CommandXboxController operator =
        new CommandXboxController(Constants.OperatorConstants.CONTROLLER_PORT);

    public RobotContainer() {
        // Default command: motor stopped whenever nothing else is scheduled
        intake.setDefaultCommand(IntakeCommands.idle(intake));
        configureBindings();
    }

    private void configureBindings() {
        // Right trigger held → run; release → command ends → stop() called automatically
        operator.rightTrigger().whileTrue(IntakeCommands.run(intake));
    }
}
