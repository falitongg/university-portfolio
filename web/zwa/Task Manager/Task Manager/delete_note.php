<?php
/**
 * @file delete_note.php
 * @brief Smazání poznámky.
 * 
 * Tento skript slouží ke smazání poznámky buď z databáze (pro přihlášené uživatele), 
 * nebo z cookies (pro nepřihlášené uživatele). Administrátor může mazat poznámky 
 * jiných uživatelů.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---
include('data_lib.php');  // --- Načtení knihovny pro správu poznámek ---

/**
 * Zpracování POST požadavku.
 * @details Skript se spustí pouze při odeslání POST požadavku.
 */
if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    /**
     * @var string|null $noteID Identifikátor poznámky, která má být smazána.
     * @details Získáno z POST parametru `note_id`. Pokud není zadán, vrací chybu.
     */
    $noteID = $_POST['note_id'] ?? null;

    if (!$noteID) {
        $_SESSION['error'] = 'Error: Note ID is not provided!'; // Nastavení chybové zprávy.
        header('Location: index.php'); // Přesměrování na hlavní stránku.
        exit;
    }

    /**
     * Logika pro přihlášené uživatele.
     * @details Uživatelé mohou mazat pouze své vlastní poznámky. Administrátor 
     * má navíc možnost mazat poznámky jiných uživatelů.
     */
    if (isset($_SESSION['username'])) {
        $username = $_SESSION['username'];

        // Kontrola administrátorských práv
        if (isAdmin($username)) {

            /**
             * @var string|null $targetUsername Uživatelské jméno vlastníka poznámky.
             * @details Získáno z POST parametru `username`. Pokud není zadán, vrací chybu.
             */
            $targetUsername = $_POST['username'] ?? null;
            if (!$targetUsername) {
                $_SESSION['error'] = 'Invalid request.';
                header('Location: admin_panel.php');
                exit;
            }

            // Načtení poznámek cílového uživatele
            $notes = getNotesForUsername($targetUsername);
            if (isset($notes[$noteID])) {
                deleteNote($targetUsername, $noteID); // Smazání poznámky.
                // Pokud administrátor maže svou vlastní poznámku, přesměrování na index.
                if ($targetUsername == $username) {
                    header('Location: index.php');
                    exit;
                }
            } else {
                $_SESSION['error'] = 'Note not found.';
            }

            // Přesměrování zpět na stránku správce uživatele
            header("Location: admin_view_user.php?username=" . urlencode($targetUsername));
            exit;
        }

        // Kontrola a smazání poznámky přihlášeného uživatele
        $notes = getNotesForUsername($username);
        if (!isset($notes[$noteID])) {
            $_SESSION['error'] = 'Note not found!';
            header('Location: index.php');
            exit;
        }

        deleteNote($username, $noteID); // Smazání poznámky uživatele.
    } else {

        /**
         * Logika pro nepřihlášené uživatele.
         * @details Poznámky jsou uloženy v cookies. Smazání probíhá lokálně.
         */
        $notes = loadNotesFromCookie();
        if (!isset($notes[$noteID])) {
            $_SESSION['error'] = 'Note not found!';
            header('Location: index.php');
            exit;
        }

        deleteNoteFromCookie($noteID); // Smazání poznámky z cookies.
    }

    // Přesměrování na hlavní stránku
    header('Location: index.php');
    exit;
}
?>
<?php
/**
 * @file delete_note.php
 * @brief Smazání poznámky.
 * 
 * Tento skript slouží ke smazání poznámky buď z databáze (pro přihlášené uživatele), 
 * nebo z cookies (pro nepřihlášené uživatele). Administrátor může mazat poznámky 
 * jiných uživatelů.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---
include('data_lib.php');  // --- Načtení knihovny pro správu poznámek ---

/**
 * Zpracování POST požadavku.
 * @details Skript se spustí pouze při odeslání POST požadavku.
 */
if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    /**
     * @var string|null $noteID Identifikátor poznámky, která má být smazána.
     * @details Získáno z POST parametru `note_id`. Pokud není zadán, vrací chybu.
     */
    $noteID = $_POST['note_id'] ?? null;

    if (!$noteID) {
        $_SESSION['error'] = 'Error: Note ID is not provided!'; // Nastavení chybové zprávy.
        header('Location: index.php'); // Přesměrování na hlavní stránku.
        exit;
    }

    /**
     * Logika pro přihlášené uživatele.
     * @details Uživatelé mohou mazat pouze své vlastní poznámky. Administrátor 
     * má navíc možnost mazat poznámky jiných uživatelů.
     */
    if (isset($_SESSION['username'])) {
        $username = $_SESSION['username'];

        // Kontrola administrátorských práv
        if (isAdmin($username)) {

            /**
             * @var string|null $targetUsername Uživatelské jméno vlastníka poznámky.
             * @details Získáno z POST parametru `username`. Pokud není zadán, vrací chybu.
             */
            $targetUsername = $_POST['username'] ?? null;
            if (!$targetUsername) {
                $_SESSION['error'] = 'Invalid request.';
                header('Location: admin_panel.php');
                exit;
            }

            // Načtení poznámek cílového uživatele
            $notes = getNotesForUsername($targetUsername);
            if (isset($notes[$noteID])) {
                deleteNote($targetUsername, $noteID); // Smazání poznámky.
                // Pokud administrátor maže svou vlastní poznámku, přesměrování na index.
                if ($targetUsername == $username) {
                    header('Location: index.php');
                    exit;
                }
            } else {
                $_SESSION['error'] = 'Note not found.';
            }

            // Přesměrování zpět na stránku správce uživatele
            header("Location: admin_view_user.php?username=" . urlencode($targetUsername));
            exit;
        }

        // Kontrola a smazání poznámky přihlášeného uživatele
        $notes = getNotesForUsername($username);
        if (!isset($notes[$noteID])) {
            $_SESSION['error'] = 'Note not found!';
            header('Location: index.php');
            exit;
        }

        deleteNote($username, $noteID); // Smazání poznámky uživatele.
    } else {

        /**
         * Logika pro nepřihlášené uživatele.
         * @details Poznámky jsou uloženy v cookies. Smazání probíhá lokálně.
         */
        $notes = loadNotesFromCookie();
        if (!isset($notes[$noteID])) {
            $_SESSION['error'] = 'Note not found!';
            header('Location: index.php');
            exit;
        }

        deleteNoteFromCookie($noteID); // Smazání poznámky z cookies.
    }

    // Přesměrování na hlavní stránku
    header('Location: index.php');
    exit;
}
?>
