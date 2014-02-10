package lv.bizapps.menudrawerproba;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	protected DrawerLayout drawerLayout;
	protected View leftMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout1);
		leftMenu = findViewById(R.id.leftMenu1);

		Button b = (Button)leftMenu.findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawerLayout.closeDrawer(Gravity.LEFT);
			}
		});

		ListView lv = (ListView)leftMenu.findViewById(R.id.listView1);
		LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View footerView = li.inflate(R.layout.list_footer, null);
		lv.addFooterView(footerView);
		lv.setAdapter(new CustomListAdapter(getApplicationContext()));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
				showDetails(pos);
				drawerLayout.closeDrawer(Gravity.LEFT);
			}
		});

		showDetails(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
			if(drawerLayout.isDrawerOpen(Gravity.LEFT)) {
				drawerLayout.closeDrawer(Gravity.LEFT);
			}
			else drawerLayout.openDrawer(Gravity.LEFT);
		}

		return super.onOptionsItemSelected(item);
	}

	public void showDetails(int position) {
		Fragment fragment = new DetailsFragment();

		Bundle b = new Bundle();
		b.putInt("pos", position);
		fragment.setArguments(b);

		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
	}

	class CustomListAdapter extends BaseAdapter {
		protected Context ctx;
		protected LayoutInflater li;

		public CustomListAdapter(Context ctx) {
			this.ctx = ctx;
			this.li = (LayoutInflater)this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return 20;
		}

		@Override
		public Object getItem(int id) {
			return id;
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int id, View view, ViewGroup viewGroup) {
			view = li.inflate(android.R.layout.simple_list_item_1, viewGroup, false);

			TextView tv = (TextView)view.findViewById(android.R.id.text1);
			tv.setTextColor(Color.BLACK);
			tv.setText("POS: "+id);

			return view;
		}		
	}
}
