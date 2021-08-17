package build.free.mrgds2.bckup

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view :View = inflater.inflate(R.layout.fragment_audio, container, false)


        val fab: FloatingActionButton = view.findViewById(R.id.return_fab)

        fab.setOnClickListener { v->
            Snackbar.make(view, "Back to record page", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            Navigation.findNavController(v).navigate(R.id.action_back_to_recordFragment)
        }


        return view
    }

    override fun onDetach() {
        super.onDetach()

    }



}
