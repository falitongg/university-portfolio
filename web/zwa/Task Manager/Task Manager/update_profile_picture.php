<?php
/**
 * @file update_profile_picture.php
 * @brief Aktualizace profilového obrázku uživatele.
 * 
 * Tento skript umožňuje uživatelům nahrát nový profilový obrázek. Obrázek je zpracován, 
 * zmenšen na standardní velikost (150x150 px) a uložen. Podporuje pouze přihlášené uživatele.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---

/**
 * @var string $username Uživatelské jméno aktuálního uživatele.
 */
if (!isset($_SESSION['username'])) {
    header('Location: https://zwa.toad.cz/~sokolant/Task%20Manager/login.php');
    exit;
}
$username = $_SESSION['username'];

/**
 * Zpracování POST požadavku.
 */
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // --- Kontrola, zda byl soubor nahrán ---
    if (!isset($_FILES['profile_picture']) || $_FILES['profile_picture']['error'] !== UPLOAD_ERR_OK) {
        $_SESSION['error'] = 'No file uploaded or file upload error.';
        header('Location: redact_profile_page.php');
        exit;
    }

    /**
     * @var string $fileTmpPath Dočasná cesta k nahranému souboru.
     * @var string $fileName Název souboru.
     * @var int $fileSize Velikost souboru.
     * @var string $fileType Typ souboru.
     */
    $fileTmpPath = $_FILES['profile_picture']['tmp_name'];
    $fileName = $_FILES['profile_picture']['name'];
    $fileSize = $_FILES['profile_picture']['size'];
    $fileType = $_FILES['profile_picture']['type'];

    // --- Validace typu souboru ---
    $allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
    if (!in_array($fileType, $allowedTypes)) {
        $_SESSION['error'] = 'Only JPEG, PNG, and GIF files are allowed.';
        header('Location: redact_profile_page.php');
        exit;
    }

    // --- Validace velikosti souboru ---
    if ($fileSize > 2 * 1024 * 1024) { // 2 MB
        $_SESSION['error'] = 'File size must not exceed 2MB.';
        header('Location: redact_profile_page.php');
        exit;
    }

    /**
     * @var string $uploadDir Cesta pro uložení nahraných souborů.
     * @var string $newFileName Unikátní název souboru.
     * @var string $destPath Cesta pro uložení zpracovaného souboru.
     */
    $uploadDir = 'uploads/';
    if (!file_exists($uploadDir)) {
        if (!mkdir($uploadDir, 0755, true)) {
            $_SESSION['error'] = 'Error: Unable to create uploads directory.';
            header('Location: redact_profile_page.php');
            exit;
        }
    }
    $newFileName = uniqid() . '-' . basename($fileName);
    $destPath = $uploadDir . $newFileName;

    /**
     * Zpracování a zmenšení obrázku.
     * @var string $convertedImagePath Cesta pro zmenšený obrázek.
     */
    $convertedImagePath = $uploadDir . 'converted_' . $newFileName;

    /**
 * @brief Zpracování a zmenšení nahraného obrázku.
 * 
 * Tento blok kódu zajišťuje zmenšení obrázku na pevně dané rozměry (150x150) 
 * a jeho uložení v příslušném formátu (JPEG, PNG, GIF). Pokud během zpracování
 * dojde k chybě, uživatel je přesměrován s příslušnou chybovou zprávou.
 * 
 * @param string $fileTmpPath Dočasná cesta k nahranému obrázku.
 * @param string $fileType Typ nahraného souboru (image/jpeg, image/png, image/gif).
 * @param string $convertedImagePath Cesta, kam se uloží zmenšený obrázek.
 * @param string $destPath Cesta k finálnímu obrázku, který bude uložen.
 * @return void
 */

list($originalWidth, $originalHeight) = getimagesize($fileTmpPath); ///< Získání původní šířky a výšky obrázku.
$newWidth = 150; ///< Nová šířka obrázku.
$newHeight = 150; ///< Nová výška obrázku.

$sourceImage = null; ///< Inicializace proměnné pro zdrojový obrázek.

