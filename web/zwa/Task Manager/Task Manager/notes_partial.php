<?php
/**
 * @file notes_partial.php
 * @brief Komponenta pro zobrazení seznamu poznámek.
 * 
 * Tato komponenta zobrazuje poznámky s možnostmi úprav a mazání. Podporuje stránkování 
 * a zobrazení pro administrátory i běžné uživatele.
 */

?>

<div id="submittedForms">
    <?php if (empty($notes)): ?>
        <p>No notes found.</p>
    <?php else: ?>
        <?php foreach ($notes as $noteID => $note): ?>
            <div class="submit" id="note-<?php echo $noteID; ?>">
                <div class="header-row">
                    <?php if (isset($_SESSION['username']) && isAdmin($_SESSION['username'])): ?>
                        <div class="buttons-container">
                            <form action="update_note.php" method="get">
                                <input type="hidden" name="username" value="<?php echo htmlspecialchars($username); ?>">
                                <input type="hidden" name="note_id" value="<?php echo $noteID; ?>">
                                <button type="submit" class="edit-button">Edit</button>
                            </form>
                            <form action="delete_note.php" method="post">
                                <input type="hidden" name="username" value="<?php echo htmlspecialchars($username); ?>">
                                <input type="hidden" name="note_id" value="<?php echo $noteID; ?>">
                                <button type="submit" class="delete-button del">Delete</button>
                            </form>
                        </div>
                    <?php else: ?>
                        <div class="buttons-container">
                            <form action="update_note.php" method="get">
                                <input type="hidden" name="note_id" value="<?php echo $noteID; ?>">
                                <button type="submit" class="edit-button">Edit</button>
                            </form>
                            <form action="delete_note.php" method="post">
                                <input type="hidden" name="note_id" value="<?php echo $noteID; ?>">
                                <button type="submit" class="delete-button del">Delete</button>
                            </form>
                        </div>
                    <?php endif; ?>
                    <a href="view_note.php?note_id=<?php echo $noteID; ?>&username=<?php echo urlencode($username); ?>" target="_blank">
                        <h3><?php echo substr(htmlspecialchars($note['title']), 0, 50) . '...'; ?></h3>
                    </a>
                </div>
                <p><?php echo substr(htmlspecialchars($note['content']), 0, 100) . '...'; ?></p>
                <p>Created: <?php echo date('d.m.Y H:i:s', strtotime($note['created_at'])); ?></p>
            </div>
        <?php endforeach; ?>
    <?php endif; ?>
</div>

<?php if (isset($_SESSION['username']) && isAdmin($_SESSION['username'])): ?>
    <a href="admin_panel.php" id="b_a_p">Back to Admin Panel</a>
<?php endif; ?>

<div class="pagination">
    <?php if ($page > 1): ?>
        <a href="?<?php echo isset($username) ? 'username=' . urlencode($username) . '&' : ''; ?>page=<?php echo $page - 1; ?>">Previous</a>
    <?php endif; ?>
    
    <?php for ($i = 1; $i <= $total_notes; $i++): ?>
        <a href="?<?php echo isset($username) ? 'username=' . urlencode($username) . '&' : ''; ?>page=<?php echo $i; ?>" class="<?php echo $i === $page ? 'active' : ''; ?>">
            <?php echo $i; ?>
        </a>
    <?php endfor; ?>
    
    <?php if ($page < $total_notes): ?>
        <a href="?<?php echo isset($username) ? 'username=' . urlencode($username) . '&' : ''; ?>page=<?php echo $page + 1; ?>">Next</a>
    <?php endif; ?>
</div>
