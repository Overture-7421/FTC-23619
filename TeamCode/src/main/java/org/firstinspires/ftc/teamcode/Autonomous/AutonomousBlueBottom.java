package org.firstinspires.ftc.teamcode.Autonomous;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.trajectory.Trajectory;
import com.arcrobotics.ftclib.trajectory.TrajectoryConfig;
import com.arcrobotics.ftclib.trajectory.TrajectoryGenerator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousCommands.Drop_pixels;
import org.firstinspires.ftc.teamcode.AutonomousCommands.SpitPixels;
import org.firstinspires.ftc.teamcode.Commands.RamseteCommand;
import org.firstinspires.ftc.teamcode.Commands.TurnToAngle;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Band;
import org.firstinspires.ftc.teamcode.Subsystems.Chassis;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Elevator;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

import java.util.Arrays;

@Autonomous
public class AutonomousBlueBottom extends LinearOpMode {
    Chassis chassis;
    Band band;
    Intake intake;
    Arm arm;
    Claw claw;
    Elevator elevator;

    @Override
    public void runOpMode() throws InterruptedException {
        chassis = new Chassis(hardwareMap);
        band = new Band(hardwareMap);
        intake = new Intake(hardwareMap);
        arm = new Arm(hardwareMap);
        claw = new Claw(hardwareMap);

        Trajectory blueBottom = TrajectoryGenerator.generateTrajectory(Arrays.asList(
                        new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
                        new Pose2d(1,0, Rotation2d.fromDegrees(0)),
                        new Pose2d(1.6, 1.4, Rotation2d.fromDegrees(90)),
                        new Pose2d(0.8,2.0, Rotation2d.fromDegrees(0)),
                        new Pose2d(1,1, Rotation2d.fromDegrees(90))),
                        new TrajectoryConfig(1, 0.8));

        SequentialCommandGroup testCommandGroup = new SequentialCommandGroup(
                 new RamseteCommand(chassis, blueBottom)
                 //new SpitPixels(band, intake).withTimeout(3000)
        );

        waitForStart();

        chassis.resetPose(blueBottom.getInitialPose());

        CommandScheduler.getInstance().schedule(testCommandGroup);

        while (opModeIsActive ()){
            CommandScheduler.getInstance().run();

            Pose2d pose = chassis.getPose();

            telemetry.addData("X", pose.getX());
            telemetry.addData("Y", pose.getY());
            telemetry.addData("Heading", pose.getRotation().getDegrees());
            telemetry.update();

        }
    }
}
