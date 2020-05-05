package com.smart.elevator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.smart.elevator.adapter.SearchElevatorAdapter;
import com.smart.elevator.adapter.UserAdapter;
import com.smart.elevator.bean.Elevator;
import com.smart.elevator.bean.User;
import com.smart.elevator.data.DBManger;
import com.smart.elevator.fragement.AboutFragment;
import com.smart.elevator.fragement.ElevotarMgrFragment;
import com.smart.elevator.fragement.ElevotarParamsMgrFragment;
import com.smart.elevator.fragement.OperateTaskFragment;
import com.smart.elevator.fragement.PersonMgrFragment;
import com.smart.elevator.fragement.PlanFragment;
import com.smart.elevator.fragement.QrcodeReportFragment;
import com.smart.elevator.fragement.ReportFragment;
import com.smart.elevator.fragement.SignFragment;
import com.smart.elevator.fragement.TaskFragment;
import com.smart.elevator.util.FragmentUtils;

import java.util.ArrayList;
import java.util.List;


public class SearchElevatorActivity extends BaseActivtiy {

    List<Elevator> mElevators = new ArrayList<>();

    ListView mListView;

    SearchElevatorAdapter mAdapter;

    EditText mEleSearchEd;

    Button mEleSearchClearBtn;

    private RadioGroup mSearchRg;

    String mSearchKey = "LIFT_USER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_elevator);

        init();

        searchData();
    }

    public void init(){
        mEleSearchEd = findViewById(R.id.ele_search_ed);
        mEleSearchClearBtn = findViewById(R.id.ele_search_clear_btn);
        mSearchRg = findViewById(R.id.rg_search_key);
        mListView = findViewById(R.id.search_ele_list);

        mAdapter = new SearchElevatorAdapter(this);
        mListView.setAdapter(mAdapter);

        mEleSearchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchData();
            }
        });

        mEleSearchClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEleSearchEd.setText("");
                searchData();
            }
        });

        mSearchRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.rb_user:
                        mSearchKey = "LIFT_USER";
                        break;
                    case R.id.rb_maintence:
                        mSearchKey = "LIFT_MAINTENANCENAME_ID";
                        break;
                    case R.id.rb_brand:
                        mSearchKey = "LIFT_BRANDID";
                        break;
                    default:

                        break;
                }
            }
        });
    }


    public void searchData(){
        String value = mEleSearchEd.getEditableText().toString();
        List<Elevator> tempElevator = DBManger.getInstance(this).QueryElevatorsByKey(mSearchKey,value);
        if (tempElevator.size()>0){
            mElevators = tempElevator;
            mAdapter.setData(mElevators);
        }
    }

}
