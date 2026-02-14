<?php
/**
 * @file users_lib.php
 * @brief Knihovna pro správu uživatelských účtů.
 * 
 * Tato knihovna poskytuje funkce pro správu uživatelů, jako je ukládání, načítání,
 * přidávání nových uživatelů, propagace na administrátora, a další operace související
 * s uživatelskými účty.
 */

/**
 * Uloží seznam uživatelů do souboru.
 * 
 * @param array $users Pole obsahující seznam uživatelů.
 * @return void
 */
function saveUsers(array $users): void {
    $str = json_encode($users);
    file_put_contents("users.json", $str);
}

/**
 * Načte seznam uživatelů ze souboru.
 * 
 * @return array Pole obsahující seznam uživatelů.
 */
function loadUsers(): array {
    $str = file_get_contents("users.json");
    if (!$str) return [];
    return json_decode($str, true);
}

/**
 * Vrátí seznam všech uživatelů.
 * 
 * @return array Pole obsahující seznam uživatelů.
 */
function listUsers(): array {
    return loadUsers();
}

/**
 * Přidá nového uživatele do seznamu.
 * 
 * @param string $username Uživatelské jméno.
 * @param string $password Heslo uživatele.
 * @param string $profile_picture Cesta k profilovému obrázku.
 * @return void
 */
function addUser(string $username, string $password, string $profile_picture): void {
    $users = listUsers();
    $hashedPassword = password_hash($password, PASSWORD_BCRYPT);
    $users[] = [
        "username" => $username,
        "password" => $hashedPassword,
        "profile_picture" => $profile_picture,
        "id" => uniqid(),
        "admin" => false
    ];
    saveUsers($users);
}

/**
 * Propaguje uživatele na administrátora.
 * 
 * @param string $username Uživatelské jméno.
 * @return void
 */
function promoteToAdmin(string $username): void {
    $users = listUsers();
    foreach ($users as &$user) {
        if ($user['username'] === $username) {
            $user['admin'] = true;
            break;
        }
    }
    saveUsers($users);
}

/**
 * Kontroluje, zda je uživatel administrátorem.
 * 
 * @param string $username Uživatelské jméno.
 * @return bool True, pokud je uživatel administrátorem, jinak False.
 */
function isAdmin(string $username): bool {
    $user = getUserByUsername($username);
    return $user && isset($user['admin']) && $user['admin'] === true;
}

/**
 * Získá uživatele podle uživatelského jména.
 * 
 * @param string $username Uživatelské jméno.
 * @return array|null Pole uživatelských dat nebo null, pokud uživatel neexistuje.
 */
function getUserByUsername(string $username): ?array {
    $users = loadUsers();
    foreach ($users as $user) {
        if ($user["username"] == $username) {
            return $user;
        }
    }
    return null;
}

/**
 * Aktualizuje profilový obrázek uživatele.
 * 
 * @param string $username Uživatelské jméno.
 * @param string $destPath Cesta k novému profilovému obrázku.
 * @return void
 */
function updateProfilePicture(string $username, string $destPath): void {
    $users = loadUsers();
    foreach ($users as &$user) {
        if ($user['username'] === $username) {
            $user['profile_picture'] = $destPath;
            break;
        }
    }
    saveUsers($users);
}

/**
 * Odstraní uživatelský účet.
 * 
 * @param string $username Uživatelské jméno.
 * @return void
 */
function deleteUserAcc(string $username): void {
    $users = loadUsers();
    foreach ($users as $index => $user) {
        if ($user['username'] === $username) {
            unset($users[$index]);
            break;
        }
    }
    saveUsers(array_values($users));
}
?>
