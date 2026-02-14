<?php
/**
 * @file data_lib.php
 * @brief Knihovna funkcí pro správu poznámek.
 * 
 * Tato knihovna obsahuje funkce pro ukládání, načítání, mazání a stránkování poznámek. 
 * Podporuje práci s poznámkami uživatelů uloženými v databázi nebo v cookies.
 */

/**
 * Uloží všechny poznámky do souboru.
 * 
 * @param array $notes Pole obsahující všechny poznámky.
 * @return void
 */
function saveNotes(array $notes): void {
    $str = json_encode($notes);
    file_put_contents('data.json', $str);
}

/**
 * Načte všechny poznámky ze souboru.
 * 
 * @return array Pole poznámek nebo prázdné pole, pokud soubor neexistuje.
 */
function loadNotes(): array {
    if (!file_exists('data.json')) {
        return [];
    }
    $str = file_get_contents('data.json');
    return $str ? json_decode($str, true) : [];
}

/**
 * Přidá poznámku konkrétnímu uživateli.
 * 
 * @param string $username Uživatelské jméno.
 * @param string $title Název poznámky.
 * @param string $content Obsah poznámky.
 * @return string Unikátní identifikátor přidané poznámky.
 */
function addNoteToUser(string $username, string $title, string $content): string {
    $notes = loadNotes();

    if (!isset($notes[$username])) {
        $notes[$username] = [];
    }

    $noteID = uniqid();
    $notes[$username][$noteID] = [
        'title' => $title,
        'content' => $content,
        'created_at' => date('Y-m-d H:i:s'),
    ];

    saveNotes($notes);

    return $noteID;
}

/**
 * Načte poznámky konkrétního uživatele a seřadí je podle data vytvoření.
 * 
 * @param string $username Uživatelské jméno.
 * @return array Pole poznámek.
 */
function getNotesForUsername(string $username): array {
    $notes = loadNotes();
    if (!isset($notes[$username])) {
        return [];
    }
    
    uasort($notes[$username], function($a, $b) {
        $timeA = isset($a['created_at']) ? strtotime($a['created_at']) : 0;
        $timeB = isset($b['created_at']) ? strtotime($b['created_at']) : 0;
        return $timeB - $timeA;
    });

    return $notes[$username];
}

/**
 * Upraví existující poznámku konkrétního uživatele.
 * 
 * @param string $username Uživatelské jméno.
 * @param string $noteID Identifikátor poznámky.
 * @param array $updated_note Aktualizovaný obsah poznámky.
 * @return void
 */
function saveNoteForUser(string $username, string $noteID, array $updated_note): void {
    $notes = loadNotes();
    if (isset($notes[$username][$noteID])) {
        $existing_note = $notes[$username][$noteID];
        $updated_note['created_at'] = $existing_note['created_at'];
        $notes[$username][$noteID] = array_merge($existing_note, $updated_note);
        saveNotes($notes);
    } else {
        echo "Error: Note not found";
    }
}

/**
 * Odstraní poznámku konkrétního uživatele.
 * 
 * @param string $username Uživatelské jméno.
 * @param string $noteID Identifikátor poznámky.
 * @return void
 */
function deleteNote(string $username, string $noteID): void {
    $notes = loadNotes();
    if (isset($notes[$username][$noteID])) {
        unset($notes[$username][$noteID]);
        saveNotes($notes);
    } else {
        echo "Error: Note not found";
    }
}

/**
 * Aktualizuje uživatelské jméno v uložených poznámkách.
 * 
 * @param string $old_username Staré uživatelské jméno.
 * @param string $new_username Nové uživatelské jméno.
 * @return void
 */
function updateUsernameInNotes(string $old_username, string $new_username): void {
    $notes = loadNotes();
    if (isset($notes[$old_username])) {
        $notes[$new_username] = $notes[$old_username];
        unset($notes[$old_username]);
        saveNotes($notes);
    }
}

/**
 * Odstraní všechny poznámky konkrétního uživatele.
 * 
 * @param string $username Uživatelské jméno.
 * @return void
 */
function deleteUserNotes(string $username): void {
    $notes = loadNotes();
    unset($notes[$username]);
    saveNotes($notes);
}

