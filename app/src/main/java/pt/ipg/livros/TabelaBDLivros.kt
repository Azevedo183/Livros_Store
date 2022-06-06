package pt.ipg.livros

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDLivros(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_TITULO TEXT NOT NULL, $CAMPO_AUTOR TEXT NOT NULL, $CAMPO_CATEGORIA_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_CATEGORIA_ID) REFERENCES ${TabelaBDCategorias.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME = "livros"
        const val CAMPO_TITULO = "titulo"
        const val CAMPO_AUTOR = "autor"
        const val CAMPO_CATEGORIA_ID = "categoriaId"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_TITULO, CAMPO_AUTOR, CAMPO_CATEGORIA_ID)
    }
}