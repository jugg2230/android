package Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Dream on 2016/11/6.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public abstract int getLayoutId();
    private BaseActivity thisActivity;

    /**
     * bindView is only used to bindView
     */
    public abstract void bindView();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindView();
    }
}
