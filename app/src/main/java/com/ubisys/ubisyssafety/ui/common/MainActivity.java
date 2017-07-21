package com.ubisys.ubisyssafety.ui.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ubisys.ubisyssafety.R;
import com.ubisys.ubisyssafety.ui.contacts.ContactsFragment;
import com.ubisys.ubisyssafety.ui.message.MessageFragment;
import com.ubisys.ubisyssafety.ui.mine.MineFragment;
import com.ubisys.ubisyssafety.ui.smartcampus.SamartCampusFragment;

import butterknife.Bind;
import ubisyssafety.ubisys.framelibrary.framebase.FrameBaseActivity;
import ubisyssafety.ubisys.framelibrary.statusbar.FillSystemUtil;


public class MainActivity extends FrameBaseActivity {

    @Bind(R.id.btn_safely_school)
    ImageView btnSafelySchool;
    @Bind(R.id.main_msg_number)
    TextView mainMsgNumber;
    @Bind(R.id.rl_safely_school)
    RelativeLayout rlSafelySchool;
    @Bind(R.id.btn_conversation)
    ImageView btnConversation;
    @Bind(R.id.unread_msg_number)
    TextView unreadMsgNumber;
    @Bind(R.id.rl_container_conversation)
    RelativeLayout rlContainerConversation;
    @Bind(R.id.btn_contacts)
    ImageView btnContacts;
    @Bind(R.id.unread_address_number)
    TextView unreadAddressNumber;
    @Bind(R.id.rl_container_address_list)
    RelativeLayout rlContainerAddressList;
    @Bind(R.id.btn_settings)
    ImageView btnSettings;
    @Bind(R.id.setting_msg_number)
    TextView settingMsgNumber;
    @Bind(R.id.rl_container_setting)
    RelativeLayout rlContainerSetting;
    @Bind(R.id.main_bottom)
    LinearLayout mainBottom;
    @Bind(R.id.fragment_container)
    RelativeLayout fragmentContainer;
    private int index;
    private int currentTabIndex=0;
    private Fragment[] fragments;
    private SamartCampusFragment samartCampusFragment;
    private MessageFragment messageFragment;
    private ContactsFragment contactsFragment;
    private MineFragment mineFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FillSystemUtil(this).fillSystemBarTM();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        samartCampusFragment = new SamartCampusFragment();
        messageFragment = new MessageFragment();
        contactsFragment = new ContactsFragment();
        mineFragment = new MineFragment();
        fragments = new Fragment[] { samartCampusFragment, messageFragment, contactsFragment,
                mineFragment };
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, samartCampusFragment)
                .add(R.id.fragment_container, messageFragment).hide(messageFragment)
                .show(samartCampusFragment).commit();
        btnSafelySchool.setSelected(true);
        btnSafelySchool.setImageResource(R.drawable.safely_school_selected);
//        new UpdateManager(this,true).checkUpdate();
    }

    private void changeShowImage() {
        btnSafelySchool.setImageResource(R.drawable.safely_school_normal);
        btnConversation.setImageResource(R.drawable.conversation_normal);
        btnContacts.setImageResource(R.drawable.contact_list_normal);
        btnSettings.setImageResource(R.drawable.settings_normal);
    }
    /**
     * on tab clicked
     */
    public void onTabClicked(View view) {
        changeShowImage();
        switch (view.getId()) {
            case R.id.btn_safely_school:
                index = 0;
                btnSafelySchool.setImageResource(R.drawable.safely_school_selected);
                break;
            case R.id.btn_conversation:
                btnConversation.setImageResource(R.drawable.conversation_selected);
                index = 1;
                break;
            case R.id.btn_contacts:
                btnContacts.setImageResource(R.drawable.contact_list_selected);
                index = 2;
                break;
            case R.id.btn_settings:
                btnSettings.setImageResource(R.drawable.settings_selected);
                index = 3;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
//        mTabs[currentTabIndex].setSelected(false);
//        // set current tab selected
//        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }
    @Override
    protected void initEvent() {

    }


}
