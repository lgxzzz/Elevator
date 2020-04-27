package com.smart.elevator;

import android.app.Person;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;

import com.smart.elevator.bean.User;
import com.smart.elevator.constant.Constant;
import com.smart.elevator.data.DBManger;
import com.smart.elevator.data.DataFactory;
import com.smart.elevator.fragement.AboutFragment;
import com.smart.elevator.fragement.ElevotarMgrFragment;
import com.smart.elevator.fragement.ElevotarParamsMgrFragment;
import com.smart.elevator.fragement.PersonMgrFragment;
import com.smart.elevator.fragement.SignFragment;
import com.smart.elevator.fragement.TaskFragment;
import com.smart.elevator.util.FragmentUtils;


public class MainActivity extends BaseActivtiy {

    private BottomNavigationView mSysPersonBottomMenu;
    private BottomNavigationView mReceptPersonBottomMenu;
    private BottomNavigationView mRepairPersonBottomMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


    }

    public void init(){
        User mUser = DBManger.getInstance(this).mUser;
        mSysPersonBottomMenu = findViewById(R.id.sys_person_bottom_menu);
        mReceptPersonBottomMenu = findViewById(R.id.recept_person_bottom_menu);
        mRepairPersonBottomMenu = findViewById(R.id.repair_person_bottom_menu);
        if (mUser!=null){
            String role = mUser.getRole();
            if (role.equals("维保人员")){
                mSysPersonBottomMenu.setVisibility(View.GONE);
                mReceptPersonBottomMenu.setVisibility(View.GONE);
                mRepairPersonBottomMenu.setVisibility(View.VISIBLE);
            }else if(role.equals("维保接待员")){
                mSysPersonBottomMenu.setVisibility(View.GONE);
                mReceptPersonBottomMenu.setVisibility(View.VISIBLE);
                mRepairPersonBottomMenu.setVisibility(View.GONE);
            }else if(role.equals("维保系统管理员")){
                mSysPersonBottomMenu.setVisibility(View.VISIBLE);
                mReceptPersonBottomMenu.setVisibility(View.GONE);
                mRepairPersonBottomMenu.setVisibility(View.GONE);
            }
        }

        mSysPersonBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showFragment(item.getItemId());
                return true;
            }
        });
        mSysPersonBottomMenu.setSelectedItemId(R.id.bottom_menu_elevotar);

        mReceptPersonBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showFragment(item.getItemId());
                return true;
            }
        });
        mReceptPersonBottomMenu.setSelectedItemId(R.id.bottom_menu_report);

        mRepairPersonBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showFragment(item.getItemId());
                return true;
            }
        });
        mRepairPersonBottomMenu.setSelectedItemId(R.id.bottom_menu_task);

    }


    /**
     * 根据id显示相应的页面
     * @param menu_id
     */
    private void showFragment(int menu_id) {
        switch (menu_id){
            case R.id.bottom_menu_sign:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, SignFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_task:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, TaskFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_about:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, AboutFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_elevotar:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, ElevotarMgrFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_elevotar_params:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, ElevotarParamsMgrFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_person_manager:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, PersonMgrFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_report:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, AboutFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_make_task:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, AboutFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_plan:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, AboutFragment.getInstance(),R.id.main_frame);
                break;
        }
    }

}
