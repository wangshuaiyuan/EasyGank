/*
 * {EasyGank}  Copyright (C) {2015}  {CaMnter}
 *
 * This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 * You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <http://www.gnu.org/licenses/>.
 *
 * The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <http://www.gnu.org/philosophy/why-not-lgpl.html>.
 */

package com.camnter.easygank.core;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.camnter.easygank.R;

/**
 * Description：BaseToolbarActivity
 * Created by：CaMnter
 * Time：2016-01-05 00:32
 */
public abstract class BaseToolbarActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected AppBarLayout mAppBarLayout;
    protected ActionBarHelper mActionBarHelper;
    protected boolean mIsHidden = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());
        this.initToolbar();
    }

    protected void initToolbar() {

        this.mToolbar = this.findView(R.id.toolbar);
        this.mAppBarLayout = this.findView(R.id.app_bar_layout);

        if (this.mToolbar == null || this.mAppBarLayout == null)
            return;

        this.setSupportActionBar(this.mToolbar);

        this.mActionBarHelper = this.createActionBarHelper();
        this.mActionBarHelper.init();


        if (Build.VERSION.SDK_INT >= 21) {
            this.mAppBarLayout.setElevation(6.6f);
        }
    }


    /**
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected void showBack() {
        this.mActionBarHelper.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * set the AppBarLayout alpha
     *
     * @param alpha alpha
     */
    protected void setAppBarLayoutAlpha(float alpha) {
        this.mAppBarLayout.setAlpha(alpha);
    }

    /**
     * set the AppBarLayout visibility
     *
     * @param visibility visibility
     */
    protected void setAppBarLayoutVisibility(boolean visibility) {
        // 0.4秒内
        this.mAppBarLayout.animate()
                .setDuration(400)
                .translationY(visibility ? 0 : -this.mAppBarLayout.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        if (visibility) {
            this.mAppBarLayout.setVisibility(View.VISIBLE);
        } else {
            this.mAppBarLayout.setVisibility(View.GONE);
        }

    }

    /**
     * Fill in layout id
     *
     * @return layout id
     */
    protected abstract int getLayoutId();

    /**
     * Find the view by id
     *
     * @param id  id
     * @param <V> V
     * @return V
     */
    @SuppressWarnings("unchecked")
    protected <V extends View> V findView(int id) {
        return (V) this.findViewById(id);
    }

    /**
     * Create a compatible helper that will manipulate the action bar if available.
     */
    public ActionBarHelper createActionBarHelper() {
        return new ActionBarHelper();
    }

    public class ActionBarHelper {
        private final ActionBar mActionBar;
        public CharSequence mDrawerTitle;
        public CharSequence mTitle;

        public ActionBarHelper() {
            this.mActionBar = getSupportActionBar();
        }

        public void init() {
            if (this.mActionBar == null) return;
            this.mActionBar.setDisplayHomeAsUpEnabled(true);
            this.mActionBar.setDisplayShowHomeEnabled(false);
            this.mTitle = mDrawerTitle = getTitle();
        }

        public void onDrawerClosed() {
            if (this.mActionBar == null) return;
            this.mActionBar.setTitle(this.mTitle);
        }

        public void onDrawerOpened() {
            if (this.mActionBar == null) return;
            this.mActionBar.setTitle(this.mDrawerTitle);
        }

        public void setTitle(CharSequence title) {
            this.mTitle = title;
        }

        public void setDrawerTitle(CharSequence drawerTitle) {
            this.mDrawerTitle = drawerTitle;
        }

        public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
            if (this.mActionBar == null) return;
            this.mActionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        }

    }

}
