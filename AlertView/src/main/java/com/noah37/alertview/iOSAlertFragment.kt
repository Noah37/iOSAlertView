package com.noah37.alertview

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.DialogFragment
import com.noah37.alertview.databinding.FragmentIOSAlertBinding

/**
 * A simple [Fragment] subclass.
 * Use the [iOSAlertFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class iOSAlertFragment(private val title: String, private val message: String, private val actions: ArrayList<UIAlertAction>,
                       private val theme: AlertThemeStyle, private val cancelButtonText: String) : DialogFragment() {

    private var _binding: FragmentIOSAlertBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvMessage: AppCompatTextView
    private lateinit var gridLayout:GridLayout
    private lateinit var primaryTextView:TextView
    private lateinit var secondaryTextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIOSAlertBinding.inflate(inflater, container, false)

        // Inflate view according to theme selected. Default is AlertTheme.LIGHT
        var view: View? = null
        if (actions.count() == 2)
            view = inflater.inflate(R.layout.alert_layout_two_button_light, container, false)
        else if (theme == AlertThemeStyle.LIGHT)
            view = inflater.inflate(R.layout.alert_layout_light, container, false)
        else if (theme == AlertThemeStyle.DARK)
            view = inflater.inflate(R.layout.alert_layout_dark, container, false)

        tvTitle = view!!.findViewById(R.id.tvTitle)
        tvMessage = view.findViewById(R.id.tvMessage)
        gridLayout = view.findViewById(R.id.gridLayout)
        if (actions.count() == 2) {
            primaryTextView = view.findViewById(R.id.primaryText)
            secondaryTextView = view.findViewById(R.id.secondaryText)
        }

        // Set up view
        initView(view)

        return view
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val view: View? = dialog.window?.decorView
        // 设置背景为半透明
        view?.setBackgroundResource(android.R.color.transparent)
        // 设置蒙层透明度，例如50%的透明度
        val attributes: WindowManager.LayoutParams? = dialog.window?.attributes
        attributes?.dimAmount = 0.2f
        dialog.window?.attributes = attributes
    }

    private fun initView(view: View?) {
        tvTitle.text = title
        tvMessage.text = message

        // In case of title or message is empty
        if (title.isEmpty()) tvTitle.visibility = View.GONE
        if (message.isEmpty()) tvMessage.visibility = View.GONE

        // Inflate action views
        inflateActionsView(gridLayout, actions)
    }

    private fun setupActionView(actionView:TextView, action:UIAlertAction) {
        actionView.text = action.title
        // Click listener for action.
        actionView.setOnClickListener {
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
                    actionView.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                }
                UIAlertActionStyle.CANCEL -> {
                    actionView.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                }
                UIAlertActionStyle.DEFAULT -> {
                    if (theme == AlertThemeStyle.LIGHT)
                        actionView.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                    else
                        actionView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
        }
    }

    /**
     * Inflate action views
     */
    private fun inflateActionsView(gridLayout: GridLayout?, actions: ArrayList<UIAlertAction>) {
        val multiLine:Boolean = actions.count() != 2
        if (!multiLine) {
            setupActionView(primaryTextView, actions[0])
            setupActionView(secondaryTextView, actions[1])
            return
        }
        var index = -1
        for (action in actions) {
            index += 1

            // Inflate action view according to theme selected
            var view: View? = null
            if (theme == AlertThemeStyle.LIGHT)
                view = LayoutInflater.from(context).inflate(R.layout.action_layout_light, null)
            else if (theme == AlertThemeStyle.DARK)
                view = LayoutInflater.from(context).inflate(R.layout.action_layout_dark, null)

            val tvAction:TextView = view!!.findViewById(R.id.tvAction)

            setupActionView(tvAction, action)

            // Add view to layout
            gridLayout?.addView(view)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment iOSAlertFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(title: String, message: String, actions: ArrayList<UIAlertAction>, theme: AlertThemeStyle, cancelButtonText: String) =
            iOSAlertFragment(title, message, actions, theme, cancelButtonText).apply {
                arguments = Bundle().apply {

                }
            }
    }
}