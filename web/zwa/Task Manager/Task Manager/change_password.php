<?php
/**
 * @file change_password.php
 * @brief Změna hesla uživatele.
 * 
 * Tento skript umožňuje uživateli změnit své heslo. Provádí kontrolu aktuálního hesla, validaci nového hesla
 * a jeho následné uložení do systému. Pokud podmínky nejsou splněny, uživatel je přesměrován zpět 
 * s chybovou zprávou.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---

/**
 * Kontrola přihlášení uživatele.
 * @details Pokud uživatel není přihlášen, přesměruje se na přihlašovací stránku.
 */
if (!isset($_SESSION['username'])) {
    header('Location: https://zwa.toad.cz/~sokolant/Task%20Manager/login.php');
    exit;
}

/**
 * Zpracování POST požadavku.
 */
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    /**
     * @var array $user Data aktuálně přihlášeného uživatele.
     */
    $user = getUserByUsername($_SESSION['username']);

    /**
     * @var string $curr_password Aktuální heslo zadané uživatelem.
     * @var string $new_password Nové heslo zadané uživatelem.
     * @var string $new_confirm_password Potvrzení nového hesla zadané uživatelem.
     */
    $curr_password = $_POST['current_password'];
    $new_password = $_POST['new_password'];
    $new_confirm_password = $_POST['new_confirm_password'];

    // --- Validace aktuálního hesla ---
    if (!password_verify($curr_password, $user['password'])) {
        $_SESSION['error'] = 'Current password is incorrect.';
        header('Location: change_password.php');
        exit;
    }

    // --- Validace nového hesla ---
    if (strlen($new_password) < 8 || !preg_match('/\d/', $new_password) || !preg_match('/[a-zA-Z]/', $new_password)) {
        $_SESSION['error'] = 'New password must be at least 8 characters long and include at least one letter and one number.';
        header('Location: change_password.php');
        exit;
    }

    // --- Kontrola, zda nové heslo není stejné jako aktuální ---
    if ($new_password == $curr_password) {
        $_SESSION['error'] = 'Passwords are the same.';
        header('Location: change_password.php');
        exit;
    }

    // --- Kontrola shody nového hesla a jeho potvrzení ---
    if ($new_password !== $new_confirm_password) {
        $_SESSION['error'] = 'New password and confirmation do not match.';
        header('Location: change_password.php');
        exit;
    }

    // --- Uložení nového hesla ---
    /**
     * @var array $users Seznam všech uživatelů.
     */
    $users = loadUsers();
    foreach ($users as &$user) {
        if ($user['username'] === $_SESSION['username']) {
            $user['password'] = password_hash($new_password, PASSWORD_BCRYPT); // Hashování nového hesla.
            break;
        }
    }
    saveUsers($users); // Uložení změn.

    // --- Přesměrování na přihlašovací stránku ---
    header('Location: https://zwa.toad.cz/~sokolant/Task%20Manager/login.php');
    exit;
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body class="redact-profile-page">
    <div class="profile-container">
        <div class="profile-section">
            <h1>Change Password</h1>
            <?php
            // --- Zobrazení chybové zprávy ---
            if (isset($_SESSION['error'])) {
                echo '<p class="error-message">' . htmlspecialchars($_SESSION['error']) . '</p>';
                unset($_SESSION['error']);
            }
            ?>
        </div>
        <div class="profile-section">
            <form action="change_password.php" method="post" id="change_password">
                <div class="profile-section">
                    <label for="current_password">Current Password:</label>
                    <input type="password" name="current_password" id="current_password" required><br>
                </div>
                <div class="profile-section">
                    <label for="new_password">New Password:</label>
                    <input type="password" name="new_password" id="new_password" required><br>
                </div>
                <div class="profile-section">
                    <label for="confirm_password">Confirm New Password:</label>
                    <input type="password" name="new_confirm_password" id="confirm_password" required><br>
                </div>
                <div class="profile-section">
                    <button type="submit" id="risky">Change Password</button>
                </div>
            </form>
        </div>
        <div class="profile-section">
            <a href="redact_profile_page.php">Back</a>
        </div>
    </div>
<script src="js/confirm.js"></script>
</body>
</html>
