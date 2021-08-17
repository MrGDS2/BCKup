package build.free.mrgds2.bckup


import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController


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
    lateinit var  ibRecord_button: ImageButton
    lateinit var ibRecordListBtn: ImageButton
    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_record, container, false)

        ibRecord_button= view.findViewById(R.id.record_btn)
        ibRecordListBtn= view.findViewById(R.id.record_list_btn)


        ibRecord_button.setOnClickListener {
            Toast.makeText(activity, "Recording now..", Toast.LENGTH_SHORT).show()
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
}
