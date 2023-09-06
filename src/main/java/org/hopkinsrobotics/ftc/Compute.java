package org.hopkinsrobotics.ftc;

public class Compute {
  public static Output compute(Input input) {
    Output output = new Output();

    if (input.gameStickLeftY < 0) {
      output.frontLeftPower = 1f;
      output.frontRightPower = 1f;
      output.rearLeftPower = 1f;
      output.rearRightPower = 1f;
    }

    return output;
  }
}
