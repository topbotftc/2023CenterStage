package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.lynx.commands.core.LynxReadVersionStringResponse;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class Robot {
    static DcMotor lf;
    static DcMotor lb;
    static DcMotor rf;
    static DcMotor rb;

    static DcMotor slide;
    static DcMotor intake;

    static DcMotor sus1;
    static DcMotor sus2;

    static Servo spatula;

    static Servo airplane;

    static Servo grabber;
    static Servo rotatorCuff;

    static Servo hook1;
    static Servo hook2;

    static DistanceSensor frontDS;
    static ColorSensor color;

    static RevBlinkinLedDriver led;

    static final double intakeSpeed = .70;
    static final double slideSpeed = .3;
    static int slideInterval = 100;

    static final int slideMax = 4350;

    static final double grabberOpen = 0.31;
    static final double grabberClosed = 0;

    static final double rotatorCuffDown = .385;

    static final double rotatorCuffOut = .58;
    static final double rotatorCuffAvoid = rotatorCuffOut + .25;

    static final double rotatorCuffScoring = rotatorCuffOut;
    static final double airplaneShoot = 0;
    static final double airplaneHold = 0.33;


    static final int susUpPos2 = 6450;
    static final int susUpPos22 = -6000;



    static double gear = 1;


    //6288 flip up with speed "0"

    static final String preselect = "teleop";

    static final double dsToBoard = 0; //(inch)



    public static void initMotors(OpMode opMode){
        rf = opMode.hardwareMap.get(DcMotor.class, "rightfront");
        lf = opMode.hardwareMap.get(DcMotor.class, "leftfront");
        lb = opMode.hardwareMap.get(DcMotor.class, "leftback");
        rb = opMode.hardwareMap.get(DcMotor.class, "rightback");

//        intake = opMode.hardwareMap.get(DcMotor.class, "intake");

        slide = opMode.hardwareMap.get(DcMotor.class, "slide");

        sus1 = opMode.hardwareMap.get(DcMotor.class, "sus1");
        sus2 = opMode.hardwareMap.get(DcMotor.class, "sus2");




//        spatula = opMode.hardwareMap.get(Servo.class, "spatula");
        airplane = opMode.hardwareMap.get(Servo.class, "airplane");

        grabber = opMode.hardwareMap.get(Servo.class, "grabber");
        rotatorCuff = opMode.hardwareMap.get(Servo.class, "rotatorCuff");

        frontDS = opMode.hardwareMap.get(DistanceSensor.class, "DS");
        color = opMode.hardwareMap.get(ColorSensor.class, "color");

        led = opMode.hardwareMap.get(RevBlinkinLedDriver.class, "led");








        rf.setDirection(DcMotor.Direction.FORWARD);
        rb.setDirection(DcMotor.Direction.FORWARD);
        lb.setDirection(DcMotor.Direction.REVERSE);
        lf.setDirection(DcMotor.Direction.REVERSE);

        sus2.setDirection(DcMotorSimple.Direction.REVERSE);




//        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        slide.setDirection(DcMotorSimple.Direction.REVERSE);


        resetMotors();

    }

    public static void initAccessories(OpMode opMode){
        grabber = opMode.hardwareMap.get(Servo.class, "grabber");
        rotatorCuff = opMode.hardwareMap.get(Servo.class, "rotatorCuff");
    }


    public static void SetPower(double LFPower, double RFPower, double LBPower, double RBPower) {

        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lf.setPower(LFPower);
        lb.setPower(LBPower);
        rf.setPower(RFPower);
        rb.setPower(RBPower);


    }

    public static void resetMotors() {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sus1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sus1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sus2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sus2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


//        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }
    public static void runIntake(double voltage){
        intake.setPower(voltage);
    }
    public static void runIntake(){
        runIntake(intakeSpeed);
    }
    public static void stopIntake(){
        runIntake(0);
    }
    public static void reverseIntake(){
        runIntake(-intakeSpeed);
    }
    public static void reverseIntake(double voltage){
        runIntake(-voltage);
    }

    public static void runSlide(int pos){
        slide.setTargetPosition(pos);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setPower(slideSpeed);
    }

    public static void runSus(double speed){
        sus1.setPower(speed);
//        sus2.setPower(speed);
    }
    public static void susUp(){

        susUp2();




    }

    public static void susUp2(){
        sus1.setTargetPosition(susUpPos2);
        sus1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        sus1.setPower(1);

        sus2.setTargetPosition(susUpPos22);
        sus2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        sus2.setPower(1);
    }
    public static void susDown(){
        sus1.setTargetPosition(5);
        sus1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        sus1.setPower(1);

        sus2.setTargetPosition(-5);
        sus2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        sus2.setPower(1);
    }
    public static void runHook(double speed){
        hook1.setPosition(speed);
        hook2.setPosition(speed);
    }

    public static void intoBoard(){
        ElapsedTime t = new ElapsedTime();
        t.startTime();
        while(frontDS.getDistance(DistanceUnit.INCH) > dsToBoard && t.milliseconds() < 2000){
            SetPower(.2, .2, .2, .2);
        }

        SetPower(0, 0, 0, 0);
    }










    }
