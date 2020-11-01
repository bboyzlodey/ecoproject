package skarlat.dev.ecoproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import skarlat.dev.ecoproject.R;


public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//		FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
//				getSupportFragmentManager(), FragmentPagerItems.with(this)
//						                             .add("2", PageFragment.class)
//						                             .add("1".concat(" !"), PageFragment.class)
//						                             .create());
//
//		ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//		viewPager.setAdapter(adapter);
//
//		SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
//		viewPagerTab.setViewPager(viewPager);
    }
}
