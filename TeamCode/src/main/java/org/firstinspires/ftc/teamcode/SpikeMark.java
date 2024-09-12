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

@Disabled
@Autonomous(name = "SpikeMark", preselectTeleOp = preselect)
public class SpikeMark extends LinearOpMode{

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
        runSlide(151);
        sleep(500);



        location = pipeline.getSide().ordinal() + 1;

        telemetry.addData("location: ", pipeline.getSide());
        telemetry.update();


        Trajectory toMiddle = drive.trajectoryBuilder(startPose)
                .lineTo(new Vector2d(-36, -38))
                .build();

        int forwardN = 0;
        switch (location){
            case 1:
                forwardN = 10;
                break;
            case 2:
                forwardN = 11;
                break;
            case 3:
                forwardN = 12;
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
                drive.turn(Math.toRadians(93));
                break;
            case 3:
                drive.turn(Math.toRadians(-100));
                break;
            default:
                break;
        }

        drive.followTrajectory(forward);

        grabber.setPosition(.77);
        sleep(600);

//        Trajectory strafeRight = drive.trajectoryBuilder(forward.end())
//                .strafeRight(4)
//                .build();

        Trajectory back = drive.trajectoryBuilder(forward.end())
                .back(12)
                .build();




//        drive.followTrajectory(strafeRight);
        drive.followTrajectory(back);






    }
}