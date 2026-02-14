<?php
/**
 * @file admin_view_user.php
 * @brief Zobrazení poznámek konkrétního uživatele.
 * 
 * Tento skript slouží k zobrazení poznámek konkrétního uživatele, které jsou stránkované. 
 * Přístup mají pouze administrátoři.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('data_lib.php');  // --- Načtení knihovny pro správu poznámek ---
include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---

/**
 * Kontrola přístupu.
 * @details Umožňuje přístup pouze přihlášeným administrátorům. Pokud podmínka není splněna, 
 * uživatel je přesměrován na přihlašovací stránku.
 */
if (!isset($_SESSION['username']) || !isAdmin($_SESSION['username'])) {
    header('Location: login.php');
    exit;
}

/**
 * @var string|null $username Uživatelské jméno uživatele, jehož poznámky se mají zobrazit.
 * @details Získáno z GET parametru `username`. Pokud není zadáno, uživatel je přesměrován 
 * na administrativní panel.
 */
$username = $_GET['username'] ?? null;
if (!$username) {
    header('Location: admin_panel.php');
    exit;
}

/**
 * @var array $notes Pole obsahující všechny poznámky uživatele.
 */
$notes = getNotesForUsername($username);

/**
 * @var int $page Aktuální stránka.
 * @details Získáváno z GET parametru `page`. Pokud není zadáno, nastaví se výchozí hodnota 1.
 */
$page = isset($_GET['page']) ? (int)$_GET['page'] : 1;

/**
 * @var int $limit Počet poznámek na stránku.
 * @details Výchozí hodnota je nastavena na 4.
 */
$limit = 4;

/**
 * @var array $pagination_data Data stránkování.
 * @details Obsahuje poznámky pro aktuální stránku a informace o celkovém počtu stránek.
 */
$pagination_data = getPaginatedNotesFromArray($notes, $page, $limit);

/**
 * @var array $notes Poznámky pro aktuální stránku.
 */
$notes = $pagination_data['notes'];

/**
 * @var int $total_notes Celkový počet stránek.
 */
$total_notes = $pagination_data['pages'];
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notes of <?php echo htmlspecialchars($username); ?></title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div id="info">
        <h1>Notes of <?php echo htmlspecialchars($username); ?></h1>
        <!-- Kontejner pro poznámky -->
        <div id="notesContainer">
            <?php include 'notes_partial.php'; ?>
        </div>
    </div>
</body>
</html>
