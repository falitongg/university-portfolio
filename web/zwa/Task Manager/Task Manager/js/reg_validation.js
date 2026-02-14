const form = document.getElementById("register_form");

form.addEventListener("submit", (event) => {
    event.preventDefault();

    const username = form.username.value.trim();
    const password = form.password.value;
    const confirm_password = form.confirm_password.value;

    if (username.length < 3 || username.length > 50){
        alert("Username must be between 3 and 50 characters.");
        return
    }

    if (password.lenght < 8 || !/\d/.test(password) || !/[a-zA-Z]/.test(password)){
        alert("Password must be at least 8 characters long and include at least one letter and one number.");
        return;
    }

    if (password !== confirm_password){
        alert("Passwords do not match.");
        return;
    }

    form.submit();
})
