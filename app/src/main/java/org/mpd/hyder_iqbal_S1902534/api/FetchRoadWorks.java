//Iqbal_Haider_S1902534
package org.mpd.hyder_iqbal_S1902534.api;

import android.util.Log;
import org.mpd.hyder_iqbal_S1902534.modelclasses.RoadWorksModel;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class FetchRoadWorks implements Callable<ArrayList<RoadWorksModel>> {
    private final String urlString;
    private ArrayList<RoadWorksModel> arrayList = new ArrayList<>();
    private Boolean itemTagStart = false;

    public FetchRoadWorks(String url) {
        this.urlString = url;
    }

    @Override
    public ArrayList<RoadWorksModel> call() throws IOException, XmlPullParserException {
        URLConnection yc;
        BufferedReader in;
        String inputLine;
        URL url = new URL(urlString);
        yc = url.openConnection();
        in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        StringBuilder result = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            result.append(inputLine);
        }
        in.close();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        RoadWorksModel roadWorksModel = null;

        xpp.setInput(new StringReader(result.toString()));
        int eventType = xpp.getEventType();
        String tag = "", text = "";
        while (eventType != XmlPullParser.END_DOCUMENT) {
            tag = xpp.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tag.equals("item")) {
                        itemTagStart = true;
                        roadWorksModel = new RoadWorksModel();
                    }
                    break;
                case XmlPullParser.TEXT:
                    text = xpp.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if (itemTagStart) {
                        switch (tag) {
                            case "title":
                                roadWorksModel.setTitle(text);
                                break;
                            case "description":
                                roadWorksModel.setDescription(text);
                                break;
                            case "location":
                                roadWorksModel.setLink(text);
                                break;
                            case "pubDate":
                                roadWorksModel.setPubDate(text);
                                break;
                            case "point":
                                roadWorksModel.setLocPoints(text);
                                break;
                            case "item":
                                RoadWorksModel roadWorksModel1 = new RoadWorksModel();
                                roadWorksModel1.setTitle(roadWorksModel.getTitle());
                                roadWorksModel1.setDescription(roadWorksModel.getDescription());
                                roadWorksModel1.setLink(roadWorksModel.getLink());
                                roadWorksModel1.setPubDate(roadWorksModel.getPubDate());
                                roadWorksModel1.setLocPoints(roadWorksModel.getLocPoints());
                                arrayList.add(roadWorksModel1);
                                itemTagStart = false;
                                break;
                        }
                    }
                    break;
            }
            eventType = xpp.next();
        }

        Log.v("size", String.valueOf(arrayList.size()));
        return arrayList;
    }
}