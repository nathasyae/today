//package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today
//
//import android.R.attr.data
//import android.app.Application
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Adapter
//import android.widget.Button
//import android.widget.EditText
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Task
//import kotlinx.android.synthetic.main.fragment_first.*
//
//
///**
// * A simple [Fragment] subclass as the default destination in the navigation.
// */
//class FirstFragment : Fragment() {
//
//    lateinit var taskTitleInput: EditText
//    lateinit var taskDetailInput: EditText
//    lateinit var recyclerView: RecyclerView
//    lateinit var add_button: Button
//    lateinit var delete_button: Button
//
//    lateinit var mAdapter: Adapter
//    lateinit var adapter: MainAdapter
//
//    lateinit var viewAdapter: MainAdapter
//
//    private lateinit var viewModel: MainViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        retainInstance = true
//    }
//
//    override fun onCreateView(inflater: LayoutInflater,
//                              container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? =
//            inflater.inflate(R.layout.fragment_first, container, false)
//
//    // populate the views now that the layout has been inflated
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        // RecyclerView node initialized here
//        recycler_view.apply {
//            // set a LinearLayoutManager to handle Android
//            // RecyclerView behavior
//            layoutManager = LinearLayoutManager(activity)
//            // set the custom adapter to the RecyclerView
//            viewModel = MainViewModel(application = Application())
//            viewAdapter = MainAdapter()
//            viewModel.tasks.observe(viewLifecycleOwner, Observer {
//                task ->
//                task?.let {
//                    viewAdapter.setTasks(it)
//                }
//            })
//        }
//    }
//
//    companion object {
//        fun newInstance(): FirstFragment = FirstFragment()
//    }
//}