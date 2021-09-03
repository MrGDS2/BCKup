import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import build.free.mrgds2.bckup.R
import java.io.File
import java.util.*

class FileListAdapter(private val mList: Array<File>) : RecyclerView.Adapter<FileListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.audio_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
          //holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
          holder.tvFileName.text = itemsViewModel.name
          holder.tvDateOnFile.text = Date(itemsViewModel.lastModified()).toString()
          holder.tvLocationOnFile.text = "Poughkeepsie, Ny"
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

       //for each view in list init buttons and text
        val ivPlayButton: ImageView = itemView.findViewById(R.id.media_list_play_btn)
        val ivMediaImage : ImageView = itemView.findViewById(R.id.media_audio_img)
        val tvFileName: TextView = itemView.findViewById(R.id.name_of_media_tv)
        val tvDateOnFile: TextView = itemView.findViewById(R.id.date_of_media_tv)
        val tvLocationOnFile: TextView = itemView.findViewById(R.id.location_of_media_tv)
    }
}