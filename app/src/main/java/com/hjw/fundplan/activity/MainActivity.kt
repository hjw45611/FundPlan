package com.hjw.fundplan.activity


import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentTransaction
import com.hjw.fundplan.R
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.contract.IMainPresenter
import com.hjw.fundplan.fragment.FundFragment
import com.hjw.fundplan.fragment.MainFragment
import com.hjw.fundplan.inter.SwitchFragmentListener
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : BaseActivity<IMainPresenter>(), SwitchFragmentListener {
    companion object {
        const val HOMEFRAGMENT_TAG = "home"
        const val FUNF_TAG = "fund"
    }

    var back: Boolean = true
    private var mHomeFragment: MainFragment? = null
    private var mFundFragment: FundFragment? = null

    //默认为0
    private var mIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val fManager = supportFragmentManager
            mHomeFragment = fManager.findFragmentByTag(HOMEFRAGMENT_TAG) as? MainFragment
            mFundFragment = fManager.findFragmentByTag(FUNF_TAG) as? FundFragment

        }

        super.onCreate(savedInstanceState)
    }

    private fun init() {
        switchFragment(R.id.workbench_title)
    }

    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            R.id.workbench_title//首页
            -> {
                mHomeFragment?.let {
                    transaction.show(it)
                } ?: MainFragment.newInstance().let {
                    mHomeFragment = it
                    transaction.add(
                        R.id.id_content, it,
                        HOMEFRAGMENT_TAG
                    )
                }
                mHomeFragment?.bindCallBack(this)
                //更新标题
                id_drawer_layout_toolbar?.setTitle(R.string.home_page)

            }
            R.id.fund_title//基金
            -> {
                mFundFragment?.let {
                    transaction.show(it)
                } ?: FundFragment.newInstance().let {
                    mFundFragment = it
                    transaction.add(
                        R.id.id_content, it,
                        FUNF_TAG
                    )
                }

                //更新标题
                id_drawer_layout_toolbar?.setTitle(R.string.fund)
            }
            else -> toast("没有相应界面！")
        }
        mIndex = position
        transaction.commitAllowingStateLoss()
    }

    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mFundFragment?.let { transaction.hide(it) }
    }

    override fun setNavigation(): Boolean = false
    override fun initView() {
        //DrawerLayout监听器
        val toggle = ActionBarDrawerToggle(
            this,
            id_drawer,
            id_drawer_layout_toolbar,
            R.string.app_name,
            R.string.app_name
        )
        id_drawer.addDrawerListener(toggle)
        toggle.syncState()
        init()
    }

    private fun calculateToolbarHeight(): Int {
        val idStatusBar = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(idStatusBar)
        val ta = obtainStyledAttributes(intArrayOf(R.attr.actionBarSize))
        val actionBarHeight = ta.getDimensionPixelSize(0, 0)
        return statusBarHeight + actionBarHeight
    }


    /**
     * 当使用setSupportActionBar()时,调用Toolbar.inflateMenu()时无效果,必须用此方法实现
     * @param menu
     * @return
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.draw_layout_menu, menu)
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (back && keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIndex != R.id.workbench_title) {
                switchFragment(R.id.workbench_title)
                return false
            }
            back = false
            Handler().postDelayed(Runnable { back = true }, 2000)
            Toast.makeText(this, "再按一次返回", Toast.LENGTH_SHORT).show()
            false
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun switchTag(tag: String) {
        when (tag) {
            FUNF_TAG -> switchFragment(
                R.id.fund_title
            )
        }
    }

    override fun initPresenter() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}
