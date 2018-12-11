package ormtransaction.example.application.orm;

import android.support.annotation.NonNull;
import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;

@Table(name = "example", database = ORMAppDatabase.class)
public class ORMDBClass extends Model {


    @PrimaryKey
    private Long id;
    @Column(name = "key")
    private String key;
    @Column(name = "hash")
    private String hash;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "updated_at")
    private String updatedAt;

    //ReActiveAndroid requires empty constructor
    public ORMDBClass() {
    }

    public ORMDBClass(
    Long id,
    String key,
    String hash,
    String createdAt,
    String updatedAt
    ) {
        this.id = id;
        this.key = key;
        this.hash = hash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @NonNull
    @Override
    public Long save() {

        if (id == null) {
            createdAt = ORMDBHelpers.dateFormat.format(ORMDBHelpers.date);
        }

        updatedAt = ORMDBHelpers.dateFormat.format(ORMDBHelpers.date);
        return super.save();
    }

}