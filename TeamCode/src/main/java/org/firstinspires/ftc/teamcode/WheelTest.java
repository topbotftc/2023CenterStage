package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import static org.firstinspires.ftc.teamcode.Robot.*;

import org.firstinspires.ftc.teamcode.util.Encoder;


@TeleOp(name = "wheeltest")
public class WheelTest extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        initMotors(this);
        Encoder leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftback"));
        Encoder rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightback"));
        Encoder frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightfront"));

        waitForStart();

        while(opModeIsActive()) {

            boolean LBumper1 = gamepad1.left_bumper;
            boolean RBumper1 = gamepad1.right_bumper;

            double LStickY = gamepad1.left_stick_y;
            double LStickX = gamepad1.left_stick_x;
            double RStickY = -gamepad1.right_stick_y;
            double RStickX = -gamepad1.right_stick_x;

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

            double power = .5;
            if(a1){
                lf.setPower(power);
            } else {
                lf.setPower(0);
            }
            if(b1){
                rf.setPower(power);
            } else {
                rf.setPower(0);
            }
            if(x1){
                rb.setPower(power);
            } else {
                rb.setPower(0);
            }
            if(y1){
                lb.setPower(power);
            } else {
                lb.setPower(0);
            }






            telemetry.addData("left encoder", leftEncoder.getCurrentPosition());
            telemetry.addData("right encoder", rightEncoder.getCurrentPosition());
            telemetry.addData("middle encoder", frontEncoder.getCurrentPosition());



            telemetry.update();


        }




    }


}
