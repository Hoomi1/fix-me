package ft.school21.fix_utils.Database;

import com.zaxxer.hikari.HikariDataSource;
import ft.school21.fix_utils.Cryptocurr.Crypto;
import ft.school21.fix_utils.Cryptocurr.CryptoMarket;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTransaction implements Runnable{

    private static final String URL = "jdbc:postgresql://localhost:5432/my_db";
    private static final String NAME = "cyuuki";
    private static final String PASSWORD = "cyuuki";
    private static HikariDataSource hikariDataSource = new HikariDataSource();
    private static DataSource dataSource;
    private String query;



    public DBTransaction() {

//        if (!hikariDataSource.getJdbcUrl().equals(URL)) {
            hikariDataSource.setJdbcUrl(URL);
            hikariDataSource.setUsername(NAME);
            hikariDataSource.setPassword(PASSWORD);
            dataSource = hikariDataSource;
//        }
    }

    @Override
    public void run() {
        saveTransaction(query);
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void saveTransaction(String str) {
        try {
            String query = "INSERT INTO fix_me(transaction) VALUES (" + "'"+ str + "');";
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            boolean res = statement.execute(query);
            System.out.println(res);
//            ResultSet resultSet = statement.executeQuery("INSERT INTO fix_me(transaction) VALUES (" + "'"+ str + "');");
//            resultSet.next();

//            for (int i = 0; i < 10; i++) {
//                Crypto crypto = new Crypto(resultSet.getString("namet"),
//                        resultSet.getString("code"),
//                        Integer.parseInt(resultSet.getString("amount")),
//                        Integer.parseInt(resultSet.getString("min_buy")),
//                        Integer.parseInt(resultSet.getString("min_sell")));
//                CryptoMarket.addCrypto(crypto);
//
//                resultSet.next();
//            }
//            resultSet.close();
//            statement.close();
//            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
