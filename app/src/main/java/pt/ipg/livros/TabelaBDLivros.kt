package pt.ipg.livros

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDLivros(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_TITULO TEXT NOT NULL, $CAMPO_AUTOR TEXT NOT NULL, $CAMPO_CATEGORIA_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_CATEGORIA_ID) REFERENCES ${TabelaBDCategorias.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = "$NOME INNER JOIN ${TabelaBDCategorias.NOME} ON ${TabelaBDCategorias.CAMPO_ID} = $CAMPO_CATEGORIA_ID"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val NOME = "livros"

        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val CAMPO_TITULO = "titulo"
        const val CAMPO_AUTOR = "autor"
        const val CAMPO_CATEGORIA_ID = "categoriaId"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_TITULO, CAMPO_AUTOR, CAMPO_CATEGORIA_ID, TabelaBDCategorias.CAMPO_NOME)
    }
}