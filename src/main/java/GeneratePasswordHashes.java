/**
 * Standalone utility to generate plain-text passwords for demo data
 * Run this to generate plain-text passwords for the demo credentials
 */
public class GeneratePasswordHashes {
    public static String hashPassword(String password) {
        // Return password as plain text (no hashing)
        return password;
    }

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("       TIME TRACKER DEMO DATA PASSWORDS (PLAIN TEXT)");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        String[] usernames = {"admin", "pm_user", "team_user", "john_dev", "jane_dev"};
        String[] passwords = {"admin123", "pm123", "team123", "john123", "jane123"};

        System.out.println("Generated Passwords (copy these for demo_data.sql):\n");

        StringBuilder sqlUpdates = new StringBuilder();
        sqlUpdates.append("\n-- UPDATE STATEMENTS FOR demo_data.sql --\n\n");

        for (int i = 0; i < usernames.length; i++) {
            String plain = hashPassword(passwords[i]);
            // Escape single quotes for SQL
            String sqlEscaped = plain.replace("'", "''");

            System.out.println("Username: " + usernames[i]);
            System.out.println("Password: " + plain);
            System.out.println();

            sqlUpdates.append("UPDATE users SET password_hash='").append(sqlEscaped)
                    .append("' WHERE username='").append(usernames[i]).append("';\n");
        }

        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("\nSQL UPDATE STATEMENTS:\n");
        System.out.println(sqlUpdates);
        System.out.println("═══════════════════════════════════════════════════════════");
    }
}
