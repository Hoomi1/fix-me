package ft.school21.fix_utils.Database;

import com.zaxxer.hikari.HikariDataSource;
import ft.school21.fix_utils.Cryptocurr.Crypto;
import ft.school21.fix_utils.Cryptocurr.CryptoMarket;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInstrum implements Runnable{

    private static final String URL = "jdbc:postgresql://localhost:5432/my_db";
    private static final String NAME = "cyuuki";
    private static final String PASSWORD = "cyuuki";
    private static HikariDataSource hikariDataSource = new HikariDataSource();
    private static DataSource dataSource;

    public DBInstrum() {

        hikariDataSource.setJdbcUrl(URL);
        hikariDataSource.setUsername(NAME);
        hikariDataSource.setPassword(PASSWORD);
        dataSource = hikariDataSource;
    }

    @Override
    public void run() {
        findBy();
    }

    public void findBy()
    {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions");
            resultSet.next();

            for (int i = 0; i < 10; i++) {
                Crypto crypto = new Crypto(resultSet.getString("namet"),
                        resultSet.getString("code"),
                        Integer.parseInt(resultSet.getString("amount")),
                        Integer.parseInt(resultSet.getString("min_buy")),
                        Integer.parseInt(resultSet.getString("min_sell")));
                CryptoMarket.addCrypto(crypto);

                resultSet.next();
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