/**
 * Načte stránkované poznámky pro konkrétního uživatele.
 * 
 * @param string $username Uživatelské jméno.
 * @param int $page Aktuální stránka.
 * @param int $limit Počet poznámek na stránku.
 * @return array Stránkované poznámky a metadata stránkování.
 */
function getPaginatedNotes(string $username, int $page, int $limit): array {
    $user_notes = getNotesForUsername($username);
    $total_notes = count($user_notes);

    if ($total_notes === 0) {
        return [
            'notes' => [],
            'total' => 0,
            'page' => $page,
            'limit' => $limit,
            'pages' => 0,
        ];
    }

    $offset = ($page - 1) * $limit;

    $paginated_notes = array_slice($user_notes, $offset, $limit, true);

    return [
        'notes' => $paginated_notes,
        'total' => $total_notes,
        'page' => $page,
        'limit' => $limit,
        'pages' => ceil($total_notes / $limit),
    ];
}

/**
 * Načte poznámky uložené v cookies a seřadí je podle data vytvoření.
 * 
 * @return array Pole poznámek.
 */
function loadNotesFromCookie(): array {
    $notes = isset($_COOKIE['notes']) ? json_decode($_COOKIE['notes'], true) : [];
    uasort($notes, function($a, $b) {
        $timeA = isset($a['created_at']) ? strtotime($a['created_at']) : 0;
        $timeB = isset($b['created_at']) ? strtotime($b['created_at']) : 0;
        return $timeB - $timeA;
    });

    return $notes;
}

/**
 * Uloží poznámky do cookies.
 * 
 * @param array $notes Pole poznámek.
 * @return void
 */
function saveNotesToCookie(array $notes): void {
    setcookie('notes', json_encode($notes), time() + (3600 * 24 * 30), '/');
}

/**
 * Přidá poznámku do cookies.
 * 
 * @param string $title Název poznámky.
 * @param string $content Obsah poznámky.
 * @return string Unikátní identifikátor přidané poznámky.
 */
function addNoteToCookie(string $title, string $content): string {
    $notes = loadNotesFromCookie();
    $noteID = uniqid();
    $notes[$noteID] = [
        'title' => $title,
        'content' => $content,
        'created_at' => date('Y-m-d H:i:s'),
    ];
    saveNotesToCookie($notes);
    return $noteID;
}

/**
 * Odstraní poznámku z cookies.
 * 
 * @param string $noteID Identifikátor poznámky.
 * @return void
 */
function deleteNoteFromCookie(string $noteID): void {
    $notes = loadNotesFromCookie();
    if (isset($notes[$noteID])) {
        unset($notes[$noteID]);
        saveNotesToCookie($notes);
    } else {
        echo "Error: Note not found in cookie";
    }
}

/**
 * Načte stránkované poznámky z pole poznámek.
 * 
 * @param array $notesArray Pole poznámek.
 * @param int $page Aktuální stránka.
 * @param int $limit Počet poznámek na stránku.
 * @return array Stránkované poznámky a metadata stránkování.
 */
function getPaginatedNotesFromArray(array $notesArray, int $page, int $limit): array {
    $total_notes = count($notesArray);

    if ($total_notes === 0) {
        return [
            'notes' => [],
            'total' => 0,
            'page' => $page,
            'limit' => $limit,
            'pages' => 0,
        ];
    }

    $offset = ($page - 1) * $limit;

    $paginated_notes = array_slice($notesArray, $offset, $limit, true);

    return [
        'notes' => $paginated_notes,
        'total' => $total_notes,
        'page' => $page,
        'limit' => $limit,
        'pages' => ceil($total_notes / $limit),
    ];
}

/**
 * Načte konkrétní poznámku podle ID uživatele.
 * 
 * @param string $username Uživatelské jméno.
 * @param string $noteID Identifikátor poznámky.
 * @return array|null Poznámka nebo null, pokud není nalezena.
 */
function getNoteById(string $username, string $noteID): ?array {
    $notes = getNotesForUsername($username);
    return $notes[$noteID] ?? null;
}

/**
 * Načte konkrétní poznámku z cookies podle ID.
 * 
 * @param string $noteID Identifikátor poznámky.
 * @return array|null Poznámka nebo null, pokud není nalezena.
 */
function getNoteByIdFromCookie(string $noteID): ?array {
    $notes = loadNotesFromCookie();
    return $notes[$noteID] ?? null;
}
