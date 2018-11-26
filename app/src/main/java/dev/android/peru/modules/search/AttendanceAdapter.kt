package dev.android.peru.modules.search

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.android.peru.R
import kotlinx.android.synthetic.main.row_attendance.view.*
import peru.android.dev.androidutils.inflate
import peru.android.dev.datamodel.Attendance
import kotlin.properties.Delegates

class AttendanceAdapter(val callback: Callback): RecyclerView.Adapter<AttendanceAdapter.ViewHolder>() {

    var data: List<Attendance> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.newInstance(parent, callback)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(itemView: View, val callback: Callback): RecyclerView.ViewHolder(itemView) {

        companion object {
            fun newInstance(parent: ViewGroup, callback: Callback): ViewHolder {
                val view = parent.inflate(R.layout.row_attendance, attachToRoot = false)
                return ViewHolder(view, callback)
            }
        }

        fun bind(attendance: Attendance) {
            itemView.attendanceIdTextView.text = attendance.id
            itemView.attendanceNameTextView.text = attendance.name

            val color = ContextCompat.getColor(itemView.context, when {
                attendance.didShowUp -> R.color.colorPrimary
                else -> R.color.textColor
            })
            itemView.attendanceIdTextView.setTextColor(color)

            itemView.setOnClickListener {
                callback.onAttendanceSelected(attendance)
            }
        }
    }

    interface Callback {
        fun onAttendanceSelected(attendance: Attendance)
    }
}