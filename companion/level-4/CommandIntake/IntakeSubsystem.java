// Level 4 capstone — FRC command-based with CTRE hardware.
// See: docs/source/guide-to-java-level-4.md — Capstone: Command-Based Intake (CTRE Hardware)

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.IntakeConstants.*;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX motor = new TalonFX(MOTOR_ID);

    // Reuse one control request object — avoids allocating garbage every loop
    private final DutyCycleOut dutyCycle = new DutyCycleOut(0);

    public void run() {
        motor.setControl(dutyCycle.withOutput(RUN_SPEED));
    }

    public void stop() {
        motor.setControl(dutyCycle.withOutput(0));
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Intake/CurrentAmps",
            motor.getSupplyCurrent().getValueAsDouble());
    }
}
