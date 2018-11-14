package com.mainstreetcode.teammate.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mainstreetcode.teammate.model.Event;
import com.mainstreetcode.teammate.model.Stat;
import com.mainstreetcode.teammate.persistence.entity.StatEntity;

import java.util.Date;
import java.util.List;

import io.reactivex.Maybe;

/**
 * DAO for {@link Event}
 */

@Dao
public abstract class StatDao extends EntityDao<StatEntity> {

    @Override
    protected String getTableName() {
        return "stats";
    }

    @Query("SELECT * FROM stats as stat" +
            " WHERE :game_id = stat_game" +
            " AND stat_created < :date" +
            " ORDER BY stat_created DESC" +
            " LIMIT :limit")
    public abstract Maybe<List<Stat>> getStats(String game_id, Date date, int limit);

    @Query("SELECT * FROM stats" +
            " WHERE :id = stat_id")
    public abstract Maybe<Stat> get(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<StatEntity> stats);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void update(List<StatEntity> stats);

    @Delete
    public abstract void delete(StatEntity stat);
}
