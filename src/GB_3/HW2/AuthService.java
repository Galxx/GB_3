package HW8;

//import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class AuthService {

    Connection connection = null;;

    private class User {
        private String login;
        private String passwd;
        private String nick;

        public User(String login, String passwd, String nick) {
            this.login = login;
            this.passwd = passwd;
            this.nick = nick;
        }

        public void SetNick(String newNick){
            this.nick = newNick;

            try (PreparedStatement statement = connection
                    .prepareStatement("UPDATE users SET nickname = ? where  login LIKE ?")){
                statement.setString(1, newNick);
                statement.setString(2, this.login);
                statement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private List<User> userList;

    public AuthService() throws Exception {
        userList = new ArrayList<>();

        init();
        connection = getConnection();

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT login,password,nickname FROM users");
            while (rs.next()) {
                String login = rs.getString("login");
                String passwd = rs.getString("password");
                String nick = rs.getString("nickname");
                userList.add(new User(login, passwd, nick));
            }
            System.out.println("----------------");
        }


    }

    public void start() {
        System.out.println("Authentication service started");
    }

    public void stop() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Authentication service stopped");
    }

    public String getNickByLoginAndPwd(String login, String passwd) {
        for(User user: userList) {
            if (user.login.equals(login) && user.passwd.equals(passwd)) {
                return user.nick;
            }
        }
        return null;
    }

    public void ChangeNick(String login, String newNick) {


        for(User user: userList) {
            if (user.login.equals(login) ) {
                user.SetNick(newNick);
            }
        }


    }

    private static void init() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=chatbase";
        //Имя пользователя БД
        String name = "sa";
        //Пароль
        String password = "12345";
        return DriverManager.getConnection(url, name, password);
    }

}
