const q = document.getElementById("risky");
const del = document.getElementById("delete");

q.addEventListener("submit", (event) => {
    event.preventDefault();

    const username = q.new_username.value.trim();

    if (username.length < 3 || username.length > 50){
        alert("Username must be between 3 and 50 characters.");
        return
    }
    form.submit();
})

function confirmUpdate(event){
    if (!confirm("Are you sure?")) {
        event.preventDefault();
    }
}
function confirmDelete(event){
    if (!confirm("Are you sure you want to delete your account? This cannot be undone.")) {
        event.preventDefault();
    }
}

q.addEventListener("click", confirmUpdate);
del.addEventListener("click", confirmDelete);