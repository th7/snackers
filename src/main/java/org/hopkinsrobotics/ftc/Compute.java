package org.hopkinsrobotics.ftc;

public class Compute {
  public static final int armUpPosition = 500;
  public static final int armDownPosition = 0;
  public static final int armSlowThreshhold = 50;

  public static final float armFast = 0.3f;
  public static final float armSlow = 0.1f;

  public static Memory memory = new Memory();

  public static Output compute(Input input) {
    Output finalOutput = new Output();

    finalOutput.armMotorPower = arm(input.dPadUp, input.dPadDown, input.cross, input.triangle, input.armPosition);
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

  private static float arm(boolean dPadUp, boolean dPadDown, boolean cross, boolean triangle, int armPosition) {
    if (dPadUp) {
      memory.autoMoveArm = false;
      return 0.25f;
    }

    if (dPadDown) {
      memory.autoMoveArm = false;
      return -0.25f;
    }

    if (triangle) {
      memory.autoMoveArm = true;
      memory.targetArmPosition = armUpPosition;
    }

    if (cross) {
      memory.autoMoveArm = true;
      memory.targetArmPosition = armDownPosition;
    }

    if (!memory.autoMoveArm) {
      return 0f;
    }

    if (armPosition < memory.targetArmPosition && memory.targetArmPosition - armPosition <= armSlowThreshhold) {
      return armSlow;
    }

    if (armPosition > memory.targetArmPosition && armPosition - memory.targetArmPosition <= armSlowThreshhold) {
      return -armSlow;
    }

    if (armPosition < memory.targetArmPosition) {
      return armFast;
    }

    if (armPosition > memory.targetArmPosition) {
      return -armFast;
    }

    return 0f;
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

  // private static float logistic(float offset) {
  //   float e = 2.71828f; // should be constant
  //   float k = -0.1f; // adjust to tune steepness of curve
  //   return 2 / (1 + Math.pow(e, k * offset);
  // }
}
