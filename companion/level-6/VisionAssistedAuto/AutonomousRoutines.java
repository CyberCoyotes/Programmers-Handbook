// Level 6 capstone — vision-assisted two-piece autonomous routine.
// See: docs/source/guide-to-java-level-6.md — Capstone: Vision-Assisted Two-Piece Auto

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.ElevatorPosition;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public final class AutonomousRoutines {

    private AutonomousRoutines() {}

    /**
     * Score preloaded piece, collect from source, score second piece.
     * Applies vision corrections whenever tags are visible.
     * Falls back to odometry automatically during dropouts.
     */
    public static Command scoreTwoPieceVision(
        DrivetrainSubsystem drivetrain,
        ElevatorSubsystem elevator,
        IntakeSubsystem intake,
        VisionSubsystem vision
    ) {
        return Commands.sequence(

            Commands.runOnce(() -> {
                if (vision.isDroppedOut()) {
                    DriverStation.reportWarning(
                        "Vision dropout at auto start — running odometry only", false);
                }
            }),

            // Piece 1
            Commands.parallel(
                AutonomousCommands.followPath(drivetrain, "StartToReef1"),
                ElevatorCommands.goTo(elevator, ElevatorPosition.L3)
                    .unless(() -> !elevator.isHealthy())
            ),
            IntakeCommands.feed(intake).withTimeout(0.5),

            // Travel to source
            Commands.parallel(
                AutonomousCommands.followPath(drivetrain, "Reef1ToSource"),
                ElevatorCommands.goTo(elevator, ElevatorPosition.INTAKE)
            ),
            IntakeCommands.run(intake).until(intake::hasPiece).withTimeout(2.0),

            // Piece 2
            Commands.parallel(
                AutonomousCommands.followPath(drivetrain, "SourceToReef2"),
                ElevatorCommands.goTo(elevator, ElevatorPosition.L4)
                    .unless(() -> !elevator.isHealthy())
            ),
            IntakeCommands.feed(intake).withTimeout(0.5),

            ElevatorCommands.goTo(elevator, ElevatorPosition.STOWED)
        );
    }
}
