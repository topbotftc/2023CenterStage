package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.Robot.initMotors;
import static org.firstinspires.ftc.teamcode.Robot.preselect;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "Forward", preselectTeleOp = preselect)
public class Park extends LinearOpMode{
    final int START_X = -36;
    final int START_Y = 64;

    final Pose2d startPose = new Pose2d(START_X, START_Y);

    @Override
    public void runOpMode() throws InterruptedException {


        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);





////////Program start////////////////////////////////////////////////////////////////////////

        waitForStart();
        ////Move on start/init

        Trajectory park = drive.trajectoryBuilder(startPose) //moves bot forward from start and turns
                .forward(35)
                .build();


        drive.followTrajectory(park);



    }
}