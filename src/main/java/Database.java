import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

@ManagedBean(name = "database")
@SessionScoped
public class Database implements Serializable {
    private ArrayList<Result> listOfResults = new ArrayList<>();
    private Connection connection;

    public ArrayList<Result> getListOfResults() {
        return listOfResults;
    }

    public void setListOfResults(ArrayList<Result> listOfResults) {
        this.listOfResults = listOfResults;
    }

    @PostConstruct
    public void init() {
        connection();
        loadFromDB();
    }

    public void connection() {
        try {
            FileInputStream bd = new FileInputStream(new File("databaseInfo"));
            Properties properties = new Properties();
            properties.load(bd);
            String url = properties.getProperty("BD.location");
            String login = properties.getProperty("BD.login");
            String password = properties.getProperty("BD.password");
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, login, password);
            System.out.println("Connection to database was successful");
        } catch (SQLException e) {
            System.out.println("Error with database");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error with file");
        }
    }

    public void loadFromDB() {
        try {
            ResultSet res = connection.createStatement().executeQuery("SELECT * FROM results");
            while (res.next()) {
                double y = res.getDouble("y");
                double x = res.getDouble("x");
                double r = res.getDouble("r");
                String result = res.getString("result");
                String timeExec = res.getString("timeExec");
                String benchmark = res.getString("benchmark");
                listOfResults.add(new Result(y, x, r, result, timeExec, benchmark));
            }
            System.out.println("All results was download to table");
        } catch (SQLException e) {
            System.out.println("Error with database");
        }
    }

    public void addToDB(Result result) {
        try {
            PreparedStatement addPs = connection.prepareStatement("INSERT INTO results (y, x, r, result, timeExec, benchmark) values (?, ?, ?, ?, ?, ?)");
            addPs.setDouble(1, result.getY());
            addPs.setDouble(2, result.getX());
            addPs.setDouble(3, result.getR());
            addPs.setString(4, result.getResult());
            addPs.setString(5, result.getTime());
            addPs.setString(6, result.getBenchmark());
            addPs.execute();
            listOfResults.add(result);
            System.out.println("Result was added to database");
        } catch (SQLException e) {
            System.out.println("Error with database");
        }
    }

    public void clearDB() {
        try {
            connection.createStatement().execute("DELETE FROM results");
            listOfResults.clear();
        } catch (SQLException e) {
            System.out.println("Error with database");
        }
    }
}