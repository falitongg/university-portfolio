const showFormButton = document.getElementById("showFormButton");
const formContainer = document.getElementById("formContainer");
const notesContainer = document.getElementById("notesContainer");
const form = document.getElementById("infoForm");

document.addEventListener('click', (event) => {
    const target = event.target;

    if (target.tagName === 'BUTTON' && target.textContent.trim() === 'Edit') {
        const userConfirmed = confirm('Are you sure you want to edit this note?');
        if (!userConfirmed) {
            event.preventDefault();
        }
    }

    if (target.tagName === 'BUTTON' && target.textContent.trim() === 'Delete') {
        const userConfirmed = confirm('Are you sure you want to delete this note? This action cannot be undone.');
        if (!userConfirmed) {
            event.preventDefault();
        }
    }
});


document.getElementById('infoForm').addEventListener('submit', function (event) {
    const title = document.getElementById('name').value.trim();
    const content = document.getElementById('task').value.trim();

    if (!title) {
        alert('Title cannot be empty.');
        event.preventDefault();
        return;
    }

    if (!content) {
        alert('Content cannot be empty.');
        event.preventDefault();
        return;
    }

    if (content.length > 10000) {
        alert('Content is too long! Maximum allowed length is 10,000 characters.');
        event.preventDefault();
        return;
    }

    if (title.length > 255) {
        alert('Title is too long! Maximum allowed length is 255 characters.');
        event.preventDefault();
        return;
    }
});


function htmlspecialchars(text) {
    return text
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;');
}
function refreshNotes(page = 1) {
    fetch(`index.php?page=${page}&ajax=1`)
        .then(response => response.text())
        .then(html => {
            notesContainer.innerHTML = html;
        })
        .catch(error => console.error('Error refreshing notes:', error));
}

showFormButton.addEventListener("click", () => {
    formContainer.classList.toggle("hidden");
});

form.addEventListener("submit", (event) => {
    event.preventDefault();

    const title = form.title.value.trim();
    const content = form.content.value.trim();

    if (!title || !content) {
        alert("Title and content cannot be empty.");
        return;
    }

    fetch('add_note.php?ajax=1', {
        method: 'POST',
        body: new FormData(form)
    })
    
    .then(response => response.json())
    .then(data => {
        console.log('Server response:', data);
        if (data.success) {
            refreshNotes();
            form.reset();
            formContainer.classList.add("hidden");
        } else {
            alert(data.message || "Failed to save the note. Please try again.");
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert(`An error occurred: ${error.message}`);
    });
});
document.addEventListener("click", (event) => {
    if (event.target.matches(".pagination a")) {
        event.preventDefault();
        const page = event.target.getAttribute("href").split("page=")[1];
        refreshNotes(page);
    }
});
