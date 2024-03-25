package edu.msoe.dematsri.transcriptmanager

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import edu.msoe.dematsri.transcriptmanager.databinding.ListItemClassBinding
import java.util.UUID

private const val TAG = "TranscriptFragment"

class ClassHolder(
    private val binding: ListItemClassBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        //thisClass:ClassDetail, onClassClicked: () -> Unit
        thisClass:ClassDetail, onClassClicked: (classID: UUID) -> Unit
    ) {
        binding.courseLine1.text = thisClass.courseID
        binding.courseLine2.text = thisClass.id.toString().substring(0,8)+" Credits: " + thisClass.credits.toString()

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${thisClass.courseID} clicked!",
                Toast.LENGTH_SHORT
            ).show()
            Log.d(TAG, "${thisClass.courseID} clicked!")

            //onClassClicked()
            onClassClicked(thisClass.id)
        }
        binding.goodGradeImage.visibility = if( thisClass.grade == "A" ) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

class ClassListAdapter(
    private val classes: List<ClassDetail>,
    //private val onClassClicked: () -> Unit
    private val onClassClicked: (classID: UUID) -> Unit
) : RecyclerView.Adapter<ClassHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : ClassHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemClassBinding.inflate(inflater, parent, false)
        return ClassHolder(binding)
    }

    override fun onBindViewHolder(holder: ClassHolder, position: Int) {
        val thisClass = classes[position]
        //holder.apply {
        //    binding.courseLine1.text = thisClass.courseID
        //    binding.courseLine2.text = thisClass.id.toString()+" Credits: " + thisClass.credits.toString()
        //}
        holder.bind(thisClass, onClassClicked)
    }

    override fun getItemCount() = classes.size
}