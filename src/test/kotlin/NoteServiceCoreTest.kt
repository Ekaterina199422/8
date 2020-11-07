import org.junit.Assert.*
import org.junit.Test

class NoteServiceTest {

    @Test
    fun addNote() {
        val service = NoteServiceCore()
        val note = Note(title = "Title", text = "text")
        service.addNote(note)
        assertEquals(note, service.getNoteById(1))
    }

    @Test(expected = NoteIdNotFoundException::class)
    fun addNoteShouldThrow() {
        val service = NoteServiceCore()
        val note = Note(title = "Title", text = "text")
        service.addNote(note)
        assertEquals(note, service.getNoteById(2))
    }

    @Test
    fun addComment() {
        val service = NoteServiceCore()
        val note = Note(title = "Title", text = "text")
        service.addNote(note)
        val comment = Comment(text = "comment")
        service.addComment(comment, note)
        assertEquals(comment, service.getNoteById(1).getCommentById(1))
    }

    @Test(expected = CommentIdNotFoundException::class)
    fun addCommentShouldThrow() {
        val service = NoteServiceCore()
        val note = Note(title = "Title", text = "text")
        service.addNote(note)
        val comment = Comment(text = "comment")
        service.addComment(comment, note)
        assertEquals(comment, service.getNoteById(1).getCommentById(2))
    }

    @Test
    fun addComment2Comment() {
        val service = NoteServiceCore()
        val note = Note(title = "Title", text = "text")
        service.addNote(note)
        val comment = Comment(text = "comment")
        val comment2Comment = Comment(text = "comment2Comment")
        service.addComment(comment, note)
        service.addComment(comment2Comment, comment)
        assertEquals(comment2Comment, service.getNoteById(1).getCommentById(1).getCommentById(1))
    }

    @Test(expected = NoteIdNotFoundException::class)
    fun deleteNoteShoudThrow() {
        val service = NoteServiceCore()
        val note = Note(title = "Title", text = "text")
        service.addNote(note)
        service.addNote(note)
        service.addNote(note)
        service.deleteNote(2)
        assertEquals(note, service.getNoteById(2))
    }

    @Test
    fun restoreNote() {
        val service = NoteServiceCore()
        val note1 = Note(id = 1, title = "Title1", text = "text1")
        val note2 = Note(id = 2, title = "Title2", text = "text2")
        val note3 = Note(id = 3, title = "Title3", text = "text3")
        val note4 = Note(id = 4, title = "Title4", text = "text4")
        service.addNote(note1)
        service.addNote(note2)
        service.addNote(note3)
        service.deleteNote(2)
        service.addNote(note4)
        service.restoreNote(2)
        assertEquals(note2, service.getNoteById(2))
    }

}