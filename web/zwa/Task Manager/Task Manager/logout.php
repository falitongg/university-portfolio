<?php
/**
 * @file logout.php
 * @brief Odhlášení uživatele z aplikace Task Manager.
 * 
 * Tento skript ukončí aktuální uživatelskou relaci a přesměruje uživatele zpět na přihlašovací stránku.
 */

session_start(); // --- Spuštění uživatelské relace ---

session_unset(); // --- Vymazání všech dat ze session ---
session_destroy(); // --- Ukončení session ---

header('Location: login.php'); // --- Přesměrování na přihlašovací stránku ---
exit; // --- Ukončení skriptu ---
