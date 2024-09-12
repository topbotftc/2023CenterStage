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

@Autonomous(name = "Red Two Pixel", preselectTeleOp = preselect)
public class RedTwoPixel extends LinearOpMode{

    OpenCvCamera webcam;
    final double START_X = 12;
    final double START_Y = -64;

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
        rotatorCuff.setPosition(rotatorCuffAvoid);
        sleep(1000);
        runSlide(100);
        sleep(500);



        location = pipeline.getSide().ordinal() + 1;

        telemetry.addData("location: ", pipeline.getSide());
        telemetry.update();


        Trajectory strafeRightPark = drive.trajectoryBuilder(startPose)
                .strafeRight(35)
                .build();


        switch(location){

            case 1:

                Trajectory toMiddle = drive.trajectoryBuilder(startPose)
                        .forward(23)
                        .build();

                Trajectory toSpikeMark = drive.trajectoryBuilder(toMiddle.end())
                        .forward(9)
                        .build();

                Trajectory backUp = drive.trajectoryBuilder(toSpikeMark.end())
                        .back(5)
                        .build();

                Trajectory toBoard3 = drive.trajectoryBuilder(backUp.end().plus(new Pose2d(0, 0, Math.toRadians(90))))
                        .lineTo(new Vector2d(42, -30))
                        .build();

                Trajectory closerToBoard3 = drive.trajectoryBuilder(toBoard3.end().plus(new Pose2d(0, 0, -90)))
                        .forward(8)
                        .build();
                Trajectory hitboard = drive.trajectoryBuilder(toBoard3.end().plus(new Pose2d(0, 0, -90)))
                        .forward(4)
                        .build();

                Trajectory offBoard3 = drive.trajectoryBuilder(closerToBoard3.end())
                                .back(3)
                                        .build();


                drive.followTrajectory(toMiddle);
                drive.turn(Math.toRadians(107));
                drive.followTrajectory(toSpikeMark);
                runSlide(300);
                sleep(400);
                drive.followTrajectory(backUp);
                drive.followTrajectory(toBoard3);
                drive.turn(Math.toRadians(-195));

                runSlide(1800);
                sleep(1000);
                rotatorCuff.setPosition(rotatorCuffScoring);
                drive.followTrajectory(closerToBoard3);
                drive.followTrajectory(hitboard);
                drive.followTrajectory(offBoard3);


                sleep(1200);

                grabber.setPosition(grabberOpen);


                sleep(1000);

                drive.followTrajectory(offBoard3);




                break;
            case 3:
                Trajectory forward = drive.trajectoryBuilder(startPose)
                        .forward(27)
                        .build();

                Trajectory toRightMark = drive.trajectoryBuilder(forward.end())
                        .strafeRight(23)
                        .build();

                Trajectory strafeLeft = drive.trajectoryBuilder(toRightMark.end())
                        .strafeLeft(4)
                        .build();

                Trajectory backup = drive.trajectoryBuilder(toRightMark.end())
                        .back(10)
                        .build();

                Trajectory toBoard2 = drive.trajectoryBuilder(backup.end())
                        .lineTo(new Vector2d(60, -50))
                                .build();

                Trajectory straferight = drive.trajectoryBuilder(toBoard2.end())
                        .strafeRight(4)
                        .build();
                Trajectory closerToBoard2 = drive.trajectoryBuilder(toBoard2.end().plus(new Pose2d(0, 0, -90)))
                        .forward(8)
                        .build();

                Trajectory hitboard3 = drive.trajectoryBuilder(closerToBoard2.end())
                        .forward(5)
                        .build();

                Trajectory offBoard2 = drive.trajectoryBuilder(closerToBoard2.end())
                        .back(3)
                        .build();

                drive.followTrajectory(forward);
                drive.followTrajectory(toRightMark);
                drive.followTrajectory(strafeLeft);

                drive.followTrajectory(backup);
                drive.followTrajectory(toBoard2);

                drive.turn(Math.toRadians(-110));


                runSlide(1800);
                sleep(1000);
                rotatorCuff.setPosition(rotatorCuffScoring);
                drive.followTrajectory(straferight);
                drive.followTrajectory(closerToBoard2);
                drive.followTrajectory(hitboard3);

                drive.followTrajectory(offBoard2);

                sleep(1200);

                grabber.setPosition(grabberOpen);


                sleep(1000);

                drive.followTrajectory(offBoard2);





                break;
            default:
                Trajectory forward4 = drive.trajectoryBuilder(startPose)
                        .forward(31)
                        .build();


                Trajectory backup4 = drive.trajectoryBuilder(forward4.end())
                        .back(10)
                        .build();

                Trajectory toBoard4 = drive.trajectoryBuilder(backup4.end())
                        .lineTo(new Vector2d(50, -47))
                        .build();

                Trajectory straferight4 = drive.trajectoryBuilder(toBoard4.end())
                        .strafeLeft(5)
                        .build();
                Trajectory closerToBoard4 = drive.trajectoryBuilder(toBoard4.end().plus(new Pose2d(0, 0, -90)))
                        .forward(10)
                        .build();
                Trajectory hitBoard = drive.trajectoryBuilder(closerToBoard4.end())
                        .forward(3)
                        .build();

                Trajectory offBoard4 = drive.trajectoryBuilder(closerToBoard4.end())
                        .back(4)
                        .build();

                drive.followTrajectory(forward4);


                drive.followTrajectory(backup4);
                drive.followTrajectory(toBoard4);

                drive.turn(Math.toRadians(-108));


                runSlide(1800);
                sleep(1000);
                rotatorCuff.setPosition(rotatorCuffScoring);
//                drive.followTrajectory(straferight4);
                drive.followTrajectory(closerToBoard4);
                drive.followTrajectory(hitBoard);
                grabber.setPosition(grabberOpen);
                sleep(1200);

                drive.followTrajectory(offBoard4);




                sleep(1000);

                drive.followTrajectory(offBoard4);






                break;



        }
        drive.followTrajectory(strafeRightPark);
        runSlide(300);
        while(Math.abs(slide.getTargetPosition() - slide.getCurrentPosition()) > 50){};





    }
}