package pt.ipg.livros

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
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
                eliminaLivro()
                true
            }
            R.id.action_cancelar -> {
                voltaListaLivros()
                true
            }
            else -> false
        }

    private fun eliminaLivro() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_livro_label)
            setMessage(R.string.confirma_eliminar_livro)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarLivro() })
            show()
        }
    }

    private fun confirmaEliminarLivro() {
        val enderecoLivro = Uri.withAppendedPath(ContentProviderLivros.ENDERECO_LIVROS, "${livro.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoLivro, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewTitulo,
                R.string.erro_eliminar_livro,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.livro_eliminado_sucesso, Toast.LENGTH_LONG).show()
        voltaListaLivros()
    }

    private fun voltaListaLivros() {
        val acao = EliminarLivroFragmentDirections.actionEliminarLivroFragmentToListaLivrosFragment()
        findNavController().navigate(acao)
    }
}