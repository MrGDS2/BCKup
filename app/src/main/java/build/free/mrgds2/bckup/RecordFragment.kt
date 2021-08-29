package build.free.mrgds2.bckup


import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.concurrent.timer


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [recordFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [recordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordFragment : Fragment() {
    lateinit var ibRecord_button: ImageButton
    lateinit var ibRecordListBtn: FloatingActionButton
    lateinit var tvFileNameText : TextView
    lateinit var chronometer: Chronometer
    private var isRecording: Boolean = false
    private val TAG = "RecordPermission"
    private val RECORD_REQUEST_CODE = 101
    private var mediaRecorder: MediaRecorder? = MediaRecorder()
    private var mediaPath  = ""
    private var bckupFileName = "BckUP_"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_record, container, false)

        ibRecord_button = view.findViewById(R.id.record_btn)
        ibRecordListBtn = view.findViewById(R.id.record_list_btn)
        tvFileNameText =view.findViewById(R.id.record_filename)
        chronometer = view.findViewById(R.id.record_timer)

        ibRecord_button.setOnClickListener {

            if (setupPermissions()) {
                toggleButtonRecord()
            } else
                Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show()
        }

        ibRecordListBtn.setOnClickListener {
            Toast.makeText(activity, "Showing list!", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).navigate(R.id.action_recordFragment_to_audioFragment)
        }


        // Inflate the layout for this fragment
        return view
    }


    override fun onPause() {
        super.onPause()

        // Free up resources from MediaRecorder when leaving Fragment
        if (mediaRecorder != null) {
           mediaRecorder?.release() //release in case object is not closed
           mediaRecorder= null
        }
        Toast.makeText(activity,"changed screens recoding has stopped",Toast.LENGTH_SHORT).show()
    }


    private fun setupPermissions(): Boolean {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.RECORD_AUDIO
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeRequest()
            return false
        }
        return true
    }


    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.RECORD_AUDIO),
            RECORD_REQUEST_CODE
        )
    }


    @SuppressLint("SetTextI18n")
    private fun toggleButtonRecord() {
        isRecording = if (isRecording) {

            stopRecording() //stop recording

            tvFileNameText.text = "Saved File: " + getOutputFileName() //show user recording file name

            ibRecord_button.setImageDrawable(
                resources.getDrawable(
                    R.drawable.record_btn_stopped,
                    null
                )
            )
            false
        } else {
            startRecording() //start recording

            tvFileNameText.text = "Recording File: ${getOutputFileName()}" //show user recording file name

            Glide.with(this).load(R.drawable.record_btn_playing).into(ibRecord_button)// Adding the gif here using glide library
            true
        }
    }


    @SuppressLint("SetTextI18n")
    private fun startRecording() {

        chronometer.base = SystemClock.elapsedRealtime() //get system clock (elapsed real time)
        chronometer.start() //start UI timer

        Toast.makeText(activity,
            "Recording now..", Toast.LENGTH_SHORT).show() //path where file is saved after recording
        mediaPath = requireActivity().getExternalFilesDir("/")!!.absolutePath

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder?.setOutputFile("$mediaPath/${getOutputFileName()}")


        //try catch to catch error when mediaRecorder is unprepared
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
        }  catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private fun stopRecording() {

         chronometer.stop() //stops UI timer

        val savedFilePath = "$mediaPath/$bckupFileName"

        if( doesFileExits()){
            Log.w("SAVED FILE==> ","Saved file: $savedFilePath")
        }
        else {
            Log.w("SAVED FILE==> ","File not created")
        }

        Toast.makeText(activity, "Saved file: $savedFilePath", Toast.LENGTH_SHORT).show()
        mediaRecorder?.stop()
        mediaRecorder?.reset()  // set state to idle
        mediaRecorder?.release()
        mediaRecorder=null

    }
    private fun getOutputFileName(): String {

        val mediaFile = "$bckupFileName${currentDateTime()}" //val with current date

        return "$mediaFile.mp3"
    }

    private fun currentDateTime() : String{

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HH_mm_ss")

        return current.format(formatter)
    }


    private fun doesFileExits() :Boolean {

        val fileName = getOutputFileName()
        val file = File(fileName)

        return file.exists()
    }
}