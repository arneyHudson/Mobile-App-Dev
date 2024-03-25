package edu.msoe.dematsri.transcriptmanager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.msoe.dematsri.transcriptmanager.databinding.FragmentTranscriptListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

private const val TAG = "TranscriptFragment"

/**
 * A fragment representing a list of Items.
 */
class TranscriptFragment : Fragment() {

    private var _binding: FragmentTranscriptListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null..."
        }

    private val transcriptViewModel: TranscriptViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d(TAG, "Class count: ${transcriptViewModel.classes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "in onCreateView")

        _binding = FragmentTranscriptListBinding.inflate(inflater, container, false)
        binding.classRecyclerView.layoutManager = LinearLayoutManager(context)

        Log.d(TAG, "leaving onCreateView")
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        job = viewLifecycleOwner.lifecycleScope.launch {
            Log.d(TAG, "in db scope")
            // var classes = transcriptViewModel.loadClasses()
            transcriptViewModel.classes.collect {classes ->
                binding.classRecyclerView.adapter = ClassListAdapter(classes) { classID ->
                    findNavController().navigate(
                        //R.id.show_class_detail
                        TranscriptFragmentDirections.showClassDetail(classID)
                    )
                }
            }
        }

    }

    private var job: Job? = null
    val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onStart() {
        Log.d(TAG, "in OnStart")

        super.onStart()

        Log.d(TAG, "leaving OnStart")
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }
}