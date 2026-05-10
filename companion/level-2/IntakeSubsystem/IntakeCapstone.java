// Level 2 capstone — OpMode that uses IntakeSubsystem.
// See: docs/source/guide-to-java-level-2.md — Capstone: IntakeSubsystem

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

@TeleOp(name = "Intake Capstone")
public class IntakeCapstone extends LinearOpMode {

    private IntakeSubsystem intake;

    @Override
    public void runOpMode() {
        intake = new IntakeSubsystem(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Driver input sets the desired state
            if (gamepad1.a) {
                intake.intake();
            } else if (gamepad1.b) {
                intake.hold();
            } else if (gamepad1.x) {
                intake.stop();
            }

            // update() applies whatever state was last set — even if no button is pressed now
            intake.update();
            intake.addTelemetry(telemetry);
            telemetry.update();
        }
    }
}
