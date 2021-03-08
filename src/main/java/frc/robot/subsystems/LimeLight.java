// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimeLight extends SubsystemBase {
  public double x = 0, y = 0;
  public boolean hasTarget = false;
  public boolean lightOn = false;

  private LimeLight() {
    setDouble(LED_MODE, LEDState.ForceOff);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setDouble(String entry, double value) {
    getTable().getEntry(entry).setDouble(value);
  }

  public NetworkTable getTable() {
    return NetworkTableInstance.getDefault().getTable("limelight");
  }

  public void setDouble(String entry, LimeLightValue value) {
    getTable().getEntry(entry).setDouble(value.getValue());
  }

  public double getDouble(String entry, double defaultValue) {
    return getTable().getEntry(entry).getDouble(defaultValue);
  }

  public interface LimeLightValue {
    public int getValue();
  }

  public void getDat() {
    x = getTable().getEntry(TARGET_X).getDouble(0);
    y = getTable().getEntry(TARGET_Y).getDouble(0);
    hasTarget = getTable().getEntry(TARGET_VISIBLE).getDouble(0) == 1 ? true : false;

    SmartDashboard.putBoolean("Limelight/Is Valid", hasTarget);
    SmartDashboard.putNumber("Limelight/Target X", x);
    SmartDashboard.putNumber("Limelight/Target Y", y);
  }

  public int getLED() {
    return (int) getTable().getEntry(LED_MODE).getDouble(0);
  }

  public void setLED(LEDState state) {
    setLED((double)state.value);
  }

  public void setLED(double a) {
    getTable().getEntry(LED_MODE).setDouble(a);
  }

  public enum CameraState implements LimeLightValue {
    VisionProccessing(0), DriverStation(1);

    int value;

    CameraState(int value) {
      this.value = value;
    }

    @Override
    public int getValue() {
      return value;
    }
  }

  public enum LEDState implements LimeLightValue {
    PipelinePreference(0), ForceOff(1), ForceBlink(2), ForceOn(3);

    int value;

    LEDState(int value) {
      this.value = value;
    }

    @Override
    public int getValue() {
      return value;
    }
  }

  public enum CaptureMode implements LimeLightValue {
    StopTakingSnapshots(0), TakeSnapshots(1);

    int value;

    CaptureMode(int value) {
      this.value = value;
    }

    @Override
    public int getValue() {
      return value;
    }
  }

  public static final String
    LED_MODE = "ledMode",
    CAMERA_MODE = "camMode",
    CAPTURE_MODE = "snapshot",
    STREAM_SELECTION = "stream",
    TARGET_X = "tx",
    TARGET_Y = "ty",
    TARGET_AREA = "ta",
    TARGET_VISIBLE = "tv";

  private static LimeLight instance;
  public static LimeLight getInstance() { if (instance == null) instance = new LimeLight(); return instance; }

}