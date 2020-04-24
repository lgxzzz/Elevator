package com.smart.elevator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.smart.elevator.data.DataFactory;
import com.smart.elevator.fragement.AboutFragment;
import com.smart.elevator.fragement.SignFragment;
import com.smart.elevator.fragement.TaskFragment;
import com.smart.elevator.util.FragmentUtils;


public class MainActivity extends BaseActivtiy {

    private BottomNavigationView mBottomMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


    }

    public void init(){


        mBottomMenu = findViewById(R.id.bottom_menu);
        mBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showFragment(item.getItemId());
                return true;
            }
        });

        mBottomMenu.setSelectedItemId(R.id.bottom_menu_task);

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
        }
    }
}
