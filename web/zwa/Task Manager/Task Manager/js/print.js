document.querySelectorAll('.print_note').forEach(button => {
    button.addEventListener('click', function(event) {
        event.preventDefault();
        window.print();
    });
});