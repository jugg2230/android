package Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dream on 2016/11/6.
 */

public abstract class BaseFragment extends Fragment {
    private View mRootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView=LayoutInflater.from(getContext()).inflate(getLayoutId(),container,false);
        bindView();
        return mRootView;
    }
    public abstract int getLayoutId();
    public abstract int bindView();
}
