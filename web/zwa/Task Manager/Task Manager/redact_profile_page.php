<?php
/**
 * @file redact_profile_page.php
 * @brief Stránka pro úpravu uživatelského profilu.
 * 
 * Tato stránka umožňuje přihlášenému uživateli spravovat jeho profil. 
 * Uživatel může aktualizovat profilovou fotku, změnit uživatelské jméno, změnit heslo 
 * nebo smazat svůj účet.
 */

session_start(); // --- Spuštění uživatelské relace ---

/**
 * Kontrola přihlášení.
 * @details Zajišťuje, že stránku mohou zobrazit pouze přihlášení uživatelé.
 * Pokud podmínka není splněna, uživatel je přesměrován na přihlašovací stránku.
 */
if (!isset($_SESSION['username'])) {
    header('Location: https://zwa.toad.cz/~sokolant/Task%20Manager/login.php');
    exit;
}

/**
 * @var string $username Uživatelské jméno aktuálně přihlášeného uživatele.
 */
$username = $_SESSION['username'];

include('data_lib.php');  // --- Načtení knihovny pro správu dat ---
include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---

/**
 * @var array $user Data aktuálně přihlášeného uživatele.
 * @var string|null $profile_picture URL profilové fotky uživatele nebo null, pokud není nastavena.
 */
$user = getUserByUsername($username);
$profile_picture = $user['profile_picture'] ?? null;
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Redact Profile Page</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body class="redact-profile-page">
    <div class="profile-container">
        <!-- Sekce pro úpravu profilové fotky -->
        <div class="profile-section">
            <h3>Profile Picture</h3>
            <img src="<?php echo htmlspecialchars($profile_picture); ?>" alt="Profile Picture" width="150">
            <form action="update_profile_picture.php" method="get" enctype="multipart/form-data">
                <button class="edit-button" type="submit">Edit</button>
            </form>
        </div>

        <!-- Sekce pro změnu uživatelského jména -->
        <div class="profile-section">
            <form action="update_username.php" method="post">
                <h3>Username</h3>
                <p><?php echo htmlspecialchars($user['username']); ?></p>
                <label for="new_username">New Username:</label>
                <?php if (isset($_SESSION['error'])): ?>
                    <p class="error-message"><?php echo htmlspecialchars($_SESSION['error']); ?></p>
                    <?php unset($_SESSION['error']); ?>
                <?php endif; ?>
                <input type="text" name="new_username" id="new_username" placeholder="<?php echo htmlspecialchars($user['username']); ?>" required><br>
                <button type="submit" id="risky" class="update-button">Update Username</button>
            </form>
        </div>

        <!-- Sekce pro změnu hesla -->
        <div class="profile-section">
            <form action="change_password.php" method="get">
                <button type="submit" id="change_password" class="change-password-button">Change password</button>
            </form>
        </div>

        <!-- Sekce pro smazání účtu -->
        <div class="profile-section">
            <form action="delete_account.php" method="post">
                <input type="hidden" name="username" value="<?php echo htmlspecialchars($user['username']); ?>"><br>
                <button type="submit" id="delete" class="delete-account-button">Delete my account</button>
            </form>
        </div>

        <!-- Tlačítko pro návrat na hlavní stránku -->
        <div class="profile-section">
            <a href="index.php" class="back-button">Back</a>
        </div>
    </div>
<script src="js/confirm.js"></script>
</body>
</html>
