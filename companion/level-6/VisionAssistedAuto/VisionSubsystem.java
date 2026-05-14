// Level 6 capstone — vision-assisted two-piece autonomous routine.
// See: docs/source/guide-to-java-level-6.md — Capstone: Vision-Assisted Two-Piece Auto

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.PoseEstimate;

public class VisionSubsystem extends SubsystemBase {

    private static final String CAMERA = "limelight";
    private static final double DROPOUT_THRESHOLD_SECONDS = 0.5;

    private double lastValidTimestamp = 0;

    // Enable MegaTag2 (IMU-assisted, multi-tag). Must be called before each measurement read.
    public void enableMegaTag2(double robotYawDegrees) {
        LimelightHelpers.SetRobotOrientation(CAMERA, robotYawDegrees, 0, 0, 0, 0, 0);
    }

    public PoseEstimate getLatestEstimate() {
        return LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(CAMERA);
    }

    public boolean hasTarget() {
        return LimelightHelpers.getTV(CAMERA);
    }

    @Override
    public void periodic() {
        var estimate = getLatestEstimate();
        if (estimate != null && estimate.tagCount > 0) {
            lastValidTimestamp = Timer.getFPGATimestamp();
        }
    }

    public boolean isDroppedOut() {
        return (Timer.getFPGATimestamp() - lastValidTimestamp) > DROPOUT_THRESHOLD_SECONDS;
    }

    public double getSecondsSinceLastUpdate() {
        return Timer.getFPGATimestamp() - lastValidTimestamp;
    }
}
