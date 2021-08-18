package build.free.mrgds2.bckup


import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import java.io.IOException
import java.util.jar.Manifest


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
    lateinit var ibRecordListBtn: ImageButton
    private var isRecording: Boolean = false
    private val TAG = "RecordPermission"
    private val RECORD_REQUEST_CODE = 101
    private val mediaRecorder: MediaRecorder? = null
    private var mediaPath: String = ""
    private var mediaFile: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_record, container, false)

        ibRecord_button = view.findViewById(R.id.record_btn)
        ibRecordListBtn = view.findViewById(R.id.record_list_btn)


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDetach() {
        super.onDetach()
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


    private fun toggleButtonRecord() {
        isRecording = if (isRecording) {
            //stop recording
            stopRecording()
            ibRecord_button.setImageDrawable(
                resources.getDrawable(
                    R.drawable.record_btn_stopped,
                    null
                )
            )
            false
        } else {
            //start recording
            startRecording()
            ibRecord_button.setImageDrawable(
                resources.getDrawable(
                    R.drawable.record_btn_recording,
                    null
                )
            )
            true


        }
    }

    private fun startRecording() {

        Toast.makeText(activity, "Recording now..", Toast.LENGTH_SHORT).show()

        //path where file is saved after recording
        mediaPath = requireActivity().getExternalFilesDir("/")!!.absolutePath
        mediaFile = "BckUP_File_20210818.mp3"

        //TODO:ERROR CHECK
        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder.setOutputFile("$mediaPath/$mediaFile")
        //TODO: try catch?
        try {
            mediaRecorder.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mediaRecorder.start()
    }


    private fun stopRecording() {

        val savedFileName = "$mediaPath/$mediaFile"

        Toast.makeText(activity, "Saved file: /$savedFileName", Toast.LENGTH_SHORT).show()
        mediaRecorder!!.stop()
        mediaRecorder.release()
    }

}