package com.hjw.fundplan.activity


import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.hjw.fundplan.fragment.FundFragment
import com.hjw.fundplan.fragment.MainFragment
import com.hjw.fundplan.R
import com.hjw.fundplan.base.BaseActivity
import com.hjw.fundplan.contract.IMainPresenter
import com.hjw.fundplan.inter.SwitchFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : BaseActivity<IMainPresenter>(), SwitchFragment {
    companion object {
        const val HOMEFRAGMENT_TAG = "home"
        const val FUNF_TAG = "fund"
    }

    var toobar: Toolbar? = null
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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        translucentStatusBar();
        setContentView(R.layout.activity_main)
        initView()
        init()
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
                toobar?.setTitle(R.string.home_page)

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
                toobar?.setTitle(R.string.fund)
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

    override fun initView() {
        toobar = id_drawer_layout_toolbar as Toolbar
        setSupportActionBar(toobar)
//        id_drawer_layout_toolbar.setNavigationIcon(R.mipmap.ic_drawer_home)
        //DrawerLayout监听器
        val toggle = ActionBarDrawerToggle(
            this,
            id_drawer,
            toobar,
            R.string.app_name,
            R.string.app_name
        )
        id_drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun calculateToolbarHeight(): Int {
        val idStatusBar = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(idStatusBar)
        val ta = obtainStyledAttributes(intArrayOf(R.attr.actionBarSize))
        val actionBarHeight = ta.getDimensionPixelSize(0, 0)
        return statusBarHeight + actionBarHeight
    }

    /**
     * 实现5.0以上状态栏透明(默认状态是半透明)
     */
    private fun translucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decorView = window.decorView as ViewGroup
            val option: Int =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = ContextCompat.getColor(
                applicationContext,
                R.color.light_menu_header
            )
        }
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
}
