package io.winf.todayilearned

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.winf.todayilearned.data.EntryRepository
import java.lang.Exception

class HomeFragment : Fragment() {

    private lateinit var entryViewModel: EntryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(false)

        val rootView = inflater.inflate(R.layout.home_fragment, container, false)

        setupEntryViewModel()
        setupListOfEntries(rootView)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCreateEntryFAB(view)
    }

    private fun setupEntryViewModel() {
        // Scoped to the activity so it can be used by multiple fragments
        entryViewModel = activity?.run {
            ViewModelProviders.of(this, EntryViewModelFactory(this.application, EntryRepository(this.application))).get(EntryViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    private fun setupListOfEntries(rootView: View) {
        val recyclerView = rootView.findViewById(R.id.recyclerview) as RecyclerView
        val adapter = EntryListAdapter(context!!)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        observeChangesToEntries(adapter)
    }

    private fun observeChangesToEntries(adapter: EntryListAdapter) {
        entryViewModel.allEntries.observe(this, Observer { entries ->
            adapter.updateCachedEntries(entries)
        })
    }

    private fun setupCreateEntryFAB(view: View) {
        val createEntryFAB = view.findViewById(R.id.fab_create_entry) as FloatingActionButton

        createEntryFAB.setOnClickListener {
            val nextAction = HomeFragmentDirections.nextAction()

            findNavController().navigate(nextAction)
        }
    }

}
