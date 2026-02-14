<?php
/**
 * @file update_username.php
 * @brief Aktualizace uživatelského jména.
 * 
 * Tento skript zpracovává POST požadavky na změnu uživatelského jména. 
 * Kontroluje, zda nové uživatelské jméno splňuje požadavky na délku, není již obsazeno 
 * a aktualizuje jej včetně všech poznámek přidružených k uživatelskému účtu.
 */

session_start(); // --- Spuštění uživatelské relace ---

/**
 * Kontrola přihlášení uživatele.
 * Uživatel musí být přihlášen, jinak je přesměrován na přihlašovací stránku.
 */
if (!isset($_SESSION['username'])) {
    header('Location: https://zwa.toad.cz/~sokolant/Task%20Manager/login.php');
    exit;
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---
    include('data_lib.php'); // --- Načtení knihovny pro správu poznámek ---

    /**
     * @var string $new_username Nové uživatelské jméno.
     */
    $new_username = trim($_POST['new_username']);

    // --- Kontrola, zda uživatelské jméno již existuje ---
    if (getUserByUsername($new_username)) {
        $_SESSION['error'] = 'Username already exists.';
        header('Location: https://zwa.toad.cz/~sokolant/Task%20Manager/redact_profile_page.php');
        exit;
    }

    // --- Kontrola délky uživatelského jména ---
    if (strlen($new_username) < 3 || strlen($new_username) > 50) {
        $_SESSION['error'] = 'Username must be between 3 and 50 characters.';
        header('Location: https://zwa.toad.cz/~sokolant/Task%20Manager/redact_profile_page.php');
        exit;
    }

    /**
     * @var array $users Pole obsahující všechny uživatele.
     */
    $users = loadUsers();

    // --- Aktualizace uživatelského jména ---
    foreach ($users as &$user) {
        if ($user['username'] === $_SESSION['username']) {
            $user['username'] = $new_username;
            updateUsernameInNotes($_SESSION['username'], $new_username); // Aktualizace poznámek uživatele.
            break;
        }
    }

    // --- Uložení změn a aktualizace relace ---
    $_SESSION['username'] = $new_username;
    saveUsers($users);
}

// --- Přesměrování na přihlašovací stránku po úspěšné změně ---
header('Location: https://zwa.toad.cz/~sokolant/Task%20Manager/login.php');
exit;
?>
