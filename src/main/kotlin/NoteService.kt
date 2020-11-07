import java.lang.RuntimeException

object NoteService : NoteServiceCore() {}


open class NoteServiceCore {
    private val noteList = mutableListOf<Note>()

    fun getNoteById(id: Int): Note {
        for (note in noteList) {
            if (note.id == id && !note.isDeleted) return note
        }
        throw NoteIdNotFoundException("Note Id not found!")
    }

    //эта функция не учитывает флаг удаления записи (требуется для restore)
    private fun getNoteByIdForced(id: Int): Note {
        for (note in noteList) {
            if (note.id == id) return note
        }
        throw NoteIdNotFoundException("Note Id not found!")
    }

    fun addNote(note: Note) {
        note.addTo(noteList)
    }

    fun addComment(comment: Comment, note: Note) {
        comment.addTo(note.comments)
    }

    fun addComment(comment: Comment, toComment: Comment) {
        comment.addTo(toComment.comments)
    }


    fun deleteNote(id: Int) {
        getNoteById(id).delete()
    }

    fun restoreNote(id: Int) {
        getNoteByIdForced(id).restore()
    }

    fun editNote(note: Note, title: String, text: String) {
        note.edit(title, text)
    }

    fun editComment(comment: Comment, text: String) {
        comment.edit(text)
    }

    //функция возвращает строку, содержащую запись с вложенными комментариями и некоторым форматированием
    //В данный момент функционал ограничен по отображению количества вложенных комментариев
    // (возможен только коммент к комменту)
    fun getNoteWithComments(note: Note): String {
        if (note.isDeleted) return ""
        var outString = "-=${note.title}=-\n"
        outString += "${note.text}\n"
        if (note.comments.isEmpty()) return outString
        outString += "\nКомментарии:\n"
        for (comment in note.comments) {
            if (!comment.isDeleted) {
                outString += "\t\t- ${comment.text} \n"
                if (comment.comments.isNotEmpty()) {
                    outString += "\n\t\tКомментарии:\n"
                }
                for (_comment in comment.comments) {
                    if (!_comment.isDeleted) {
                        outString += "\t\t\t\t- ${_comment.text} \n"
                    }
                }
            }
        }
        return outString
    }

}


class NoteIdNotFoundException(message: String) : RuntimeException(message)