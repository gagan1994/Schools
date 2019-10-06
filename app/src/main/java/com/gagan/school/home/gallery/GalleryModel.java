package com.gagan.school.home.gallery;

import com.gagan.school.library.view.adapter.RvItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gagan S Patil on 6/10/19.
 */
public class GalleryModel implements RvItems {
    private String imageUrl;

    public GalleryModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static List<GalleryModel> getGalleryList() {
        List<GalleryModel> list = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            String url;
            switch (i % 5) {
                case 0:
                    url = ("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 1:
                    url = ("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
                    break;
                case 2:
                    url = ("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 3:
                    url = ("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                default:
                    url = ("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
            }
            list.add(new GalleryModel(url));
        }
        return list;
    }

    public String url() {
        return imageUrl;
    }
}
