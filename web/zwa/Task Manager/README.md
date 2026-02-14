# Task Manager - Semester Project

## 📄 Project Overview

**Task Manager** is a web-based application designed for creating, managing, and organizing personal notes. Developed as a semester project, it demonstrates a full-stack implementation using native **PHP** without a relational database, relying instead on structured **JSON** files for data persistence and **Cookies** for temporary guest storage.

The project features a responsive design, AJAX-based interactivity, image processing for profiles, and a dedicated administration panel.

---

## 🚀 Key Features

### 👤 Guest Mode (Unauthorized Access)

* **Immediate Use:** Users can create and manage notes without registering.
* **Cookie Storage:** Notes are stored locally in the browser's cookies.


* **Limitations:** Data is specific to the device/browser and is not persistent across sessions if cookies are cleared.

### 🔐 Registered Users

* **Secure Authentication:** Registration and Login system using `password_hash` (BCRYPT) for security.


* **Persistent Storage:** Notes are saved server-side in `data.json`.


* **Profile Management:**
* Update username and password.
* **Avatar Upload:** Users can upload profile pictures (JPG, PNG, GIF). The system automatically validates, crops, and resizes images to **150x150px** using the GD library.


* Delete Account (removes user data and associated notes).




* **Note Operations:** Create, Read (with print view), Update, and Delete (CRUD).
* **AJAX Integration:** Notes can be added and lists refreshed dynamically without reloading the entire page.



### 🛠 Administrator Panel

* **User Oversight:** View a list of all registered users, their note counts, and last activity.


* **Content Moderation:** View, edit, or delete notes belonging to any user.


* **User Management:** Promote standard users to Admins or delete users entirely.



---

## 💻 Technical Implementation

### Backend (PHP)

The application avoids SQL databases, implementing a custom data handling layer:

* **`data_lib.php`**: Handles CRUD operations for notes, switching logic between `$_SESSION` (for DB users) and `$_COOKIE` (for guests).


* **`users_lib.php`**: Manages user authentication, role checks (`isAdmin`), and profile updates.


* **JSON Storage**: `users.json` and `data.json` act as the database, requiring careful array manipulation and file locking mechanisms.



### Frontend (HTML/CSS/JS)

* **Responsive Design:** `styles.css` ensures the layout adapts to desktops and mobile devices.


* **JavaScript:**
* `script.js`: Handles AJAX requests for adding notes and pagination.


* `reg_validation.js`: Client-side validation for forms (password strength, matching fields).


* `print.js`: Provides a clean print layout for individual notes.





---

## 📂 Project Structure

```text
├── index.php                # Homepage (Note list & Guest logic)
├── login.php / register.php # Authentication pages
├── admin_panel.php          # Admin dashboard
├── add_note.php             # Backend handler for creating notes (supports AJAX)
├── data_lib.php             # Library: JSON Note & Cookie handling
├── users_lib.php            # Library: User management & Auth
├── css/
│   └── styles.css           # Main stylesheet
├── js/
│   ├── script.js            # Main UI logic & AJAX
│   ├── confirm.js           # Deletion confirmation dialogs
│   └── ...
├── uploads/                 # Directory for storing user avatars
├── data.json                # Storage for user notes
└── users.json               # Storage for user credentials

```

---

## ⚙️ Installation & Setup

To run this project locally:

1. **Requirements:** A PHP server (Apache/Nginx) with the **GD Library** enabled (for image resizing).
2. **Permissions:** Ensure the server has write permissions for the following files and folders:
* `users.json`
* `data.json`
* `uploads/` directory
* *Command:* `chmod 777 users.json data.json uploads/` (or `755` depending on server config).


3. **Deploy:** Place the files in your server's public directory (e.g., `htdocs` or `www`).
4. **Access:** Open `index.php` in your browser.

---

## 🛡 Security Measures

* **Input Sanitization:** Usage of `htmlspecialchars` to prevent XSS attacks when displaying note content.
* **Access Control:** strict session checks (`!isset($_SESSION['username'])`) on protected pages like `admin_panel.php`.


* **Validation:** Both client-side (JS) and server-side (PHP) validation for form inputs.



---

*Created as part of the Web Applications (ZWA) semester course.*
