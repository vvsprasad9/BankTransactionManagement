import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionTest {

    public static void main(String[] args) {

        String updateSql =
                "UPDATE customers " +
                        "SET phone = ? " +
                        "WHERE customer_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(updateSql)
        ) {

            // Start manual transaction
            connection.setAutoCommit(false);

            statement.setString(1, "8888888888");
            statement.setInt(2, 1);

            int rowsAffected =
                    statement.executeUpdate();

            System.out.println(
                    "Rows updated: " + rowsAffected
            );

            // Commit the transaction
            connection.commit();

            System.out.println(
                    "Transaction committed."
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}