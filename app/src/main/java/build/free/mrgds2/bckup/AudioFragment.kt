package build.free.mrgds2.bckup

import FileListAdapter
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.RecyclerView
import java.io.File


// TODO: Rename parameter arguments, choose names that match


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AudioFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AudioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var playerSheet: ConstraintLayout

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var allFiles : Array<File>

    private lateinit var fileListAdapter : FileListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_audio, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerSheet =view.findViewById(R.id.player_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(playerSheet)

        bottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback) //set bottom sheet callback

        mRecyclerView= view.findViewById(R.id.audio_list) //init recycler view


        fileListAdapter = FileListAdapter(getDirectory())
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = fileListAdapter //set list adapter


        val fab: FloatingActionButton = view.findViewById(R.id.return_fab)

        fab.setOnClickListener { v->
            Navigation.findNavController(v).navigate(R.id.action_back_to_recordFragment)
        }

    }


    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
               Toast.makeText(requireContext(),"Dismiss",Toast.LENGTH_SHORT).show()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {/**nothing can be done**/ }
    }

    private fun getDirectory(): Array<File> {

        val path = requireActivity()
            .getExternalFilesDir("/")!!.absolutePath

        allFiles =  File(path).listFiles() //set directory for files to be put in array

        return allFiles
    }


    override fun onDetach() {
        super.onDetach()

    }



}
