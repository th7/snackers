package org.hopkinsrobotics.ftc;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp2023", group="Linear Opmode")
@Disabled
public class TeleOp extends LinearOpMode {
  private ElapsedTime runtime = new ElapsedTime();
  private DcMotor leftFront = null;
  private DcMotor rightFront = null;
  private DcMotor LeftBack = null;
  private DcMotor rightBack = null;
  private DcMotor armMotor1 = null;
  private DcMotor armMotor2 = null;

  @Override
  public void runOpMode() {

    leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
    rightFront = hardwareMap.get(DcMotor.class, "rightFront");
    leftBack = hardwareMap.get(DcMotor.class, "leftBack");
    rightBack = hardwareMap.get(DcMotor.class, "rightBack");
    armMotor1 = hardwareMap.get(DcMotor.class, "armMotor1");
    armMotor2 = hardwareMap.get(DcMotor.class, "armMotor2");

    telemetry.addData("Status", "Initialized");
    telemetry.update();
    waitForStart();
    runtime.reset();

    while (opModeIsActive()) {
      Input input = new Input();

      input.gameStickLeftX = gamepad1.left_stick_x;
      input.gameStickLeftY = gamepad1.left_stick_y;
      input.gameStickRightX = gamepad1.right_stick_x;
      input.gameStickRightY = gamepad1.right_stick_y;

      input.dPadUp = gamepad1.dpad_up;
      input.dPadDown = gamepad1.dpad_down;
      input.triangle = gamepad1.triangle;
      input.cross = gamepad1.cross;

      input.armPosition = armMotor1.getCurrentPosition();

      Output output = Compute.compute(input);

      leftFront.setPower(output.frontLeftPower);
      rightFront.setPower(output.frontRightPower);
      leftBack.setPower(output.rearLeftPower);
      rightBack.setPower(output.rearRightPower);

      armMotor1.setPower(output.armMotorPower);
      armMotor2.setPower(output.armMotorPower);
    }
  }
}