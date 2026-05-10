// Level 2 capstone — FTC SDK, LinearOpMode pattern.
// See: docs/source/guide-to-java-level-2.md — Capstone: IntakeSubsystem

package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeSubsystem {

    // The three modes this intake can be in.
    // An enum keeps states explicit — no more magic numbers or stray booleans.
    public enum IntakeState {
        INTAKING,
        HOLDING,   // slow reverse keeps the game piece from falling out
        STOPPED
    }

    private final DcMotor motor;
    private IntakeState state = IntakeState.STOPPED;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, "intake");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Methods describe robot intent, not hardware action
    public void intake() { state = IntakeState.INTAKING; }
    public void hold()   { state = IntakeState.HOLDING;  }
    public void stop()   { state = IntakeState.STOPPED;  }

    public IntakeState getState() { return state; }

    // Call this once per loop to apply the current state to the hardware
    public void update() {
        switch (state) {
            case INTAKING: motor.setPower(1.0);    break;
            case HOLDING:  motor.setPower(-0.15);  break;
            case STOPPED:
            default:       motor.setPower(0);      break;
        }
    }

    public void addTelemetry(Telemetry telemetry) {
        telemetry.addData("Intake state", state);
        telemetry.addData("Intake power", motor.getPower());
    }
}
