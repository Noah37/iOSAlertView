package com.noah37.alertview

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.noah37.alertview.databinding.FragmentIOSActionSheetBinding

class iOSActionSheetFragment(private val title: String, private val message: String, private val actions: ArrayList<UIAlertAction>,
                             private val theme: AlertThemeStyle, private val cancelButtonText: String) : BottomSheetDialogFragment() {

    private var _binding: FragmentIOSActionSheetBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvMessage: AppCompatTextView
    private lateinit var tvCancel: AppCompatTextView
    private lateinit var actionsLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentIOSActionSheetBinding.inflate(inflater, container, false)

        // Inflate view according to theme selected. Default is AlertTheme.LIGHT
        var view: View? = null
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 确保布局完成后设置透明度
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
        tvCancel.text = cancelButtonText

        // In case of title or message is empty
        if (title.isEmpty()) tvTitle.visibility = View.GONE
        if (message.isEmpty()) tvMessage.visibility = View.GONE
        if (cancelButtonText.isEmpty()) tvCancel.text = requireContext().getString(R.string.cancel)

        // Inflate action views
        inflateActionsView(actionsLayout, actions)
    }

    /**
     * Inflate action views
     */
    private fun inflateActionsView(actionsLayout: LinearLayout?, actions: ArrayList<UIAlertAction>) {
        for (action in actions) {
            if (action.style == UIAlertActionStyle.CANCEL) {
                tvCancel.text = action.title
                tvCancel.setOnClickListener {
                    dismiss()

                    // For Kotlin
                    action.action?.invoke(action)

                    // For Java
                    action.actionListener?.onActionClick(action)
                }
                continue
            }

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
                        tvAction.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    }
                    UIAlertActionStyle.CANCEL -> {
                        tvAction.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                    }
                    UIAlertActionStyle.DEFAULT -> {
                        if (theme == AlertThemeStyle.LIGHT)
                            tvAction.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                        else
                            tvAction.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }
                }
            }

            // Add view to layout
            actionsLayout?.addView(view)
        }
    }
}