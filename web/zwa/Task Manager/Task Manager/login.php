<?php
/**
 * @file login.php
 * @brief Přihlašovací stránka pro aplikaci Task Manager.
 * 
 * Tento skript umožňuje uživatelům přihlášení do aplikace pomocí uživatelského jména a hesla. 
 * Podporuje také přihlášení administrátorů a možnost pokračovat jako host.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---

/**
 * @var string $username Uživatelské jméno zadané uživatelem. Výchozí hodnota je prázdný řetězec.
 */
$username = '';

/**
 * Zpracování POST požadavku na přihlášení.
 * @details Ověřuje uživatelské jméno a heslo proti uloženým datům. Pokud je přihlášení úspěšné:
 * - Přihlásí administrátora do administrativního panelu.
 * - Přesměruje běžného uživatele na hlavní stránku.
 * Pokud je přihlášení neúspěšné, nastaví chybovou zprávu.
 */
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = htmlspecialchars(trim($_POST['username'])); // Získání uživatelského jména.
    $password = $_POST['password']; // Získání hesla.

    $user = getUserByUsername($username); // Načtení uživatele podle uživatelského jména.

    if ($user && password_verify($password, $user['password'])) { // Ověření hesla.
        $_SESSION['username'] = $user['username']; // Nastavení uživatelského jména do relace.

        // Přesměrování podle typu uživatele.
        if ($_SESSION['username'] === 'admin') {
            header('Location: https://zwa.toad.cz/~sokolant/Task%20Manager/admin_panel.php'); // Přesměrování administrátora.
            exit;
        }
        header('Location: https://zwa.toad.cz/~sokolant/Task%20Manager/index.php'); // Přesměrování běžného uživatele.
        exit;
    } else {
        $_SESSION['error'] = 'Invalid username or password.'; // Nastavení chybové zprávy.
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body class="login-page">
<div id="login-container">
    <h1>Login</h1>
    <?php 
    /**
     * Zobrazení chybové zprávy, pokud je nastavena v relaci.
     */
    if (isset($_SESSION['error'])) {
        echo '<p>' . htmlspecialchars($_SESSION['error']) . '</p>';
        unset($_SESSION['error']); // Vymazání chybové zprávy z relace.
    }
    ?>
    <form action="login.php" method="post">
        <!-- Vstupní pole pro uživatelské jméno -->
        <input type="text" name="username" id="username" required placeholder="Username" 
               value="<?php echo htmlspecialchars($username); ?>"><br>

        <!-- Vstupní pole pro heslo -->
        <input type="password" name="password" id="password" required placeholder="Password"><br>

        <!-- Tlačítko pro odeslání formuláře -->
        <button type="submit">Log in</button>
    </form>
    <p>First time here? <a href="register.php">Create an account</a></p>
    <p><a href="index.php?guest=1">Continue without login</a></p>
</div>
</body>
</html>
