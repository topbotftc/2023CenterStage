package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.Robot.*;
import static org.firstinspires.ftc.teamcode.Robot.initMotors;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Red Far Side", preselectTeleOp = preselect)
public class RedFarSide extends LinearOpMode{

    OpenCvCamera webcam;
    final double START_X = -36;
    final double START_Y = -60;

    int location = 1;

    final double heading = Math.toRadians(90);

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
        if(location != 3) runSlide(50);

        telemetry.addData("location: ", pipeline.getSide());
        telemetry.update();



        Trajectory toMiddle;
        if(location != 1) {
             toMiddle = drive.trajectoryBuilder(startPose)
                    .lineTo(new Vector2d(-36, -38))
                    .build();
        } else {
             toMiddle = drive.trajectoryBuilder(startPose)
                    .lineTo(new Vector2d(-36, -34))
                    .build();
        }

        double forwardN = 0;
        switch (location){
            case 1:
                forwardN = 1;
                break;
            case 2:
                forwardN = 10;
                break;
            case 3:
                forwardN = 10;
                break;
            default:
                break;
        }
        Trajectory forward = drive.trajectoryBuilder(toMiddle.end())
                .forward(forwardN)
                .build();



        drive.followTrajectory(toMiddle);


        switch(location){
            case 1:
                break;
            case 3:
                drive.turn(Math.toRadians(-110));
                break;
            default:
                break;
        }
        if(location != 1) {
            drive.followTrajectory(forward);
        } else {
            drive.followTrajectory(drive.trajectoryBuilder(startPose).strafeLeft(12.5).build());
        }

        sleep(600);

//        Trajectory strafeRight = drive.trajectoryBuilder(forward.end())
//                .strafeRight(4)
//                .build();

        Trajectory back = drive.trajectoryBuilder(forward.end())
                .back(12)
                .build();




//        drive.followTrajectory(strafeRight);
        drive.followTrajectory(back);


        if(location == 1){
            Trajectory getToSide = drive.trajectoryBuilder(startPose)
                    .strafeLeft(-10)
                    .build();
            drive.followTrajectory(getToSide);
        }


        switch (location) {
            case 1:
                drive.turn(Math.toRadians(-105));

                break;
            case 3:

                break;
            default:
                drive.turn(Math.toRadians(-110));
                break;



        }
        if(location == 1){
            drive.followTrajectory(drive.trajectoryBuilder(startPose).strafeLeft(12).build());
        }
        drive.setPoseEstimate(new Pose2d(-36, -36,0 ));
        Trajectory toReset = drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineTo(new Vector2d(-36, -72))
                .build();
        Trajectory resetAgain = drive.trajectoryBuilder(toReset.end())
                .strafeRight(6)
                .build();
        Trajectory offWall = drive.trajectoryBuilder(toReset.end())
                .strafeLeft(4.5)
                .build();
        Trajectory acrossField;
        if(location == 2) {
            acrossField = drive.trajectoryBuilder(offWall.end())
                    .forward(81)
                    .build();
        } else{
            acrossField = drive.trajectoryBuilder(offWall.end())
                    .forward(82)
                    .build();
        }

        Trajectory goLeft;
        switch (location) {
            case 1:
                goLeft = drive.trajectoryBuilder(acrossField.end())
                        .strafeLeft(35)
                        .build();
                break;
            case 2:
                goLeft = drive.trajectoryBuilder(acrossField.end())
                        .strafeLeft(25)
                        .build();

                break;
            default:
                goLeft = drive.trajectoryBuilder(acrossField.end())
                        .strafeLeft(14.5)
                        .build();
                break;

        }


        Trajectory closerToBoard2 = drive.trajectoryBuilder(goLeft.end())
                .forward(9.5)
                .build();

        Trajectory furtherFromBoard = drive.trajectoryBuilder(closerToBoard2.end())
                .back(1.5)
                .build();

        Trajectory offBoard2 = drive.trajectoryBuilder(closerToBoard2.end())
                .back(5)
                .build();



        drive.followTrajectory(toReset);
        drive.followTrajectory(resetAgain);
        drive.followTrajectory(offWall);
        sleep(4500);
        drive.followTrajectory(acrossField);

        drive.turn(Math.toRadians(-6));

        drive.followTrajectory(goLeft);

        runSlide(1600);
        sleep(1000);
        rotatorCuff.setPosition(rotatorCuffScoring);
        drive.followTrajectory(closerToBoard2);
        drive.followTrajectory(furtherFromBoard);

        sleep(1200);

        grabber.setPosition(grabberOpen);


        sleep(1000);

        drive.followTrajectory(offBoard2);

        runSlide(300);
        while(Math.abs(slide.getTargetPosition() - slide.getCurrentPosition()) > 50){};








    }
}