/**
 * Načtení obrázku podle jeho typu (JPEG, PNG, GIF).
 * 
 * @details Každý typ obrázku je zpracován odpovídající funkcí PHP.
 */
if ($fileType === 'image/jpeg') {
    $sourceImage = imagecreatefromjpeg($fileTmpPath); ///< Načtení JPEG obrázku.
} elseif ($fileType === 'image/png') {
    $sourceImage = imagecreatefrompng($fileTmpPath); ///< Načtení PNG obrázku.
} elseif ($fileType === 'image/gif') {
    $sourceImage = imagecreatefromgif($fileTmpPath); ///< Načtení GIF obrázku.
}

/**
 * Kontrola, zda byl obrázek úspěšně načten.
 */
if ($sourceImage) {
    /**
     * Vytvoření prázdného obrázku s novými rozměry.
     * 
     * @details Používá funkci imagecreatetruecolor pro vytvoření prázdného obrázku
     * s nastavenou šířkou a výškou.
     */
    $resizedImage = imagecreatetruecolor($newWidth, $newHeight);

    /**
     * Přizpůsobení zdrojového obrázku do cílových rozměrů.
     * 
     * @details Přepočítání rozměrů a překreslení obrázku do cílové velikosti
     * pomocí funkce imagecopyresampled.
     */
    imagecopyresampled(
        $resizedImage,        ///< Cílový obrázek.
        $sourceImage,         ///< Zdrojový obrázek.
        0, 0,                 ///< Levý horní roh cílového obrázku.
        0, 0,                 ///< Levý horní roh zdrojového obrázku.
        $newWidth, $newHeight, ///< Cílová šířka a výška.
        $originalWidth, $originalHeight ///< Původní šířka a výška.
    );

    /**
     * Uložení zmenšeného obrázku podle jeho typu.
     */
    if ($fileType === 'image/jpeg') {
        imagejpeg($resizedImage, $convertedImagePath, 90); ///< Uložení JPEG obrázku s kvalitou 90.
    } elseif ($fileType === 'image/png') {
        imagepng($resizedImage, $convertedImagePath, 9); ///< Uložení PNG obrázku s maximální kompresí.
    } elseif ($fileType === 'image/gif') {
        imagegif($resizedImage, $convertedImagePath); ///< Uložení GIF obrázku.
    }

    /**
     * Uvolnění paměti pro zdrojový i zmenšený obrázek.
     */
    imagedestroy($sourceImage); ///< Uvolnění paměti pro zdrojový obrázek.
    imagedestroy($resizedImage); ///< Uvolnění paměti pro zmenšený obrázek.

    $destPath = $convertedImagePath; ///< Aktualizace cesty k výslednému obrázku.
} else {
    /**
     * Chyba při zpracování obrázku.
     * 
     * @details Nastaví chybovou zprávu do session a přesměruje uživatele.
     */
    $_SESSION['error'] = 'There was an error processing the uploaded file.';
    header('Location: redact_profile_page.php');
    exit;
}


    // --- Aktualizace profilového obrázku ---
    updateProfilePicture($username, $destPath);

    $_SESSION['success'] = 'Profile picture updated successfully.';
    header('Location: redact_profile_page.php');
    exit;
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Profile Picture</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body class="redact-profile-page">
    <div class="profile-container">
        <div class="profile-section">
            <h1>Update Profile Picture</h1>
            <?php 
                    if(isset($_SESSION['error'])){
                        echo '<p class="error-message">' . htmlspecialchars($_SESSION['error']) . '</p>';
                        unset($_SESSION['error']);
                    }
                ?>
        </div>

        <div class="profile-section">
            <form action="update_profile_picture.php" method="post" enctype="multipart/form-data">
                <div class="profile-section">
                    <label for="profile_picture">Profile Picture:</label>
                    <input type="file" name="profile_picture" id="profile_picture" accept="image/*">
                    <button type="submit">Save</button>
                
                </div>

            </form>
        </div>


    <div class="profile-section">
        <a href="redact_profile_page.php">Back</a>
    </div>

    </div>
</body>
</html>
