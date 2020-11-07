import java.lang.RuntimeException
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Note(
    var title: String? = null,
    val id: Int = 1,
    val ownerId: Int = 1,
    var text: String,
    var isDeleted: Boolean = false,
    val date: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
    val comments: MutableList<Comment> = mutableListOf<Comment>(),
) : Notable<Note> {

    override fun addTo(dest: MutableList<Note>) {
        if (dest.isEmpty()) dest.add(this.copy(id = 1))
        else dest.add(this.copy(id = dest.last().id + 1))
    }

    fun edit(title: String, text: String) {
        this.title = title
        this.text = text
    }

    override fun getCommentById(id: Int): Comment {
        for (comment in comments) {
            if (comment.id == id && !comment.isDeleted) return comment
        }
        throw CommentIdNotFoundException("Comment Id not found!")
    }

    override fun getCommentByIdForced(id: Int): Comment {
        for (comment in comments) {
            if (comment.id == id) return comment
        }
        throw CommentIdNotFoundException("Comment Id not found!")
    }

    override fun delete() {
        isDeleted = true
    }

    override fun restore() {
        isDeleted = false
    }


}

class CommentIdNotFoundException(message: String) : RuntimeException(message)