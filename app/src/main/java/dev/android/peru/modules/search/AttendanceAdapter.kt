package dev.android.peru.modules.search

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import dev.android.peru.R
import kotlinx.android.synthetic.main.row_attendance.view.*
import peru.android.dev.androidutils.inflate
import peru.android.dev.datamodel.Attendance
import kotlin.properties.Delegates

class AttendanceAdapter: RecyclerView.Adapter<AttendanceAdapter.ViewHolder>() {

    var data: List<Attendance> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.newInstance(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        companion object {
            fun newInstance(parent: ViewGroup): ViewHolder {
                val view = parent.inflate(R.layout.row_attendance, attachToRoot = false)
                return ViewHolder(view)
            }
        }

        fun bind(attendance: Attendance) {
            itemView.attendanceIdTextView.text = attendance.id
            itemView.attendanceNameTextView.text = attendance.name

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Clicked: $attendance", Toast.LENGTH_SHORT).show()
            }
        }
    }

}