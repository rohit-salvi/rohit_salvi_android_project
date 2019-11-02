package com.salavation.carmall;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDAO {

    @Query("SELECT * FROM post")
    List<Post> getAll();

    @Insert
    void insert(Post post);

}
