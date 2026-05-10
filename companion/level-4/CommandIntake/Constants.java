// Level 4 capstone — FRC command-based with CTRE hardware.
// See: docs/source/guide-to-java-level-4.md — Capstone: Command-Based Intake (CTRE Hardware)

package frc.robot;

public final class Constants {

    public static final class IntakeConstants {
        public static final int MOTOR_ID    = 1;    // verify CAN ID in Phoenix Tuner X
        public static final double RUN_SPEED = 0.8;
    }

    public static final class OperatorConstants {
        public static final int CONTROLLER_PORT = 0;
    }
}
