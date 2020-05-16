package com.example.gowithme.ui.event_page

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentEventCommentsBinding
import com.example.gowithme.ui.event_page.adapter.CommentRecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class EventCommentsFragment : Fragment() {

    private lateinit var binding: FragmentEventCommentsBinding
    private val commentRecyclerAdapter by lazy { CommentRecyclerAdapter() }
    private val safeArgs by navArgs<EventCommentsFragmentArgs>()
    private val eventId by lazy { safeArgs.eventId }
    private val eventPageViewModel by viewModel<EventPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_comments, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventPageViewModel.getComments(eventId)
        with(binding) {
            commentList.adapter = commentRecyclerAdapter
            send.setOnClickListener {
                Log.d("taaag", comment.text.toString())
                if (comment.text.toString().isNotBlank()) {
                    eventPageViewModel.postComment(eventId, comment.text.toString())
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        eventPageViewModel.eventDetailsUI.observe(viewLifecycleOwner, Observer {
            when(it) {
                is EventPageUI.EventComments -> {
                    commentRecyclerAdapter.setComment(it.comments)
                }

                is EventPageUI.PostCommentSuccess -> {
                    commentRecyclerAdapter.addComment(it.comment)
                    binding.commentList.scrollToPosition(commentRecyclerAdapter.commentsAmount - 1)
                    binding.comment.setText("")
                    val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view?.windowToken, 0)
                }
            }
        })

    }



}
