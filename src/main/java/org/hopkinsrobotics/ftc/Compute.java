package org.hopkinsrobotics.ftc;

public class Compute {
  public static Output compute(Input input) {
    Output finalOutput = new Output();

    arm(input.dPadUp, input.dPadDown, input.cross, input.triangle, finalOutput);
    drive(input.gameStickRightX, input.gameStickLeftY, input.gameStickLeftX, finalOutput);

    return finalOutput;
  }

  private static void drive(float gameStickRightX, float gameStickLeftY, float gameStickLeftX, Output output) {
    Output turnOutput = turnOutput(gameStickRightX);
    Output moveOutput = moveOutput(gameStickLeftY);
    Output strafeOutput = strafeOutput(gameStickLeftX);

    output.frontLeftPower = clip(turnOutput.frontLeftPower + moveOutput.frontLeftPower + strafeOutput.frontLeftPower);
    output.frontRightPower = clip(turnOutput.frontRightPower + moveOutput.frontRightPower + strafeOutput.frontRightPower);
    output.rearLeftPower = clip(turnOutput.rearLeftPower + moveOutput.rearLeftPower + strafeOutput.rearLeftPower);
    output.rearRightPower = clip(turnOutput.rearRightPower + moveOutput.rearRightPower + strafeOutput.rearRightPower);
  }

  private static Output turnOutput(float gameStickRightX) {
    Output output = new Output();
    output.frontLeftPower = gameStickRightX;
    output.frontRightPower = -gameStickRightX;
    output.rearLeftPower = gameStickRightX;
    output.rearRightPower = -gameStickRightX;
    return output;
  }

  private static Output moveOutput(float gameStickLeftY) {
    Output output = new Output();
    output.frontLeftPower = -gameStickLeftY;
    output.frontRightPower = -gameStickLeftY;
    output.rearLeftPower = -gameStickLeftY;
    output.rearRightPower = -gameStickLeftY;

   return output;
  }

  private static Output strafeOutput(float gameStickLeftX) {
    Output output = new Output();
    output.frontLeftPower = -gameStickLeftX;
    output.frontRightPower = gameStickLeftX;
    output.rearLeftPower = gameStickLeftX;
    output.rearRightPower = -gameStickLeftX;

    return output;
  }

  private static void arm(boolean dPadUp, boolean dPadDown, boolean cross, boolean triangle, Output output) {
    if (dPadUp) {
      output.armMotorPower = 0.25f;
    }

    if (dPadDown) {
      output.armMotorPower = -0.25f;
    }

    if (cross) {
      output.setArmMotorPosition = true;
      output.armMotorPosition = 0;
    }

    if (triangle) {
      output.setArmMotorPosition = true;
      output.armMotorPosition = 500;
    }
  }

  private static float clip(float unclipped) {
    if (unclipped < -1) {
      return -1f;
    }

    if (unclipped > 1) {
      return 1f;
    }

    return unclipped;
  }
}
