package org.hopkinsrobotics.ftc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hopkinsrobotics.ftc.Input;
import org.hopkinsrobotics.ftc.Output;
import org.hopkinsrobotics.ftc.Compute;

public class ComputeTest {
  Input input = new Input();
  Output output;

  public void compute() { output = Compute.compute(input); }

  @Test
  public void defaults() {
    compute();
    assertEquals(0f, output.frontLeftPower);
    assertEquals(0f, output.frontRightPower);
    assertEquals(0f, output.rearLeftPower);
    assertEquals(0f, output.rearRightPower);
  }

  @Test
  public void straightForward() {
    input.gameStickLeftY = -0.5f;
    compute();
    assertEquals(1f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }

  @Test
  public void straightBackward() {
    input.gameStickLeftY = 0.5f;
    compute();
    assertEquals(-1f, output.frontLeftPower);
    assertEquals(-1f, output.frontRightPower);
    assertEquals(-1f, output.rearLeftPower);
    assertEquals(-1f, output.rearRightPower);
  }

  @Test
  public void leftTurn() {
    input.gameStickRightX = -0.5f; // this may be reversed, not sure
    compute();
    assertEquals(-1f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(-1f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }

  @Test
  public void rightTurn() {
    input.gameStickRightX = 0.5f; // this may be reversed, not sure
    compute();
    assertEquals(1f, output.frontLeftPower);
    assertEquals(-1f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(-1f, output.rearRightPower);
  }

  @Test
  public void strafeLeft() {
    input.gameStickLeftX = -0.5f; // this may be reversed, not sure
    compute();
    assertEquals(1f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(-1f, output.rearLeftPower);
    assertEquals(-1f, output.rearRightPower);
  }

  @Test
  public void strafeRight() {
    input.gameStickLeftX = 0.5f; // this may be reversed, not sure
    compute();
    assertEquals(-1f, output.frontLeftPower);
    assertEquals(-1f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }
}
