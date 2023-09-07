package org.hopkinsrobotics.ftc;

public class Compute {
  public static Output compute(Input input) {
    Output output = new Output();

    float power = input.gameStickLeftY * -2;

    output.frontLeftPower = power;
    output.frontRightPower = power;
    output.rearLeftPower = power;
    output.rearRightPower = power;

    return output;
  }
}
