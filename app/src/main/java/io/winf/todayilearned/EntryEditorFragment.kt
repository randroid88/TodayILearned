package io.winf.todayilearned

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import io.winf.todayilearned.utils.EntryCreator
import java.lang.Exception

class EntryEditorFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)

        entryViewModel = activity?.run {
            ViewModelProviders.of(this, EntryViewModelFactory(this.application, EntryRepository(this.application))).get(EntryViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        return inflater.inflate(R.layout.entry_editor_fragment, container, false)
    }

    private lateinit var entryViewModel: EntryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_save).setOnClickListener {
            closeKeyboard(view)
            saveNewEntry()
            navigate()
        }
    }

    private fun saveNewEntry() {
        entryViewModel.insert(EntryCreator().create(getEntryText()))
    }

    private fun closeKeyboard(view: View) {
        view.findViewById<EditText>(R.id.edit_entry).onEditorAction(EditorInfo.IME_ACTION_DONE)
    }

    private fun navigate() {
        findNavController().navigate(EntryEditorFragmentDirections.nextAction())
    }

    private fun getEntryText(): String {
        val editText = view!!.findViewById(R.id.edit_entry) as EditText
        return editText.text.toString()
    }
}
