// Level 3 capstone — multi-subsystem coordination, FTC SDK.
// See: docs/source/guide-to-java-level-3.md — Capstone: Intake + Arm Coordination

package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem {

    public enum State { IDLE, INTAKING, HOLDING }

    private final DcMotor motor;
    private State state = State.IDLE;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, "intake");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void startIntaking() { state = State.INTAKING; }
    public void hold()          { state = State.HOLDING;  }
    public void stop()          { state = State.IDLE;     }

    public State getState()    { return state; }
    public boolean isHolding() { return state == State.HOLDING; }

    public void update() {
        switch (state) {
            case INTAKING: motor.setPower(1.0);   break;
            case HOLDING:  motor.setPower(0.15);  break;
            case IDLE:
            default:       motor.setPower(0);     break;
        }
    }
}
