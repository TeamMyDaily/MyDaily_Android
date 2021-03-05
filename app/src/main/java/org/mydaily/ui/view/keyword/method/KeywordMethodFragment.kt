package org.mydaily.ui.view.keyword.method

import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mydaily.R
import org.mydaily.databinding.FragmentKeywordMethodBinding
import org.mydaily.ui.base.BaseFragment
import org.mydaily.ui.view.keyword.guide.KeywordGuideSelectFragment
import org.mydaily.ui.viewmodel.KeywordViewModel
import org.mydaily.util.extension.replace

class KeywordMethodFragment : BaseFragment<FragmentKeywordMethodBinding, KeywordViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_keyword_method
    override val viewModel: KeywordViewModel by viewModel()

    override fun initView() {
        initClickEvent()
    }

    override fun initBeforeBinding() {

    }

    override fun initAfterBinding() {

    }

    private fun initClickEvent() {
        binding.tvFollowGuide.setOnClickListener {
            replace(R.id.container_keyword, KeywordGuideSelectFragment())
        }
    }
}