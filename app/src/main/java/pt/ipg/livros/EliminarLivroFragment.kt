package pt.ipg.livros

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import pt.ipg.livros.databinding.FragmentEliminarLivroBinding

class EliminarLivroFragment : Fragment() {
    private var _binding: FragmentEliminarLivroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var livro: Livro

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarLivroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        livro = EliminarLivroFragmentArgs.fromBundle(arguments!!).livro

        binding.textViewTitulo.text = livro.titulo
        binding.textViewAutor.text = livro.autor
        binding.textViewCategoria.text = livro.categoria.nome
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {

                true
            }
            R.id.action_cancelar -> {
                //voltaListaLivros()
                true
            }
            else -> false
        }
}