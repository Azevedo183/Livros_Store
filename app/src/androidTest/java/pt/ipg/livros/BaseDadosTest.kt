package pt.ipg.livros

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BaseDadosTest {
    private fun appContext() =
        InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BDLivrosOpenHelper(appContext())
        return openHelper.writableDatabase
    }

    private fun insereCategoria(db: SQLiteDatabase, categoria: Categoria) {
        categoria.id = TabelaBDCategorias(db).insert(categoria.toContentValues())
        assertNotEquals(-1, categoria.id)
    }

    private fun insereLivro(db: SQLiteDatabase, livro: Livro) {
        livro.id = TabelaBDLivros(db).insert(livro.toContentValues())
        assertNotEquals(-1, livro.id)
    }

    @Before
    fun apagaBaseDados() {
        appContext().deleteDatabase(BDLivrosOpenHelper.NOME)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BDLivrosOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }

    @Test
    fun consegueInserirCategoria() {
        val db = getWritableDatabase()

        insereCategoria(db, Categoria("Drama"))

        db.close()
    }

    @Test
    fun consegueInserirLivro() {
        val db = getWritableDatabase()

        val categoria = Categoria("Aventura")
        insereCategoria(db, categoria)

        val livro = Livro("O Leão que Temos Cá Dentro", "Rachel Bright", categoria.id)
        insereLivro(db, livro)

        db.close()
    }

    @Test
    fun consegueAlterarCategoria() {
        val db = getWritableDatabase()

        val categoria = Categoria("Teste")
        insereCategoria(db, categoria)

        categoria.nome = "Ficção científica"

        val registosAlterados = TabelaBDCategorias(db).update(
            categoria.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${categoria.id}"))

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarLivro() {
        val db = getWritableDatabase()

        val categoriaSuspense = Categoria("Suspense")
        insereCategoria(db, categoriaSuspense)

        val categoriaMisterio = Categoria("Mistério")
        insereCategoria(db, categoriaMisterio)

        val livro = Livro("Teste", "Teste", categoriaSuspense.id)
        insereLivro(db, livro)

        livro.titulo = "A rapariga no comboio"
        livro.autor = "Paula Hawkins"
        livro.idCategoria = categoriaMisterio.id

        val registosAlterados = TabelaBDLivros(db).update(
            livro.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${livro.id}"))

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueEliminarCategoria() {
        val db = getWritableDatabase()

        val categoria = Categoria("Teste")
        insereCategoria(db, categoria)

        val registosEliminados = TabelaBDCategorias(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${categoria.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }
}