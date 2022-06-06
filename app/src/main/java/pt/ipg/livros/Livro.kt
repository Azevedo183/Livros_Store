package pt.ipg.livros

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Livro(
    var titulo : String,
    var autor: String,
    var idCategoria: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDLivros.CAMPO_TITULO, titulo)
        valores.put(TabelaBDLivros.CAMPO_AUTOR, autor)
        valores.put(TabelaBDLivros.CAMPO_CATEGORIA_ID, idCategoria)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Livro {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posTitulo = cursor.getColumnIndex(TabelaBDLivros.CAMPO_TITULO)
            val posAutor = cursor.getColumnIndex(TabelaBDLivros.CAMPO_AUTOR)
            val posIdCateg = cursor.getColumnIndex(TabelaBDLivros.CAMPO_CATEGORIA_ID)

            val id = cursor.getLong(posId)
            val titulo = cursor.getString(posTitulo)
            val autor = cursor.getString(posAutor)
            val idCategoria = cursor.getLong(posIdCateg)

            return Livro(titulo, autor, idCategoria, id)
        }
    }
}