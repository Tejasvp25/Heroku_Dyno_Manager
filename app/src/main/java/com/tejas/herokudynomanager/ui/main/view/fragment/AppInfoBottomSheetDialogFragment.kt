package com.tejas.herokudynomanager.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tejas.herokudynomanager.R
import com.tejas.herokudynomanager.network.models.DynoFormation
import kotlinx.android.synthetic.main.fragment_dyno_info.*

class AppInfoBottomSheetDialogFragment: BottomSheetDialogFragment() {
    lateinit var dynoFormation: DynoFormation
    companion object{
        const val TAG = "AppInfoBottomSheetDialogFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_dyno_info,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            arguments?.let {
                dynoFormation = requireArguments().getSerializable("dyno") as DynoFormation
                dynoFormation?.let {
                    text_dyno_command.text = dynoFormation.command
                    text_dyno_quantity.text = dynoFormation.quantity.toString()
                    text_dyno_size.text = dynoFormation.size
                    text_dyno_type.text = dynoFormation.type
                    text_dyno_createdat.text = dynoFormation.createdAt
                    text_dyno_updatedat.text = dynoFormation.updatedAt
                }
            }

    }
}