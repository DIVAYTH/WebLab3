import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.DoubleStream;

@ManagedBean(name = "pointsBean")
@SessionScoped
public class PointsBean implements Serializable {
    @ManagedProperty(value = "#{database}")
    private Database database;
    private String fieldY;
    private boolean[] fieldR = new boolean[]{true, false, false, false, false};
    private boolean[] fieldX = new boolean[]{false, false, false, false, false, true, false, false, false};
    double y;
    double x;
    double r;
    int lastR;

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public String getFieldY() {
        return fieldY;
    }

    public void setFieldY(String fieldY) {
        this.fieldY = fieldY;
    }

    public boolean[] getFieldR() {
        return fieldR;
    }

    public void setFieldR(boolean[] fieldR) {
        this.fieldR = fieldR;
    }

    public boolean[] getFieldX() {
        return fieldX;
    }

    public void setFieldX(boolean[] fieldX) {
        this.fieldX = fieldX;
    }

    public double getR() {
        double defaultR = 1;
        for (int i = 0; i < fieldR.length; i++) {
            if (fieldR[i]) {
                lastR = i;
                return defaultR;
            } else {
                defaultR += 0.5;
            }
        }
        fieldR[lastR] = true;
        return getR();
    }

    public double getX() {
        double defaultX = -5;
        for (boolean x : fieldX) {
            if (x) {
                return defaultX;
            } else {
                defaultX += 1;
            }
        }
        return -6;
    }

    public void deleteAllCheckboxR() {
        Arrays.fill(fieldR, false);
    }

    public void deleteAllCheckboxX() {
        Arrays.fill(fieldX, false);
    }

    public void checkPoints() {
        long startTime = System.nanoTime();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.get("x") != null && params.get("y") != null && params.get("r") != null) {
            y = Double.parseDouble(params.get("y"));
            x = Double.parseDouble(params.get("x"));
            r = Double.parseDouble(params.get("r"));
        } else {
            y = Double.parseDouble(fieldY);
            r = getR();
            x = getX();
        }
        if (x <= 3 && x >= -5 && y <= 5 && y >= -3 && DoubleStream.of(1, 1.5, 2, 2.5, 3).anyMatch(a -> a == r)) {
            String result = checkArea(x, y, r);
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss"));
            database.addToDB(new Result(y, x, r, result, time, String.valueOf((System.nanoTime() - startTime) / 1000)));
        }
    }

    private String checkArea(double x, double y, double r) {
        if (x <= 0 && y >= 0 && y <= x + r) {
            return "true";
        }
        if (y <= 0 && x <= 0 && x >= -r && y >= -r) {
            return "true";
        }
        if (x >= 0 && y <= 0 && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2)) {
            return "true";
        }
        return "false";
    }
}