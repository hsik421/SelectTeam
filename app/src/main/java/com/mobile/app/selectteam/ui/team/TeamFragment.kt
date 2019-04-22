package com.mobile.app.selectteam.ui.team

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mobile.app.selectteam.R
import com.mobile.app.selectteam.databinding.TeamFragmentBinding
import com.mobile.app.selectteam.ui.MainActivity
import com.mobile.app.selectteam.ui.main.MainViewModel
import com.mobile.app.selectteam.util.InjectorUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class TeamFragment : Fragment() {

    companion object {
        fun newInstance() = TeamFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var viewDataBinding : TeamFragmentBinding
    private val teamAdapter by lazy { TeamAdapter(viewModel) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater,
            R.layout.team_fragment,container,false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = InjectorUtils.provideMainViewModelFactory(context?:return)
        viewModel = ViewModelProviders.of(requireActivity(),factory).get(MainViewModel::class.java)
        viewDataBinding.viewModel = viewModel
        setupView()
        subscribeUi()

        viewModel.load()
    }
    private fun setupView(){
        viewDataBinding.recycler.apply {
            adapter = teamAdapter
            layoutManager = LinearLayoutManager(context)
            if(itemDecorationCount == 0) addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
            val itemTouchHelper = ItemTouchHelper(viewModel.getSwipeCallback(resources))
            itemTouchHelper.attachToRecyclerView(this)
        }
        (activity as MainActivity).fab?.setImageDrawable(resources.getDrawable(R.drawable.baseline_add_white_24,null))
    }
    private fun subscribeUi(){
        viewModel.allDatas.observe(this, Observer {
            viewDataBinding.hasDatas = (it != null && it.isNotEmpty())
            if(it != null && it.isNotEmpty()){
                teamAdapter.submitList(it)
            }else{
                teamAdapter.notifyDataSetChanged()
            }
        })
        viewModel.selectEvent.observe(this, Observer {
            teamAdapter.notifyDataSetChanged()
        })
        viewModel.finishEvent.observe(this, Observer {
            fragmentManager?.popBackStack()
        })
    }
}

