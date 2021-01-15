package org.mydaily.ui.view.keyword

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mydaily.R
import org.mydaily.databinding.ActivityKeywordSelectBinding
import org.mydaily.ui.base.BaseActivity
import org.mydaily.ui.view.keyword.popup.KeywordPopupActivity
import org.mydaily.ui.view.keyword.settings.KeywordSettingsActivity
import org.mydaily.ui.viewmodel.KeywordViewModel
import org.mydaily.util.extension.setupToast

class KeywordSelectActivity : BaseActivity<ActivityKeywordSelectBinding, KeywordViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_keyword_select

    override val viewModel: KeywordViewModel by viewModel()

    private val setlifewordlist = arrayListOf<String>()
    private val setworkwordlist = arrayListOf<String>()
    private val setmywordlist = arrayListOf<String>()

    private var clickedChipCount: Int = 0
    private var finalSelectedKeywordList = mutableListOf<String>()
    private val keywords: List<String> = finalSelectedKeywordList

    override fun initView() {
        initToolbar()
        getkeywordlistIntent()
        addChipLife()
        addChipWork()
        addChipMy()
        onClickBtnSelectFinish()
        setupToast(this, viewModel.toastMessage)

    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbKeywordSelectActivity)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
        )
        binding.tbKeywordSelectActivity.setNavigationOnClickListener {
            finish()
        }
    }

    override fun initBeforeBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun floatingDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.up_to_four)
            .setMessage(R.string.think_more)
            .setPositiveButton(getString(R.string.okay), null)
            .create()
            .show()
    }

    private fun addKeywordList(text: String) {
        finalSelectedKeywordList.add(text)
    }

    private fun removeKeywordList(text: String) {
        finalSelectedKeywordList.remove(text)
    }

    private fun getkeywordlistIntent() {
        setlifewordlist.addAll(intent.getStringArrayListExtra("selectedlifeword") as ArrayList)
        setworkwordlist.addAll(intent.getStringArrayListExtra("selectedworkword") as ArrayList)
        setmywordlist.addAll(intent.getStringArrayListExtra("selectedmyword") as ArrayList)
    }

    private fun addChipLife() {
        for (str in setlifewordlist) {
            binding.cgLifeFour.addView(createChip(str))
        }
        setlifewordlist.clear()
    }

    private fun addChipWork() {
        for (str in setworkwordlist) {
            binding.cgWorkFour.addView(createChip(str))
        }
        setworkwordlist.clear()
    }

    private fun addChipMy() {
        for (str in setmywordlist) {
            binding.cgMyWordFour.addView(createChip(str))
        }
        setmywordlist.clear()
    }

    private fun createChip(str: String): Chip {
        val chipDrawable = ChipDrawable.createFromAttributes(
            this,
            null,
            0,
            R.style.Widget_MaterialComponents_Chip_Choice
        )
        return Chip(this).apply {
            text = str
            setChipDrawable(chipDrawable)
            setChipBackgroundColorResource(R.color.selector_chip)
            setTextAppearance(R.style.MyDailyChipTextStyleAppearance)
            setRippleColorResource(android.R.color.transparent)
            setOnClickListener {
                it as Chip
                if (isChecked) {
                    clickedChipCount++
                    if (clickedChipCount >= 5) {
                        binding.btnSelectFourFinish.isEnabled = true
                        it.isChecked = false
                        clickedChipCount--
                        floatingDialog()
                    } else {
                        addKeywordList(it.text as String)
                        binding.btnSelectFourFinish.isEnabled = true
                    }
                } else {
                    clickedChipCount--
                    if (clickedChipCount < 1) {
                        binding.btnSelectFourFinish.isEnabled = false
                    }
                    removeKeywordList(it.text as String)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_help -> {
                val intent = Intent(this, KeywordPopupActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_keyword_list, menu)
        return true
    }

    private fun onClickBtnSelectFinish() {
        binding.btnSelectFourFinish.setOnClickListener {
            viewModel.postKeywordSelect(keywords)

            val intent = Intent(this, KeywordSettingsActivity::class.java)
            intent.putStringArrayListExtra("keywords", keywords as java.util.ArrayList<String>)
            startActivity(intent)
        }
    }
}