<?php
/**
 * @file promote_user.php
 * @brief Skript pro povýšení uživatele na administrátora.
 * 
 * Tento skript umožňuje povýšení zvoleného uživatele na administrátora. Přístup je omezen 
 * pouze na uživatele s administrátorskými právy.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---

/**
 * Kontrola oprávnění.
 * @details Zajišťuje, že skript může spustit pouze přihlášený uživatel s administrátorskými právy.
 * Pokud podmínka není splněna, uživatel je přesměrován na přihlašovací stránku.
 */
if (!isset($_SESSION['username']) || $_SESSION['username'] !== 'admin') {
    header('Location: login.php');
    exit;
}

/**
 * @var string|null $username Uživatelské jméno uživatele, který má být povýšen na administrátora.
 * @details Hodnota je získána z POST parametru `username`. Pokud není zadána, uživatel je přesměrován
 * zpět na administrativní panel s chybovou zprávou.
 */
$username = $_POST['username'] ?? null;

if (!$username) {
    $_SESSION['error'] = 'Invalid request: username is missing.'; // Nastavení chybové zprávy.
    header('Location: admin_panel.php'); // Přesměrování zpět na administrativní panel.
    exit;
}

/**
 * Povýšení uživatele na administrátora.
 * @details Používá funkci `promoteToAdmin` z knihovny uživatelů.
 */
promoteToAdmin($username);

/**
 * Nastavení úspěšné zprávy a přesměrování zpět na administrativní panel.
 */
$_SESSION['success'] = "User {$username} has been promoted to admin."; // Nastavení úspěšné zprávy.
header('Location: admin_panel.php'); // Přesměrování zpět na administrativní panel.
exit; // Ukončení skriptu.
?>
