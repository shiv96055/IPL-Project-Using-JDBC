import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        findNumberOfMatchesPlayedPerYear();
        findNumberOfMatchesWonAllTeamInAllYear();
        findExtraRunPerTeamIn2016();
    }

    public static Connection getConnection(){
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String databaseUrl = "jdbc:mysql://localhost:3306/ipl_project";
            String userName = "root";
            String password = "123@Admin";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(databaseUrl, userName,password);
            System.out.println("database is connected");
            return  conn;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    private static void findNumberOfMatchesPlayedPerYear(){
        try{
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT season, COUNT(*) AS COUNT FROM matches GROUP BY season");
            while (resultSet.next()){
                System.out.println(resultSet.getString("season") + " " + resultSet.getInt("count"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void findNumberOfMatchesWonAllTeamInAllYear(){
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT winner, COUNT(*) AS times FROM matches GROUP BY winner");

            while (resultSet.next()){
                System.out.println(resultSet.getString("winner") + " " + resultSet.getInt("times"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private static void findExtraRunPerTeamIn2016(){
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(" SELECT bowling_team, SUM(extra_runs) AS extra_run_per_team " +
                    "FROM deliveries " +
                    "where match_id in(select id from matches where season = 2016)" +
                    "group by bowling_team;");

            while (resultSet.next()){
                System.out.println(resultSet.getString("bowling_team") +
                        " " + resultSet.getInt("extra_run_per_team"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}