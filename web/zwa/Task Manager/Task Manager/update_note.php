<?php
/**
 * @file update_note.php
 * @brief Zobrazení poznámky pro úpravu.
 * 
 * Tento skript slouží k zobrazení poznámky pro její následnou úpravu. Podporuje jak přihlášené uživatele,
 * tak administrátory, kteří mohou upravovat poznámky jiných uživatelů. Nepřihlášení uživatelé mají přístup
 * pouze k poznámkám uloženým v cookies.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('data_lib.php');  // --- Načtení knihovny pro správu poznámek ---
include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---

/**
 * @var string|null $noteID Identifikátor poznámky z GET parametru.
 */
$noteID = $_GET['note_id'] ?? null;

// --- Kontrola existence note_id ---
if (!$noteID) {
    $_SESSION['error'] = 'Invalid request: note_id is missing.';
    header('Location: index.php');
    exit;
}

/**
 * @var string|null $username Uživatelské jméno aktuálního uživatele.
 * @var array|null $note Pole obsahující data poznámky.
 */
if (!isset($_SESSION['username'])) {
    // --- Zpracování pro nepřihlášené uživatele ---
    $notes = loadNotesFromCookie();
    $note = $notes[$noteID] ?? null;

    if (!$note) {
        $_SESSION['error'] = 'Note not found.';
        header('Location: index.php');
        exit;
    }
    $username = null;
} else {
    // --- Zpracování pro přihlášené uživatele ---
    $username = $_SESSION['username'];

    if (isAdmin($_SESSION['username'])) {
        /**
         * @var string|null $targetUsername Uživatelské jméno cílového uživatele (pro administrátora).
         */
        $targetUsername = $_GET['username'] ?? null;

        if (!$targetUsername) {
            $_SESSION['error'] = 'Invalid request: username is missing.';
            header('Location: admin_panel.php');
            exit;
        }

        $notes = getNotesForUsername($targetUsername);
        $note = $notes[$noteID] ?? null;

        if (!$note) {
            $_SESSION['error'] = 'Note not found.';
            header("Location: admin_view_user.php?username=" . urlencode($targetUsername));
            exit;
        }
    } else {
        // --- Zpracování pro běžné uživatele ---
        $notes = getNotesForUsername($username);
        $note = $notes[$noteID] ?? null;

        if (!$note) {
            $_SESSION['error'] = 'Note not found.';
            header('Location: index.php');
            exit;
        }
    }
}

/**
 * @var string $backUrl URL pro návrat zpět.
 */
$backUrl = $username === 'admin' ? 'admin_view_user.php?username=' . urlencode($targetUsername) : 'index.php';
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Note</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body class="update-note-page">
    <div id="update-note-container">
        <form action="save_update.php" method="post">
            <input type="text" name="title" id="title" value="<?php echo htmlspecialchars($note['title']); ?>" required>
            
            <textarea name="content" id="content" required><?php echo htmlspecialchars($note['content']); ?></textarea>
            
            <input type="hidden" name="note_id" value="<?php echo htmlspecialchars($noteID); ?>">
            <input type="hidden" name="username" value="<?php echo htmlspecialchars($username === 'admin' ? $targetUsername : $username); ?>">
            
            <button type="submit">Save</button>
        </form>
        <a href="<?php echo htmlspecialchars($backUrl); ?>" id="back">Back</a>
    </div>
</body>
</html>
