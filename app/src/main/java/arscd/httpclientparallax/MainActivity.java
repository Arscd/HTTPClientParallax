package arscd.httpclientparallax;

import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import domain.Clip;
import restclient.ClipRetriever;

/**
 * Created by Arscd on 07.12.2015.
 */
public class MainActivity extends ListActivity {

    private List<Clip> clipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        new DownloadClipsTask().execute(new Void[]{});
    }

    class DownloadClipsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),
                    "Downloading clips...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            clipList = new ClipRetriever().getClipList();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            setListAdapter(new Adapter());

        }
    }

    class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return clipList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.v_item, parent, false);
            convertView.setTag(convertView.findViewById(R.id.image));
            ImageView imageView = (ImageView) convertView.getTag();
            imageView.setImageDrawable(clipList.get(position).getPicture());

            convertView.setTag(convertView.findViewById(R.id.clip_name));
            TextView textView = (TextView) convertView.getTag();
            textView.setText(clipList.get(position).getName());

            convertView.setTag(convertView.findViewById(R.id.artists));
            TextView textViewArt = (TextView) convertView.getTag();
            textViewArt.setText(clipList.get(position).listTotal(clipList.get(position).getArtists()));

            convertView.setTag(convertView.findViewById(R.id.clip_view));
            TextView textViewClipView = (TextView) convertView.getTag();
            textViewClipView.setText(clipList.get(position).getViewCount().toString());

            return convertView;
        }
    }
}
