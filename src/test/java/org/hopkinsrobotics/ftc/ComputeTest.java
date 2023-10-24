package org.hopkinsrobotics.ftc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hopkinsrobotics.ftc.Input;
import org.hopkinsrobotics.ftc.Output;
import org.hopkinsrobotics.ftc.Compute;
import org.hopkinsrobotics.ftc.Memory;

public class ComputeTest {
  Input input = new Input();
  Output output;

  public void compute() { output = Compute.compute(input); }

  @BeforeEach
  public void beforeEach() {
    Compute.memory = new Memory();
  }

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
    input.gameStickLeftY = -1f;
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
    assertEquals(0.01f, output.frontLeftPower);
    assertEquals(0.01f, output.frontRightPower);
    assertEquals(0.01f, output.rearLeftPower);
    assertEquals(0.01f, output.rearRightPower);
  }

  @Test
  public void fullBackward() {
    input.gameStickLeftY = 1f;
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
    assertEquals(-0.01f, output.frontLeftPower);
    assertEquals(-0.01f, output.frontRightPower);
    assertEquals(-0.01f, output.rearLeftPower);
    assertEquals(-0.01f, output.rearRightPower);
  }

  @Test
  public void leftTurn() {
    input.gameStickRightX = -1f;
    compute();
    assertEquals(-1f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(-1f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }

  @Test
  public void rightTurn() {
    input.gameStickRightX = 1f;
    compute();
    assertEquals(1f, output.frontLeftPower);
    assertEquals(-1f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(-1f, output.rearRightPower);
  }

  @Test
  public void forwardLeftTurn() {
    input.gameStickLeftY = -1f;
    input.gameStickRightX = -1f;
    compute();
    assertEquals(0f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(0f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }

  @Test
  public void forwardRightTurn() {
    input.gameStickLeftY = -1f;
    input.gameStickRightX = 1f;
    compute();
    assertEquals(1f, output.frontLeftPower);
    assertEquals(0f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(0f, output.frontRightPower);
  }

  @Test
  public void strafeLeft() {
    input.gameStickLeftX = -1f;
    compute();
    assertEquals(1f, output.frontLeftPower);
    assertEquals(-1f, output.frontRightPower);
    assertEquals(-1f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }

  @Test
  public void strafeRight() {
    input.gameStickLeftX = 1f;
    compute();
    assertEquals(-1f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(-1f, output.rearRightPower);
  }

  @Test
  public void forwardStrafeRightTurnLeft() {
    input.gameStickLeftY = -1f;
    input.gameStickLeftX = 1f;
    input.gameStickRightX = -1f;
    compute();
    assertEquals(-1f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }

  @Test
  public void armUp() {
    Compute.memory.autoMoveArm = true;
    input.dPadUp = true;
    compute();
    assertEquals(false, Compute.memory.autoMoveArm);
    assertEquals(0.25f, output.armMotorPower);
  }

  @Test
  public void armDown() {
    Compute.memory.autoMoveArm = true;
    input.dPadDown = true;
    compute();
    assertEquals(false, Compute.memory.autoMoveArm);
    assertEquals(-0.25f, output.armMotorPower);
  }

  @Test
  public void armUpAutoOffFast() {
    Compute.memory.targetArmPosition = Compute.armUpPosition;
    input.armPosition = Compute.armUpPosition - Compute.armSlowThreshhold - 1;
    Compute.memory.autoMoveArm = false;
    compute();
    assertEquals(0f, output.armMotorPower);
  }

  @Test
  public void armUpAutoOffSlow() {
    Compute.memory.targetArmPosition = Compute.armUpPosition;
    input.armPosition = Compute.armUpPosition - Compute.armSlowThreshhold;
    Compute.memory.autoMoveArm = false;
    compute();
    assertEquals(0f, output.armMotorPower);
  }

  @Test
  public void armDownAutoOffFast() {
    Compute.memory.targetArmPosition = Compute.armDownPosition;
    input.armPosition = Compute.armDownPosition + Compute.armSlowThreshhold + 1;
    Compute.memory.autoMoveArm = false;
    compute();
    assertEquals(0f, output.armMotorPower);
  }

  @Test
  public void armDownAutoOffSlow() {
    Compute.memory.targetArmPosition = Compute.armDownPosition;
    input.armPosition = Compute.armDownPosition + Compute.armSlowThreshhold;
    Compute.memory.autoMoveArm = false;
    compute();
    assertEquals(0f, output.armMotorPower);
  }

  @Test
  public void setArmUp() {
    input.triangle = true;
    compute();
    assertEquals(true, Compute.memory.autoMoveArm);
    assertEquals(Compute.armUpPosition, Compute.memory.targetArmPosition);
  }

  @Test
  public void armUpFast() {
    Compute.memory.targetArmPosition = Compute.armUpPosition;
    input.armPosition = Compute.armUpPosition - Compute.armSlowThreshhold - 1;
    Compute.memory.autoMoveArm = true;
    compute();
    assertEquals(Compute.armFast, output.armMotorPower);
  }

  @Test
  public void armUpSlow() {
    Compute.memory.targetArmPosition = Compute.armUpPosition;
    input.armPosition = Compute.armUpPosition - Compute.armSlowThreshhold;
    Compute.memory.autoMoveArm = true;
    compute();
    assertEquals(Compute.armSlow, output.armMotorPower);
  }

  @Test
  public void setArmDown() {
    input.cross = true;
    compute();
    assertEquals(true, Compute.memory.autoMoveArm);
    assertEquals(Compute.armDownPosition, Compute.memory.targetArmPosition);
  }

  @Test
  public void armDownFast() {
    Compute.memory.targetArmPosition = Compute.armDownPosition;
    input.armPosition = Compute.armDownPosition + Compute.armSlowThreshhold + 1;
    Compute.memory.autoMoveArm = true;
    compute();
    assertEquals(-Compute.armFast, output.armMotorPower);
  }

  @Test
  public void armDownSlow() {
    Compute.memory.targetArmPosition = Compute.armDownPosition;
    input.armPosition = Compute.armDownPosition + Compute.armSlowThreshhold;
    Compute.memory.autoMoveArm = true;
    compute();
    assertEquals(-Compute.armSlow, output.armMotorPower);
  }

  @Test
  public void winchUp() {
    input.dPadRight = true;
    compute();
    assertEquals(0.25f, output.winchMotorPower);
  }

  @Test
  public void setAutoMoveForward() {
    input.rightTrigger = 0.1f;
    input.wheelPosition = 123;
    compute();
    assertEquals(323, Compute.memory.targetMovePosition);
  }
}
