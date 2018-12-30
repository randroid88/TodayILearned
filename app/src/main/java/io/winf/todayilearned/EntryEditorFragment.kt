package io.winf.todayilearned

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController

class EntryEditorFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)

        return inflater.inflate(R.layout.entry_editor_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_save).setOnClickListener {
            closeKeyboard(view)
            navigate()
        }
    }

    private fun closeKeyboard(view: View) {
        view.findViewById<EditText>(R.id.edit_entry).onEditorAction(EditorInfo.IME_ACTION_DONE)
    }

    private fun navigate() {
        findNavController().navigate(EntryEditorFragmentDirections.nextAction())
    }
}
