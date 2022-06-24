package pt.ipg.livros

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Livro(
    var titulo : String,
    var autor: String,
    var categoria: Categoria,
    var id: Long = -1
) : Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDLivros.CAMPO_TITULO, titulo)
        valores.put(TabelaBDLivros.CAMPO_AUTOR, autor)
        valores.put(TabelaBDLivros.CAMPO_CATEGORIA_ID, categoria.id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Livro {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posTitulo = cursor.getColumnIndex(TabelaBDLivros.CAMPO_TITULO)
            val posAutor = cursor.getColumnIndex(TabelaBDLivros.CAMPO_AUTOR)
            val posIdCateg = cursor.getColumnIndex(TabelaBDLivros.CAMPO_CATEGORIA_ID)
            val posNomeCateg =  cursor.getColumnIndex(TabelaBDCategorias.CAMPO_NOME)

            val id = cursor.getLong(posId)
            val titulo = cursor.getString(posTitulo)
            val autor = cursor.getString(posAutor)

            val idCategoria = cursor.getLong(posIdCateg)
            val nomeCategoria = cursor.getString(posNomeCateg)
            val categoria = Categoria(nomeCategoria, idCategoria)

            return Livro(titulo, autor, categoria, id)
        }
    }
}