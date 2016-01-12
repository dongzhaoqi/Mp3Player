package com.dong.mp3player;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.dong.R;
import com.dong.download.HttpDownloader;
import com.dong.model.Mp3Info;
import com.dong.service.DownloadServie;
import com.dong.xml.Mp3ListContentHandler;

public class Mp3ListActivity extends ListActivity {

	private static final int UPDATE = 1;
	private static final int MAIN = 2;
	private List<HashMap<String, String>> list = new ArrayList<>();
	private List<Mp3Info> mp3Infos = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		//updateListVIew();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, UPDATE, 1, R.string.update_list);
		menu.add(0, MAIN, 2, R.string.main);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == UPDATE) {
			updateListVIew();
		} else if (id == MAIN) {
			Intent intent = new Intent(Mp3ListActivity.this,CustomizedListView.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Mp3Info mp3Info = mp3Infos.get(position);
		Intent intent = new Intent();
		intent.putExtra("mp3Info", mp3Info);
		intent.setClass(this, DownloadServie.class);
		startService(intent);

	}

	private void updateListVIew() {
		String xml = downloadXml("http://192.168.1.127:8080/mp3/resources.xml");
		mp3Infos = parse(xml);
		list.clear();
		for (Iterator iterator = mp3Infos.iterator(); iterator.hasNext();) {
			Mp3Info mp3Info = (Mp3Info) iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("mp3_name", mp3Info.getMp3Name());
			map.put("mp3_size", mp3Info.getMp3Size());
			list.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.list_item, new String[] { "mp3_name", "mp3_size" },
				new int[] { R.id.mp3_name, R.id.mp3_size });
		setListAdapter(adapter);
	}

	private String downloadXml(String urlString) {
		HttpDownloader httpDownloader = new HttpDownloader();
		// String urlString = "http://192.168.1.127/mp3/resources.xml";
		String result = httpDownloader.download(urlString);
		return result;
	}

	private List<Mp3Info> parse(String xmlStr) {

		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		List<Mp3Info> infos = new ArrayList<Mp3Info>();
		try {
			XMLReader xmlReader = saxParserFactory.newSAXParser()
					.getXMLReader();
			Mp3ListContentHandler mp3ListContentHandler = new Mp3ListContentHandler(
					infos);
			xmlReader.setContentHandler(mp3ListContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
				Mp3Info mp3Info = (Mp3Info) iterator.next();
				System.out.println("mp3Info:" + mp3Info);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return infos;
	}
}
