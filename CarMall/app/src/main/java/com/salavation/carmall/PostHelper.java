package com.salavation.carmall;

import android.content.Context;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class PostHelper {

    private final Context context;
    ArrayList<Post> postArrayList;


    public PostHelper(Context context) {
        this.context = context;
        postArrayList = new ArrayList<>();
    }

    public ArrayList<Post> getNextPosts() {
        Random random = new Random();
        ArrayList<Post> newPosts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int ran = random.nextInt() % 6;
            Post post = new Post();
            post.content = Post.POST_TEXT;
            post.price = currencyFormatter(getRandomPrice());

            switch (ran) {
                case 0:
                    post.title = "Renault Kwid";
                    post.imgUris.add(R.mipmap.kwid);
                    post.imgUris.add(R.mipmap.kwid_1);
                    post.imgUris.add(R.mipmap.kwid_2);
                    post.make = "Renault";
                    post.model = "Renault Kwid 2018";
                    post.location = "Hinjewadi,Pune";
                    break;

                case 1:
                    post.title = "Maruti Suzuki Swift";
                    post.imgUris.add(R.mipmap.swift);
                    post.imgUris.add(R.mipmap.swift_1);
                    post.imgUris.add(R.mipmap.swift_2);

                    post.make = "Maruti Suzuki";
                    post.model = "Maruti Suzuki Swift 2016";
                    post.location = "Dadar(W), Mumbai";
                    break;

                case 2:
                    post.title = "MS Swift Dezire";
                    post.imgUris.add(R.mipmap.swift);
                    post.imgUris.add(R.mipmap.swift_1);
                    post.imgUris.add(R.mipmap.swift_2);

                    post.make = "Maruti Suzuki";
                    post.model = "Maruti Suzuki Swift Dezire 2017";
                    post.location = "Vashi(E), Mumbai";
                    break;

                case 3:
                    post.title = "Tata Tiago";
                    post.imgUris.add(R.mipmap.tiago);
                    post.imgUris.add(R.mipmap.tiago_1);
                    post.imgUris.add(R.mipmap.tiago_2);

                    post.make = "Tata";
                    post.model = "Tata Tiago 2018";
                    post.location = "Shivaji Nagar, Kolhapur";
                    break;

                case 4:
                    post.title = "Mahindra Scorpio";
                    post.imgUris.add(R.mipmap.scorpio_1);
                    post.imgUris.add(R.mipmap.scorpio);
                    post.imgUris.add(R.mipmap.scorpio_2);

                    post.make = "Mahindra";
                    post.model = "Mahindra Scorpio 2015";
                    post.location = "Dombivali(E), Mumbai";
                    break;

                case 5:
                    post.title = "Honda Civic";
                    post.imgUris.add(R.mipmap.civic);
                    post.imgUris.add(R.mipmap.civic_1);
                    post.imgUris.add(R.mipmap.civic_2);


                    post.make = "Honda";
                    post.model = "Honda Civic GT 2019";
                    post.location = "Kothrud, Pune";
                    break;

                default:
                    post.title = "Honda Civic";
                    post.imgUris.add(R.mipmap.civic);
                    post.imgUris.add(R.mipmap.civic_1);
                    post.imgUris.add(R.mipmap.civic_2);


                    post.make = "Honda";
                    post.model = "Honda Civic GT 2019";
                    post.location = "Kothrud, Pune";
                    break;
            }

            newPosts.add(post);
        }

        return newPosts;
    }

    public static int getRandomPrice() {
        int min = 100000;
        int max = 1000000;

        double rand = Math.random();
        return (int) (rand * ((max - min) + 1)) + min;
    }

    public String currencyFormatter(int num) {
        double m = num;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(m);
    }
}
