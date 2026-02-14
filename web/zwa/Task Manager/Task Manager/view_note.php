<?php
/**
 * @file view_note.php
 * @brief Zobrazení detailu poznámky.
 * 
 * Tento skript načítá a zobrazuje detail konkrétní poznámky. Uživatelé mohou zobrazit
 * obsah poznámky a vytisknout ji. Administrátoři mohou zobrazit poznámky jiných uživatelů.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('data_lib.php');  // --- Načtení knihovny pro správu poznámek ---
include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---

/**
 * @var string|null $noteID Identifikátor poznámky.
 * @details Získáno z GET parametru `note_id`. Pokud není zadán, skript skončí s chybou.
 */
$noteID = $_GET['note_id'] ?? null;
if (!$noteID) {
    echo 'Invalid note ID.';
    exit;
}

/**
 * @var array|null $note Pole obsahující data o poznámce.
 * @details Načítá poznámku z databáze nebo cookies podle stavu přihlášení uživatele.
 */
if (isset($_SESSION['username'])) {
    if ($_SESSION['username'] === 'admin') {
        $username = $_GET['username'] ?? null; // Uživatelské jméno z GET parametru
        $note = getNoteById($username, $noteID);
    } else {
        $username = $_SESSION['username'];
        $note = getNoteById($username, $noteID);
    }
} else {
    $notes = loadNotesFromCookie();
    $note = $notes[$noteID] ?? null;
}

/**
 * Kontrola existence poznámky.
 * Pokud poznámka neexistuje, skript skončí s chybovou zprávou.
 */
if (!$note) {
    echo 'Note not found.';
    exit;
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?php echo htmlspecialchars($note['title']); ?></title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div id="note-container">
    <!-- Zobrazení poznámky -->
    <h1><?php echo htmlspecialchars($note['title']); ?></h1>
    <p><?php echo nl2br(htmlspecialchars($note['content'])); ?></p>

    <!-- Ovládací tlačítka -->
    <div class="button-container">
        <?php if (isset($_SESSION['username']) && $_SESSION['username'] === 'admin'): ?>
            <a href="admin_view_user.php?username=<?php echo urlencode($username); ?>">Back to Notes</a>
            <a href="#" class="print_note">Print</a>
        <?php else: ?>
            <a href="index.php">Back to Notes</a>
            <a href="#" class="print_note">Print</a>
        <?php endif; ?>
    </div>
</div>
<script src="js/print.js"></script>
</body>
</html>
