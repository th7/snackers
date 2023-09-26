package org.hopkinsrobotics.ftc;

public class Compute {
  public static Output compute(Input input) {
    Output turnOutput = turnOutput(input.gameStickRightX);
    Output moveOutput = moveOutput(input.gameStickLeftY);
    Output strafeOutput = strafeOutput(input.gameStickLeftX);
    Output finalOutput = new Output();

    finalOutput.frontLeftPower = clip(turnOutput.frontLeftPower + moveOutput.frontLeftPower + strafeOutput.frontLeftPower);
    finalOutput.frontRightPower = clip(turnOutput.frontRightPower + moveOutput.frontRightPower + strafeOutput.frontRightPower);
    finalOutput.rearLeftPower = clip(turnOutput.rearLeftPower + moveOutput.rearLeftPower + strafeOutput.rearLeftPower);
    finalOutput.rearRightPower = clip(turnOutput.rearRightPower + moveOutput.rearRightPower + strafeOutput.rearRightPower);
    return finalOutput;
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
    output.frontRightPower = -gameStickLeftX;
    output.rearLeftPower = gameStickLeftX;
    output.rearRightPower = gameStickLeftX;

    return output;
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
