import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RollBackTest {

    public static void main(String[] args) {

        String sql =
                "UPDATE customers " +
                        "SET phone = ? " +
                        "WHERE customer_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            connection.setAutoCommit(false);

            try {

                // Operation 1
                statement.setString(1, "7777777777");
                statement.setInt(2, 1);

                int firstUpdate =
                        statement.executeUpdate();

                System.out.println(
                        "First update: " + firstUpdate
                );

                if (firstUpdate == 0) {
                    throw new SQLException(
                            "Customer 1 was not found."
                    );
                }


                // Operation 2
                statement.setString(1, "6666666666");
                statement.setInt(2, 999);

                int secondUpdate =
                        statement.executeUpdate();

                System.out.println(
                        "Second update: " + secondUpdate
                );

                if (secondUpdate == 0) {
                    throw new SQLException(
                            "Customer 999 was not found."
                    );
                }


                // Only reached if everything succeeds
                connection.commit();

                System.out.println(
                        "Transaction committed."
                );

            } catch (SQLException e) {

                System.out.println(
                        "Something failed."
                );

                connection.rollback();

                System.out.println(
                        "Transaction rolled back."
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}