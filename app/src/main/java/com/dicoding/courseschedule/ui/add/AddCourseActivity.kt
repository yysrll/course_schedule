package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel
    private lateinit var inputCourseName: TextInputEditText
    private lateinit var inputLecturer: TextInputEditText
    private lateinit var inputNote: TextInputEditText
    private lateinit var spinner: Spinner
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.title = getString(R.string.add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        inputCourseName = findViewById(R.id.input_course_name)
        inputLecturer = findViewById(R.id.input_lecturer)
        inputNote = findViewById(R.id.input_note)
        spinner = findViewById(R.id.spinner_day)
        tvStartTime = findViewById(R.id.tv_start_time)
        tvEndTime = findViewById(R.id.tv_end_time)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

        viewModel.saved.observe(this, {
            if (it.getContentIfNotHandled() == true) {
                finish()
            } else {
                Toast.makeText(this, getString(R.string.input_empty_message), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_insert -> {
                insertCourse()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertCourse() {
        val courseName = inputCourseName.text.toString().trim()
        val lecturer = inputLecturer.text.toString().trim()
        val note = inputNote.text.toString().trim()
        val day = spinner.selectedItemPosition
        val start = tvStartTime.text.toString().trim()
        val end = tvEndTime.text.toString().trim()
        Log.d("TIMESTART", start)

        if (courseName.isNotBlank() && note.isNotBlank() && lecturer.isNotBlank() && (start.isNotBlank() && start != getString(
                R.string.time_template
            )) && (end.isNotBlank() && end != getString(R.string.time_template))
        ) {
            viewModel.insertCourse(courseName, day, start, end, lecturer, note)
        } else {
            Toast.makeText(this, getString(R.string.input_empty_message), Toast.LENGTH_SHORT).show()
        }
    }

    fun showTimePicker(view: View) {
        val dialogFragment = TimePickerFragment()
        val tag = when(view.id) {
            R.id.btn_start_time -> "start"
            R.id.btn_end_time -> "end"
            else -> ""
        }
        dialogFragment.show(supportFragmentManager, tag)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val formatTime = SimpleDateFormat("kk:mm", Locale.getDefault())
        if (tag == "start") {
            tvStartTime.text = formatTime.format(calendar.time).toString()
        } else {
            tvEndTime.text = formatTime.format(calendar.time).toString()
        }
    }
}