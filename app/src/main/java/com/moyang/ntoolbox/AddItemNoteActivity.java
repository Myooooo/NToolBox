package com.moyang.ntoolbox;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddItemNoteActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private RelativeLayout validPeriodRelativeLayout, alertRelativeLayout;
    private EditText editItemTitle, editItemLocation, editItemQuantity, editItemQuantifier, editItemDescription, editItemTags;
    private Button selectValidPeriodButton, selectDueDateButton, selectAlertDateButton, itemConfirmButton;
    private TextView tvValidPeriod, tvDueDate, tvAlertDate;
    private Switch dueFlagSwitch, isAlertSwitch, isStarredSwitch;

    private ItemNote itemNote;
    private String title;
    private String description;
    private List<String> tags;
    private boolean isAlert;
    private Date alertDate;
    private boolean isStarred;

    private String location;
    private int quantity;
    private String quantifier;
    private int[] validPeriod = new int[4];
    private Date dueDate;
    private boolean dueFlag;

    private int[] selectedDate = new int[5]; // 年，月，日，小时, 分钟
    private String dateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_note);

        validPeriodRelativeLayout = (RelativeLayout) findViewById(R.id.valid_period_relative_layout);
        alertRelativeLayout = (RelativeLayout) findViewById(R.id.alert_relative_layout);

        editItemTitle = (EditText) findViewById(R.id.edit_item_title);
        editItemLocation = (EditText) findViewById(R.id.edit_item_location);
        editItemQuantity = (EditText) findViewById(R.id.edit_item_quantity);
        editItemQuantifier = (EditText) findViewById(R.id.edit_item_quantifier);
        editItemDescription = (EditText) findViewById(R.id.edit_item_description);
        editItemTags = (EditText) findViewById(R.id.edit_item_tags) ;

        selectValidPeriodButton = (Button) findViewById(R.id.item_select_valid_period_button);
        selectDueDateButton = (Button) findViewById(R.id.item_select_due_date_button);
        selectAlertDateButton = (Button) findViewById(R.id.item_select_alert_date_button);
        itemConfirmButton = (Button) findViewById(R.id.item_confirm_button);

        tvValidPeriod = (TextView) findViewById(R.id.item_valid_period);
        tvDueDate = (TextView) findViewById(R.id.item_due_date);
        tvAlertDate = (TextView) findViewById(R.id.item_alert_date);

        dueFlagSwitch = (Switch) findViewById(R.id.item_due_flag_switch);
        isAlertSwitch = (Switch) findViewById(R.id.item_is_alert_switch);
        isStarredSwitch = (Switch) findViewById(R.id.item_is_starred_switch);

        title = "无标题";
        description = "无";
        tags = new ArrayList<>();
        isAlert = false;
        alertDate = new Date(0);
        isStarred = false;

        location = "无位置信息";
        quantity = 1;
        quantifier = "个";
        for(int i = 0; i < 4; i++){
            validPeriod[i] = 0;
        }
        dueDate = alertDate;
        dueFlag = false;

        initView();
    }

    private void initView(){

        // 显示返回按钮
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        validPeriodRelativeLayout.setVisibility(View.GONE);
        alertRelativeLayout.setVisibility(View.GONE);

        editItemQuantifier.setText(quantifier);

        // 保质期开关监听
        dueFlagSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    validPeriodRelativeLayout.setVisibility(View.VISIBLE);
                }else {
                    validPeriodRelativeLayout.setVisibility(View.GONE);
                }
            }
        });

        // 提醒开关监听
        isAlertSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    alertRelativeLayout.setVisibility(View.VISIBLE);
                }else {
                    alertRelativeLayout.setVisibility(View.GONE);
                }
            }
        });

        selectValidPeriodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSetPeriodDialog();
            }
        });

        selectDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDateTimePickerDialog(0);
            }
        });

        selectAlertDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDateTimePickerDialog(1);
            }
        });

        itemConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createObject();
            }
        });

    }

    private void createSetPeriodDialog(){

        final AlertDialog.Builder setPeriodDialog = new AlertDialog.Builder(this);
        setPeriodDialog.setTitle("输入保质期");
        View view = View.inflate(this, R.layout.set_period_dialog, null);
        final EditText inputYears = (EditText) view.findViewById(R.id.input_years);
        final EditText inputMonths = (EditText) view.findViewById(R.id.input_months);
        final EditText inputDays = (EditText) view.findViewById(R.id.input_days);
        setPeriodDialog.setView(view);
        setPeriodDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(inputYears.getText())){
                    validPeriod[0] = 0;
                }else{
                    validPeriod[0] = Integer.parseInt(inputYears.getText().toString());
                }
                if(TextUtils.isEmpty(inputMonths.getText())){
                    validPeriod[1] = 0;
                }else{
                    validPeriod[1] = Integer.parseInt(inputMonths.getText().toString());
                }
                if(TextUtils.isEmpty(inputDays.getText())){
                    validPeriod[2] = 0;
                }else{
                    validPeriod[2] = Integer.parseInt(inputDays.getText().toString());
                }
                validPeriod[3] = 0;
                TimeUtility.standardizePeriod(validPeriod);

                dueDate.setTime(System.currentTimeMillis() + TimeUtility.toMillis(validPeriod));

                if(validPeriod[0] == 0 && validPeriod[2] == 0 && validPeriod[3] == 0){
                    tvValidPeriod.setText(new StringBuffer().append("保质期限：已过期"));
                    dueFlag = false;
                }else{
                    tvValidPeriod.setText(new StringBuffer().append("保质期限：")
                            .append(validPeriod[0]).append("年 ")
                            .append(validPeriod[1]).append("个月 ")
                            .append(validPeriod[2]).append("天 ")
                            .append(validPeriod[3]).append("小时"));
                }

                tvDueDate.setText(new StringBuffer().append("到期时间：").append(TimeUtility.formatTime(4,dueDate)));
            }
        });
        setPeriodDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        setPeriodDialog.show();
    }

    private void createDateTimePickerDialog(final int mode){

        Calendar calendar = Calendar.getInstance();

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectedDate[3] = hourOfDay;
                selectedDate[4] = minute;
                dateStr = selectedDate[0] + "年" + selectedDate[1] + "月" + selectedDate[2] + "日 " + selectedDate[3] + ":" + selectedDate[4];
                if(mode == 0){
                    dueDate = TimeUtility.toDate(selectedDate);
                    validPeriod = TimeUtility.daysToYears(TimeUtility.getTimeDiff(new Date(System.currentTimeMillis()),dueDate));
                    tvDueDate.setText(new StringBuffer().append("到期时间：").append(dateStr));
                    if(validPeriod[0] < 0 || validPeriod[1] < 0 || validPeriod[2] < 0 || validPeriod[3] < 0){
                        tvValidPeriod.setText("保质期限：已过期");
                        dueFlag = false;
                    }else if(validPeriod[0] == 0 && validPeriod[1] == 0 && validPeriod[2] == 0 && validPeriod[3] == 0){
                        tvValidPeriod.setText("保质期限：已过期");
                        dueFlag = false;
                    }else{
                        tvValidPeriod.setText(new StringBuffer().append("保质期限：")
                                .append(validPeriod[0]).append("年 ")
                                .append(validPeriod[1]).append("个月 ")
                                .append(validPeriod[2]).append("天 ")
                                .append(validPeriod[3]).append("小时"));
                    }
                }else{
                    alertDate = TimeUtility.toDate(selectedDate);
                    tvAlertDate.setText(new StringBuffer().append("提醒时间：").append(dateStr));
                }
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),false);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddItemNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate[0] = year;
                selectedDate[1] = month + 1;
                selectedDate[2] = dayOfMonth;
                timePickerDialog.show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void createObject(){
        // 检查输入的完整性
        if(TextUtils.isEmpty(editItemTitle.getText())){
            createErrorDialog("请输入物品名称！");
        }else if(TextUtils.isEmpty(editItemLocation.getText())){
            createErrorDialog("请输入物品位置！");
        }else if(dueFlagSwitch.isChecked() && dueDate.getTime() == 0){
            createErrorDialog("请选择保质期或到期时间！");
        }else if(isAlertSwitch.isChecked() && alertDate.getTime() == 0){
            createErrorDialog("请选择提醒时间！");
        }else{
            title = editItemTitle.getText().toString();
            if(!TextUtils.isEmpty(editItemDescription.getText())){
                description = editItemDescription.getText().toString();
            }
            isAlert = isAlertSwitch.isChecked();
            isStarred = isStarredSwitch.isChecked();

            // 添加标签并去重
            if(!TextUtils.isEmpty(editItemTags.getText())){
                String[] splitTags = editItemTags.getText().toString().split("\\s+");
                for(String s : splitTags){
                    tags.add(s);
                }
                tags = DataUtility.clearDuplicate(tags);
                DataUtility.tags.addAll(tags);
                DataUtility.clearDuplicate(DataUtility.tags);
            }

            location = editItemLocation.getText().toString();
            if(!TextUtils.isEmpty(editItemQuantity.getText())){
                quantity = Integer.parseInt(editItemQuantity.getText().toString());
            }
            if(!TextUtils.isEmpty(editItemQuantifier.getText())){
                quantifier = editItemQuantifier.getText().toString();
            }
            dueFlag = dueFlagSwitch.isChecked();

            itemNote = new ItemNote(title, description, tags, isAlert, alertDate, isStarred, location, quantity, quantifier, dueDate, dueFlag);

            DataUtility.ItemNoteList.add(0, itemNote);

            Intent i = new Intent();
            setResult(Constants.ADD_ITEM_NOTE_SUCCESS,i);
            finish();
        }
    }

    private void createErrorDialog(String errorMessage){
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
        errorDialog.setTitle("错误");
        errorDialog.setMessage(errorMessage);
        errorDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        errorDialog.show();
    }

    // 配置左上返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                createCancelDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 返回键弹窗
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            createCancelDialog();
        }
        return false;
    }

    // 弹窗
    private void createCancelDialog(){
        AlertDialog.Builder isCancel = new AlertDialog.Builder(this);
        isCancel.setTitle("确定要退出吗？");
        isCancel.setMessage("当前页面所做的更改不会被保存");
        isCancel.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent();
                setResult(Constants.ADD_NOTE_ERROR,i);
                finish();
            }
        });
        isCancel.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        isCancel.show();
    }

    // 点击外部隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    // 隐藏键盘
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onRestart(){
        super.onRestart();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
