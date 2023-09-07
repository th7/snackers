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
    // the last argument specifying an allowable delta of 0
    // may seem like nonsense, but it tricks java into agreeing
    // that 0 and -0 are in fact equal
    assertEquals(0f, output.frontLeftPower, 0);
    assertEquals(0f, output.frontRightPower, 0);
    assertEquals(0f, output.rearLeftPower, 0);
    assertEquals(0f, output.rearRightPower, 0);
  }

  @Test
  public void fullForward() {
    input.gameStickLeftY = -0.5f;
    compute();
    assertEquals(1f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }

  @Test
  public void smallForward() {
    input.gameStickLeftY = -0.01f;
    compute();
    assertEquals(0.02f, output.frontLeftPower);
    assertEquals(0.02f, output.frontRightPower);
    assertEquals(0.02f, output.rearLeftPower);
    assertEquals(0.02f, output.rearRightPower);
  }

  @Test
  public void fullBackward() {
    input.gameStickLeftY = 0.5f;
    compute();
    assertEquals(-1f, output.frontLeftPower);
    assertEquals(-1f, output.frontRightPower);
    assertEquals(-1f, output.rearLeftPower);
    assertEquals(-1f, output.rearRightPower);
  }

  @Test
  public void smallBackward() {
    input.gameStickLeftY = 0.01f;
    compute();
    assertEquals(-0.02f, output.frontLeftPower);
    assertEquals(-0.02f, output.frontRightPower);
    assertEquals(-0.02f, output.rearLeftPower);
    assertEquals(-0.02f, output.rearRightPower);
  }

  // @Test
  // public void leftTurn() {
  //   input.gameStickRightX = -0.5f; // this may be reversed, not sure
  //   compute();
  //   assertEquals(-1f, output.frontLeftPower);
  //   assertEquals(1f, output.frontRightPower);
  //   assertEquals(-1f, output.rearLeftPower);
  //   assertEquals(1f, output.rearRightPower);
  // }

  // @Test
  // public void rightTurn() {
  //   input.gameStickRightX = 0.5f; // this may be reversed, not sure
  //   compute();
  //   assertEquals(1f, output.frontLeftPower);
  //   assertEquals(-1f, output.frontRightPower);
  //   assertEquals(1f, output.rearLeftPower);
  //   assertEquals(-1f, output.rearRightPower);
  // }

  // @Test
  // public void strafeLeft() {
  //   input.gameStickLeftX = -0.5f; // this may be reversed, not sure
  //   compute();
  //   assertEquals(1f, output.frontLeftPower);
  //   assertEquals(1f, output.frontRightPower);
  //   assertEquals(-1f, output.rearLeftPower);
  //   assertEquals(-1f, output.rearRightPower);
  // }

  // @Test
  // public void strafeRight() {
  //   input.gameStickLeftX = 0.5f; // this may be reversed, not sure
  //   compute();
  //   assertEquals(-1f, output.frontLeftPower);
  //   assertEquals(-1f, output.frontRightPower);
  //   assertEquals(1f, output.rearLeftPower);
  //   assertEquals(1f, output.rearRightPower);
  // }
}
