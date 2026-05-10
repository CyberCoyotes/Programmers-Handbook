// Level 3 capstone — multi-subsystem coordination, FTC SDK.
// See: docs/source/guide-to-java-level-3.md — Capstone: Intake + Arm Coordination

package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmSubsystem {

    public enum Position { STOWED, INTAKE, SCORE }

    private final DcMotor motor;
    private Position target = Position.STOWED;
    private boolean locked = false;  // true when movement is blocked by coordination rules

    public ArmSubsystem(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, "arm");
    }

    // Requests a position change — silently ignored when locked
    public void goTo(Position position) {
        if (!locked) target = position;
    }

    public void setLocked(boolean locked) { this.locked = locked; }
    public Position getTarget()           { return target; }
    public boolean isLocked()             { return locked; }

    public void update() {
        // Simplified: a real robot would use encoder-based position control here
        switch (target) {
            case SCORE:  motor.setPower(-0.4); break;
            case INTAKE: motor.setPower(0.4);  break;
            case STOWED:
            default:     motor.setPower(0);    break;
        }
    }
}
