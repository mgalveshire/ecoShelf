package edu.arizona.cast.mollyalveshire.ecoshelf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.arizona.cast.mollyalveshire.ecoshelf.R

class OptionsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_options, container, false) // Ensure you have a corresponding layout
    }
}