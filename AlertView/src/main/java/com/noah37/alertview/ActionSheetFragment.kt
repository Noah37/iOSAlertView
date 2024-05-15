package com.noah37.alertview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.noah37.alertview.databinding.FragmentActionSheetBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ActionSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ActionSheetFragment(private val title: String, private val message: String, private val actions: ArrayList<UIAlertAction>,
                          private val theme: AlertThemeStyle) : BottomSheetDialogFragment() {

    private var _binding: FragmentActionSheetBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var tvTitle:AppCompatTextView
    private lateinit var tvMessage:AppCompatTextView
    private lateinit var tvCancel:AppCompatTextView
    private lateinit var actionsLayout:LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentActionSheetBinding.inflate(inflater, container, false)

        // Inflate view according to theme selected. Default is AlertTheme.LIGHT
        var view:View? = null
        if (theme == AlertThemeStyle.LIGHT)
            view = inflater.inflate(R.layout.alert_layout_light, container, false)
        else if (theme == AlertThemeStyle.DARK)
             view = inflater.inflate(R.layout.alert_layout_dark, container, false)

        tvTitle = view!!.findViewById(R.id.tvTitle)
        tvMessage = view.findViewById(R.id.tvMessage)
        tvCancel = view.findViewById(R.id.tvCancel)
        actionsLayout = view.findViewById(R.id.actionsLayout)

        // Set up view
        initView(view)

        return view
    }

    private fun initView(view: View?) {
        tvTitle.text = title
        tvMessage.text = message
        tvCancel.visibility = View.GONE

        // In case of title or message is empty
        if (title.isEmpty()) tvTitle.visibility = View.GONE
        if (message.isEmpty()) tvMessage.visibility = View.GONE

        // Inflate action views
        inflateActionsView(actionsLayout, actions)
    }

    /**
     * Inflate action views
     */
    private fun inflateActionsView(actionsLayout: LinearLayout?, actions: ArrayList<UIAlertAction>) {
        for (action in actions) {

            // Inflate action view according to theme selected
            var view: View? = null
            if (theme == AlertThemeStyle.LIGHT)
                view = LayoutInflater.from(context).inflate(R.layout.action_layout_light, null)
            else if (theme == AlertThemeStyle.DARK)
                view = LayoutInflater.from(context).inflate(R.layout.action_layout_dark, null)

            val tvAction:AppCompatTextView = view!!.findViewById(R.id.tvAction)

            tvAction.text = action.title

            // Click listener for action.
            tvAction.setOnClickListener {
                dismiss()

                // For Kotlin
                action.action?.invoke(action)

                // For Java
                action.actionListener?.onActionClick(action)
            }

            // Action text color according to AlertActionStyle
            if (context != null) {
                when (action.style) {
                    UIAlertActionStyle.DESTRUCTIVE -> {
                        tvAction.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                    }
                    UIAlertActionStyle.CANCEL -> {
                        tvAction.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    }
                    UIAlertActionStyle.DEFAULT -> {
                        if (theme == AlertThemeStyle.LIGHT)
                            tvAction.setTextColor(ContextCompat.getColor(requireContext(), R.color.optionsColor))
                        else
                            tvAction.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }

                }
            }

//            Add view to layout
            actionsLayout?.addView(view)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ActionSheetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(title: String, message: String, actions: ArrayList<UIAlertAction>,
                        theme: AlertThemeStyle) =
            ActionSheetFragment(title, message, actions, theme).apply {
                arguments = Bundle().apply {

                }
            }
    }
}