package com.discut.pocket.dao;

import com.discut.pocket.bean.Tag;

import java.util.List;

public interface ITagDao {

    boolean insert(Tag tag);

    List<Tag> getAll();

    boolean delete(Tag tag);

    boolean update(Tag tag);

    boolean deleteTagsBy(String accountId);
}
