package skarlat.dev.ecoproject.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import skarlat.dev.ecoproject.activity.PageFragment;

import skarlat.dev.ecoproject.fragment.UserFragment;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
	final int PAGE_COUNT = 2;
	private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
	private Context context;
	
	public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}
	
	@Override public int getCount() {
		return PAGE_COUNT;
	}
	
	@Override public Fragment getItem(int position) {
		
		return position == 0  ? PageFragment.newInstance(position + 1) : UserFragment.newInstance(2);
	}
	
	@Override public CharSequence getPageTitle(int position) {
		// генерируем заголовок в зависимости от позиции
		return null;
	}
	
}
