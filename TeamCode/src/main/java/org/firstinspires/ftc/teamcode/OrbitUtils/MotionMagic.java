package org.firstinspires.ftc.teamcode.OrbitUtils;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robotData.GlobalData;

public class MotionMagic {
    private static final ElapsedTime timer = new ElapsedTime();
    public double kP = 0;
    public double kI = 0;
    public double kD = 0;
    public double kF = 0;
    public double iZone = 0;
    public double kS = 0;
    public double Ps = 0;
    public double Pg = 0;
    public double Pg0 = Pg;
    public double alpha = 0;
    public double V_w = 0;
    public double kV = 0;
    public double Pv = 0;
    public double Ppid = 0;
    public double motorPower = 0;

    public double wanted = 0;

    private double integral = 0;

    private double prevError = 0;
    private double prevTime = 0;
    public double pos_w = 0;
    public double a_w = 0;
    public double a_w_prev = 0;
    public double pos_w_prev = 0;
    public double V_w_prev = 0;
    public double V_max = 0;
    public double target;

    public MotionMagic(final double kP, final double kI, final double kD, final double kF, final double iZone, final double kS, final double kV, final  double alpha) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.iZone = iZone;
        this.kS = kS;
        this.kV = kV;
        this.alpha = alpha;
    }

    public void setWanted(final double wanted) {
        this.pos_w = wanted;
    }

    public double update(final double current) {
        final double currentError = pos_w - current;
        final double currentTime = timer.milliseconds();
        final double deltaTime = currentTime - prevTime;

        if (Math.abs(currentError) < iZone) {
            if (Math.signum(currentError) != Math.signum(prevError)) {
                integral = 0;
            } else {
                integral += currentError * deltaTime;
            }
        }

        final double derivative = deltaTime == 0 ? 0 : (currentError - prevError) / deltaTime;

        prevError = currentError;
        prevTime = currentTime;

        a_w = (V_w - V_w_prev) / deltaTime;
        V_w = Math.min(Math.min(V_w_prev + a_w * deltaTime,V_max),Math.sqrt( 2 * a_w * Math.abs(target - current )));
        pos_w = pos_w_prev + V_w_prev * GlobalData.deltaTime + a_w_prev * (deltaTime * deltaTime);

        Ps = Math.signum(V_w) * kS;
        Pg = Pg0 * Math.sin(alpha);
        Pv = V_w * kV;
        Ppid = kP * currentError /* + kI * integral + kD * derivative + kF * wanted */;

        motorPower = Ps + Pg + Pv + Ppid;

        a_w_prev = a_w;
        pos_w_prev = pos_w;
        V_w_prev = V_w;
        Pg0 = Pg;

        return motorPower;
    }
}
