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
import java.lang.Exception

class HomeFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)

        val rootView = inflater.inflate(R.layout.home_fragment, container, false)
        initRecyclerView(rootView)

        return rootView
    }

    private lateinit var entryViewModel: EntryViewModel

    private fun initRecyclerView(rootView: View) {
        val recyclerView = rootView.findViewById(R.id.recyclerview) as RecyclerView
        val adapter = EntryListAdapter(context!!)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        entryViewModel = activity?.run {
            ViewModelProviders.of(this, EntryViewModelFactory(this.application, EntryRepository(this.application))).get(EntryViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        entryViewModel.allEntries.observe(this, Observer { entries ->
            adapter.updateCachedEntries(entries)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            val nextAction = HomeFragmentDirections.nextAction()
            findNavController().navigate(nextAction)
        }
    }

}
