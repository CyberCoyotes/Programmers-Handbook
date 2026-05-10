// Level 6 capstone — vision-assisted two-piece autonomous routine.
// See: docs/source/guide-to-java-level-6.md — Capstone: Vision-Assisted Two-Piece Auto

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionPoseUpdater extends SubsystemBase {

    private final DrivetrainSubsystem drivetrain;
    private final VisionSubsystem vision;

    public VisionPoseUpdater(DrivetrainSubsystem drivetrain, VisionSubsystem vision) {
        this.drivetrain = drivetrain;
        this.vision = vision;
    }

    @Override
    public void periodic() {
        // Provide current heading to MegaTag2 solver
        vision.enableMegaTag2(drivetrain.getGyroAngle().getDegrees());

        var estimate = vision.getLatestEstimate();
        if (drivetrain.isValidEstimate(estimate)) {
            drivetrain.addVisionMeasurement(
                estimate.pose,
                estimate.timestampSeconds,
                drivetrain.getVisionStdDevs(estimate)
            );
        }
    }
}
