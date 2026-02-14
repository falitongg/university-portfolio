<?php
/**
 * @file admin_panel.php
 * @brief Administrativní panel pro správu uživatelů.
 * 
 * Tento skript zobrazuje seznam uživatelů s možnostmi správy, jako je zobrazení poznámek, 
 * povýšení na administrátora nebo odstranění účtu. Přístup mají pouze administrátoři.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---
include('data_lib.php');  // --- Načtení knihovny pro práci s poznámkami ---

/**
 * Kontrola oprávnění.
 * @details Pokud uživatel není přihlášen nebo není administrátor, přesměruje ho na přihlašovací stránku.
 */
if (!isset($_SESSION['username']) || !isAdmin($_SESSION['username'])) {
    header('Location: login.php');
    exit;
}

/**
 * @var string $username Uživatelské jméno aktuálně přihlášeného administrátora.
 */
$username = $_SESSION['username'];

/**
 * @var array $users Pole všech uživatelů získaných z databáze.
 */
$users = listUsers();

$int = (int) $y;
/**
 * @var array $filteredUsers Filtrovaný seznam uživatelů.
 * @details Obsahuje všechny uživatele kromě hlavního administrátora a aktuálně přihlášeného uživatele.
 */
$filteredUsers = array_filter($users, function($user) use ($username) {
    return $user["username"] !== "admin" && $user["username"] !== $username;
});
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body id="admin">
    <!-- Zobrazení aktuálně přihlášeného uživatele -->
    <p>Logged in as: <?php echo htmlspecialchars($_SESSION['username']); ?></p>

    <h1>Admin Panel</h1>
    <h2>List of Users</h2>
    <table id="admin_table">
        <thead>
            <tr>
                <th>Username</th>
                <th>Notes Count</th>
                <th>Last Note Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <?php foreach ($filteredUsers as $user): ?>
                <?php 
                    /**
                     * @var string $username Uživatelské jméno aktuálně zpracovávaného uživatele.
                     */
                    $username = $user['username'];

                    /**
                     * @var array $notes Pole poznámek aktuálního uživatele.
                     */
                    $notes = getNotesForUsername($username);

                    /**
                     * @var int $notes_count Počet poznámek aktuálního uživatele.
                     */
                    $notes_count = count($notes);

                    /**
                     * @var string $last_note_date Datum vytvoření poslední poznámky.
                     * @details Pokud uživatel nemá žádné poznámky, zobrazí se text "No notes".
                     */
                    $last_note_date = $notes_count > 0 ? date('d.m.Y H:i:s', strtotime(reset($notes)['created_at'])) : 'No notes';
                ?>
                <tr>
                    <td><?php echo htmlspecialchars($username); ?></td>
                    <td><?php echo htmlspecialchars($notes_count); ?></td>
                    <td><?php echo htmlspecialchars($last_note_date); ?></td>
                    <td>
                        <!-- Odkaz na zobrazení poznámek -->
                        <a href="admin_view_user.php?username=<?php echo urlencode($username); ?>">View Notes</a>
                        <!-- Formulář pro odstranění uživatele -->
                        <form action="delete_account.php" method="post">
                            <input type="hidden" name="username" value="<?php echo htmlspecialchars($username); ?>">
                            <button type="submit">Delete User</button>
                        </form>
                        <?php if (!$user['admin']): ?>
                            <!-- Formulář pro povýšení uživatele na administrátora -->
                            <form action="promote_user.php" method="post">
                                <input type="hidden" name="username" value="<?php echo htmlspecialchars($username); ?>">
                                <button type="submit">Promote to Admin</button>
                            </form>
                        <?php else: ?>
                            <span>Already Admin</span>
                        <?php endif; ?>
                    </td>
                </tr>
            <?php endforeach; ?>
        </tbody>
    </table>
    <!-- Odkaz pro odhlášení -->
    <a href="logout.php">Logout</a>
</body>
</html>
