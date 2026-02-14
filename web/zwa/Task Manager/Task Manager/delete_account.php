<?php
/**
 * @file delete_account.php
 * @brief Odstranění uživatelského účtu.
 * 
 * Tento skript zpracovává odstranění uživatelského účtu a všech jeho poznámek. 
 * Administrátor má právo mazat jiné uživatele. Pokud běžný uživatel provede akci, 
 * odstraní pouze svůj vlastní účet.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---
include('data_lib.php');  // --- Načtení knihovny pro správu poznámek ---

/**
 * Kontrola přihlášení uživatele.
 * @details Pokud uživatel není přihlášen, je přesměrován na přihlašovací stránku.
 */
if (!isset($_SESSION['username'])) {
    header('Location: https://zwa.toad.cz/~sokolant/Task%20Manager/login.php');
    exit;
}

/**
 * @var string|null $username Uživatelské jméno uživatele, který má být odstraněn.
 * @details Získáno z POST požadavku. Pokud není zadáno, hodnota je null.
 */
$username = $_POST['username'] ?? null;

/**
 * Logika pro administrátora.
 * @details Pokud je aktuálně přihlášený uživatel administrátor, může mazat jiné uživatele.
 */
if ($_SESSION['username'] === 'admin') {
    if (!$username) {
        $_SESSION['error'] = 'Invalid request.'; // Nastavení chybové zprávy.
        header('Location: admin_panel.php'); // Přesměrování na administrativní panel.
        exit;
    }

    deleteUserAcc($username); // Odstranění uživatelského účtu.
    deleteUserNotes($username); // Odstranění všech poznámek uživatele.
    header('Location: admin_panel.php'); // Přesměrování na administrativní panel.
    exit;
}

/**
 * Logika pro běžného uživatele.
 * @details Běžný uživatel může odstranit pouze svůj vlastní účet a poznámky.
 */
deleteUserNotes($username); // Odstranění všech poznámek uživatele.
deleteUserAcc($username); // Odstranění uživatelského účtu.

session_destroy(); // Ukončení relace.
header('Location: https://zwa.toad.cz/~sokolant/Task%20Manager/login.php'); // Přesměrování na přihlašovací stránku.
exit;
?>
