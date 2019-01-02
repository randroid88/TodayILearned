package io.winf.todayilearned

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import io.winf.todayilearned.data.Entry
import io.winf.todayilearned.data.EntryRepository
import java.lang.Exception

class EntryEditorFragment : Fragment() {

    private lateinit var entryViewModel: EntryViewModel
    private var entryId: Int = 0
    private var existingEntry: Entry? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(false)

        setupEntryViewModel()

        getExistingEntryId()

        return inflater.inflate(R.layout.entry_editor_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entryEditText = view.findViewById(R.id.edit_entry) as EditText

        setTextFromExistingEntry(entryEditText)
        setupSaveButton(view, entryEditText)
    }

    private fun setupEntryViewModel() {
        // Scoped to the activity so it can be used by multiple fragments
        entryViewModel = activity?.run {
            ViewModelProviders.of(this, EntryViewModelFactory(this.application, EntryRepository(this.application))).get(EntryViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    private fun getExistingEntryId() {
        val safeArgs = EntryEditorFragmentArgs.fromBundle(arguments!!)
        entryId = safeArgs.entryId
    }

    private fun setTextFromExistingEntry(entryEditText: EditText) {
        entryViewModel.getEntry(entryId).observe(this, Observer { entry ->
            entry?.let {
                this.existingEntry = it
                entryEditText.setText(it.entryText)
            }
        })
    }

    private fun setupSaveButton(view: View, entryEditText: EditText) {
        val saveEntryButton = view.findViewById(R.id.button_save_entry) as Button

        saveEntryButton.setOnClickListener {
            val entryText = getEntryText(entryEditText)

            closeKeyboard(entryEditText)
            saveEntry(entryText)
            navigate()
        }
    }

    private fun closeKeyboard(entryEditText: EditText) {
        entryEditText.onEditorAction(EditorInfo.IME_ACTION_DONE)
    }

    private fun saveEntry(entryText: String) {
        entryViewModel.updateOrInsert(entryId, entryText)
    }

    private fun getEntryText(entryEditText: EditText): String {
        return entryEditText.text.toString()
    }

    private fun navigate() {
        findNavController().navigate(EntryEditorFragmentDirections.nextAction())
    }
}
