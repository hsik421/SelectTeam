package com.mobile.app.selectteam.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mobile.app.selectteam.R
import com.mobile.app.selectteam.ui.team.TeamFragment
import com.mobile.app.selectteam.databinding.MainFragmentBinding
import com.mobile.app.selectteam.ui.MainActivity
import com.mobile.app.selectteam.util.InjectorUtils
import com.mobile.app.selectteam.util.SpacesItemDecoration
import com.mobile.app.selectteam.util.replaceBackFragmentInActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var viewDataBinding : MainFragmentBinding
    private val mainAdapter by lazy { MainAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater,
            R.layout.main_fragment,container,false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = InjectorUtils.provideMainViewModelFactory(context?:return)
        viewModel = ViewModelProviders.of(requireActivity(),factory).get(MainViewModel::class.java)

        subscribeUi()
        setupView()
        setupListener()
    }
    private fun subscribeUi(){
        viewModel.listEvent.observe(requireActivity(), Observer {
            (activity as MainActivity).replaceBackFragmentInActivity(
                TeamFragment.newInstance(),
                R.id.contentFrame
            )
        })
        viewModel.allDatas.observe(this, Observer {
            viewDataBinding.hasDatas = (it != null && it.isNotEmpty())
            if(it != null && it.isNotEmpty()){
                mainAdapter.submitList(it.filter {data -> data.select }.toMutableList())
            }else{
                mainAdapter.notifyDataSetChanged()
            }
        })
    }
    private fun setupView(){
        viewDataBinding.recycler.apply {
            adapter = mainAdapter
            layoutManager = GridLayoutManager(context,2)
        }
        (activity as MainActivity).fab?.setImageDrawable(resources.getDrawable(R.drawable.baseline_playlist_add_white_24,null))
    }
    private fun setupListener(){
        viewDataBinding.swipe.setOnRefreshListener {
            mainAdapter.submitList(viewModel.suffleData())
        }
        mainAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
                viewDataBinding.swipe.isRefreshing = false
                super.onChanged()
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                viewDataBinding.swipe.isRefreshing = false
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                viewDataBinding.swipe.isRefreshing = false
                super.onItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                viewDataBinding.swipe.isRefreshing = false
                super.onItemRangeChanged(positionStart, itemCount, payload)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                viewDataBinding.swipe.isRefreshing = false
                super.onItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                viewDataBinding.swipe.isRefreshing = false
                super.onItemRangeRemoved(positionStart, itemCount)
            }
        })
    }
}
