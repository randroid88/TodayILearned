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

    private var entryViewModel: EntryViewModel? = null

    private fun initRecyclerView(rootView: View) {
        val recyclerView = rootView.findViewById(R.id.recyclerview) as RecyclerView
        val adapter = EntryListAdapter(context!!)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        entryViewModel = ViewModelProviders.of(this, EntryViewModelFactory(activity!!.application, EntryRepository(activity!!.application))).get(EntryViewModel::class.java)

        entryViewModel!!.allEntries.observe(this, Observer { entries ->
            adapter.updateCachedEntries(entries!!)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.nextAction())
        }
    }

}
