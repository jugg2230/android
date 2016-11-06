package Helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import Base.BaseActivity;

/**
 * Created by Dream on 2016/11/6.
 */

public class FragmentHelper {
    private BaseActivity mContext;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    public FragmentHelper(BaseActivity mContext){
        this.mContext=mContext;
        mFragmentManager =mContext.getSupportFragmentManager();
        mTransaction=mFragmentManager.beginTransaction();
    }
    public void addFragment(Fragment fragment){
        mTransaction.replace(mContext.getLayoutId(),fragment);
        mTransaction.commit();
    }
    public void removeFragment(Fragment fragment){
        mTransaction.remove(fragment);
        mTransaction.commit();
    }
}
