// Level 3 capstone — multi-subsystem coordination, FTC SDK.
// See: docs/source/guide-to-java-level-3.md — Capstone: Intake + Arm Coordination

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp(name = "Coordinated TeleOp")
public class CoordinatedTeleOp extends LinearOpMode {

    private IntakeSubsystem intake;
    private ArmSubsystem arm;

    @Override
    public void runOpMode() {
        intake = new IntakeSubsystem(hardwareMap);
        arm    = new ArmSubsystem(hardwareMap);

        telemetry.addData("Status", "Ready");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            handleInput();
            coordinateSubsystems();

            intake.update();
            arm.update();

            telemetry.addData("Intake",     intake.getState());
            telemetry.addData("Arm target", arm.getTarget());
            telemetry.addData("Arm locked", arm.isLocked());
            telemetry.update();
        }
    }

    private void handleInput() {
        if (gamepad1.a)            intake.startIntaking();
        if (gamepad1.b)            intake.hold();
        if (gamepad1.x)            intake.stop();

        if (gamepad1.right_bumper) arm.goTo(ArmSubsystem.Position.SCORE);
        if (gamepad1.left_bumper)  arm.goTo(ArmSubsystem.Position.INTAKE);
        if (gamepad1.y)            arm.goTo(ArmSubsystem.Position.STOWED);
    }

    private void coordinateSubsystems() {
        // Rule 1: arm cannot score while intake is still running (piece not yet secured)
        arm.setLocked(intake.getState() == IntakeSubsystem.State.INTAKING);

        // Rule 2: once the driver signals the piece is held, automatically stage for scoring
        if (intake.isHolding()) {
            arm.goTo(ArmSubsystem.Position.SCORE);
        }
    }
}
