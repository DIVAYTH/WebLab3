import java.io.Serializable;

public class Result implements Serializable {
    private double y;
    private double x;
    private double r;
    private String result;
    private String time;
    private String benchmark;

    public Result(double y, double x, double r, String result, String time, String benchmark) {
        this.x = x;
        this.r = r;
        this.y = y;
        this.result = result;
        this.time = time;
        this.benchmark = benchmark;
    }

    public double getX() {
        return x;
    }

    public double getR() {
        return r;
    }

    public double getY() {
        return y;
    }

    public String getResult() {
        return result;
    }

    public String getTime() {
        return time;
    }

    public String getBenchmark() {
        return benchmark;
    }
}