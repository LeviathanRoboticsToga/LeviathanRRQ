package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.ArrayList;
import java.util.List;

@TeleOp
public class LimelightTest extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException
    {
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(1);
        ElapsedTime timer = new ElapsedTime();

        /*
         * Starts polling for data.
         */
        limelight.start();
        waitForStart();
        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();
            if (result != null) {
                if (result.isValid()) {
                    Pose3D botpose = result.getBotpose();

                    List<LLResultTypes.ColorResult> sample = result.getColorResults();
                    double distance = 3.788/Math.tan(Math.toRadians(sample.get(0).getTargetYDegrees()));
                    int total = 0;

                    if(timer.seconds()>1)
                    {
                        telemetry.addData("angleTan", Math.tan(Math.toRadians(sample.get(0).getTargetYDegrees())));
                        telemetry.addData("angle", sample.get(0).getTargetYDegrees());
                        telemetry.addData("result", Math.abs(distance));
                        telemetry.addData("status", limelight.getStatus());
                        telemetry.addData("tx", result.getTx());
                        telemetry.addData("ty", sample.get(0).getTargetYDegrees());
                        //telemetry.addData("Botpose", botpose.toString());
                        telemetry.addData("ta", result.getTa());
                        telemetry.update();
                        timer.reset();
                    }

                }
            }
        }
    }
}
