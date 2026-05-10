// Level 5 capstone — elevator + intake autonomous routine.
// See: docs/source/guide-to-java-level-5.md — Capstone: Elevator + Intake Autonomous Routine

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.ElevatorPosition;
import frc.robot.subsystems.IntakeSubsystem;

public final class AutonomousRoutines {

    private AutonomousRoutines() {}

    /**
     * Score preload at L4, drive to first game piece while lowering to intake height,
     * grab the piece, then drive back and score at L2.
     *
     * Aborts cleanly if the elevator reports a fault.
     */
    public static Command scoreTwoPiece(
        DrivetrainSubsystem drivetrain,
        ElevatorSubsystem   elevator,
        IntakeSubsystem     intake
    ) {
        return Commands.sequence(
            // 1. Score preload at L4
            ElevatorCommands.goTo(elevator, ElevatorPosition.L4),
            IntakeCommands.eject(intake).withTimeout(0.5),

            // 2. Drive to first piece while lowering elevator — run in parallel
            Commands.parallel(
                AutonomousCommands.followPath(drivetrain, "ScoreToFirstPiece"),
                ElevatorCommands.goTo(elevator, ElevatorPosition.INTAKE)
            ),

            // 3. Grab the piece — sensor-gated with a timeout safety net
            IntakeCommands.run(intake)
                .until(intake::hasPiece)
                .withTimeout(2.0),

            // 4. Drive back and stage elevator simultaneously
            Commands.parallel(
                AutonomousCommands.followPath(drivetrain, "FirstPieceToScore"),
                ElevatorCommands.goTo(elevator, ElevatorPosition.L2)
            ),

            // 5. Score
            Commands.waitUntil(elevator::isAtTarget),
            IntakeCommands.eject(intake).withTimeout(0.5),

            // 6. Return elevator to stowed
            ElevatorCommands.goTo(elevator, ElevatorPosition.STOWED)

        ).unless(() -> !elevator.isHealthy());
    }
}
