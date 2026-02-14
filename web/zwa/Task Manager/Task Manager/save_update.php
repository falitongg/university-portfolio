<?php
/**
 * @file save_update.php
 * @brief Aktualizace existující poznámky.
 * 
 * Tento skript zpracovává POST požadavky na aktualizaci poznámky. Umožňuje změnu názvu a obsahu
 * poznámky pro přihlášené uživatele i poznámky uložené v cookies. Administrátor může upravovat
 * poznámky jiných uživatelů.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('data_lib.php');  // --- Načtení knihovny pro práci s poznámkami ---
include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---

/**
 * Zpracování POST požadavku.
 * @details Skript provádí validaci vstupních dat a aktualizaci poznámky.
 */
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    /**
     * @var string|null $noteID Identifikátor poznámky získaný z POST požadavku.
     * @var string $newTitle Nový název poznámky z POST požadavku.
     * @var string $newContent Nový obsah poznámky z POST požadavku.
     */
    $noteID = $_POST['note_id'] ?? null;
    $newTitle = trim($_POST['title'] ?? '');
    $newContent = trim($_POST['content'] ?? '');

    // --- Validace vstupních dat ---
    if (!$noteID || empty($newTitle) || empty($newContent)) {
        $_SESSION['error'] = 'Note ID, title, and content are required.';
        header('Location: update_note.php');
        exit;
    }
    if (strlen($newTitle) > 255) {
        $_SESSION['error'] = 'Title is too long. Maximum length is 255 characters.';
        header('Location: update_note.php');
        exit;
    }
    if (strlen($newContent) > 10000) {
        $_SESSION['error'] = 'Note is too long. Maximum length is 10,000 characters.';
        header('Location: update_note.php');
        exit;
    }

    // --- Zpracování poznámky pro přihlášené uživatele ---
    if (isset($_SESSION['username'])) {
        $username = $_SESSION['username'];

        // --- Zpracování poznámky administrátorem ---
        if ($username === 'admin') {
            /**
             * @var string|null $targetUsername Uživatelské jméno cílového uživatele, jehož poznámka se upravuje.
             */
            $targetUsername = $_POST['username'] ?? null;
            if (!$targetUsername) {
                $_SESSION['error'] = 'Invalid request: username is required.';
                header('Location: admin_panel.php');
                exit;
            }

            $notes = getNotesForUsername($targetUsername);
            if (isset($notes[$noteID])) {
                saveNoteForUser($targetUsername, $noteID, [
                    'title' => $newTitle,
                    'content' => $newContent
                ]);
                $_SESSION['success'] = 'Note updated successfully.';
            } else {
                $_SESSION['error'] = 'Note not found.';
            }
            header("Location: admin_view_user.php?username=" . urlencode($targetUsername));
            exit;
        }

        // --- Zpracování poznámky pro běžného uživatele ---
        $notes = getNotesForUsername($username);
        if (isset($notes[$noteID])) {
            saveNoteForUser($username, $noteID, [
                'title' => $newTitle,
                'content' => $newContent
            ]);
            $_SESSION['success'] = 'Note updated successfully.';
        } else {
            $_SESSION['error'] = 'Note not found.';
        }
    } else {
        // --- Zpracování poznámky pro nepřihlášeného uživatele (poznámky v cookies) ---
        $notes = loadNotesFromCookie();
        if (isset($notes[$noteID])) {
            $notes[$noteID]['title'] = $newTitle;
            $notes[$noteID]['content'] = $newContent;
            saveNotesToCookie($notes);
            $_SESSION['success'] = 'Note updated successfully.';
        } else {
            $_SESSION['error'] = 'Note not found.';
        }
    }

    // --- Přesměrování na hlavní stránku ---
    header('Location: index.php');
    exit;
}
?>
