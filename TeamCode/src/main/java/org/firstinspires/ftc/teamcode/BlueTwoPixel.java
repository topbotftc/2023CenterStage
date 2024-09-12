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

@Autonomous(name = "Blue Two Pixel", preselectTeleOp = preselect)
public class BlueTwoPixel extends LinearOpMode{

    OpenCvCamera webcam;
    final double START_X = 12;
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


////////Program start////////////////////////////////////////////////////////////////////////
        initMotors(this);
        initAccessories(this);

        waitForStart();
        ////Move on start/init

        grabber.setPosition(grabberClosed);
        sleep(1000);
        rotatorCuff.setPosition(rotatorCuffAvoid);
        sleep(350);
        runSlide(100);
        sleep(550);



        location = pipeline.getSide().ordinal() + 1;

        telemetry.addData("location: ", pipeline.getSide());
        telemetry.update();

        Trajectory strafeLeftPark;
        switch(location){
            case 1:
                strafeLeftPark = drive.trajectoryBuilder(startPose)
                        .strafeLeft(25)
                        .build();
                break;
            case 2:
                strafeLeftPark = drive.trajectoryBuilder(startPose)
                        .strafeLeft(35)
                        .build();
                break;
            default:
                strafeLeftPark = drive.trajectoryBuilder(startPose)
                        .strafeLeft(45)
                        .build();
                break;
        }
        switch(location){

            case 1:
                Trajectory leftOfMark = drive.trajectoryBuilder(startPose)
                        .lineTo(new Vector2d(32, 34))
                        .build();

                Trajectory backup = drive.trajectoryBuilder(leftOfMark.end())
                        .back(14)
                                .build();
                Trajectory toBoard = drive.trajectoryBuilder(backup.end())
                        .lineTo(new Vector2d(67, 40))// 64, 51
                        .build();

                Trajectory closerToBoard = drive.trajectoryBuilder(toBoard.end().plus(new Pose2d(0, 0, 90)))
                        .forward(7)
                                .build();

                Trajectory strafeRight = drive.trajectoryBuilder(closerToBoard.end())
                        .strafeRight(5)
                                .build();

                Trajectory whackBoard = drive.trajectoryBuilder(strafeRight.end())
                        .forward(5)
                                .build();
                Trajectory offBoard = drive.trajectoryBuilder(whackBoard.end())
                        .back(1.5)
                        .build();



//                Trajectory score = drive.trajectoryBuilder(leftOfMark.end())
//                        .forward(6)
//                        .build();
//
//                Trajectory toBoard = drive.trajectoryBuilder(score.end())
//                        .lineTo(new Vector2d(64 - 24, 36 - 30))
//                        .build();
//
//                Trajectory strafeRight = drive.trajectoryBuilder(toBoard.end())
//                                .strafeRight(3)
//                                        .build();

                drive.followTrajectory(leftOfMark);
                runSlide(300);
                sleep(400);
                drive.followTrajectory(backup);
                drive.followTrajectory(toBoard);
                drive.turn(Math.toRadians(103));

                runSlide(1800);
                sleep(1000);
                rotatorCuff.setPosition(rotatorCuffScoring);
                drive.followTrajectory(closerToBoard);

//                drive.followTrajectory(strafeRight);
                drive.followTrajectory(whackBoard);
                drive.followTrajectory(offBoard);

                sleep(1200);

                grabber.setPosition(grabberOpen);


                sleep(500);

                drive.followTrajectory(offBoard);


                break;


            case 2:

                Trajectory leftOfMark2 = drive.trajectoryBuilder(startPose)
                        .lineTo(new Vector2d(10, 33))
                        .build();

                Trajectory backup2 = drive.trajectoryBuilder(leftOfMark2.end())
                        .back(12)
                        .build();

                Trajectory toBoard2 = drive.trajectoryBuilder(backup2.end())
                        .lineTo(new Vector2d(64, 33))
                        .build();

                Trajectory closerToBoard2 = drive.trajectoryBuilder(toBoard2.end().plus(new Pose2d(0, 0, 90)))
                        .forward(2)
                        .build();

                Trajectory strafeRight2 = drive.trajectoryBuilder(closerToBoard2.end())
                        .strafeRight(3)
                        .build();

                Trajectory whackBoard2 = drive.trajectoryBuilder(strafeRight2.end())
                        .forward(3)
                        .build();

                Trajectory offBoard2 = drive.trajectoryBuilder(whackBoard2.end())
                        .back(1.5)
                                .build();



//                Trajectory score = drive.trajectoryBuilder(leftOfMark.end())
//                        .forward(6)
//                        .build();5
//
//                Trajectory toBoard = drive.trajectoryBuilder(score.end())
//                        .lineTo(new Vector2d(64 - 24, 36 - 30))
//                        .build();
//
//                Trajectory strafeRight = drive.trajectoryBuilder(toBoard.end())
//                                .strafeRight(3)
//                                        .build();

                drive.followTrajectory(leftOfMark2);
                runSlide(300);
                sleep(400);
                drive.followTrajectory(backup2);
                drive.followTrajectory(toBoard2);
                drive.turn(Math.toRadians(100));

                runSlide(1800);
                sleep(1000);
                rotatorCuff.setPosition(rotatorCuffScoring);
                drive.followTrajectory(closerToBoard2);

//                drive.followTrajectory(strafeRight2);
                drive.followTrajectory(whackBoard2);
                drive.followTrajectory(offBoard2);

                sleep(1200);

                grabber.setPosition(grabberOpen);



                sleep(800);
                drive.followTrajectory(offBoard2);

                break;

            default:

                Trajectory toMiddle = drive.trajectoryBuilder(startPose)
                        .forward(21)
                        .build();

                Trajectory  wiggleLeft = drive.trajectoryBuilder(toMiddle.end())
                        .strafeRight(5)
                        .build();
                Trajectory wiggleRight = drive.trajectoryBuilder(wiggleLeft.end())
                        .strafeLeft(5)
                        .build();


                Trajectory toSpikeMark = drive.trajectoryBuilder(toMiddle.end())
                        .forward(11)
                        .build();

                Trajectory backUp = drive.trajectoryBuilder(toSpikeMark.end())
                        .back(15)
                        .build();

                Trajectory toBoard3 = drive.trajectoryBuilder(backUp.end())
                        .lineTo(new Vector2d(50, 27))
                        .build();

                Trajectory closerToBoard3 = drive.trajectoryBuilder(toBoard3.end().plus(new Pose2d(0, 0, 90)))
                        .forward(10)
                        .build();

                Trajectory strafeRight3 = drive.trajectoryBuilder(closerToBoard3.end())
                        .strafeRight(9)
                        .build();

                Trajectory whackBoard3 = drive.trajectoryBuilder(strafeRight3.end())
                        .forward(4)
                        .build();
                Trajectory offBoard3 = drive.trajectoryBuilder(whackBoard3.end())
                        .back(1.5)
                        .build();





//                Trajectory score = drive.trajectoryBuilder(leftOfMark.end())
//                        .forward(6)
//                        .build();
//
//                Trajectory toBoard = drive.trajectoryBuilder(score.end())
//                        .lineTo(new Vector2d(64 - 24, 36 - 30))
//                        .build();
//
//                Trajectory strafeRight = drive.trajectoryBuilder(toBoard.end())
//                                .strafeRight(3)
//                                        .build();

                drive.followTrajectory(toMiddle);
                drive.turn(Math.toRadians(-105));
//                drive.followTrajectory(wiggleLeft);
//                drive.followTrajectory(wiggleRight);
                drive.followTrajectory(toSpikeMark);

                runSlide(300);
                sleep(400);
                drive.followTrajectory(backUp);

                drive.turn(Math.toRadians(100));
                drive.followTrajectory(toBoard3);
                drive.turn(Math.toRadians(102));

                runSlide(1500);
                sleep(1000);
                rotatorCuff.setPosition(rotatorCuffScoring);
                drive.followTrajectory(closerToBoard3);

                sleep(1200);
//                drive.followTrajectory(strafeRight3);
                drive.followTrajectory(whackBoard3);
                drive.followTrajectory(offBoard3);
                grabber.setPosition(grabberOpen);


                sleep(1000);

                drive.followTrajectory(offBoard3);




                break;

        }
        runSlide(300);
        sleep(1000);
        drive.followTrajectory(strafeLeftPark);
        while(Math.abs(slide.getTargetPosition() - slide.getCurrentPosition()) > 50){};




    }
}