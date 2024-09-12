package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import static org.firstinspires.ftc.teamcode.Robot.*;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp(name = "teleop")
public class Teleop extends LinearOpMode {

    public void runOpMode() throws InterruptedException {

        boolean moved = false;
        double airplanePos = airplaneHold;
        double grabberPos = grabberOpen;
        double rotatorPos = rotatorCuffDown;

        boolean gearDown = false;

        initMotors(this);

        int slideTargetPos = 0;
        int slideCurrentPos = slide.getCurrentPosition();


        boolean isUp = false;

        boolean colorConnected = true;
        boolean pixelIn = false;


        waitForStart();

        while(opModeIsActive()) {

            // CONTROL BUTTONS

            boolean LBumper1 = gamepad1.left_bumper;
            boolean RBumper1 = gamepad1.right_bumper;

            double LStickY = gamepad1.left_stick_y;
            double LStickX = -gamepad1.left_stick_x;
            double RStickY = gamepad1.right_stick_y;
            double RStickX = gamepad1.right_stick_x;

            double LTrigger1 = gamepad1.left_trigger; //
            double RTrigger1 = gamepad1.right_trigger; //

            boolean a1 = gamepad1.a;
            boolean b1 = gamepad1.b;
            boolean x1 = gamepad1.x;
            boolean y1 = gamepad1.y;

            boolean a2 = gamepad2.a;
            boolean b2 = gamepad2.b;
            boolean x2 = gamepad2.x;
            boolean y2 = gamepad2.y;

            double LTrigger2 = gamepad2.left_trigger;
            double RTrigger2 = gamepad2.right_trigger;
            boolean LBumper2 = gamepad2.left_bumper;
            boolean RBumper2 = gamepad2.right_bumper;

            double RStickY2 = -gamepad2.right_stick_y;
            double RStickX2 = gamepad2.right_stick_x;
            double LStickY2 = -gamepad2.left_stick_y;
            double LStickX2 = gamepad2.left_stick_x;

            boolean dpadUp1 = gamepad1.dpad_up;
            boolean dpadDown1 = gamepad1.dpad_down;
            boolean dpadRight1 = gamepad1.dpad_right;
            boolean dpadLeft1 = gamepad1.dpad_left;

            boolean dpadUp2 = gamepad2.dpad_up;
            boolean dpadDown2 = gamepad2.dpad_down;
            boolean dpadRight2 = gamepad2.dpad_right;
            boolean dpadLeft2 = gamepad2.dpad_left;

            boolean LStickIn = gamepad1.left_stick_button;
            boolean LStickIn2 = gamepad2.left_stick_button;

            boolean RStickIn2 = gamepad2.right_stick_button;

            slideCurrentPos = slide.getCurrentPosition();



            // Gear Shift
            if(colorConnected) {

                pixelIn = color.red() + color.green() + color.blue() > 215;
                if (pixelIn && !gamepad2.isRumbling()) {
                    gamepad2.rumble(100);

                    gamepad1.rumble(100);
                }
            }
            if(gamepad1.touchpad || gamepad2.touchpad){
                colorConnected = false;
            }
            if(!pixelIn){
                led.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            } else if(pixelIn && grabber.getPosition() == grabberOpen){
                led.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE);
            } else {
                led.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
            }

            if(!gearDown && LBumper1){
                if(gear == .5) gear = 1;

                else gear = .5;
            }
            gearDown = LBumper1;


            // DRIVE CODE
            {
                if (Math.abs(LStickX) > 0.05 || Math.abs(LStickY) > 0.05 || Math.abs(RStickX) > 0.05) {


                    double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y) * gear;
                    double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
                    double rightX = gamepad1.right_stick_x / 2;

                    double v1 = r * Math.cos(robotAngle) + rightX; //lf // wsa cos
                    double v2 = r * Math.sin(robotAngle) - rightX; //rf // was sin
                    double v3 = r * Math.sin(robotAngle) + rightX; //lb // was sin
                    double v4 = r * Math.cos(robotAngle) - rightX; //rb // was cos

                    SetPower( v1,  v2,  v3,  v4);

                }
                //Trigger Turning
//                else if (LBumper1) {
//                    // Gear Shift
//
//                } else if (RBumper1) {
//
//                } else if (RTrigger1 > .25) {
//                    SetPower(.25 * 1, -.25 * 1, -.25 * 1, .25 * 1);
                else if (LTrigger1 > .25) {
                    SetPower(-.25 * 1, .25 * 1, .25 * 1, -.25 * 1);
                } else if(dpadUp1){
                    SetPower(.25, .25, .25, .25);
                } else if(dpadRight1){
                    SetPower(.25, -.25, -.25, .25);
                } else if(dpadDown1){
                    SetPower(-.25, -.25, -.25, -.25);
                } else if(dpadLeft1){
                    SetPower(-.25, .25, .25, -.25);
                }
                else {
                    SetPower(0, 0, 0, 0);
                }
            }
//
//            if(dpadUp2){
//                slideTargetPos = slideCurrentPos + slideInterval;
//            } else if(dpadDown2){
//                slideTargetPos = slideCurrentPos - slideInterval;
//            } else {
//                slideTargetPos = slideCurrentPos;
//            }
//             if(slideTargetPos > slideMax) slideTargetPos = slideMax;
//
            double power = LStickY2;
            if(Math.abs(power) < .4) power = 0;
            slide.setPower(power * .30);


//            +
//            runSlide(slideTargetPos);

            if(RBumper2){
                airplanePos = airplaneShoot;
                airplane.setPosition(airplanePos);
            } else if(LBumper2){
                airplanePos = airplaneHold;
                airplane.setPosition(airplanePos);
            }

            if(a2){
                rotatorPos = rotatorCuffDown;
            } else if(b2){
                rotatorPos = rotatorCuffOut;
            }

            if(LTrigger2 > .5){
                grabberPos = grabberOpen;
            } else if(RTrigger2 > .5){
                grabberPos = grabberClosed;
            }


            if(x2 && y2){
                if(!isUp) {
                    susUp();
                    isUp = true;
                    sleep(50);
                }
                else {
                    susDown();
                    sleep(50);
                }
            }
            if(x2){
                susUp();
            } else if(y2){
                susDown();
            }




            grabber.setPosition(grabberPos);
            rotatorCuff.setPosition(rotatorPos);

//
//            telemetry.addData("airplane position", airplane.getPosition());
//            telemetry.addData("grabber pos", grabber.getPosition());
//            telemetry.addData("rotatorCuff pos", rotatorCuff.getPosition());
//            telemetry.addData("Power of slide", slide.getPower());

//            telemetry.addData("Red: ", color.red());
//            telemetry.addData("Green: ", color.green());
//            telemetry.addData("Blue: ", color.blue());
//            telemetry.addData("Total light: ", color.red() + color.green() + color.blue());
            telemetry.addData("Pixel In: ", pixelIn);
            telemetry.update();
        }




    }


}
