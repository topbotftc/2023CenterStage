package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.Robot.*;
import static org.firstinspires.ftc.teamcode.Robot.initMotors;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Blue Far side", preselectTeleOp = preselect)
public class BlueFarSide extends LinearOpMode{

    OpenCvCamera webcam;
    final double START_X = -36;
    final double START_Y = 64;

    int location = 1;

    final double heading = Math.toRadians(270);

    Pose2d startPose = new Pose2d(START_X, START_Y, heading);



    @Override
    public void runOpMode() throws InterruptedException {


        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(startPose);

        initAccessories(this);


////////CAMERA Stuff//////////////////////////////////////////////
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam"), cameraMonitorViewId);


        ConeVisionPipeline pipeline = new ConeVisionPipeline(telemetry);
        webcam.setPipeline(pipeline);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming( 320, 240, OpenCvCameraRotation.UPRIGHT); //320, 240
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("ERROR",errorCode);
                telemetry.update();
            }
        });
////ifjack is pogers then awseom/////

////////Program start////////////////////////////////////////////////////////////////////////
        initMotors(this);
        initAccessories(this);

        waitForStart();
        ////Move on start/init

        grabber.setPosition(grabberClosed);
        sleep(1000);
        runSlide(10);
        rotatorCuff.setPosition(rotatorCuffAvoid);
        sleep(500);

        location = pipeline.getSide().ordinal() + 1;

        switch(location){
            case 1:
                Trajectory toMiddle = drive.trajectoryBuilder(startPose)
                    .lineTo(new Vector2d(-38, 41))
                    .build();


                Trajectory forward = drive.trajectoryBuilder(toMiddle.end())
                                .forward(7)
                                .build();

                Trajectory strafeRight = drive.trajectoryBuilder(forward.end())
                        .strafeRight(4)
                        .build();

                Trajectory backUp = drive.trajectoryBuilder(forward.end())
                                .back(12)
                                .build();

                Trajectory hitWall = drive.trajectoryBuilder(backUp.end())
                                .strafeLeft(35)
                                        .build();

                Trajectory hitWallAgain = drive.trajectoryBuilder(hitWall.end())
                                .strafeLeft(14)
                                        .build();

                Trajectory offWall = drive.trajectoryBuilder(hitWallAgain.end())
                                .strafeRight(4.5)
                                        .build();


                drive.followTrajectory(toMiddle);
                drive.turn(Math.toRadians(102.5));
                drive.followTrajectory(forward);
                drive.followTrajectory(strafeRight);
                drive.followTrajectory(backUp);
                drive.followTrajectory(hitWall);
                drive.followTrajectory(hitWallAgain);
                drive.followTrajectory(offWall);
//                drive.turn(Math.toRadians(-2));

                break;

            case 2:

                Trajectory toMiddle2 = drive.trajectoryBuilder(startPose)
                        .lineTo(new Vector2d(-36, 44.5))
                        .build();


                Trajectory forward2 = drive.trajectoryBuilder(toMiddle2.end())
                        .forward(14)
                        .build();

                Trajectory backUp2 = drive.trajectoryBuilder(forward2.end())
                        .back(20)
                        .build();
                Trajectory strafeRight2 = drive.trajectoryBuilder(backUp2.end())
                        .strafeRight(6)
                        .build();
                Trajectory hitWall2 = drive.trajectoryBuilder(backUp2.end())
                        .strafeLeft(20)
                        .build();

                Trajectory hitWallAgain2 = drive.trajectoryBuilder(hitWall2.end())
                        .strafeLeft(10)
                        .build();

                Trajectory offWall2 = drive.trajectoryBuilder(hitWallAgain2.end())
                        .strafeRight(4.5)
                        .build();


                drive.followTrajectory(toMiddle2);
                drive.followTrajectory(forward2);
                drive.followTrajectory(strafeRight2);
                drive.followTrajectory(backUp2);
                drive.turn(Math.toRadians(100));
                drive.followTrajectory(hitWall2);
                drive.followTrajectory(hitWallAgain2);
                drive.followTrajectory(offWall2);
//                drive.turn(Math.toRadians(-4));


                break;
            case 3:
                Trajectory toMiddle3 = drive.trajectoryBuilder(startPose)
                        .lineTo(new Vector2d(-36, 42))
                        .build();


                Trajectory forward3 = drive.trajectoryBuilder(toMiddle3.end())
                        .forward(12)
                        .build();

                Trajectory backUp3 = drive.trajectoryBuilder(forward3.end())
                        .back(20)
                        .build();
                Trajectory strafeRight3 = drive.trajectoryBuilder(backUp3.end())
                        .strafeRight(22)
                        .build();
                Trajectory resetX = drive.trajectoryBuilder(strafeRight3.end())
                        .forward(12)
                        .build();
                Trajectory hitWall3 = drive.trajectoryBuilder(backUp3.end())
                        .strafeLeft(15)
                        .build();

                Trajectory hitWallAgain3 = drive.trajectoryBuilder(hitWall3.end())
                        .strafeLeft(12)
                        .build();

                Trajectory offWall3 = drive.trajectoryBuilder(hitWallAgain3.end())
                        .strafeRight(4.5)
                        .build();


                drive.followTrajectory(toMiddle3);
                drive.followTrajectory(strafeRight3);
                drive.followTrajectory(backUp3);
                drive.turn(Math.toRadians(105));

                drive.followTrajectory(resetX);

                drive.followTrajectory(hitWall3);
                drive.followTrajectory(hitWallAgain3);
                drive.followTrajectory(offWall3);

//                drive.turn(Math.toRadians(-4));



                break;

        }

        Trajectory acrossField = drive.trajectoryBuilder(new Pose2d(-12, 64, Math.toRadians(0)))
                .forward(70)
                .build();

        Trajectory toBoard;
        switch(location){
            case 1:
                toBoard = drive.trajectoryBuilder(acrossField.end())
                        .strafeRight(33)
                        .build();
                break;
            case 2:
                toBoard = drive.trajectoryBuilder(acrossField.end())
                        .strafeRight(40)
                        .build();
                break;
            default:
                toBoard = drive.trajectoryBuilder(acrossField.end())
                        .strafeRight(45)
                        .build();
                break;

        }

        Trajectory closerToBoard2 = drive.trajectoryBuilder(toBoard.end())
                .forward(12)
                .build();

        Trajectory closerAgain = drive.trajectoryBuilder(closerToBoard2.end())
                .forward(7)
                .build();
        Trajectory furtherFromBoard = drive.trajectoryBuilder(closerToBoard2.end())
                .back(1)
                .build();

        Trajectory offBoard2 = drive.trajectoryBuilder(closerToBoard2.end())
                .back(5)
                .build();



        Trajectory back = drive.trajectoryBuilder(startPose)
                .back(12)
                .build();



        drive.turn(Math.toRadians(-3));
        sleep(4000);

        drive.followTrajectory(acrossField);
        drive.turn(Math.toRadians(-5.5));
        drive.followTrajectory(toBoard);

        runSlide(1600);
        sleep(1000);
        rotatorCuff.setPosition(rotatorCuffScoring);
        drive.followTrajectory(closerToBoard2);
        drive.followTrajectory(closerAgain);
        drive.followTrajectory(furtherFromBoard);

        sleep(1200);

        grabber.setPosition(grabberOpen);


        sleep(1000);

        drive.followTrajectory(offBoard2);

        runSlide(300);
        while(Math.abs(slide.getTargetPosition() - slide.getCurrentPosition()) > 50){};








//        drive.followTrajectory(strafeRight);
//        drive.followTrajectory(back);






    }
}