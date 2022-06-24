package pt.ipg.livros

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Categoria(var nome: String = "", var id: Long = -1) : Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDCategorias.CAMPO_NOME, nome)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Categoria {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDCategorias.CAMPO_NOME)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Categoria(nome, id)
        }
    }
}