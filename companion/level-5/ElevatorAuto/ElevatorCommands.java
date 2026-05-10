// Level 5 capstone — elevator + intake autonomous routine.
// See: docs/source/guide-to-java-level-5.md — Capstone: Elevator + Intake Autonomous Routine

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.ElevatorPosition;

public final class ElevatorCommands {

    private ElevatorCommands() {}

    public static Command goTo(ElevatorSubsystem elevator, ElevatorPosition target) {
        return Commands.sequence(
            Commands.runOnce(() -> elevator.goTo(target), elevator),
            Commands.waitUntil(elevator::isAtTarget)
        ).withName("Elevator→" + target.name());
    }

    public static Command idle(ElevatorSubsystem elevator) {
        return Commands.run(() -> {}, elevator).withName("ElevatorIdle");
    }
}
