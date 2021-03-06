package org.mydaily.ui.view.task.detail

import android.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import androidx.core.widget.addTextChangedListener
import com.xw.repo.BubbleSeekBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mydaily.R
import org.mydaily.databinding.ActivityTaskAddBinding
import org.mydaily.ui.base.BaseActivity
import org.mydaily.ui.viewmodel.TaskViewModel

class TaskAddActivity : BaseActivity<ActivityTaskAddBinding, TaskViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_task_add
    override val viewModel: TaskViewModel by viewModel()

    private var isTitleEntered = false
    private var isDescriptionEntered = false

    private var intentKeywordId: Int = 0
    private var intentKeywordName: String = ""
    private var intentTasksId: Int = 0


    private var isInputChanged: Boolean = false

    private fun isSaveButtonEnabled(): Boolean = isTitleEntered && isDescriptionEntered && isInputChanged

    override fun initView() {
        getIntentData()
        initToolbar()

        initEditText()
        initViewAttribute()
        initBubbleSeekBar()

        initSaveButton()
    }

    override fun initBeforeBinding() {
        binding.lifecycleOwner = this
        viewModel.getTaskById(intentTasksId)
    }

    override fun initAfterBinding() {
        observeTaskDetail()
    }

    override fun onBackPressed() {
        showBackPressedDialog()
    }

    private fun getIntentData() {
        intentKeywordId = intent.getIntExtra("keywordId", 0)
        intentKeywordName = intent.getStringExtra("keywordName") ?: "NULL"
        intentTasksId = intent.getIntExtra("taskId", 0)

        binding.tvKeyword.text = intentKeywordName
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbDailyDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.tbDailyDetail.setNavigationOnClickListener {//수정 시도했을때
            showBackPressedDialog()
        }
    }

    private fun initViewAttribute() {
        binding.btnSave.isEnabled = false
        binding.tvSatisfactionScore.text = binding.bubbleSeekBar.progress.toString()
    }

    private fun initEditText() {
        binding.etTitle.addTextChangedListener {
            val length = binding.etTitle.length()
            isTitleEntered = length > 0
            isInputChanged = true
            binding.tvTitleByte.text = length.toString()
            binding.btnSave.isEnabled = isSaveButtonEnabled()
        }
        binding.etDescription.addTextChangedListener {
            val length = binding.etDescription.length()
            isDescriptionEntered = length > 0
            isInputChanged = true
            binding.tvDescByte.text = length.toString()
            binding.btnSave.isEnabled = isSaveButtonEnabled()
        }
    }

    private fun initBubbleSeekBar() {
        binding.bubbleSeekBar.onProgressChangedListener = bubbleSeekBarListener
    }

    private fun initSaveButton() {
        if(intent.action == "MODIFY"){ //수정
            binding.btnSave.setOnClickListener {
                viewModel.putTask(
                    intentTasksId, binding.etTitle.text.toString(),
                    binding.etDescription.text.toString(), binding.bubbleSeekBar.progress
                )
                finish()
            }
        }
        else {  //저장
            binding.btnSave.setOnClickListener {
                viewModel.postTask(
                    intentKeywordId.toString(), binding.etTitle.text.toString(),
                    binding.etDescription.text.toString(), binding.bubbleSeekBar.progress
                )
                finish()
            }
        }
    }

    private fun observeTaskDetail() {
        if(intent.action == "MODIFY"){
            viewModel.task.observe(this, {
                binding.etTitle.setText(it.title)
                binding.etDescription.setText(it.detail)
                binding.bubbleSeekBar.setProgress(it.satisfaction.toFloat())
                binding.tvSatisfactionScore.text = it.satisfaction.toString()
                binding.tvTitleByte.text = binding.etTitle.text.length.toString()
                binding.tvDescByte.text = binding.etDescription.text.length.toString()
            })
        }
    }

    private val bubbleSeekBarListener = object : BubbleSeekBar.OnProgressChangedListener {
        override fun onProgressChanged(
            bubbleSeekBar: BubbleSeekBar?,
            progress: Int,
            progressFloat: Float,
            fromUser: Boolean
        ) {
            binding.tvSatisfactionScore.text = progress.toString()
        }

        override fun getProgressOnActionUp(
            bubbleSeekBar: BubbleSeekBar?,
            progress: Int,
            progressFloat: Float
        ) {}

        override fun getProgressOnFinally(
            bubbleSeekBar: BubbleSeekBar?,
            progress: Int,
            progressFloat: Float,
            fromUser: Boolean
        ) {}
    }

    private fun showBackPressedDialog(){
        if (isInputChanged) {
            AlertDialog.Builder(this)
                .setTitle("정말 뒤로가시겠어요?")
                .setMessage("뒤로가기를 누르시면 작성 중인 내용이 삭제되고 이전 페이지로 돌아갑니다.")
                .setPositiveButton("뒤로가기") { _, _ ->
                    finish()
                }
                .setNegativeButton("취소하기") { _, _ ->
                }
                .create()
                .show()

        }
        else {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(intent.action=="MODIFY"){
            menuInflater.inflate(R.menu.menu_delete, menu)
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                AlertDialog.Builder(this)
                    .setTitle("이 기록을 삭제하시겠어요?")
                    .setPositiveButton("삭제하기"){ _, _ ->
                        viewModel.deleteTask(intentTasksId)
                        finish()
                    }
                    .setNegativeButton("취소하기"){ _, _ ->

                    }
                    .create()
                    .show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}