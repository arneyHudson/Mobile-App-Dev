package edu.msoe.dematsri.transcriptmanager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.msoe.dematsri.transcriptmanager.databinding.
import edu.msoe.dematsri.transcriptmanager.databinding.FragmentClassDetailBinding
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

/**
 * A fragment representing a list of Items.
 */

private const val TAG = "ClassDetailFragment"
class ClassDetailFragment : Fragment() {
    private val args: ClassDetailFragmentArgs by navArgs()

    private val classDetailViewModel: ClassDetailViewModel by viewModels {
        ClassDetailViewModelFactory(args.classID)
    }

    private lateinit var binding: FragmentClassDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "The class ID is: ${args.classID}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
        //    courseName.setText(classDetail.courseID)
        //    courseCredits.setText(classDetail.credits.toString())
        //    courseGrade.setText(classDetail.grade)

            courseName.doOnTextChanged { text, _, _, _ ->
                classDetailViewModel.updateClass { oldClass ->
                    oldClass.copy(courseID = text.toString())
                }
            }
            courseGrade.doOnTextChanged { text, _, _, _ ->
                classDetailViewModel.updateClass { oldClass ->
                    oldClass.copy(grade = text.toString())
                }
            }
            courseCredits.doOnTextChanged { text, _, _, _ ->
                classDetailViewModel.updateClass { oldClass ->
                    oldClass.copy(credits = text.toString().toInt())
                }
            }
        }
        setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY_DATE
        ) { requestKey, bundle ->
            val newDate =
                bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            classDetailViewModel.updateClass { it.copy(startDate = newDate) }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                classDetailViewModel.classDetail.collect { classID ->
                    classID?.let { updateUi(it) }
                }
            }
        }
    }
    private fun updateUi(thisClass: ClassDetail) {
        binding.apply {
            if (courseName.text.toString() != thisClass.courseID) {
                courseName.setText(thisClass.courseID)
            }
            courseCredits.setText(thisClass.credits.toString())
            courseGrade.setText(thisClass.grade)
            courseDate.setText(thisClass.startDate.toString())

            datePickerButton.setOnClickListener {
                findNavController().navigate(
                    ClassDetailFragmentDirections.selectDate(thisClass.startDate)
                )
            }
        }
    }

}