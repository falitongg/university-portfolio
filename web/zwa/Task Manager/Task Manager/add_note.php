<?php
/**
 * @file add_note.php
 * @brief Zpracování uložení poznámky.
 * 
 * Tento skript zpracovává POST požadavek na uložení poznámky. Podporuje ukládání 
 * poznámek do databáze pro přihlášené uživatele a do cookies pro nepřihlášené uživatele.
 * Vrací odpověď ve formátu JSON pro AJAX požadavky nebo přesměrovává na hlavní stránku.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('data_lib.php'); // --- Načtení knihovny pro správu poznámek ---

ob_start();
/**
 * Získání dat z POST požadavku.
 * 
 * @var string|null $title Název poznámky. Pokud není zadán, hodnota bude null.
 * @var string|null $content Obsah poznámky. Pokud není zadán, hodnota bude null.
 */
$title = isset($_POST['title']) ? trim($_POST['title']) : null;
$content = isset($_POST['content']) ? trim($_POST['content']) : null;

/**
 * Validace vstupních dat.
 * Pokud nejsou zadány povinné údaje (název a obsah), vrací se chybová zpráva:
 * - Pro AJAX požadavky ve formátu JSON.
 * - Pro běžné požadavky přesměrování s chybovou zprávou.
 */
if (empty($title) || empty($content)) {
    if (isset($_SERVER['HTTP_X_REQUESTED_WITH']) && $_SERVER['HTTP_X_REQUESTED_WITH'] === 'XMLHttpRequest') {
        ob_clean();
        echo json_encode([
            'success' => false,
            'message' => 'Title and content cannot be empty.', // Chybová zpráva pro AJAX.
        ]);
    } else {
        $_SESSION['error'] = 'Title and content cannot be empty.'; // Nastavení chyby do relace.
        header('Location: index.php'); // Přesměrování na hlavní stránku.
    }
    exit; // Ukončení skriptu.
}

/**
 * Uložení poznámky.
 * Pokud je uživatel přihlášen, poznámka je uložena do databáze. Pokud není přihlášen, poznámka je uložena do cookies.
 * 
 * @var string $noteID Identifikátor uložené poznámky.
 */
if (isset($_SESSION['username'])) {
    $username = $_SESSION['username'];
    $noteID = addNoteToUser($username, $title, $content); // Uložení poznámky do databáze.
} else {
    $noteID = addNoteToCookie($title, $content); // Uložení poznámky do cookies.
}

/**
 * Odpověď na AJAX požadavek.
 * Pokud byl požadavek AJAX, vrací se JSON obsahující detaily uložené poznámky.
 */
if (isset($_GET['ajax']) && $_GET['ajax'] == 1) {
    ob_clean();
    echo json_encode([
        'success' => true,
        'message' => 'Note saved successfully!'
    ]);
    exit;
}

header('Location: index.php');
exit;


/**
 * Přesměrování na hlavní stránku.
 * Pokud nebyl požadavek AJAX, provede se přesměrování na hlavní stránku.
 */
header('Location: index.php');
exit; // Ukončení skriptu.
