<?php
/**
 * @file register.php
 * @brief Registrace nového uživatele.
 * 
 * Tento skript zpracovává registraci nového uživatele, včetně validace vstupů,
 * zpracování nahrané profilové fotky a přidání uživatele do systému.
 */

session_start(); // --- Spuštění uživatelské relace ---

include('users_lib.php'); // --- Načtení knihovny pro správu uživatelů ---

/**
 * Zpracování POST požadavku.
 * @details Skript validuje vstupy, zpracovává profilovou fotku a ukládá uživatele.
 */
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    /**
     * @var string $username Uživatelské jméno zadané uživatelem.
     * @var string $password Heslo zadané uživatelem.
     * @var string $confirm_password Potvrzení hesla zadané uživatelem.
     */
    $username = trim($_POST['username']);
    $password = $_POST['password'];
    $confirm_password = $_POST['confirm_password'];

    // --- Validace uživatelského jména ---
    if (strlen($username) < 3 || strlen($username) > 50) {
        $_SESSION['error'] = 'Username must be between 3 and 50 characters.';
        header('Location: register.php');
        exit;
    }

    // --- Validace hesla ---
    if (strlen($password) < 8 || !preg_match('/\d/', $password) || !preg_match('/[a-zA-Z]/', $password)) {
        $_SESSION['error'] = 'Password must be at least 8 characters long and include at least one letter and one number.';
        header('Location: register.php');
        exit;
    }

    // --- Kontrola shody hesel ---
    if ($password !== $confirm_password) {
        $_SESSION['error'] = 'Passwords do not match.';
        header('Location: register.php');
        exit;
    }

    // --- Kontrola existence uživatele ---
    if (getUserByUsername($username)) {
        $_SESSION['error'] = 'User already exists.';
        header('Location: register.php');
        exit;
    }

    // --- Zpracování profilové fotky ---
    if (!isset($_FILES['profile_picture']) || $_FILES['profile_picture']['error'] !== UPLOAD_ERR_OK) {
        $_SESSION['error'] = 'No file uploaded or file upload error.';
        header('Location: register.php');
        exit;
    }

    $fileTmpPath = $_FILES['profile_picture']['tmp_name'];
    $fileName = $_FILES['profile_picture']['name'];
    $fileSize = $_FILES['profile_picture']['size'];
    $fileType = $_FILES['profile_picture']['type'];

    // --- Kontrola typu a velikosti souboru ---
    $allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
    if (!in_array($fileType, $allowedTypes)) {
        $_SESSION['error'] = 'Only JPEG, PNG, and GIF files are allowed.';
        header('Location: register.php');
        exit;
    }
    if ($fileSize > 2 * 1024 * 1024) {
        $_SESSION['error'] = 'File size must not exceed 2MB.';
        header('Location: register.php');
        exit;
    }

    // --- Ukládání souboru ---
    $uploadDir = 'uploads/';
    if (!file_exists($uploadDir) && !mkdir($uploadDir, 0755, true)) {
        $_SESSION['error'] = 'Error: Unable to create uploads directory.';
        header('Location: register.php');
        exit;
    }
    $newFileName = uniqid() . '-' . basename($fileName);
    $destPath = $uploadDir . $newFileName;

    // --- Zmenšení a konverze obrázku ---
    $convertedImagePath = $uploadDir . 'converted_' . $newFileName;
    list($originalWidth, $originalHeight) = getimagesize($fileTmpPath);
    $newWidth = 150;
    $newHeight = 150;

    $sourceImage = match ($fileType) {
        'image/jpeg' => imagecreatefromjpeg($fileTmpPath),
        'image/png' => imagecreatefrompng($fileTmpPath),
        'image/gif' => imagecreatefromgif($fileTmpPath),
        default => null
    };

    if ($sourceImage) {
        $resizedImage = imagecreatetruecolor($newWidth, $newHeight);
        imagecopyresampled(
            $resizedImage,
            $sourceImage,
            0, 0, 0, 0,
            $newWidth, $newHeight,
            $originalWidth, $originalHeight
        );

        $saveFunction = match ($fileType) {
            'image/jpeg' => fn($resizedImage, $path) => imagejpeg($resizedImage, $path, 90),
            'image/png' => fn($resizedImage, $path) => imagepng($resizedImage, $path, 9),
            'image/gif' => fn($resizedImage, $path) => imagegif($resizedImage, $path),
        };
        $saveFunction($resizedImage, $convertedImagePath);

        imagedestroy($sourceImage);
        imagedestroy($resizedImage);

        $destPath = $convertedImagePath;
    } else {
        $_SESSION['error'] = 'There was an error processing the uploaded file.';
        header('Location: register.php');
        exit;
    }

    // --- Přidání uživatele ---
    addUser($username, $password, $destPath);

    // --- Přesměrování na přihlašovací stránku ---
    header('Location: login.php');
    exit;
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body class="register-page">
    <div id="register-container">
        <h1>Registration</h1>
        <?php
        if (isset($_SESSION['error'])) {
            echo '<p style="color: red;">' . htmlspecialchars($_SESSION['error']) . '</p>';
            unset($_SESSION['error']);
        }
        ?>
        <form action="register.php" method="post" id="register_form" enctype="multipart/form-data">
            <label for="username">Username:</label>
            <input type="text" name="username" id="username" required placeholder="Username"><br>

            <label for="password">Password:</label>
            <input type="password" name="password" id="password" required placeholder="Password"><br>
            
            <label for="confirm_password">Confirm your password:</label>
            <input type="password" name="confirm_password" id="confirm_password" placeholder="Password confirmation" required><br>
            
            <label for="profile_picture">Profile Picture:</label>
            <input type="file" name="profile_picture" id="profile_picture" accept="image/*">
            
            <button type="submit">Register</button>
        </form>
        <p class="login-link">Already have an account? <a href="login.php">Log in here</a></p>
    </div>
    <script src="js/reg_validation.js"></script>
</body>
</html>
