
import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;

public class BMK_JDBC {

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Подключение к базе данных
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weather", "root", "PassW0rd++");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery("SELECT * FROM weather");

            // Вывод начальных данных
            System.out.println("Прогноз погоды:");
            while (rs.next()) {
                System.out.println(rs.getString("day") + ": " + rs.getString("weather") + "℃");
            }

            System.out.println("\nИзменение значения температуры от -10 до +10 градусов ...\n");
            rs.beforeFirst();
            while (rs.next()) {
                String oldTemperature = rs.getString("weather");
                String newTemperature = changeTemeprature(oldTemperature);
                rs.updateString("weather", newTemperature);
                rs.updateRow();
            }

            System.out.println("Конечное значение температуры:");
            rs.beforeFirst();
            while (rs.next()) {
                System.out.println(rs.getString("day") + ": " + rs.getString("weather") + "℃");
            }
        } catch (SQLException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private static String changeTemeprature(String temperature) {
        return Long.toString(Long.parseLong(temperature) + ThreadLocalRandom.current().nextInt(-10, 10));
    }
}
