package com.yelai.wearable.ui.mine

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.router.Router
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import com.yelai.wearable.AppData
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.base.WebActivity
import com.yelai.wearable.present.PViod
import com.yelai.wearable.ui.login.LoginActivity
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.mine_setting_activity.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.sport_type_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class SettingActivity : BaseActivity<PViod>(){

    internal val adapter: Adapter by lazy {
        Adapter(context)
    }


    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("设置", ContextCompat.getColor(this, R.color.text_black_color))
        showToolbar()


        contentLayout.recyclerView.verticalLayoutManager(context)
        contentLayout.recyclerView.horizontalDividerMargin(R.color.text_black_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.isRefreshEnabled = false
        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null))

        contentLayout.recyclerView.setPage(1, 1)
        contentLayout.showContent()


        adapter.setData(listOf(
                "修改密码","缓存管理","运动风险须知",
                "关于乐生"))

        adapter.notifyDataSetChanged()


        btnLogout.onClick {

            SweetAlertDialog(this@SettingActivity.context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("退出登录")
                    .setCancelText("取消")
                    .setConfirmText("好的")
                    .showCancelButton(true)
                    .setConfirmClickListener { sDialog -> run{

                        AppData.user = null;
                        AppData.userInfo = null;

                        LoginActivity.launch(this@SettingActivity.context)

                        AppManager.finishAllActivity()
                    }
                    }
                    .show()

        }


    }



    override fun getLayoutId(): Int = R.layout.mine_setting_activity

    override fun newP(): PViod = PViod()


    internal inner class Adapter(context: Context) : SimpleRecAdapter<String, Adapter.ViewHolder>(context) {

        override fun newViewHolder(itemView: View): ViewHolder {
            return ViewHolder(itemView)
        }

        override fun getLayoutId(): Int {
            return R.layout.sport_type_item
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]

            holder.setData(item)

            holder.itemView.setOnClickListener {

                when(position){
                    0->{
                        ModifyPasswordActivity.launch(context)
                    }
                    1->{

                        SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("清理缓存")
                                .setCancelText("取消")
                                .setConfirmText("好的")
                                .showCancelButton(true)
                                .setConfirmClickListener { sDialog -> sDialog
                                        .setTitleText("清理缓存成功!")
                                        .setConfirmText("好的")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                }
                                .show()
                    }
                    2->{

                        WebActivity.launch(this@SettingActivity.context,"http://sport.aieye8.com/item/notes.html","运动风险须知");
                    }
                    3->{
                        WebActivity.launch(this@SettingActivity.context,"http://sport.aieye8.com/item/about.html","关于乐生");

                    }
                }


            }
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun setData(data: String) {

                itemView.tvSportName.text = data
            }

        }
    }

    companion object {

        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(SettingActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }

}
