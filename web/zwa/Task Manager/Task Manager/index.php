<?php
/**
 * @file index.php
 * @brief Hlavní stránka aplikace Task Manager.
 * 
 * Tento skript načítá a zobrazuje poznámky uživatele, přičemž podporuje přihlášené 
 * i nepřihlášené uživatele (hosty). Pro poznámky je k dispozici stránkování.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('data_lib.php');  // --- Načtení knihovny pro správu poznámek ---
include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---

/**
 * Kontrola přihlášení uživatele.
 * @details Pokud uživatel není přihlášen, kontroluje se možnost přístupu jako host.
 * Hosté mají omezené funkce a jejich poznámky jsou uloženy v cookies.
 */
if (!isset($_SESSION['username'])) {
    if (isset($_GET['guest'])) {
        setcookie('guest', '1', time() + 3600, '/'); // Nastavení cookies pro hosta.
    } elseif (!isset($_COOKIE['guest'])) {
        header('Location: login.php'); // Přesměrování na přihlašovací stránku.
        exit;
    }
}

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
 * Logika pro přihlášené a nepřihlášené uživatele.
 * @details Přihlášení uživatelé mají své poznámky uloženy v databázi, zatímco poznámky hostů 
 * jsou uloženy v cookies. Stránkování je podporováno pro oba typy uživatelů.
 */
if (!isset($_SESSION['username'])) {
    $notes = loadNotesFromCookie(); // Načtení poznámek z cookies.
    $pagination_data = getPaginatedNotesFromArray($notes, $page, $limit);
    $profile_picture = null; // Hosté nemají profilový obrázek.
} else {
    $username = $_SESSION['username'];
    $notes = getNotesForUsername($username); // Načtení poznámek z databáze.
    $pagination_data = getPaginatedNotes($username, $page, $limit); // Stránkování poznámek.
    $user = getUserByUsername($username); // Načtení dat uživatele.
    $profile_picture = $user['profile_picture'] ?? null; // Profilový obrázek uživatele.
}

/**
 * @var array $notes Poznámky pro aktuální stránku.
 */
$notes = $pagination_data['notes'];

/**
 * @var int $total_notes Celkový počet stránek.
 */
$total_notes = $pagination_data['pages'];

/**
 * AJAX režim.
 * @details Pokud je požadavek typu AJAX (GET parametr `ajax`), vrací pouze část HTML s poznámkami.
 */
if (isset($_GET['ajax']) && $_GET['ajax'] == 1) {
    include 'notes_partial.php';
    exit;
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task Manager</title>
</head>
<body id="index">
    <h1>Welcome to Task Manager</h1>
    <?php if (isset($_SESSION['username'])): ?>
        <?php if ($profile_picture): ?>
            <div>
                <h3>Profile Picture</h3>
                <img src="<?php echo htmlspecialchars($profile_picture); ?>" alt="Profile Picture">
            </div>
        <?php else: ?>
            <p>No profile picture uploaded.</p>
        <?php endif; ?>
        <div class="nvg">
            <a href="logout.php" id="logout">Logout</a>
            <a href="redact_profile_page.php" id="redact_profile">Redact profile</a>
        </div>
    <?php else: ?>
        <p>The capabilities of the unauthorized version are very limited. Your notes are stored in cookies and will only be accessible on this device. </p>
        <div class="nvg">
            <a href="https://zwa.toad.cz/~sokolant/Task%20Manager/login.php" id="log">Login</a>
        </div>
    <?php endif; ?>
    <button id="showFormButton">+</button>
    <div id="formContainer" class="hidden">
        <form id="infoForm" action="add_note.php" method="post">
            <div id="div_name">
                <input type="text" id="name" name="title" maxlength="255" placeholder="Title">
            </div>
            <div id="div_task">
                <textarea id="task" name="content" placeholder="Content" maxlength="10000" required></textarea>
            </div>
            <div id="div_button">
                <button id="button" type="submit">Save Note</button>
            </div>
        </form>
    </div>
    <div id="info">
        <h2>Your Notes:</h2>
        <div id="notesContainer">
            <?php include 'notes_partial.php'; ?>
        </div>
    </div>
    <script src="js/script.js"></script>
</body>
<link rel="stylesheet" href="css/styles.css">
</html>
