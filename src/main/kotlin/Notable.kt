interface Notable<D> {
    fun addTo(dest: MutableList<D>)
    fun delete()
    fun restore()
    fun getCommentById(id: Int): Comment
    fun getCommentByIdForced(id: Int): Comment
}