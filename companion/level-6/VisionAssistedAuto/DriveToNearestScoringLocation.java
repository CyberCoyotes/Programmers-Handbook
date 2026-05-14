// Level 6 capstone — field-relative scoring command.
// See: docs/source/guide-to-java-level-6.md — Part 5: Field-Relative Scoring Commands

package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DrivetrainSubsystem;

import java.util.Comparator;
import java.util.List;

public class DriveToNearestScoringLocation extends Command {

    private static final List<Pose2d> REEF_BRANCHES = List.of(
        new Pose2d(3.0, 4.1, Rotation2d.fromDegrees(180)),
        new Pose2d(3.0, 3.5, Rotation2d.fromDegrees(180)),
        new Pose2d(3.5, 3.2, Rotation2d.fromDegrees(150))
        // Add all reef branch poses for your field layout
    );

    private final DrivetrainSubsystem drivetrain;
    private Pose2d target;

    private final ProfiledPIDController xController =
        new ProfiledPIDController(5, 0, 0, new TrapezoidProfile.Constraints(3.0, 4.0));
    private final ProfiledPIDController yController =
        new ProfiledPIDController(5, 0, 0, new TrapezoidProfile.Constraints(3.0, 4.0));
    private final ProfiledPIDController rotController =
        new ProfiledPIDController(4, 0, 0, new TrapezoidProfile.Constraints(Math.PI, Math.PI * 2));

    public DriveToNearestScoringLocation(DrivetrainSubsystem drivetrain) {
        this.drivetrain = drivetrain;
        rotController.enableContinuousInput(-Math.PI, Math.PI);
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        Pose2d current = drivetrain.getPose();
        target = REEF_BRANCHES.stream()
            .min(Comparator.comparingDouble(p -> p.getTranslation()
                .getDistance(current.getTranslation())))
            .orElseThrow();

        xController.reset(current.getX());
        yController.reset(current.getY());
        rotController.reset(current.getRotation().getRadians());
    }

    @Override
    public void execute() {
        Pose2d current = drivetrain.getPose();
        drivetrain.driveFieldRelative(
            xController.calculate(current.getX(), target.getX()),
            yController.calculate(current.getY(), target.getY()),
            rotController.calculate(current.getRotation().getRadians(),
                                    target.getRotation().getRadians())
        );
    }

    @Override
    public boolean isFinished() {
        return xController.atGoal() && yController.atGoal() && rotController.atGoal();
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}
